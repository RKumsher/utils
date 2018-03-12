package com.github.rkumsher.date;

import static com.google.common.base.Preconditions.*;
import static java.time.Month.FEBRUARY;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/** Utility library for working with {@link Date}s. */
public final class DateUtils {

  /** February 29th. */
  static final MonthDay LEAP_DAY = MonthDay.of(FEBRUARY, 29);

  private DateUtils() {}

  /**
   * Returns whether or not the given {@link TemporalAccessor} is leap day (February 29th).
   *
   * @return whether or not the given {@link TemporalAccessor} is leap day
   * @throws IllegalArgumentException if the given {@link TemporalAccessor} does not support {@link
   *     ChronoField#MONTH_OF_YEAR} or {@link ChronoField#DAY_OF_MONTH}
   */
  public static boolean isLeapDay(TemporalAccessor temporal) {
    checkArgument(
        temporal.isSupported(MONTH_OF_YEAR), "%s does not support MONTH_OF_YEAR", temporal);
    checkArgument(temporal.isSupported(DAY_OF_MONTH), "%s does not support DAY_OF_MONTH", temporal);
    MonthDay monthDay = MonthDay.from(temporal);
    return monthDay.equals(LEAP_DAY);
  }

  /**
   * Returns the {@link Date} of midnight at the start of the given {@link Date}.
   *
   * <p>This returns a {@link Date} formed from the given {@link Date} at the time of midnight,
   * 00:00, at the start of this {@link Date}.
   *
   * @return the {@link Date} of midnight at the start of the given {@link Date}
   */
  public static Date atStartOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    return localDateTimeToDate(startOfDay);
  }

  /**
   * Returns the {@link Date} at the end of day of the given {@link Date}.
   *
   * <p>This returns a {@link Date} formed from the given {@link Date} at the time of 1 millisecond
   * prior to midnight the next day.
   *
   * @return the {@link Date} at the end of day of the given {@link Date}j
   */
  public static Date atEndOfDay(Date date) {
    LocalDateTime localDateTime = dateToLocalDateTime(date);
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return localDateTimeToDate(endOfDay);
  }

  private static LocalDateTime dateToLocalDateTime(Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }

  static Date localDateTimeToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
