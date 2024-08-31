package com.edelflex.app.batch.send_process_data.step_1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("send-process-data-batch")
public class SendProcessDataStep {

  private final StepBuilderFactory stepBuilderFactory;
  private final SendProcessDataTasklet sendProcessDataTasklet;

  public SendProcessDataStep(
      StepBuilderFactory stepBuilderFactory, SendProcessDataTasklet sendProcessDataTasklet) {
    this.stepBuilderFactory = stepBuilderFactory;
    this.sendProcessDataTasklet = sendProcessDataTasklet;
  }

  @Bean
  public Step processorSendProcessDataStep() {
    return stepBuilderFactory.get("SendProcessDataStep").tasklet(sendProcessDataTasklet).build();
  }
}
