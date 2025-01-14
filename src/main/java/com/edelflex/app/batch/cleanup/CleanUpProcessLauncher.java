package com.edelflex.app.batch.cleanup;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
public class CleanUpProcessLauncher {

  public static final String CLEAN_UP_PROCESS = "CLEAN_UP_PROCESS";

  @Autowired
  @Qualifier("jobLauncherCleanUpProcess")
  private JobLauncher jobLauncher;

  @Autowired private ApplicationContext context;

  private JobExecution execution;

  @Scheduled(cron = "0 0 23 * * *")
  public void schedule() {
    if (execution == null || !execution.isRunning()) {
      try {
        Job processCleanUpJob = (Job) context.getBean("processCleanUpJob");
        execution =
            jobLauncher.run(
                processCleanUpJob,
                new JobParametersBuilder().addLong("time", new Date().getTime()).toJobParameters());
      } catch (Exception e) {
        log.error("Error al lanzar proceso", e);
      } finally {
        MDC.clear();
      }
    }
  }
}
