package com.edelflex.app.services.integration;

import java.util.Map;

import com.edelflex.app.model.ProductProcessInfo;
import com.edelflex.app.model.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SapItemService {

  private final int PAGE_SIZE = 5000;

  private final CommonAPIService sapApiService;

  public SapItemService(CommonAPIService sapApiService) {
    this.sapApiService = sapApiService;
  }

  public ProductProcessInfo create(Map requestData) {
    /*
    try {
      HttpHeaders headers = sapApiService.getHeaders();
      headers.set("Prefer", "return-no-content");
      HttpEntity<Object> request = new HttpEntity<>(requestData, headers);
      ResponseEntity<Void> response =
          sapApiService
              .getRestTemplate()
              .exchange(
                  sapApiService.getBaseUrl() + "BusinessPartners",
                  HttpMethod.POST,
                  request,
                  Void.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        // OK
      } else {
        // ERROR
      }
    } catch (Exception exc) {
      //  log.error("SAP Error", exc);
      String responseError;
      if (exc instanceof HttpClientErrorException) {
        responseError = ((HttpClientErrorException) exc).getResponseBodyAsString();
      } else {
        responseError = exc.getMessage();
      }
    }*/
    return ProductProcessInfo.builder()
        .request(requestData)
        .response("")
        .status(ProductProcessInfo.Status.OK)
        .action(Product.Action.CREATE)
        .build();
  }

  public ProductProcessInfo update(String code, Map requestData) {

    /*
    try {

      String url = sapApiService.getBaseUrl() + String.format("BusinessPartners('%s')", code);

      HttpHeaders headers = sapApiService.getHeaders();
      headers.set("Prefer", "return-no-content");
      HttpEntity<Object> request = new HttpEntity<>(requestData, headers);
      ResponseEntity<Void> response =
          sapApiService.getRestTemplate().exchange(url, HttpMethod.PATCH, request, Void.class);

      if (response.getStatusCode().is2xxSuccessful()) {
        // OK
      } else {
        // ERROR
      }
    } catch (Exception exc) {
      //  log.error("SAP Error", exc);
      String responseError;
      if (exc instanceof HttpClientErrorException) {
        responseError = ((HttpClientErrorException) exc).getResponseBodyAsString();
      } else {
        responseError = exc.getMessage();
      }
    }*/
    return ProductProcessInfo.builder()
        .request(requestData)
        .response("")
        .status(ProductProcessInfo.Status.OK)
        .action(Product.Action.UPDATE)
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
