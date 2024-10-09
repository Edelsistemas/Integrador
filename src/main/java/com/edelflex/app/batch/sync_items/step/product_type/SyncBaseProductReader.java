package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.utils.ProcessInfo;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class SyncBaseProductReader<T extends Product> implements ItemReader<T> {

  private final Iterator<T> data;
  private int totalItems;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    ProcessInfo processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    SyncItemsMetrics.registerReader(processInfo, totalItems);
  }

  public SyncBaseProductReader(
      JdbcTemplate jdbcTemplate, String query, Class<? extends Product> clazz) {
    List<T> data =
        jdbcTemplate
            .query(
                query,
                (rs, i) -> {
                  try {
                    return (T) clazz.getDeclaredMethod("create", ResultSet.class).invoke(null, rs);
                  } catch (Exception e) {
                    e.printStackTrace();
                  }
                  return null;
                })
            .stream()
            .filter(Objects::nonNull)
            .map(p -> (T) p)
            .collect(Collectors.toList());
    this.totalItems = data.size();
    this.data = data.iterator();
  }

  @Override
  public T read() {
    if (data.hasNext()) {
      return data.next();
    }
    return null;
  }
}
