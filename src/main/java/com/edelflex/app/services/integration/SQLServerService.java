package com.edelflex.app.services.integration;

import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.model.sap.SapLoginRequest;
import com.edelflex.app.utils.Utils;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class SQLServerService {

  private JdbcTemplate jdbcTemplate;

  public SQLServerService(@Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Product> getProducts(String query, String tableName, Map<String, Object> fields) {
    String targetQuery = createQuery(query, tableName, fields);
    return jdbcTemplate
        .query(
            targetQuery,
            (rs, i) -> {
              try {
                Product item = Product.builder().build();
                item.setProductType(tableName);
                populateItem(item, rs);
                return item;
              } catch (Exception e) {
                log.error("ERROR QUERY: " + targetQuery, e);
              }
              return null;
            })
        .stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private void populateItem(Product item, ResultSet rs) throws SQLException {
    PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(item);
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
      String columnName = getFieldName(rs.getMetaData().getColumnName(i));
      int type = rs.getMetaData().getColumnType(i);
      if (Types.NVARCHAR == type) {
        myAccessor.setPropertyValue(columnName, rs.getString(i));
      } else if (Types.INTEGER == type) {
        myAccessor.setPropertyValue(columnName, rs.getLong(i));
      } else if (Types.VARCHAR == type) {
        myAccessor.setPropertyValue(columnName, rs.getString(i));
      }
    }
  }

  private String createQuery(String query, String tableName, Map<String, Object> fields) {
    String queryFields =
        fields.entrySet().stream()
            .filter(
                entry -> {
                  if (entry.getValue() instanceof String) {
                    return Utils.isNotEmpty((String) entry.getValue());
                  } else if (entry.getValue() instanceof Number) {
                    return ((Number) entry.getValue()).longValue() > 0;
                  }
                  return false;
                })
            .map(
                field -> {
                  if (field.getKey().startsWith("FIXED_")) {
                    if (field.getValue() instanceof String) {
                      return String.format(
                          "'%s' AS %s", field.getValue(), field.getKey().replaceAll("FIXED_", ""));
                    } else {
                      return String.format(
                          "%s AS %s", field.getValue(), field.getKey().replaceAll("FIXED_", ""));
                    }
                  } else if (field.getKey().startsWith("CLAUSE_")) {
                    return String.format(
                        "%s AS %s", field.getValue(), field.getKey().replaceAll("CLAUSE_", ""));
                  } else {
                    return String.format("\"%s\" AS %s", field.getValue(), field.getKey());
                  }
                })
            .collect(Collectors.joining(", "));
    return query.replaceAll("FIELDS", queryFields).replaceAll("TABLE", tableName);
  }

  private String getFieldName(String rawFieldName) {
    String[] tokens = rawFieldName.split("_");
    if (tokens.length > 1) {
      return tokens[0].toLowerCase()
          + IntStream.range(1, tokens.length)
              .mapToObj(i -> StringUtils.capitalize(tokens[i].toLowerCase()))
              .collect(Collectors.joining());
    } else if (tokens.length == 1) {
      return tokens[0].toLowerCase();
    }
    return "";
  }

  @Transactional
  public void updateProducts(
      List<? extends ProductProcessInfo> list, String updateQuery, String jobId) {
    list.forEach(
        productProcessInfo ->
            jdbcTemplate.update(
                updateQuery,
                productProcessInfo.getStatus().name(),
                new Date(),
                productProcessInfo.getResponseData(),
                jobId,
                productProcessInfo.getRecordId()));
  }
}
