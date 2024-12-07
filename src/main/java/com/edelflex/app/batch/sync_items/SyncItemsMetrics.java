package com.edelflex.app.batch.sync_items;

import com.edelflex.app.utils.ProcessInfo;
import com.edelflex.app.utils.ProcessTracer;
import com.edelflex.app.utils.Utils;
import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

public class SyncItemsMetrics {

  public static ProcessInfo getProcessInfo(StepExecution stepExecution) {
    return ProcessTracer.getProcessInfo(stepExecution, 1);
  }

  public static void registerProcessStart(
      ProcessInfo processInfo, String url, String code, String product, String action) {
    processInfo.addMetric("P_0Tarea", String.format("[%s] %s %s - %s", action, action, code, product));
    processInfo.addMetric("P_0API", url);
  }

  public static void registerProcessEnd(ProcessInfo processInfo, long t1, long t2) {
    long durationMillis = t2 - t1;
    processInfo.removeMetric("P_0Tarea");
    processInfo.removeMetric("P_0API");
    processInfo.accumulateMetric("P_3Total Proceso(seg)", (durationMillis / 1000));
    processInfo.accumulateMetric("ITEMS_PROCESSED", 1);
    int items = processInfo.getMetric("ITEMS");
    int pagesProcessed = processInfo.getMetric("ITEMS_PROCESSED");
    processInfo.addMetric("P_1Total Procesado", pagesProcessed + "/" + items);
  }

  public static void registerReader(ProcessInfo processInfo, int totalItems, String query) {
    processInfo.addMetric("QUERY", query);
    processInfo.addMetric("ITEMS", totalItems);
  }

  public static void registerWriter(ProcessInfo processInfo, long createCount, long updateCount, long errorCount) {
    processInfo.accumulateMetric("P_2Items Creados", createCount);
    processInfo.accumulateMetric("P_2Items Actualizados", updateCount);
    processInfo.accumulateMetric("P_2Items Errores", errorCount);
  }

  public static void registerAfterStep(
      ProcessInfo processInfo, Date startDate, Date endDate, ExitStatus exitStatus) {
    if (endDate == null) {
      endDate = new Date();
    }
    processInfo.setEndTime(endDate);
    processInfo.setEndTimeStr(Utils.convertDateToDefaultFormat(endDate));
    long durationMillis = endDate.getTime() - startDate.getTime();
    processInfo.setDuration(Utils.getTimeFormatFromMillis(durationMillis));
    processInfo.setStatus(
        exitStatus.getExitCode().equals(ExitStatus.FAILED.getExitCode())
            ? ProcessInfo.ProcessStatus.ERROR
            : ProcessInfo.ProcessStatus.COMPLETE);
  }
}
