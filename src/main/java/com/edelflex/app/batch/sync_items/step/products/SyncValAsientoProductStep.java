package com.edelflex.app.batch.sync_items.step.products;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.ValAliSegProduct;
import com.edelflex.app.model.product.ValAsientoProduct;
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
public class SyncValAsientoProductStep {

  @Bean("syncValAsientoProductStepDefinition")
  public Step syncValAsientoProductStep(
      StepBuilderFactory stepBuilderFactory,
      @Qualifier("valAsientoReader") SyncBaseProductReader<ValAsientoProduct> reader,
      @Qualifier("valAsientoProcessor") SyncBaseProductProcessor<ValAsientoProduct> processor,
      @Qualifier("valAsientoWriter") SyncBaseProductWriter writer) {
    return stepBuilderFactory
        .get("Sync ValAsiento Step")
        .<ValAsientoProduct, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean("valAsientoReader")
  public SyncBaseProductReader<ValAsientoProduct> reader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.val-asiento.get}") String query) {
    return new SyncBaseProductReader<>(jdbcTemplate, query, ValAsientoProduct.class);
  }

  @Bean("valAsientoProcessor")
  public SyncBaseProductProcessor<ValAsientoProduct> processor(SapItemService sapItemService) {
    return new SyncBaseProductProcessor<>(sapItemService);
  }

  @Bean("valAsientoWriter")
  public SyncBaseProductWriter writer(
      MongoTemplate mongoTemplate,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.val-asiento.update}") String updateQuery) {
    return new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
  }
}
