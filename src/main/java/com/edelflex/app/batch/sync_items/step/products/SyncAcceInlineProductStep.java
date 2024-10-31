package com.edelflex.app.batch.sync_items.step.products;

import com.edelflex.app.batch.sync_items.SyncItemsExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.AcceInlineProduct;
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

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Profile("sync-items-batch")
public class SyncAcceInlineProductStep {

  @Bean("syncAcceInlineProductStepDefinition")
  public Step syncAcceInlineProductStep(
      StepBuilderFactory stepBuilderFactory,
      @Qualifier("acceInlineReader") SyncBaseProductReader<AcceInlineProduct> reader,
      @Qualifier("acceInlineProcessor") SyncBaseProductProcessor<AcceInlineProduct> processor,
      @Qualifier("acceInlineWriter") SyncBaseProductWriter writer) {
    return stepBuilderFactory
        .get("Sync AcceInline Step")
        .<AcceInlineProduct, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean("acceInlineReader")
  public SyncBaseProductReader<AcceInlineProduct> reader(
          @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
          @Value("${team-center.querys.acce-inline.get}") String query,
          @Value("#{${team-center.querys.acce-inline.fields}}") Map<String, String> fields) {
    return new SyncBaseProductReader<>(jdbcTemplate, query, fields, AcceInlineProduct.class);
  }

  @Bean("acceInlineProcessor")
  public SyncBaseProductProcessor<AcceInlineProduct> processor(SapItemService sapItemService) {
    return new SyncBaseProductProcessor<>(sapItemService);
  }

  @Bean("acceInlineWriter")
  public SyncBaseProductWriter writer(
      MongoTemplate mongoTemplate,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      @Value("${team-center.querys.acce-inline.update}") String updateQuery) {
    return new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
  }


  @Bean
  public Flow syncAcceInlineProductStepFlow(Step syncAcceInlineProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncAcceInlineProductStepFlow")
            .start(syncAcceInlineProductStepDefinition)
            .build();
  }

}
