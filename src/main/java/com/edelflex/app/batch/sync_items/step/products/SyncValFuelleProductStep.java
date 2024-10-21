package com.edelflex.app.batch.sync_items.step.products;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.ValDiaProduct;
import com.edelflex.app.model.product.ValFuelleProduct;
import com.edelflex.app.services.integration.SapItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("sync-items-batch")
public class SyncValFuelleProductStep {

  @Bean("syncValFuelleProductStepDefinition")
  public Step syncValFuelleProductStep(
          StepBuilderFactory stepBuilderFactory,
          @Qualifier("valFuelleReader") SyncBaseProductReader<ValFuelleProduct> reader,
          @Qualifier("valFuelleProcessor") SyncBaseProductProcessor<ValFuelleProduct> processor,
          @Qualifier("valFuelleWriter") SyncBaseProductWriter writer) {
    return stepBuilderFactory
        .get("Sync ValFuelle Step")
        .<ValFuelleProduct, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean("valFuelleReader")
  public SyncBaseProductReader<ValFuelleProduct> reader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.val-fuelle.get}") String query) {
    return new SyncBaseProductReader<>(jdbcTemplate, query, ValFuelleProduct.class);
  }

  @Bean("valFuelleProcessor")
  public SyncBaseProductProcessor<ValFuelleProduct> processor(SapItemService sapItemService) {
    return new SyncBaseProductProcessor<>(sapItemService);
  }

  @Bean("valFuelleWriter")
  public SyncBaseProductWriter writer(
      MongoTemplate mongoTemplate,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.val-fuelle.update}") String updateQuery) {
    return new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
  }

  @Bean
  public Flow syncValFuelleProductStepFlow(Step syncValFuelleProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncValFuelleProductStepFlow")
            .start(syncValFuelleProductStepDefinition)
            .build();
  }
}
