package com.edelflex.app.services.integration;

import java.util.Map;

import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
public class SapItemService {

  private final CommonAPIService sapApiService;

  public SapItemService(CommonAPIService sapApiService) {
    this.sapApiService = sapApiService;
  }

  public ProductProcessInfo create(long id, String code, Map requestData) {
    log.info("CREATE");
    log.info(requestData.toString());
    String responseError = null;
    ProductProcessInfo.Status status;
    Map<String, Object> responseData = null;
    try {
      HttpHeaders headers = sapApiService.getHeaders();
      headers.set("Prefer", "return-no-content");
      HttpEntity<Object> request = new HttpEntity<>(requestData, headers);
      ResponseEntity<Map> response =
          sapApiService
              .getRestTemplate()
              .exchange(sapApiService.getBaseUrl() + "Items", HttpMethod.POST, request, Map.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        // OK
        status = ProductProcessInfo.Status.OK;
        responseData = response.getBody();
      } else {
        status = ProductProcessInfo.Status.ERROR;
        responseData = response.getBody();
        // ERROR
      }
    } catch (Exception exc) {
      log.error("SAP Error", exc);
      status = ProductProcessInfo.Status.ERROR;
      if (exc instanceof HttpClientErrorException) {
        responseError = ((HttpClientErrorException) exc).getResponseBodyAsString();
      } else {
        responseError = exc.getMessage();
      }
    }
    return ProductProcessInfo.builder()
        .request(requestData)
        .response(responseData)
        .errorMessage(responseError)
        .status(status)
        .action(Product.Action.CREATE)
        .code(code)
        .recordId(id)
        .build();
  }

  public ProductProcessInfo update(long id, String code, Map requestData) {
    log.info("UPDATE: " + code);
    log.info(requestData.toString());
    String responseError = null;
    ProductProcessInfo.Status status;
    Map<String, Object> responseData = null;
    try {

      String url = sapApiService.getBaseUrl() + String.format("Items('%s')", code);

      HttpHeaders headers = sapApiService.getHeaders();
      headers.set("Prefer", "return-no-content");
      HttpEntity<Object> request = new HttpEntity<>(requestData, headers);
      ResponseEntity<Map> response =
          sapApiService.getRestTemplate().exchange(url, HttpMethod.PATCH, request, Map.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        // OK
        status = ProductProcessInfo.Status.OK;
        responseData = response.getBody();
      } else {
        status = ProductProcessInfo.Status.ERROR;
        responseData = response.getBody();
        // ERROR
      }
    } catch (Exception exc) {
      log.error("SAP Error", exc);
      status = ProductProcessInfo.Status.ERROR;
      if (exc instanceof HttpClientErrorException) {
        responseError = ((HttpClientErrorException) exc).getResponseBodyAsString();
      } else {
        responseError = exc.getMessage();
      }
    }
    return ProductProcessInfo.builder()
        .request(requestData)
        .response(responseData)
        .errorMessage(responseError)
        .status(status)
        .action(Product.Action.UPDATE)
        .code(code)
        .recordId(id)
        .build();
  }

  public String getUrl(Product.Action action, String code) {
    if (action.equals(Product.Action.CREATE)) {
      return sapApiService.getBaseUrl() + "Items";
    } else {
      return sapApiService.getBaseUrl() + String.format("Items('%s')", code);
    }
  }
}
