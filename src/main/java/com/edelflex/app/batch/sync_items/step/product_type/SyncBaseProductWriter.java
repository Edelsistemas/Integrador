package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsConfig;
import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.utils.ProcessInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
public class SyncBaseProductWriter implements ItemWriter<ProductProcessInfo> {

  protected final MongoTemplate mongoTemplate;
  private final JdbcTemplate jdbcTemplate;
  private final String updateQuery;
  private String jobId;

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
    this.jobId =
        stepExecution.getJobParameters().getString(SyncItemsConfig.PARAM_PROCESS_IDENTIFIER);
  }

  @Override
  public void write(List<? extends ProductProcessInfo> list) {
    //process(list);
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

    BulkOperations bulkOperations =
        mongoTemplate.bulkOps(
            BulkOperations.BulkMode.UNORDERED, Map.class, SyncItemsConfig.ITEMS_HISTORY_COLLECTION);
    bulkOperations.insert(list).execute();
  }

  private void process(List<? extends ProductProcessInfo> list) {
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
