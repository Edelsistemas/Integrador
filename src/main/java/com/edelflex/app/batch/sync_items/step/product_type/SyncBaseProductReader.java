package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.utils.ProcessInfo;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class SyncBaseProductReader implements ItemReader<Product> {

  private final Iterator<Product> data;
  private int totalItems;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    ProcessInfo processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    SyncItemsMetrics.registerReader(processInfo, totalItems);
  }

  public SyncBaseProductReader(
      JdbcTemplate jdbcTemplate, String query, String tableName, Map<String, Object> fields)
      throws Exception {
    String targetQuery = createQuery(query, tableName, fields);
    List<Product> data =
        jdbcTemplate
            .query(
                targetQuery,
                (rs, i) -> {
                  try {
                    Product item = Product.builder().build();
                    populateItem(item, rs);
                    return item;
                  } catch (Exception e) {
                    log.error("ERROR QUERY: " + targetQuery);
                    log.error(e.getCause().getMessage());
                  }
                  return null;
                })
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    this.totalItems = data.size();
      this.data = data.iterator();
  }

    private void populateItem(Product item, ResultSet rs) throws SQLException {
    PropertyAccessor myAccessor = PropertyAccessorFactory.forBeanPropertyAccess(item);
    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
      String columnName = rs.getMetaData().getColumnName(i);
      int type = rs.getMetaData().getColumnType(i);
      if (Types.NVARCHAR == type) {
        myAccessor.setPropertyValue(columnName.toLowerCase(), rs.getString(i));
      } else if (Types.INTEGER == type) {
        myAccessor.setPropertyValue(columnName.toLowerCase(), rs.getLong(i));
      }
    }
  }

  private String createQuery(String query, String tableName, Map<String, Object> fields) {
    String queryFields =
        fields.entrySet().stream()
            .map(
                field -> {
                  if (field.getKey().startsWith("FIXED_")) {
                    if (field.getValue() instanceof String) {
                      return String.format("'%s' AS %s", field.getValue(), field.getKey().replaceAll("FIXED_", ""));
                    } else {
                      return String.format("%s AS %s", field.getValue(), field.getKey().replaceAll("FIXED_", ""));
                    }
                  } else if (field.getKey().startsWith("CLAUSE_")) {
                      return String.format("%s AS %s", field.getValue(), field.getKey().replaceAll("CLAUSE_", ""));
                  } else {
                    return String.format("\"%s\" AS %s", field.getValue(), field.getKey());
                  }


                })
            .collect(Collectors.joining(", "));
    return query.replaceAll("FIELDS", queryFields).replaceAll("TABLE", tableName);
  }

  public SyncBaseProductReader(
      JdbcTemplate jdbcTemplate, String query, Class<? extends Product> clazz) {
    List<Product> data =
        jdbcTemplate
            .query(
                query,
                (rs, i) -> {
                  try {
                    return (Product)
                        clazz.getDeclaredMethod("create", ResultSet.class).invoke(null, rs);
                  } catch (Exception e) {
                    log.error("ERROR QUERY: " + query);
                    log.error(e.getCause().getMessage());
                  }
                  return null;
                })
            .stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    this.totalItems = data.size();
    this.data = data.iterator();
  }

  @Override
  public Product read() {
    if (data.hasNext()) {
      return data.next();
    }
    return null;
  }
}
