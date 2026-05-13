package com.edelflex.app.batch.send_process_data;

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
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("send-process-data-batch")
@Slf4j
public class SendProcessDataLauncher {

  public static final String SEND_PROCESS_DATA = "SEND_PROCESS_DATA";

  @Autowired
  @Qualifier("jobLauncherSendProcessData")
  private JobLauncher jobLauncher;

  @Autowired private ApplicationContext context;

  private JobExecution execution;

  @Scheduled(fixedDelayString = "10000")
  public void schedule() {
    if (execution == null || !execution.isRunning()) {
      try {
        Job processSendProcessDataJob = (Job) context.getBean("processSendProcessDataJob");
        execution =
            jobLauncher.run(
                processSendProcessDataJob,
                new JobParametersBuilder().addLong("time", new Date().getTime()).toJobParameters());
      } catch (Exception e) {
        log.error("Error al lanzar proceso", e);
      } finally {
        MDC.clear();
      }
    }
  }
}
