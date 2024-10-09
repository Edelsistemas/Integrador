package com.edelflex.app.batch.sync_items.step.products;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.BomDesPosProduct;
import com.edelflex.app.model.product.BombaCenProduct;
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
public class SyncBombaCenProductStep {

  @Bean("syncBombaCenProductStepDefinition")
  public Step syncBomDesPosProductStep(
      StepBuilderFactory stepBuilderFactory,
      @Qualifier("bombaCenReader") SyncBaseProductReader<BombaCenProduct> reader,
      @Qualifier("bombaCenProcessor")SyncBaseProductProcessor<BombaCenProduct> processor,
      @Qualifier("bombaCenWriter")SyncBaseProductWriter writer) {
    return stepBuilderFactory
        .get("Sync BombaCen Step")
        .<BombaCenProduct, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean("bombaCenReader")
  public SyncBaseProductReader<BombaCenProduct> reader(
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.bomba-cen.get}") String query) {
    return new SyncBaseProductReader<>(jdbcTemplate, query, BombaCenProduct.class);
  }

  @Bean("bombaCenProcessor")
  public SyncBaseProductProcessor<BombaCenProduct> processor(SapItemService sapItemService) {
    return new SyncBaseProductProcessor<>(sapItemService);
  }

  @Bean("bombaCenWriter")
  public SyncBaseProductWriter writer(
      MongoTemplate mongoTemplate,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.bomba-cen.update}") String updateQuery) {
    return new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
  }
}
