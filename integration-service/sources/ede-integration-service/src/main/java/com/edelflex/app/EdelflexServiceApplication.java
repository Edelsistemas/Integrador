package com.edelflex.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class EdelflexServiceApplication {

  // -Dspring.profiles.active=initial
  public static void main(String[] args) {
    SpringApplication.run(EdelflexServiceApplication.class, args);
  }

}
