package com.edelflex.app.services.integration;

import com.edelflex.app.model.BatchProcess;
import com.edelflex.app.model.BatchProcessStatus;
import com.edelflex.app.repository.BatchProcessRepository;
import com.edelflex.app.utils.ProcessInfo;
import com.edelflex.app.utils.ProcessTracer;
import com.edelflex.app.utils.Utils;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessService {

  private final BatchProcessRepository batchProcessRepository;
  private final MongoTemplate mongoTemplate;

  public ProcessService(
      BatchProcessRepository batchProcessRepository, MongoTemplate mongoTemplate) {
    this.batchProcessRepository = batchProcessRepository;
    this.mongoTemplate = mongoTemplate;
  }

  public BatchProcess saveProcess(BatchProcess batchProcess) {
    batchProcessRepository.save(batchProcess);
    return batchProcess;
  }

  public BatchProcess getByJobIdAndJob(String jobId, String job) {
    return batchProcessRepository.findByJobIdAndKey(jobId, job).orElse(null);
  }

  public List<BatchProcess> getProcessListToSend() {
    List<BatchProcess> processList = new ArrayList<>();
    processList.addAll(
        batchProcessRepository.findByStatus(BatchProcessStatus.PROCESSING).stream()
            .peek(
                batchProcess -> {
                  batchProcess.setProcessInfo(
                      ProcessTracer.getCurrent(batchProcess.getKey(), batchProcess.getJobId()));
                  batchProcess.setDuration(
                      Utils.getTimeFormatFromMillis(
                          new Date().getTime() - batchProcess.getDateStart().getTime()));
                })
            .collect(Collectors.toList()));
    processList.addAll(
        batchProcessRepository
            .findByStatusIsInAndSyncedIsFalse(
                Arrays.asList(BatchProcessStatus.SUCCESS, BatchProcessStatus.ERROR))
            .stream()
            .peek(
                batchProcess ->
                    batchProcess.getProcessInfo().forEach(ProcessInfo::updateMetricsList))
            .collect(Collectors.toList()));
    return processList;
  }

  public void markAsSynced(List<String> ids) {
    if (Utils.isNotEmpty(ids)) {
      BulkOperations bulkOperations =
          this.mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, BatchProcess.class);
      ids.forEach(
          processId -> {
            Update update = new Update();
            update.set("synced", true);
            bulkOperations.updateOne(Query.query(Criteria.where("_id").is(processId)), update);
          });
      bulkOperations.execute();
    }
  }
}
