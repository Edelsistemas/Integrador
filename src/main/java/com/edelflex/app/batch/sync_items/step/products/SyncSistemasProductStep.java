package com.edelflex.app.batch.sync_items.step.products;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.SisRecProdProduct;
import com.edelflex.app.model.product.SistemasProduct;
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
public class SyncSistemasProductStep {

  @Bean("syncSistemasProductStepDefinition")
  public Step syncSistemasProductStep(
      StepBuilderFactory stepBuilderFactory,
      @Qualifier("sistemasReader") SyncBaseProductReader<SistemasProduct> reader,
      @Qualifier("sistemasProcessor") SyncBaseProductProcessor<SistemasProduct> processor,
      @Qualifier("sistemasWriter") SyncBaseProductWriter writer) {
    return stepBuilderFactory
        .get("Sync Sistemas Step")
        .<SistemasProduct, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean("sistemasReader")
  public SyncBaseProductReader<SistemasProduct> reader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.sistemas.get}") String query) {
    return new SyncBaseProductReader<>(jdbcTemplate, query, SistemasProduct.class);
  }

  @Bean("sistemasProcessor")
  public SyncBaseProductProcessor<SistemasProduct> processor(SapItemService sapItemService) {
    return new SyncBaseProductProcessor<>(sapItemService);
  }

  @Bean("sistemasWriter")
  public SyncBaseProductWriter writer(
      MongoTemplate mongoTemplate,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.sistemas.update}") String updateQuery) {
    return new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
  }

  @Bean
  public Flow syncSistemasProductStepFlow(Step syncSistemasProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncSistemasProductStepFlow")
            .start(syncSistemasProductStepDefinition)
            .build();
  }
}
