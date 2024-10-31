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
import com.edelflex.app.services.integration.SapItemService;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@Slf4j
@Profile("sync-items-batch")
public class SyncItemsConfig {

  public static final String PARAM_PROCESS_IDENTIFIER = "process-identifier";
  public static final String SYNC_ITEMS = "SYNC_ITEMS";
  public static final String ITEMS_HISTORY_COLLECTION = "items-process-history";

  @Autowired
  @Qualifier("jobLauncherSyncItems")
  private JobLauncher jobLauncherSyncItems;

  @Autowired
  @Qualifier("syncItems")
  private Job syncItems;

  private JobExecution execution;

  @Scheduled(fixedDelayString = "${batch-process.sync-business-partners}")
  public void schedule() {
    if (execution == null || !execution.isRunning()) {
      try {
        String jobId = String.valueOf(new Date().getTime());
        execution =
            jobLauncherSyncItems.run(
                syncItems,
                new JobParametersBuilder()
                    .addString(PARAM_PROCESS_IDENTIFIER, jobId)
                    .toJobParameters());
      } catch (Exception e) {
        log.error("Error al lanzar proceso", e);
      } finally {
        MDC.clear();
      }
    }
  }

  @Bean
  public Job syncItems(
      JobBuilderFactory jobBuilderFactory,
      StepBuilderFactory stepBuilderFactory,
      MongoTemplate mongoTemplate,
      ItemProcessConfigProperties itemProcessConfigProperties,
      @Qualifier("jdbcTemplateSQLServer") JdbcTemplate jdbcTemplate,
      SapItemService sapItemService,
      ProcessService processService) {

    Flow[] flows =
        itemProcessConfigProperties.getConfigs().stream()
            .map(
                config -> {
                  try {
                    return new FlowBuilder<SimpleFlow>("syncProductStepFlow")
                        .start(
                            syncProductStep(
                                stepBuilderFactory,
                                mongoTemplate,
                                jdbcTemplate,
                                itemProcessConfigProperties.getGet(),
                                config.getTable(),
                                config.getFields(),
                                itemProcessConfigProperties.getUpdate(),
                                sapItemService))
                        .build();
                  } catch (Exception e) {
                      e.printStackTrace();
                    throw new RuntimeException(e);
                  }
                })
            .toArray(Flow[]::new);

    return jobBuilderFactory
        .get(SYNC_ITEMS)
        .start(
            new FlowBuilder<SimpleFlow>("getFlow").split(getFlowTaskExecutor()).add(flows).build())
        .build()
        .preventRestart()
        .listener(new BatchExecutionListener(processService))
        .build();
  }

  public Step syncProductStep(
      StepBuilderFactory stepBuilderFactory,
      MongoTemplate mongoTemplate,
      JdbcTemplate jdbcTemplate,
      String query,
      String tableName,
      Map<String, Object> fields,
      String updateQuery,
      SapItemService sapItemService)
      throws Exception {
    SyncBaseProductReader reader =
        new SyncBaseProductReader(jdbcTemplate, query, tableName, fields);
    SyncBaseProductProcessor processor = new SyncBaseProductProcessor(sapItemService);
    SyncBaseProductWriter writer =
        new SyncBaseProductWriter(mongoTemplate, jdbcTemplate, updateQuery);
    return stepBuilderFactory
        .get("Sync " + tableName + " Step")
        .<Product, ProductProcessInfo>chunk(100)
        .reader(reader)
        .processor(processor)
        .writer(new ItemStreamWriter<ProductProcessInfo>() {
          @Override
          public void open(ExecutionContext executionContext) throws ItemStreamException {

          }

          @Override
          public void update(ExecutionContext executionContext) throws ItemStreamException {

          }

          @Override
          public void close() throws ItemStreamException {

          }

          @Override
          public void write(List<? extends ProductProcessInfo> list) throws Exception {

          }
        })
        .faultTolerant()
        .retryLimit(10)
        .retry(SapCallException.class)
        .listener(new SyncItemsExecutionListener())
        .build();
  }

