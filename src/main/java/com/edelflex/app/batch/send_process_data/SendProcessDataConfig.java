package com.edelflex.app.batch.send_process_data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@Slf4j
@Profile("send-process-data-batch")
public class SendProcessDataConfig {


  @Bean
  public Job processSendProcessDataJob(Step processorSendProcessDataStep, JobRepository jobRepository) {
    return new JobBuilder(SendProcessDataLauncher.SEND_PROCESS_DATA, jobRepository).start(processorSendProcessDataStep).build();
  }

  @Bean
  TaskExecutor sendProcessTaskExecutor() {
    return new SimpleAsyncTaskExecutor("SendProcessDataTaskExecutor");
  }

  @Bean
  JobLauncher jobLauncherSendProcessData(JobRepository jobRepository, TaskExecutor sendProcessTaskExecutor) throws Exception {
    TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(sendProcessTaskExecutor);
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }
}
