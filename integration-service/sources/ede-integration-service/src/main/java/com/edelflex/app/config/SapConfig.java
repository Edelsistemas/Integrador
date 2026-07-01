package com.edelflex.app.config;

import com.edelflex.app.services.integration.CommonAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SapConfig {

  @Bean
  CommonAPIService sapApiService(
      @Value("${sap.edelflex.credentials.user}") String user,
      @Value("${sap.edelflex.credentials.password}") String password,
      @Value("${sap.edelflex.credentials.company}") String company,
      @Value("${sap.edelflex.url}") String baseUrl)
      throws Exception {
    return new CommonAPIService(user, password, company, baseUrl);
  }
}
