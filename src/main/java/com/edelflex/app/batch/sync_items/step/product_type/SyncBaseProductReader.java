package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.utils.ProcessInfo;
import com.edelflex.app.utils.Utils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@StepScope
public class SyncBaseProductReader implements ItemReader<Product> {

  private final Iterator<Product> data;
  private final int totalItems;
  private final String query;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    ProcessInfo processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    SyncItemsMetrics.registerReader(processInfo, totalItems, query);
  }

  public SyncBaseProductReader(
      JdbcTemplate jdbcTemplate, String query, String tableName, Map<String, Object> fields) {
    String targetQuery = createQuery(query, tableName, fields);
    List<Product> data =
        jdbcTemplate
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
    this.totalItems = data.size();
    this.query = targetQuery;
    this.data = data.iterator();
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

  @Override
  public Product read() {
    if (data.hasNext()) {
      return data.next();
    }
    return null;
  }
}
