package com.edelflex.app.batch;

import com.edelflex.app.batch.sync_items.SyncItemsConfig;
import com.edelflex.app.model.BatchProcess;
import com.edelflex.app.model.BatchProcessStatus;
import com.edelflex.app.services.integration.ProcessService;
import com.edelflex.app.utils.ProcessTracer;
import com.edelflex.app.utils.Utils;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class BatchExecutionListener implements JobExecutionListener {

  private final ProcessService processService;

  public BatchExecutionListener(ProcessService processService) {
    this.processService = processService;
  }

  @Override
  public void beforeJob(JobExecution jobExecution) {
    String job = jobExecution.getJobInstance().getJobName();
    String jobId =
        jobExecution
            .getJobParameters()
            .getString(SyncItemsConfig.PARAM_PROCESS_IDENTIFIER);
    BatchProcess batchProcess = BatchProcess.builder().build();
    batchProcess.setDateStart(new Date());
    batchProcess.setDateStartStr(Utils.convertDateToDefaultFormat(batchProcess.getDateStart()));
    batchProcess.setKey(job);
    batchProcess.setJobId(jobId);
    batchProcess.setSuccess(Boolean.FALSE);
    batchProcess.setStatus(BatchProcessStatus.PROCESSING);
    processService.saveProcess(batchProcess);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    String job = jobExecution.getJobInstance().getJobName();
    String jobId =
        jobExecution
            .getJobParameters()
            .getString(SyncItemsConfig.PARAM_PROCESS_IDENTIFIER);
    BatchProcess batchProcess = processService.getByJobIdAndJob(jobId, job);
    batchProcess.setDateEnd(new Date());
    batchProcess.setDateEndStr(Utils.convertDateToDefaultFormat(batchProcess.getDateEnd()));
    if (jobExecution.getStatus() == BatchStatus.FAILED) {
      batchProcess.setSuccess(Boolean.FALSE);
      batchProcess.setStatus(BatchProcessStatus.ERROR);
      batchProcess.setErrors(
          jobExecution.getAllFailureExceptions().stream()
              .map(Utils::convertStackTraceToString)
              .toArray(String[]::new));
    } else {
      batchProcess.setErrors(null);
      batchProcess.setSuccess(Boolean.TRUE);
      batchProcess.setStatus(BatchProcessStatus.SUCCESS);
    }
    long time = batchProcess.getDateEnd().getTime() - batchProcess.getDateStart().getTime();
    batchProcess.setDuration(Utils.getTimeFormatFromMillis(time));
    batchProcess.setProcessInfo(ProcessTracer.getCurrent(job, batchProcess.getJobId()));
    processService.saveProcess(batchProcess);
    ProcessTracer.clear(job, jobId);
  }
}
