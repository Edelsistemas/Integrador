package com.edelflex.app.services.integration;

import com.edelflex.app.exceptions.SapCallException;
import com.edelflex.app.model.sap.SapLoginRequest;
import com.edelflex.app.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommonAPIService {

  private final String user;
  private final String password;
  private final String company;
  private final String baseUrl;
  private final RestTemplate restTemplate;
  private String cookie;
  private Date cookieExpiration;

  public CommonAPIService(
      String user,
      String password,
      String company,
      String baseUrl)
      throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
    this.user = user;
    this.password = password;
    this.company = company;
    this.baseUrl = baseUrl;
    TrustManager[] trustAllCerts =
        new TrustManager[] {
          new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[0];
            }

            public void checkClientTrusted(
                X509Certificate[] certs, String authType) {}

            public void checkServerTrusted(
                X509Certificate[] certs, String authType) {}
          }
        };
    SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
    CloseableHttpClient httpClient =
        HttpClients.custom()
            .setSSLContext(sslContext)
            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
            .build();
    HttpComponentsClientHttpRequestFactory customRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    customRequestFactory.setHttpClient(httpClient);
    customRequestFactory.setConnectTimeout(800000);
    customRequestFactory.setReadTimeout(800000);
    customRequestFactory.setConnectionRequestTimeout(800000);
    restTemplate = new RestTemplate(customRequestFactory);
  }

  private synchronized String getCookieSession() throws SapCallException {
    if (cookieExpiration == null || cookieExpiration.compareTo(new Date()) <= 0) {
      Map<String, Object> loginResponse = doLogin();
      Integer sessionTimeout = (Integer) loginResponse.get("SessionTimeout");
      cookieExpiration = Utils.addMinutesToCurrentDate(sessionTimeout - 10);
      cookie = loginResponse.get("Cookie").toString();
    }
    return cookie;
  }

  public HttpHeaders getHeaders() throws SapCallException {
    String cookie = getCookieSession();
    HttpHeaders headers = new HttpHeaders();
    headers.set("Cookie", cookie);
    headers.setContentType(MediaType.APPLICATION_JSON);
    return headers;
  }

  public RestTemplate getRestTemplate() {
    return this.restTemplate;
  }

  public String getBaseUrl() {
    return this.baseUrl;
  }

  private synchronized Map<String, Object> doLogin() throws SapCallException {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<SapLoginRequest> request =
          new HttpEntity<>(
              SapLoginRequest.builder().user(user).password(password).company(company).build(),
              headers);
      ResponseEntity<Map<String, Object>> response =
          restTemplate.exchange(
              baseUrl + "Login",
              HttpMethod.POST,
              request,
              new ParameterizedTypeReference<Map<String, Object>>() {});
      Map<String, Object> loginResponse = new HashMap<>();
      loginResponse.put("SessionTimeout", response.getBody().get("SessionTimeout"));
      loginResponse.put(
          "Cookie",
          StringUtils.join(response.getHeaders().get(HttpHeaders.SET_COOKIE).toArray(), "; "));
      return loginResponse;
    } catch (Exception exc) {
      log.error("SAP Error: Error al realizar Login en SAP", exc);
      throw new SapCallException(exc);
    }
  }
}
