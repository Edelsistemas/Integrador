package com.edelflex.app.batch.cleanup.step_1;

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
@Profile("clean-up-process-batch")
public class CleanUpProcessStep {
  private final CleanUpProcessTasklet cleanUpProcessTasklet;

  public CleanUpProcessStep(CleanUpProcessTasklet cleanUpProcessTasklet) {
    this.cleanUpProcessTasklet = cleanUpProcessTasklet;
  }

  @Bean
  Step processorCleanUpStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("CleanUpStep", jobRepository).tasklet(cleanUpProcessTasklet, platformTransactionManager).build();
  }
}
