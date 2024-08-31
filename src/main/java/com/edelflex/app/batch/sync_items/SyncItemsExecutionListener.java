package com.edelflex.app.batch.sync_items;

import com.edelflex.app.utils.ProcessInfo;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

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
                stepExecution.getStartTime(),
                stepExecution.getEndTime(),
                stepExecution.getExitStatus());
        return stepExecution.getExitStatus();
        }
}
