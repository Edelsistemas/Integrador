package com.edelflex.app.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "team-center.querys")
@Data
public class ItemProcessConfigProperties {

    private String schema;
    private String get;
    private String update;
    private List<ItemProcessConfig> configs;

    @Data
    public static class ItemProcessConfig {
        private String table;
        private Map<String, Object> fields;
    }
}
