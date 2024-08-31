package com.edelflex.app.repository;

import com.edelflex.app.model.BatchProcess;
import com.edelflex.app.model.BatchProcessStatus;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchProcessRepository extends MongoRepository<BatchProcess, String> {

  Optional<BatchProcess> findByJobIdAndKey(String jobId, String key);

  List<BatchProcess> findByStatusIsInAndSyncedIsFalse(List<BatchProcessStatus> statusList);

  List<BatchProcess> findByStatus(BatchProcessStatus status);
}
