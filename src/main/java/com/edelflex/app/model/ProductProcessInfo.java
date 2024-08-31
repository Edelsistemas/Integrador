package com.edelflex.app.model;

import java.util.Map;

import com.edelflex.app.model.product.Product;
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
    OK, ERROR
  }
  
  private String response;
  private Status status;
  private String errorMessage;
  private Map<String, Object> request;
  private String code;
  private Product.Action action;
}
