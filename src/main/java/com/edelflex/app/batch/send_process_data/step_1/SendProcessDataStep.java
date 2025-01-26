package com.edelflex.app.batch.send_process_data.step_1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Component
@Profile("send-process-data-batch")
public class SendProcessDataStep {
  private final SendProcessDataTasklet sendProcessDataTasklet;

  public SendProcessDataStep(SendProcessDataTasklet sendProcessDataTasklet) {
    this.sendProcessDataTasklet = sendProcessDataTasklet;
  }

  @Bean
  Step processorSendProcessDataStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("SendProcessDataStep", jobRepository).tasklet(sendProcessDataTasklet, platformTransactionManager).build();
  }
}
