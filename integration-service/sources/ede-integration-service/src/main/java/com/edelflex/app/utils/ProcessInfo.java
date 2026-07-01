package com.edelflex.app.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessInfo {

  private String jobId;
  private int group;
  private String name;
  private String job;
  private Date startTime;
  private Date endTime;
  private String startTimeStr;
  private String endTimeStr;
  private String duration;
  private ProcessStatus status;
  private String errorMessage;
  private Map<String, Object> metrics;
  private List<ProcessInfoMetric> metricsList;

  public enum ProcessStatus {
    RUNNING,
    ERROR,
    COMPLETE
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class ProcessInfoMetric {

    private int order;
    private String key;
    private Object value;
  }

  public void updateMetricsList() {
    metricsList =
        metrics.entrySet().stream()
            .filter(entry -> entry.getKey().startsWith("P_"))
            .map(
                entry -> {
                  int order = Integer.parseInt(entry.getKey().substring(2, 3));
                  String key = entry.getKey().substring(3);
                  return ProcessInfoMetric.builder()
                      .key(key)
                      .value(entry.getValue())
                      .order(order)
                      .build();
                })
            .sorted(Comparator.comparing(ProcessInfoMetric::getOrder))
            .collect(Collectors.toList());
  }

  public void addMetric(String key, Object value) {
    metrics.put(key, value);
  }

  public <T> T getMetric(String key, Object defaultValue) {
    return (T) metrics.getOrDefault(key, defaultValue);
  }

  public <T> T getMetric(String key) {
    return (T) metrics.get(key);
  }

  public void accumulateMetric(String key, int value) {
    metrics.get(key);
    Integer currentValue = (Integer) metrics.getOrDefault(key, 0);
    if (currentValue == null) {
      currentValue = 0;
    }
    currentValue += value;
    metrics.put(key, currentValue);
  }

  public void accumulateMetric(String key, long value) {
    Long currentValue = (Long) metrics.getOrDefault(key, 0L);
    if (currentValue == null) {
      currentValue = 0L;
    }
    currentValue += value;
    metrics.put(key, currentValue);
  }

  public void removeMetric(String key) {
    metrics.remove(key);
  }
}
