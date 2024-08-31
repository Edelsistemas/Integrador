package com.edelflex.app.batch.send_process_data.step_1;

import com.edelflex.app.model.BatchProcess;
import com.edelflex.app.model.BatchProcessStatus;
import com.edelflex.app.services.integration.ProcessService;
import com.edelflex.app.utils.Utils;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@Profile("send-process-data-batch")
public class SendProcessDataTasklet implements Tasklet {

  private final ProcessService processService;
  private final HttpHeaders headers = new HttpHeaders();
  private final RestTemplate restTemplate = new RestTemplate();
  private final String url;

  public SendProcessDataTasklet(
      ProcessService processService,
      @Value("${bitone.integration.api-key}") String bitToolsApiKey,
      @Value("${bitone.integration.url}") String bitToolsApiUrl) {
    this.processService = processService;
    headers.add("api-key", bitToolsApiKey);
    this.url = bitToolsApiUrl;
  }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    List<BatchProcess> batchProcessList = processService.getProcessListToSend();
    if (Utils.isNotEmpty(batchProcessList)) {
      HttpEntity<List<BatchProcess>> entity = new HttpEntity<>(batchProcessList, headers);
      restTemplate.exchange(new URI(url), HttpMethod.POST, entity, Object.class);
      processService.markAsSynced(
          batchProcessList.stream()
              .filter(
                  batchProcess -> !batchProcess.getStatus().equals(BatchProcessStatus.PROCESSING))
              .map(BatchProcess::getId)
              .collect(Collectors.toList()));
    }
    return RepeatStatus.FINISHED;
  }
}
