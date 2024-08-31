package com.edelflex.app.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.core.AppenderBase;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class BitOneAppender extends AppenderBase<ILoggingEvent> {

  private String app;
  private String clientCode;
  private String apiKey;
  private String server;
  private boolean sendLogs;
  private final HttpHeaders headers = new HttpHeaders();
  private final RestTemplate restTemplate = new RestTemplate();
  private URI uri;
  private final ExecutorService executor = Executors.newFixedThreadPool(10);

  @Override
  protected void append(ILoggingEvent event) {
    if (!event.getMDCPropertyMap().containsKey("P_REQUEST_PATH")) {
        return;
    }
    Map<String, Object> loggingEvent = new LinkedHashMap<>();
    loggingEvent.put("P_TIMESTAMP", event.getTimeStamp());
    loggingEvent.put("P_MESSAGE", event.getFormattedMessage());
    loggingEvent.put("P_MDC", event.getMDCPropertyMap());
    loggingEvent.put("P_LEVEL", event.getLevel().levelStr);
    loggingEvent.put("P_APP", app);
    loggingEvent.put("P_CLIENT_CODE", clientCode);
    populateFromMDC(loggingEvent, event.getMDCPropertyMap());
    if (event.getLevel().levelStr.equals("ERROR")) {
      StringBuilder errorMessage = new StringBuilder();
      errorMessage.append(event.getThrowableProxy().getClassName());
      errorMessage.append(event.getThrowableProxy().getMessage());
      errorMessage.append("\n");
      if (event.getThrowableProxy().getStackTraceElementProxyArray() != null) {
        Arrays.stream(event.getThrowableProxy().getStackTraceElementProxyArray())
            .forEach(
                stack -> {
                  errorMessage.append(stack.getStackTraceElement().toString());
                  errorMessage.append("\n");
                });
      }
      loggingEvent.put("P_ERROR", event.getThrowableProxy().getMessage());
      loggingEvent.put("P_STACK_TRACE", errorMessage.toString());
    }
    System.out.println(loggingEvent);
    if (sendLogs) {
      executor.execute(new Processor(restTemplate, uri, headers, loggingEvent));
    }
  }

  private static class Processor implements Runnable {

    private final Map<String, Object> data;
    private final RestTemplate restTemplate;
    private final URI uri;
    private final HttpHeaders headers;

    public Processor(
        RestTemplate restTemplate, URI uri, HttpHeaders headers, Map<String, Object> data) {
      this.data = data;
      this.restTemplate = restTemplate;
      this.headers = headers;
      this.uri = uri;
    }

    @Override
    public void run() {
      HttpEntity<Map<String, Object>> entity = new HttpEntity<>(data, headers);
      try {
        restTemplate.exchange(uri, HttpMethod.POST, entity, Object.class);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void populateFromMDC(Map<String, Object> loggingEvent, Map<String, String> mdc) {
    loggingEvent.put("P_START_TIME", mdc.getOrDefault("P_START_TIME", ""));
    loggingEvent.put("P_REQUEST_BODY", mdc.getOrDefault("P_REQUEST_BODY", ""));
    loggingEvent.put("P_ERROR_CODE", mdc.getOrDefault("P_ERROR_CODE", ""));
    loggingEvent.put("P_HTTP_STATUS", mdc.getOrDefault("P_HTTP_STATUS", ""));
    loggingEvent.put("P_REQUEST_PATH", mdc.getOrDefault("P_REQUEST_PATH", ""));
    loggingEvent.put("P_HTTP_METHOD", mdc.getOrDefault("P_HTTP_METHOD", ""));
    loggingEvent.put("P_USERNAME", mdc.getOrDefault("P_USERNAME", ""));
    loggingEvent.put("P_END_TIME", mdc.getOrDefault("P_END_TIME", ""));
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public String getClientCode() {
    return clientCode;
  }

  public void setClientCode(String clientCode) {
    this.clientCode = clientCode;
  }

  public boolean isSendLogs() {
    return sendLogs;
  }

  public void setSendLogs(boolean sendLogs) {
    this.sendLogs = sendLogs;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
    headers.add("api-key", apiKey);
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
    try {
      uri = new URI(server);
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
  }
}
