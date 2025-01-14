package com.edelflex.app.batch.cleanup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@Profile("clean-up-process-batch")
public class CleanUpProcessConfig {


  @Bean
  public Job processCleanUpJob(Step processorCleanUpStep, JobRepository jobRepository) {
    return new JobBuilder(CleanUpProcessLauncher.CLEAN_UP_PROCESS, jobRepository).start(processorCleanUpStep).build();
  }

  @Bean
  JobLauncher jobLauncherCleanUpProcess(JobRepository jobRepository) throws Exception {
    TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.afterPropertiesSet();
    return jobLauncher;
  }
}
