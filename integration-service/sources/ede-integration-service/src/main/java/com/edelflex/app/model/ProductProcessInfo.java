package com.edelflex.app.model;

import java.util.Date;
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
  private Map<String, Object> sapData;
  private Map<String, Object> response;
  private String productType;
  private Status status;
  private String errorMessage;
  private Map<String, Object> request;
  private String code;
  private String jobId;
  private Product.Action action;
  private String api;
  private Date date;

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

  public String getActionData() {
    return action.getLabel() + " :: " + getApi();
  }

  public String getRequestData() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }
}
