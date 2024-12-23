package com.edelflex.app.batch.sync_items;

import com.edelflex.app.batch.BatchExecutionListener;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductProcessor;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductReader;
import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.config.ItemProcessConfigProperties;
import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import com.edelflex.app.services.integration.ProcessService;
import com.edelflex.app.services.integration.SQLServerService;
import com.edelflex.app.services.integration.SapItemService;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
@Profile("sync-items-batch")
public class SyncItemsConfig {

  public static final String SYNC_ITEMS = "SYNC_ITEMS";
  public static final String ITEMS_HISTORY_COLLECTION = "items-process-history";

  @Bean
  @Scope("prototype")
  public Job syncItems(
      PlatformTransactionManager transactionManager,
      MongoTemplate mongoTemplate,
      ItemProcessConfigProperties itemProcessConfigProperties,
      SQLServerService sqlServerService,
      SapItemService sapItemService,
      ProcessService processService,
      JobRepository jobRepository) {

    Flow[] flows =
        itemProcessConfigProperties.getConfigs().stream()
            .map(
                config -> {
                  try {
                    return new FlowBuilder<SimpleFlow>("syncProductStepFlow")
                        .start(
                            syncProductStep(
                                transactionManager,
                                mongoTemplate,
                                sqlServerService,
                                itemProcessConfigProperties.getGet(),
                                config.getTable(),
                                config.getFields(),
                                itemProcessConfigProperties.getUpdate(),
                                sapItemService,
                                jobRepository))
                        .build();
                  } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                  }
                })
            .toArray(Flow[]::new);

    return new JobBuilder(SYNC_ITEMS, jobRepository)
        .start(
            new FlowBuilder<SimpleFlow>("getFlow").split(getFlowTaskExecutor()).add(flows).build())
        .build()
        .preventRestart()
        .listener(new BatchExecutionListener(processService))
        .build();
  }

  public Step syncProductStep(
      PlatformTransactionManager transactionManager,
      MongoTemplate mongoTemplate,
      SQLServerService sqlServerService,
      String query,
      String tableName,
      Map<String, Object> fields,
      String updateQuery,
      SapItemService sapItemService,
      JobRepository jobRepository) {
    SyncBaseProductReader reader =
        new SyncBaseProductReader(sqlServerService, query, tableName, fields);
    SyncBaseProductProcessor processor = new SyncBaseProductProcessor(sapItemService);
    SyncBaseProductWriter writer =
        new SyncBaseProductWriter(
            mongoTemplate, sqlServerService, updateQuery.replaceAll("TABLE", tableName));
    return new StepBuilder("Sync " + tableName + " Step", jobRepository)
        .<Product, ProductProcessInfo>chunk(100, transactionManager)
        .reader(reader)
        .processor(processor)
        .writer(writer)
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  @Bean
  TaskExecutor getFlowTaskExecutor() {
    return new SimpleAsyncTaskExecutor("GetFlowTaskExecutor");
  }

  @Bean
  JobLauncher jobLauncherSyncItems(JobRepository jobRepository) throws Exception {
    TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }
}
