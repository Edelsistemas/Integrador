package com.edelflex.app.model;

import com.edelflex.app.utils.ProcessInfo;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "batch-process")
public class BatchProcess {

  @Id private String id;
  private Date dateStart;
  private Date dateEnd;
  private String dateStartStr;
  private String dateEndStr;
  private BatchProcessStatus status;
  private String[] errors;
  private String key;
  private String jobId;
  private String duration;
  private boolean success;
  private List<ProcessInfo> processInfo;
  private boolean synced;
}
