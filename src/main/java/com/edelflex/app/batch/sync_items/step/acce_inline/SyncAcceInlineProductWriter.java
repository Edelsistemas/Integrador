package com.edelflex.app.batch.sync_items.step.acce_inline;

import com.edelflex.app.batch.sync_items.step.product_type.SyncBaseProductWriter;
import com.edelflex.app.model.ProductProcessInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
@Profile("sync-items-batch")
public class SyncAcceInlineProductWriter extends SyncBaseProductWriter {

  public SyncAcceInlineProductWriter(MongoTemplate mongoTemplate) {
    super(mongoTemplate);
  }

  @Override
  protected void process(List<? extends ProductProcessInfo> list) {

  }
}
