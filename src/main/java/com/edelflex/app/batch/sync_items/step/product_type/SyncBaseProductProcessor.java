package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsConfig;
import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.services.integration.SapItemService;
import com.edelflex.app.utils.ProcessInfo;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class SyncBaseProductProcessor<T extends Product>
    implements ItemProcessor<T, ProductProcessInfo> {

  protected final SapItemService sapItemService;
  private String jobId;

  public SyncBaseProductProcessor(SapItemService sapItemService) {
    this.sapItemService = sapItemService;
  }

  protected ProcessInfo processInfo;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    this.processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
    this.jobId =
        stepExecution.getJobParameters().getString(SyncItemsConfig.PARAM_PROCESS_IDENTIFIER);
  }

  @Override
  public ProductProcessInfo process(T productInfo) {
    long t1 = System.currentTimeMillis();
    String url = sapItemService.getUrl(productInfo.getAction(), productInfo.getProduct());
    SyncItemsMetrics.registerProcessStart(
        processInfo,
        url,
        productInfo.getProduct(),
        productInfo.getName(),
        productInfo.getAction().getLabel());
    Map<String, Object> request = productInfo.createRequest();
    ProductProcessInfo productProcessInfo = ProductProcessInfo.builder().build();
    // CREATE
    if (productInfo.getAction().equals(Product.Action.CREATE)) {
     // productProcessInfo =
    //      sapItemService.create(productInfo.getId(), productInfo.getProduct(), request);
    } else { // UPDATE
    //  productProcessInfo =
    //      sapItemService.update(productInfo.getId(), productInfo.getProduct(), request);
    }
    long t2 = System.currentTimeMillis();
    SyncItemsMetrics.registerProcessEnd(processInfo, t1, t2);
    productProcessInfo.setJobId(jobId);
    return productProcessInfo;
  }
}
