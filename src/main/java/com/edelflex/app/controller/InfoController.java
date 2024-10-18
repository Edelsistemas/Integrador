package com.edelflex.app.controller;

import com.edelflex.app.batch.sync_items.SyncItemsConfig;
import com.edelflex.app.model.BatchProcess;
import com.edelflex.app.model.BatchProcessStatus;
import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.repository.BatchProcessRepository;
import java.util.Collections;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfoController {

  private final BatchProcessRepository batchProcessRepository;
  private final MongoTemplate mongoTemplate;

  public InfoController(
      BatchProcessRepository batchProcessRepository, MongoTemplate mongoTemplate) {
    this.batchProcessRepository = batchProcessRepository;
    this.mongoTemplate = mongoTemplate;
  }

  @GetMapping("/process/status")
  public String status(Model model) {

    boolean isRunningItems =
        batchProcessRepository.countByKeyAndStatus(
                SyncItemsConfig.SYNC_ITEMS, BatchProcessStatus.PROCESSING)
            > 0;

    BatchProcess batchItems =
        batchProcessRepository
            .findFirstByKeyAndStatusIsNot(
                SyncItemsConfig.SYNC_ITEMS,
                BatchProcessStatus.PROCESSING,
                Sort.by(Sort.Direction.DESC, "dateStart"))
            .orElse(null);

    List<ProductProcessInfo> resultsItems =
        batchItems != null
            ? mongoTemplate.find(
                Query.query(Criteria.where("jobId").is(batchItems.getJobId())),
                ProductProcessInfo.class,
                SyncItemsConfig.ITEMS_HISTORY_COLLECTION)
            : Collections.emptyList();

    model.addAttribute("batchItems", batchItems);
    model.addAttribute("resultsItems", resultsItems);
    model.addAttribute("isRunningItems", isRunningItems);
    return "status";
  }

  /*
  @PostMapping("/intercompany/run_items")
  public void runItems(HttpServletResponse response) throws IOException {
    syncItemConfig.schedule();
    response.sendRedirect("/intercompany/status");
  }
   */
}
