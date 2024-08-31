package com.edelflex.app.batch.send_process_data;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableBatchProcessing
@Slf4j
@Profile("send-process-data-batch")
public class SendProcessDataConfig {

  private static final String SEND_PROCESS_DATA = "SEND_PROCESS_DATA";

  @Autowired
  @Qualifier("jobLauncherSendProcessData")
  private JobLauncher jobLauncher;

  @Autowired
  @Qualifier("processSendProcessDataJob")
  private Job processSendProcessDataJob;

  private JobExecution execution;

  @Bean
  public Job processSendProcessDataJob(
      JobBuilderFactory jobBuilderFactory, Step processorSendProcessDataStep) {
    return jobBuilderFactory.get(SEND_PROCESS_DATA).start(processorSendProcessDataStep).build();
  }

  @Scheduled(fixedDelayString = "10000")
  public void schedule() {
    if (execution == null || !execution.isRunning()) {
      try {
        execution = jobLauncher.run(
            processSendProcessDataJob,
            new JobParametersBuilder().addLong("time", new Date().getTime()).toJobParameters());
      } catch (Exception e) {
        log.error("Error al lanzar proceso", e);
      } finally {
        MDC.clear();
      }
    }
  }

  @Bean
  public JobLauncher jobLauncherSendProcessData(JobRepository jobRepository) throws Exception {
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }
}
