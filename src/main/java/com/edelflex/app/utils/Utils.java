package com.edelflex.app.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;

@Slf4j
public class Utils {

  private Utils() {}

  public static final String DATE_FORMAT_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String DATE_FORMAT_DATE_DD_MM_YYYY_SLASH = "dd/MM/yyyy";
  public static final String DATE_FORMAT_DATE_MM_DD_YYYY_SLASH = "MM/dd/yyyy";
  public static final String DATE_FORMAT_DATE_DD_MM_YYYY_TIME_SLASH = "dd/MM/yyyy HH:mm";
  public static final String DATE_FORMAT_DATE_YYYY_MM_DD_MIDDLE_DASH = "yyyy-MM-dd";
  public static final String DATE_FORMAT_DATE_DD_MM_YYYY_MIDDLE_DASH = "dd-MM-yyyy";
  public static final String DATE_FORMAT_DATE_MMM_YY_MIDDLE_DASH = "MMM-yy";
  public static final String DATE_FORMAT_DATE_MMM_YYYY_MIDDLE_DASH = "MMM-yyyy";
  public static final String DATE_FORMAT_DATE_MMMM_YYYY_MIDDLE_DASH = "MMMM-yyyy";
  public static final String DATE_FORMAT_DATE_MM_YY_MIDDLE_UNDERSCORE = "MM-yy";
  public static final String DATE_FORMAT_DATE_YYYY_MM_MIDDLE_DASH = "yyyy-MM";
  public static final String DATE_FORMAT_DATE_MM = "MM";

  public static String convertStackTraceToString(Throwable pThrowable) {
    if (pThrowable == null) {
      return null;
    } else {
      StringWriter sw = new StringWriter();
      pThrowable.printStackTrace(new PrintWriter(sw));
      return sw.toString();
    }
  }

  public static String convertCurrentDateToFormat(String format) {
    return new SimpleDateFormat(format, Locale.of("es", "ES")).format(new Date());
  }

  public static String convertDateToFormat(Date date, String format) {
    if (date == null) return "";
    return new SimpleDateFormat(format, Locale.of("es", "ES")).format(date);
  }

  public static Date convertDateStrToDate(String strDate, String format) {
    if (isEmpty(strDate)) return null;
    try {
      return new SimpleDateFormat(format, Locale.of("es", "ES")).parse(strDate);
    } catch (ParseException e) {
      log.warn("Error al convertir fecha {} a Date", strDate, e);
      return null;
    }
  }

  public static Date convertDateStrToDate(String strDate) {
    return convertDateStrToDate(strDate, DATE_FORMAT_DATE_DD_MM_YYYY_SLASH);
  }

  public static java.sql.Date convertDateSQLStrToDate(String strDate) {
    Date targetDate = convertDateStrToDate(strDate, DATE_FORMAT_DATE_DD_MM_YYYY_SLASH);
    return new java.sql.Date(targetDate.getTime());
  }

  public static String convertDateToDefaultFormat(Date date) {
    return convertDateToFormat(date, "HH:mm:ss dd/MM/yyyy");
  }

  public static boolean isEmpty(String str) {
    return str == null || str.isEmpty();
  }

  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static boolean isNumber(String value) {
    return NumberUtils.isNumber(value);
  }

  public static boolean isEmpty(Collection<?> collection) {
    return collection == null || collection.isEmpty();
  }

  public static boolean isNotEmpty(Collection<?> collection) {
    return !isEmpty(collection);
  }

  public static Integer convertDecimalFormatToInteger(String decimal) {

    if (isEmpty(decimal)) return null;

    try {
      String newDecimal =
          decimal.replaceAll(Pattern.quote("."), "").replaceAll(Pattern.quote(","), ".");
      return Double.valueOf(newDecimal).intValue();
    } catch (Exception e) {
      log.warn("Error al convertir Decimal a Integer", e);
    }
    return null;
  }

