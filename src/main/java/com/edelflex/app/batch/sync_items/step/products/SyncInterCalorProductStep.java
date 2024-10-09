package com.edelflex.app.batch.sync_items.step.products;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.InstrumenProduct;
import com.edelflex.app.model.product.InterCalorProduct;
import com.edelflex.app.services.integration.SapItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
public class SyncInterCalorProductStep {

  @Bean("syncInterCalorProductStepDefinition")
  public Step syncInterCalorProductStep(
      StepBuilderFactory stepBuilderFactory,
      @Qualifier("interCalorReader") SyncBaseProductReader<InterCalorProduct> reader,
      @Qualifier("interCalorProcessor") SyncBaseProductProcessor<InterCalorProduct> processor,
      @Qualifier("interCalorWriter")SyncBaseProductWriter writer) {
    return stepBuilderFactory
        .get("Sync InterCalor Step")
        .<InterCalorProduct, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean("interCalorReader")
  public SyncBaseProductReader<InterCalorProduct> reader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.inter-calor.get}") String query) {
    return new SyncBaseProductReader<>(jdbcTemplate, query, InterCalorProduct.class);
  }

  @Bean("interCalorProcessor")
  public SyncBaseProductProcessor<InterCalorProduct> processor(SapItemService sapItemService) {
    return new SyncBaseProductProcessor<>(sapItemService);
  }

  @Bean("interCalorWriter")
  public SyncBaseProductWriter writer(
      MongoTemplate mongoTemplate,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.inter-calor.update}") String updateQuery) {
    return new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
  }
}
