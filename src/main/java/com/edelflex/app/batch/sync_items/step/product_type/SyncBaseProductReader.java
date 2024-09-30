package com.edelflex.app.batch.sync_items.step.product_type;

import java.util.Iterator;
import java.util.List;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.utils.ProcessInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Slf4j
@Profile("sync-bp-batch")
public abstract class GetBaseProductReader<T> implements ItemReader<T> {

  private final Iterator<T> data;
  private int totalItems;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
     ProcessInfo processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    SyncItemsMetrics.registerReader(processInfo, totalItems);
  }

  public GetBaseProductReader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      RowMapper<T> rowMapper,
      String query) {
    List<T> data = jdbcTemplate.query(query, rowMapper);
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
