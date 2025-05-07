package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsLauncher;
import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.services.integration.SapItemService;
import com.edelflex.app.utils.ProcessInfo;

import java.util.Date;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
@StepScope
public class SyncBaseProductProcessor implements ItemProcessor<Product, ProductProcessInfo> {

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
        stepExecution.getJobParameters().getString(SyncItemsLauncher.PARAM_PROCESS_IDENTIFIER);
  }

  @Override
  public ProductProcessInfo process(Product productInfo) throws SapCallException {

    if (sapItemService.existItem(productInfo.getProduct())) {
      productInfo.setAction(Product.Action.UPDATE);
      Map<String, Object> itemData = sapItemService.getItem(productInfo.getProduct());
      productInfo.setOrigin(itemData);
      productInfo.evaluateInactivo(itemData);
    } else {
      productInfo.setAction(Product.Action.CREATE);
      productInfo.setInactivo(true);
    }

    String url = sapItemService.getUrl(productInfo.getAction(), productInfo.getProduct());

    long t1 = System.currentTimeMillis();
    SyncItemsMetrics.registerProcessStart(
        processInfo,
        url,
        productInfo.getProduct(),
        productInfo.getName(),
        productInfo.getAction().getLabel());

    Map<String, Object> request = productInfo.createRequest();
    ProductProcessInfo productProcessInfo;

    if (productInfo.getAction().equals(Product.Action.CREATE)) {
      // CREATE
      productProcessInfo =
          sapItemService.create(productInfo.getId(), productInfo.getProduct(), request);
    } else {
      // UPDATE
      productProcessInfo =
          sapItemService.update(productInfo.getId(), productInfo.getProduct(), request);
      productProcessInfo.setSapData(productInfo.getOrigin());
    }
    productProcessInfo.setProductType(productInfo.getProductType());
    long t2 = System.currentTimeMillis();
    SyncItemsMetrics.registerProcessEnd(processInfo, t1, t2);
    productProcessInfo.setJobId(jobId);
    productProcessInfo.setDate(new Date());
    return productProcessInfo;
  }
}
