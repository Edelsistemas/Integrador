package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsConfig;
import com.edelflex.app.batch.sync_items.SyncItemsLauncher;
import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.services.integration.SQLServerService;
import com.edelflex.app.utils.ProcessInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

@Slf4j
@StepScope
public class SyncBaseProductWriter implements ItemWriter<ProductProcessInfo> {

  protected final MongoTemplate mongoTemplate;
  private final SQLServerService sqlServerService;
  private final String updateQuery;
  private String jobId;

  public SyncBaseProductWriter(
      MongoTemplate mongoTemplate, SQLServerService sqlServerService, String updateQuery) {
    this.mongoTemplate = mongoTemplate;
    this.sqlServerService = sqlServerService;
    this.updateQuery = updateQuery;
  }

  protected ProcessInfo processInfo;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    this.processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    this.jobId =
        stepExecution.getJobParameters().getString(SyncItemsLauncher.PARAM_PROCESS_IDENTIFIER);
  }

  @Override
  public void write(Chunk<? extends ProductProcessInfo> list) {
    try{
    process(list.getItems());
    long createCount =
        list.getItems().stream()
            .filter(
                item ->
                    item.getStatus().equals(ProductProcessInfo.Status.OK)
                        && item.getAction().equals(Product.Action.CREATE))
            .count();
    long updateCount =
        list.getItems().stream()
            .filter(
                item ->
                    item.getStatus().equals(ProductProcessInfo.Status.OK)
                        && item.getAction().equals(Product.Action.UPDATE))
            .count();
    long errorCount =
        list.getItems().stream()
            .filter(item -> item.getStatus().equals(ProductProcessInfo.Status.ERROR))
            .count();
    SyncItemsMetrics.registerWriter(processInfo, createCount, updateCount, errorCount);

    BulkOperations bulkOperations =
        mongoTemplate.bulkOps(
            BulkOperations.BulkMode.UNORDERED, Map.class, SyncItemsConfig.ITEMS_HISTORY_COLLECTION);
    bulkOperations.insert(list.getItems()).execute();
  } catch (Exception e) {
    log.error("WRITE ERROR", e);
    SyncItemsMetrics.registerWriterError(processInfo, e.getMessage());
  }
  }

  private void process(List<? extends ProductProcessInfo> list) throws Exception {
    String info =
        list.stream()
            .map(productProcessInfo -> String.valueOf(productProcessInfo.getRecordId()))
            .collect(Collectors.joining(","));
    processInfo.addMetric("TO_UPDATE", info);
    sqlServerService.updateProducts(list, updateQuery, jobId);
  }
}
