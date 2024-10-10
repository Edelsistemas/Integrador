package com.edelflex.app.batch.sync_items;

import com.edelflex.app.batch.BatchExecutionListener;
import com.edelflex.app.services.integration.ProcessService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@Slf4j
@Profile("sync-items-batch")
public class SyncBusinessPartnerConfig {

  public static final String PARAM_PROCESS_IDENTIFIER = "process-identifier";
  private static final String SYNC_ITEMS = "SYNC_ITEMS";

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
      JobBuilderFactory jobBuilderFactory, ProcessService processService, Flow splitFlow) {
    return jobBuilderFactory
        .get(SYNC_ITEMS)
        .start(splitFlow)
        .build()
        .preventRestart()
        .listener(new BatchExecutionListener(processService))
        .build();
  }

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
      Flow syncSisCalEHProductStepFlow) {
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
            syncSisCalEHProductStepFlow)
        .build();
  }

  @Bean
  public Flow syncAcceInlineProductStepFlow(Step syncAcceInlineProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncAcceInlineProductStepFlow")
        .start(syncAcceInlineProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncBomDesPosProductStepFlow(Step syncBomDesPosProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncBomDesPosProductStepFlow")
        .start(syncBomDesPosProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncBombaCenProductStepFlow(Step syncBombaCenProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncBombaCenProductStepFlow")
        .start(syncBombaCenProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncDispLimpiProductStepFlow(Step syncDispLimpiProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncDispLimpiProductStepFlow")
        .start(syncDispLimpiProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncHomogenProductStepFlow(Step syncHomogenProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncHomogenProductStepFlow")
        .start(syncHomogenProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncInstrIndProductStepFlow(Step syncInstrIndProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncInstrIndProductStepFlow")
        .start(syncInstrIndProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncInstrumenProductStepFlow(Step syncInstrumenProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncInstrumenProductStepFlow")
        .start(syncInstrumenProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncInterCalorProductStepFlow(Step syncInterCalorProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncInterCalorProductStepFlow")
        .start(syncInterCalorProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncMatPriProductStepFlow(Step syncMatPriProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncMatPriProductStepFlow")
        .start(syncMatPriProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncOtrasBomProductStepFlow(Step syncOtrasBomProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncOtrasBomProductStepFlow")
        .start(syncOtrasBomProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncOtrasValProductStepFlow(Step syncOtrasValProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncOtrasValProductStepFlow")
        .start(syncOtrasValProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncOtrosCompProductStepFlow(Step syncOtrosCompProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncOtrosCompProductStepFlow")
        .start(syncOtrosCompProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncPiezaProductStepFlow(Step syncPiezaProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncPiezaProductStepFlow")
        .start(syncPiezaProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncRepuestosProductStepFlow(Step syncRepuestosProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncRepuestosProductStepFlow")
        .start(syncRepuestosProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncRestoInterProductStepFlow(Step syncRestoInterProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncRestoInterProductStepFlow")
        .start(syncRestoInterProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncSemielaboProductStepFlow(Step syncSemielaboProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncSemielaboProductStepFlow")
        .start(syncSemielaboProductStepDefinition)
        .build();
  }

  @Bean
  public Flow syncSisCalEHProductStepFlow(Step syncSisCalEHProductStepDefinition) {
    return new FlowBuilder<SimpleFlow>("syncSisCalEHProductStepFlow")
        .start(syncSisCalEHProductStepDefinition)
        .build();
  }

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
