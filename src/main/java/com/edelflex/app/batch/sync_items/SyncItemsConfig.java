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
