package com.edelflex.app.batch.sync_items;

import com.edelflex.app.utils.ProcessInfo;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

import java.time.ZoneId;
import java.util.Date;

public class SyncItemsExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        SyncItemsMetrics.getProcessInfo(stepExecution);
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        ProcessInfo processInfo = SyncItemsMetrics.getProcessInfo(stepExecution);
        SyncItemsMetrics.registerAfterStep(
                processInfo,
                Date.from(stepExecution.getStartTime().atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(stepExecution.getEndTime().atZone(ZoneId.systemDefault()).toInstant()),
                stepExecution.getExitStatus());
        return stepExecution.getExitStatus();
        }
}
