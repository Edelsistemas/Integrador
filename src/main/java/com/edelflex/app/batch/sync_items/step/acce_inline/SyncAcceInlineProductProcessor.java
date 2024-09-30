package com.edelflex.app.batch.sync_items.step.product_type_inter_calor;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.batch.sync_items.step.product_type.GetBaseProductProcessor;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.InterCalorProduct;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.services.integration.SapItemService;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
@Profile("sync-items-batch")
public class GetInterCalorProductProcessor extends GetBaseProductProcessor<InterCalorProduct> {

  public GetInterCalorProductProcessor(SapItemService sapItemService) {
    super(sapItemService);
  }

  @Override
  public ProductProcessInfo process(InterCalorProduct productInfo) {
    long t1 = System.currentTimeMillis();
    String url = sapItemService.getUrl(productInfo.getAction(), productInfo.getProduct());
    SyncItemsMetrics.registerProcessStart(
        processInfo,
        url,
        productInfo.getProduct(),
        productInfo.getName(),
        productInfo.getAction().getLabel());
    Map<String, Object> request = productInfo.createRequest();
    ProductProcessInfo productProcessInfo;
    // CREATE
    if (productInfo.getAction().equals(Product.Action.CREATE)) {
      productProcessInfo = sapItemService.create(request);
    } else { // UPDATE
      productProcessInfo = sapItemService.update(productInfo.getProduct(), request);
    }
    long t2 = System.currentTimeMillis();
    SyncItemsMetrics.registerProcessEnd(processInfo, t1, t2);
    return productProcessInfo;
  }
}
