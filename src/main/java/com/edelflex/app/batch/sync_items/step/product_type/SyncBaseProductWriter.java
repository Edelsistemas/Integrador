package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.utils.ProcessInfo;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class SyncBaseProductWriter implements ItemWriter<ProductProcessInfo> {

  protected final MongoTemplate mongoTemplate;
  private final JdbcTemplate jdbcTemplate;
  private final String updateQuery;

  public SyncBaseProductWriter(
      MongoTemplate mongoTemplate, JdbcTemplate jdbcTemplate, String updateQuery) {
    this.mongoTemplate = mongoTemplate;
    this.jdbcTemplate = jdbcTemplate;
    this.updateQuery = updateQuery;
  }

  protected ProcessInfo processInfo;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    this.processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
  }

  @Override
  public void write(List<? extends ProductProcessInfo> list) {
    process(list);
    long createCount =
        list.stream()
            .filter(
                item ->
                    item.getStatus().equals(ProductProcessInfo.Status.OK)
                        && item.getAction().equals(Product.Action.CREATE))
            .count();
    long updateCount =
        list.stream()
            .filter(
                item ->
                    item.getStatus().equals(ProductProcessInfo.Status.OK)
                        && item.getAction().equals(Product.Action.UPDATE))
            .count();
    long errorCount =
        list.stream()
            .filter(item -> item.getStatus().equals(ProductProcessInfo.Status.ERROR))
            .count();
    SyncItemsMetrics.registerWriter(processInfo, createCount, updateCount, errorCount);
  }

  private void process(List<? extends ProductProcessInfo> list) {

    // TODO: UPDATE

  }
}
