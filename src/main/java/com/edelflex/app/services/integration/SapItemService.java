package com.edelflex.app.services.integration;

import java.util.Map;

import com.edelflex.app.exceptions.SapCallException;
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
    String url = getUrl(Product.Action.CREATE, null);
    try {
      HttpHeaders headers = sapApiService.getHeaders();
      headers.set("Prefer", "return-no-content");
      HttpEntity<Object> request = new HttpEntity<>(requestData, headers);
      ResponseEntity<Map> response =
          sapApiService.getRestTemplate().exchange(url, HttpMethod.POST, request, Map.class);

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
      if (exc instanceof HttpClientErrorException exception) {
        responseError = exception.getResponseBodyAsString();
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
        .api("POST " + url)
        .build();
  }

  public ProductProcessInfo update(long id, String code, Map requestData) {
    log.info("UPDATE: " + code);
    log.info(requestData.toString());
    String responseError = null;
    ProductProcessInfo.Status status;
    Map<String, Object> responseData = null;
    String url = getUrl(Product.Action.UPDATE, code);
    try {
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
      if (exc instanceof HttpClientErrorException exception) {
        responseError = exception.getResponseBodyAsString();
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
        .api("PUT " + url)
        .build();
  }

  public boolean existItem(String code) throws SapCallException {
    log.info("GET");
    try {
      HttpHeaders headers = sapApiService.getHeaders();
      HttpEntity<Object> request = new HttpEntity<>(headers);
      ResponseEntity<String> response =
          sapApiService
              .getRestTemplate()
              .exchange(
                  sapApiService.getBaseUrl()
                      + "Items/$count?$filter=ItemCode eq '%s'".formatted(code),
                  HttpMethod.GET,
                  request,
                  String.class);

      if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        return Integer.parseInt(response.getBody()) == 1;
      }
    } catch (Exception exc) {
      log.error("SAP Error", exc);
    }
    throw new SapCallException("Error al obtener datos del Item: " + code);
  }

  public String getUrl(Product.Action action, String code) {
    if (action.equals(Product.Action.CREATE)){
      return sapApiService.getBaseUrl() + "Items";
    } else {
      return sapApiService.getBaseUrl() + "Items('%s')".formatted(code);
    }
  }
}
