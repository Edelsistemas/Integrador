package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.services.integration.SQLServerService;
import com.edelflex.app.utils.ProcessInfo;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;

@Slf4j
@StepScope
public class SyncBaseProductReader implements ItemReader<Product> {

  private final SQLServerService sqlServerService;
  private Iterator<Product> data;
  private final String query;
  private final String tableName;
  private final Map<String, Object> fields;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    ProcessInfo processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    try {
      List<Product> data = sqlServerService.getProducts(query, tableName, fields);
      int totalItems = data.size();
      this.data = data.iterator();
      SyncItemsMetrics.registerReader(processInfo, totalItems);
    } catch (Exception exc) {
      log.error("READ ERROR", exc);
      SyncItemsMetrics.registerReaderError(processInfo, exc.getMessage());
    }
  }

  public SyncBaseProductReader(
      SQLServerService sqlServerService,
      String query,
      String tableName,
      Map<String, Object> fields) {
    this.sqlServerService = sqlServerService;
    this.query = query;
    this.tableName = tableName;
    this.fields = fields;
  }

  @Override
  public Product read() {
    if (data.hasNext()) {
      return data.next();
    }
    return null;
  }
}
