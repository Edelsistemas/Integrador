package com.edelflex.app.batch.sync_items.step.product_type_inter_calor;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.exceptions.SapCallException;

import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.InterCalorProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("sync-items-batch")
public class GetInterCalorProductStep {

  @Bean("getInterCalorProductStepDefinition")
  public Step getInterCalorProductStep(
      StepBuilderFactory stepBuilderFactory,
      GetInterCalorProductReader getInterCalorProductReader,
      GetInterCalorProductWriter getInterCalorProductWriter,
      GetInterCalorProductProcessor getInterCalorProductProcessor) {
    return stepBuilderFactory
        .get("GetInterCalorProductStep")
        .<InterCalorProduct, ProductProcessInfo>chunk(100)
        .reader(getInterCalorProductReader)
        .processor(getInterCalorProductProcessor)
        .writer(getInterCalorProductWriter)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }
}
