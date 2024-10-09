package com.edelflex.app.batch.sync_items.step.products;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.OtrasBomProduct;
import com.edelflex.app.model.product.OtrasValProduct;
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
public class SyncOtrasValProductStep {

  @Bean("syncOtrasValProductStepDefinition")
  public Step syncOtrasValProductStep(
      StepBuilderFactory stepBuilderFactory,
      @Qualifier("otrasValReader") SyncBaseProductReader<OtrasValProduct> reader,
      @Qualifier("otrasValProcessor")SyncBaseProductProcessor<OtrasValProduct> processor,
      @Qualifier("otrasValWriter") SyncBaseProductWriter writer) {
    return stepBuilderFactory
        .get("Sync OtrasVal Step")
        .<OtrasValProduct, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean("otrasValReader")
  public SyncBaseProductReader<OtrasValProduct> reader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.otras-val.get}") String query) {
    return new SyncBaseProductReader<>(jdbcTemplate, query, OtrasValProduct.class);
  }

  @Bean("otrasValProcessor")
  public SyncBaseProductProcessor<OtrasValProduct> processor(SapItemService sapItemService) {
    return new SyncBaseProductProcessor<>(sapItemService);
  }

  @Bean("otrasValWriter")
  public SyncBaseProductWriter writer(
      MongoTemplate mongoTemplate,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.otras-val.update}") String updateQuery) {
    return new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
  }
}
