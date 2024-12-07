package com.edelflex.app.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class GeneralConfig {

  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    return mapper;
  }

  @Bean
  public ResourcelessTransactionManager transactionManager() {
    return new ResourcelessTransactionManager();
  }

  @Bean
  public JobRepository jobRepository(DataSource dataSource) throws Exception {
    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
    factory.setDataSource(dataSource);
    factory.setTransactionManager(transactionManager());
    return factory.getObject();
  }

  @Bean
  @Primary
  public HikariDataSource getDataSource(){
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:hsqldb:mem:testdb;sql.enforce_strict_size=true;hsqldb.tx=MVCC");
    config.setUsername("sa");
    config.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
    config.setConnectionTimeout(300000);
    config.setMaximumPoolSize(50);
    config.setMaxLifetime(300000);
    return new HikariDataSource(config);
  }

  @Bean
  @Qualifier("dataSourceSQLServer")
  @ConfigurationProperties(prefix = "sqlserver")
  public DataSource dataSourceSQLServer() {
    return DataSourceBuilder.create().build();
  }

  @Bean
  @Qualifier("jdbcTemplateSQLServer")
  public JdbcTemplate jdbcTemplate() throws SQLException {
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    DataSource ds = dataSourceSQLServer();
    ds.getConnection().setAutoCommit(true);
    jdbcTemplate.setDataSource(ds);
    return jdbcTemplate;
  }


}
