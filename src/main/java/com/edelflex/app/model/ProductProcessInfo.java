package com.edelflex.app.model;

import java.util.Map;

import com.edelflex.app.model.product.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductProcessInfo {

  public enum Status {
    OK,
    ERROR
  }

  private String id;
  private long recordId;
  private Map<String, Object> response;
  private Status status;
  private String errorMessage;
  private Map<String, Object> request;
  private String code;
  private Product.Action action;
  private String jobId;

  public String getResponseData() {
    ObjectMapper objectMapper = new ObjectMapper();
    StringBuilder str = new StringBuilder();
    if (response != null) {
      try {
        str.append(objectMapper.writeValueAsString(response));
        str.append("\n");
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    if (errorMessage != null) {
      str.append(errorMessage);
    }
    return str.toString();
  }
}