  public static Date addMinutesToCurrentDate(Integer minutes) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.add(Calendar.MINUTE, minutes);
    return calendar.getTime();
  }

  public static BigDecimal convertToBigDecimalFromString(String str) {
    if (isEmpty(str)) return BigDecimal.ZERO;
    return BigDecimal.valueOf(
        Double.parseDouble(
            str.replaceAll(Pattern.quote("."), "").replaceAll(Pattern.quote(","), ".")));
  }

  public static BigDecimal convertToBigDecimalFromStringDotDecimal(String str) {
    if (isEmpty(str)) return BigDecimal.ZERO;
    return BigDecimal.valueOf(Double.parseDouble(str));
  }

  public static BigDecimal parseDouble(BigDecimal value) {
    if (value == null) return BigDecimal.ZERO.setScale(4, RoundingMode.HALF_UP);
    return value.setScale(4, RoundingMode.HALF_UP);
  }

  public static BigDecimal parseDouble(double value) {
    return parseDouble(BigDecimal.valueOf(value));
  }

  public static BigDecimal parseDouble(double value, int scale) {
    return parseDouble(BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP));
  }

  public static long getDaysUntilTodayForDate(Date targetDate, String format) {
    if (targetDate == null) return 0;
    long diff = new Date().getTime() - targetDate.getTime();
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
  }

  public static long getDaysUntilTodayForDate(String date, String format) {
    Date targetDate = convertDateStrToDate(date, format);
    if (targetDate == null) return 0;
    long diff = new Date().getTime() - targetDate.getTime();
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
  }

  public static long getDaysDiff(Date targetDate, Date toDate) {
    if (targetDate == null) return 0;
    long diff = toDate.getTime() - targetDate.getTime();
    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
  }

  public static long getHoursUntilTodayForDate(Date targetDate) {
    if (targetDate == null) return 0;
    long diff = new Date().getTime() - targetDate.getTime();
    return TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
  }

  public static long getDaysFromPeriod(Integer period) {
    if (period == null) return 0;
    return period * 30;
  }

  public static BigDecimal getOverPercentFromValues(BigDecimal from, BigDecimal to) {
    return getPercentFromValues(from, to).subtract(BigDecimal.valueOf(100));
  }

  public static BigDecimal getPercentFromValues(BigDecimal from, BigDecimal to) {

    if (to == null || to.doubleValue() == 0) return from;

    if (to.doubleValue() <= 0) return BigDecimal.ZERO;
    return to.multiply(BigDecimal.valueOf(100)).divide(from, 4, RoundingMode.HALF_UP);
  }

  public static boolean isInRange(BigDecimal value, BigDecimal minValue, BigDecimal maxValue) {
    if (value == null || minValue == null) {
      return false;
    }
    value = value.setScale(0, RoundingMode.UP);
    if (maxValue == null) {
      return value.doubleValue() >= minValue.doubleValue();
    } else {
      return value.doubleValue() >= minValue.doubleValue()
          && value.doubleValue() <= maxValue.doubleValue();
    }
  }

  public static Date getDateFromPeriod(String period) {
    return getDateFromPeriod(period, new Date());
  }

  public static Date getDateFromPeriod(String period, Date date) {

    if (period == null) return null;

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MONTH, Integer.parseInt(period));
    return calendar.getTime();
  }

  public static Date changeDateToLastDayOfMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
    return calendar.getTime();
  }

  public static Date getFirstDateActualYear() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MONTH, Calendar.JANUARY);
    calendar.set(Calendar.DATE, 1);
    return calendar.getTime();
  }

  public static Date getLastDateActualYear() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MONTH, Calendar.DECEMBER);
    calendar.set(Calendar.DATE, 31);
    return calendar.getTime();
  }

  public static Date getFirstDateActualMonth() {
    return getFirstDateActualMonth(new Date());
  }

  public static Date getLastDateActualMonth() {
    return getLastDateActualMonth(new Date());
  }

  public static Date getFirstDateActualMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
    return calendar.getTime();
  }

  public static Date getLastDateActualMonth(Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
    return calendar.getTime();
  }

  public static String getTimeFormatFromMillis(long millis) {
    Duration duration = Duration.ofMillis(millis);
    long seconds = duration.getSeconds();
    long HH = seconds / 3600;
    long MM = (seconds % 3600) / 60;
    long SS = seconds % 60;
    return "%02d:%02d:%02d".formatted(HH, MM, SS);
  }
}