  /*
  @Bean
  public Flow splitFlow(Flow syncAcceInlineProductStepFlow) {
    return new FlowBuilder<SimpleFlow>("getFlow")
        .split(getFlowTaskExecutor())
        .add(new FlowBuilder<SimpleFlow>("syncProductStepFlow")
                .start(syncProductStepFlowDefinition)
                .build())
        .build();
  }

  @Bean
  public Flow syncAcceInlineProductStepFlow(Step syncProductStep) {
    return new FlowBuilder<SimpleFlow>("syncProductStepFlow")
            .start(syncProductStep)
            .build();
  }*/

  /*
  @Bean
  public Flow splitFlow(
      Flow syncAcceInlineProductStepFlow,
      Flow syncBomDesPosProductStepFlow,
      Flow syncBombaCenProductStepFlow,
      Flow syncDispLimpiProductStepFlow,
      Flow syncHomogenProductStepFlow,
      Flow syncInstrIndProductStepFlow,
      Flow syncInstrumenProductStepFlow,
      Flow syncInterCalorProductStepFlow,
      Flow syncMatPriProductStepFlow,
      Flow syncOtrasBomProductStepFlow,
      Flow syncOtrasValProductStepFlow,
      Flow syncOtrosCompProductStepFlow,
      Flow syncPiezaProductStepFlow,
      Flow syncRepuestosProductStepFlow,
      Flow syncRestoInterProductStepFlow,
      Flow syncSemielaboProductStepFlow,
      Flow syncSisCalEHProductStepFlow,
      Flow syncSisLimECProductStepFlow,
      Flow syncSisMezEMProductStepFlow,
      Flow syncSisRecProdProductStepFlow,
      Flow syncSistemasProductStepFlow,
      Flow syncSubconProductStepFlow,
      Flow syncTanquesProductStepFlow,
      Flow syncValAliSegProductStepFlow,
      Flow syncValAsientoProductStepFlow,
      Flow syncValControlProductStepFlow,
      Flow syncValDiaProductStepFlow,
      Flow syncValFuelleProductStepFlow,
      Flow syncValRetenProductStepFlow,
      Flow syncValvulasProductStepFlow,
      Flow syncENAProductStepFlow) {
    return new FlowBuilder<SimpleFlow>("getFlow")
        .split(getFlowTaskExecutor())
        .add(
            syncAcceInlineProductStepFlow,
            syncBomDesPosProductStepFlow,
            syncBombaCenProductStepFlow,
            syncDispLimpiProductStepFlow,
            syncHomogenProductStepFlow,
            syncInstrIndProductStepFlow,
            syncInstrumenProductStepFlow,
            syncInterCalorProductStepFlow,
            syncMatPriProductStepFlow,
            syncOtrasBomProductStepFlow,
            syncOtrasValProductStepFlow,
            syncOtrosCompProductStepFlow,
            syncPiezaProductStepFlow,
            syncRepuestosProductStepFlow,
            syncRestoInterProductStepFlow,
            syncSemielaboProductStepFlow,
            syncSisCalEHProductStepFlow,
            syncSisLimECProductStepFlow,
            syncSisMezEMProductStepFlow,
            syncSisRecProdProductStepFlow,
            syncSistemasProductStepFlow,
            syncSubconProductStepFlow,
            syncTanquesProductStepFlow,
            syncValAliSegProductStepFlow,
            syncValAsientoProductStepFlow,
            syncValControlProductStepFlow,
            syncValDiaProductStepFlow,
            syncValFuelleProductStepFlow,
            syncValRetenProductStepFlow,
            syncValvulasProductStepFlow,
            syncENAProductStepFlow)
        .build();
  }*/

  @Bean
  public TaskExecutor getFlowTaskExecutor() {
    return new SimpleAsyncTaskExecutor("GetFlowTaskExecutor");
  }

  @Bean
  public JobLauncher jobLauncherSyncItems(JobRepository jobRepository) throws Exception {
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }
}
