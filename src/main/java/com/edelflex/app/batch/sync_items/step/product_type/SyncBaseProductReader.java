package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.services.integration.SQLServerService;
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

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    ProcessInfo processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    SyncItemsMetrics.registerReader(processInfo, totalItems);
  }

  public SyncBaseProductReader(
      SQLServerService sqlServerService,
      String query,
      String tableName,
      Map<String, Object> fields) {
    List<Product> data = sqlServerService.getProducts(query, tableName, fields);
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
