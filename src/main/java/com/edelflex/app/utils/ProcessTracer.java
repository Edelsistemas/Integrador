package com.edelflex.app.utils;

import com.edelflex.app.batch.sync_items.SyncBusinessPartnerConfig;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;

@Slf4j
public class ProcessTracer {

  public static final List<ProcessInfo> INFO = new ArrayList<>();

  public static List<ProcessInfo> getCurrent(String job, String jobId) {
    return INFO.stream()
        .filter(info -> info.getJob().equals(job) && info.getJobId().equals(jobId))
        .peek(ProcessInfo::updateMetricsList)
        .sorted(Comparator.comparing(ProcessInfo::getGroup).thenComparing(ProcessInfo::getName))
        .collect(Collectors.toList());
  }

  public static synchronized ProcessInfo getProcessInfo(StepExecution stepExecution, int group) {
    String job = stepExecution.getJobExecution().getJobInstance().getJobName();
    String jobId =
        stepExecution
            .getJobParameters()
            .getString(SyncBusinessPartnerConfig.PARAM_PROCESS_IDENTIFIER);
    ProcessInfo processInfo =
        INFO.stream()
            .filter(
                info ->
                    info.getJob().equals(job)
                        && info.getJobId().equals(jobId)
                        && info.getName().equals(stepExecution.getStepName()))
            .findFirst()
            .orElse(null);
    if (processInfo == null) {
      processInfo =
          ProcessInfo.builder()
              .jobId(jobId)
              .group(group)
              .job(job)
              .name(stepExecution.getStepName())
              .startTime(stepExecution.getStartTime())
              .startTimeStr(Utils.convertDateToDefaultFormat(stepExecution.getStartTime()))
              .metrics(new HashMap<>())
              .status(ProcessInfo.ProcessStatus.RUNNING)
              .build();
      INFO.add(processInfo);
    }
    return processInfo;
  }

  public static synchronized void clear(String job, String jobId) {
    INFO.removeIf(info -> info.getJob().equals(job) && info.getJobId().equals(jobId));
  }
}
