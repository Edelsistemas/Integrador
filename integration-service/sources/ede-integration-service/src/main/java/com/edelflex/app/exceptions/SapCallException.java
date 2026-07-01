package com.edelflex.app.exceptions;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;

@Data
public class SapCallException extends Exception {

  private String request;
  private String responseError;
  private Date date;
  private Calendar calendar = Calendar.getInstance();

  public SapCallException(Throwable th) {
    super(th);
  }

  public SapCallException(String msg) {
    super(msg);
  }

  public SapCallException(Throwable th, String request, String responseError) {
    super(th);
    this.request = request;
    this.responseError = responseError;
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, 5);
    this.date = calendar.getTime();
  }

  public SapCallException(String msg, String request, String responseError) {
    super(msg);
    this.request = request;
    this.responseError = responseError;
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, 5);
    this.date = calendar.getTime();
  }
}
