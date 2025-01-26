package com.edelflex.app.batch.cleanup.step_1;

import com.edelflex.app.batch.sync_items.SyncItemsConfig;
import com.edelflex.app.model.BatchProcess;
import com.edelflex.app.model.BatchProcessStatus;
import com.edelflex.app.services.integration.ProcessService;
import com.edelflex.app.utils.Utils;
import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@Profile("clean-up-process-batch")
public class CleanUpProcessTasklet implements Tasklet {

  private final MongoTemplate mongoTemplate;

  public CleanUpProcessTasklet(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    BulkOperations bulkOperations =
        mongoTemplate.bulkOps(
            BulkOperations.BulkMode.UNORDERED, Map.class, SyncItemsConfig.ITEMS_HISTORY_COLLECTION);
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -2);
    bulkOperations.remove(Query.query(Criteria.where("date").lt(calendar.getTime()))).execute();
    return RepeatStatus.FINISHED;
  }
}
