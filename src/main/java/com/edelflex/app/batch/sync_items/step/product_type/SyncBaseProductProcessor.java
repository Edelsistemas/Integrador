package com.edelflex.app.batch.sync_items.step.product_type;

import com.edelflex.app.batch.sync_items.SyncItemsMetrics;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.services.integration.SapItemService;
import com.edelflex.app.utils.ProcessInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("sync-bp-batch")
public abstract class GetBaseProductProcessor<T> implements ItemProcessor<T, ProductProcessInfo> {

  protected final SapItemService sapItemService;

  public GetBaseProductProcessor(SapItemService sapItemService) {
    this.sapItemService = sapItemService;
  }

  protected ProcessInfo processInfo;

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    this.processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
  }

  @Override
  public abstract ProductProcessInfo process(T productInfo);
}
