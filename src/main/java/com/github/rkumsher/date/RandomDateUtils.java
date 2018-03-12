package com.github.rkumsher.date;

import static com.github.rkumsher.date.DateUtils.LEAP_DAY;
import static com.github.rkumsher.number.RandomNumberUtils.randomInt;
import static com.github.rkumsher.number.RandomNumberUtils.randomLong;
import static com.github.rkumsher.number.RandomNumberUtils.randomNegativeInt;
import static com.github.rkumsher.number.RandomNumberUtils.randomNegativeLong;
import static com.github.rkumsher.number.RandomNumberUtils.randomPositiveInt;
import static com.github.rkumsher.number.RandomNumberUtils.randomPositiveLong;
import static com.google.common.base.Preconditions.*;
import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MONTHS;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Date;

import org.apache.commons.lang3.RandomUtils;

import com.github.rkumsher.collection.IterableUtils;
import com.github.rkumsher.enums.RandomEnumUtils;

/**
 * Utility library to return random dates, e.g., {@link Instant}s, {@link ZonedDateTime}s, {@link
 * LocalDate}s, {@link Date}s, etc.
 *
 * <p>Note: All returned dates will be between 1970 and 9999.
 */
public final class RandomDateUtils {

  private static final ZoneId UTC = ZoneId.of("UTC").normalized();
  private static final ZoneOffset UTC_OFFSET = ZoneOffset.UTC;
  private static final int LEAP_YEAR = 2004;
  private static final int MAX_ZONE_OFFSET_SECONDS = 64800;
  private static final int MIN_YEAR = 1970;
  private static final int MAX_YEAR = 9999;
  /** 1970-01-01T00:00:00Z. */
  static final Instant MIN_INSTANT = Instant.ofEpochMilli(0);
  /** December 31st, 9999. */
  static final Instant MAX_INSTANT =
      Instant.ofEpochMilli(
          LocalDate.of(MAX_YEAR, 12, 31).atStartOfDay(UTC).toInstant().toEpochMilli());

  private RandomDateUtils() {}

  /**
   * Returns a random {@link ZonedDateTime} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link ZonedDateTime}
   */
  public static ZonedDateTime randomZonedDateTime() {
    return ZonedDateTime.ofInstant(randomInstant(), UTC);
  }

  /**
   * Returns a random {@link ZonedDateTime} within the specified range.
   *
   * @param startInclusive the earliest {@link ZonedDateTime} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link ZonedDateTime}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static ZonedDateTime randomZonedDateTime(
      ZonedDateTime startInclusive, ZonedDateTime endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    Instant instant = randomInstant(startInclusive.toInstant(), endExclusive.toInstant());
    return ZonedDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link ZonedDateTime} that is after the current system clock.
   *
   * @return the random {@link ZonedDateTime}
   */
  public static ZonedDateTime randomFutureZonedDateTime() {
    return randomZonedDateTimeAfter(ZonedDateTime.now());
  }

  /**
   * Returns a random {@link ZonedDateTime} that is after the given {@link ZonedDateTime}.
   *
   * @param after the value that returned {@link ZonedDateTime} must be after
   * @return the random {@link ZonedDateTime}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static ZonedDateTime randomZonedDateTimeAfter(ZonedDateTime after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomInstantAfter(after.toInstant());
    return ZonedDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link ZonedDateTime} that is before the current system clock.
   *
   * @return the random {@link ZonedDateTime}
   */
  public static ZonedDateTime randomPastZonedDateTime() {
    return randomZonedDateTimeBefore(ZonedDateTime.now());
  }

  /**
   * Returns a random {@link ZonedDateTime} that is before the given {@link ZonedDateTime}.
   *
   * @param before the value that returned {@link ZonedDateTime} must be before
   * @return the random {@link ZonedDateTime}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static ZonedDateTime randomZonedDateTimeBefore(ZonedDateTime before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomInstantBefore(before.toInstant());
    return ZonedDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link OffsetDateTime} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link OffsetDateTime}
   */
  public static OffsetDateTime randomOffsetDateTime() {
    return OffsetDateTime.ofInstant(randomInstant(), UTC);
  }

  /**
   * Returns a random {@link OffsetDateTime} within the specified range.
   *
   * @param startInclusive the earliest {@link OffsetDateTime} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link OffsetDateTime}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static OffsetDateTime randomOffsetDateTime(
      OffsetDateTime startInclusive, OffsetDateTime endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    Instant instant = randomInstant(startInclusive.toInstant(), endExclusive.toInstant());
    return OffsetDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link OffsetDateTime} that is after the current system clock.
   *
   * @return the random {@link OffsetDateTime}
   */
  public static OffsetDateTime randomFutureOffsetDateTime() {
    return randomOffsetDateTimeAfter(OffsetDateTime.now());
  }

  /**
   * Returns a random {@link OffsetDateTime} that is after the given {@link OffsetDateTime}.
   *
   * @param after the value that returned {@link OffsetDateTime} must be after
   * @return the random {@link OffsetDateTime}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static OffsetDateTime randomOffsetDateTimeAfter(OffsetDateTime after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomInstantAfter(after.toInstant());
    return OffsetDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link OffsetDateTime} that is before the current system clock.
   *
   * @return the random {@link OffsetDateTime}
   */
  public static OffsetDateTime randomPastOffsetDateTime() {
    return randomOffsetDateTimeBefore(OffsetDateTime.now());
  }

  /**
   * Returns a random {@link OffsetDateTime} that is before the given {@link OffsetDateTime}.
   *
   * @param before the value that returned {@link OffsetDateTime} must be before
   * @return the random {@link OffsetDateTime}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static OffsetDateTime randomOffsetDateTimeBefore(OffsetDateTime before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomInstantBefore(before.toInstant());
    return OffsetDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link LocalDateTime} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link LocalDateTime}
   */
  public static LocalDateTime randomLocalDateTime() {
    return LocalDateTime.ofInstant(randomInstant(), UTC);
  }

  /**
   * Returns a random {@link LocalDateTime} within the specified range.
   *
   * @param startInclusive the earliest {@link LocalDateTime} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link LocalDateTime}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static LocalDateTime randomLocalDateTime(
      LocalDateTime startInclusive, LocalDateTime endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    Instant startInstant = startInclusive.toInstant(UTC_OFFSET);
    Instant endInstant = endExclusive.toInstant(UTC_OFFSET);
    Instant instant = randomInstant(startInstant, endInstant);
    return LocalDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link LocalDateTime} that is after the current system clock.
   *
   * @return the random {@link LocalDateTime}
   */
  public static LocalDateTime randomFutureLocalDateTime() {
    return randomLocalDateTimeAfter(LocalDateTime.now());
  }

  /**
   * Returns a random {@link LocalDateTime} that is after the given {@link LocalDateTime}.
   *
   * @param after the value that returned {@link LocalDateTime} must be after
   * @return the random {@link LocalDateTime}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static LocalDateTime randomLocalDateTimeAfter(LocalDateTime after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomInstantAfter(after.toInstant(UTC_OFFSET));
    return LocalDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link LocalDateTime} that is before the current system clock.
   *
   * @return the random {@link LocalDateTime}
   */
  public static LocalDateTime randomPastLocalDateTime() {
    return randomLocalDateTimeBefore(LocalDateTime.now());
  }

  /**
   * Returns a random {@link LocalDateTime} that is before the given {@link LocalDateTime}.
   *
   * @param before the value that returned {@link LocalDateTime} must be before
   * @return the random {@link LocalDateTime}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static LocalDateTime randomLocalDateTimeBefore(LocalDateTime before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomInstantBefore(before.toInstant(UTC_OFFSET));
    return LocalDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link LocalDate} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link LocalDate}
   */
  public static LocalDate randomLocalDate() {
    return randomInstant().atZone(UTC).toLocalDate();
  }

  /**
   * Returns a random {@link LocalDate} within the specified range.
   *
   * @param startInclusive the earliest {@link LocalDate} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link LocalDate}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static LocalDate randomLocalDate(LocalDate startInclusive, LocalDate endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    Instant startInstant = startInclusive.atStartOfDay().toInstant(UTC_OFFSET);
    Instant endInstant = endExclusive.atStartOfDay().toInstant(UTC_OFFSET);
    Instant instant = randomInstant(startInstant, endInstant);
    return instant.atZone(UTC).toLocalDate();
  }

  /**
   * Returns a random {@link LocalDate} that is after the current system clock.
   *
   * @return the random {@link LocalDate}
   */
  public static LocalDate randomFutureLocalDate() {
    return randomLocalDateAfter(LocalDate.now());
  }

  /**
   * Returns a random {@link LocalDate} that is after the given {@link LocalDate}.
   *
   * @param after the value that returned {@link LocalDate} must be after
   * @return the random {@link LocalDate}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static LocalDate randomLocalDateAfter(LocalDate after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomInstantAfter(after.atStartOfDay(UTC).plus(1, DAYS).toInstant());
    return instant.atZone(UTC).toLocalDate();
  }

  /**
   * Returns a random {@link LocalDate} that is before the current system clock.
   *
   * @return the random {@link LocalDate}
   */
  public static LocalDate randomPastLocalDate() {
    return randomLocalDateBefore(LocalDate.now());
  }

  /**
   * Returns a random {@link LocalDate} that is before the given {@link LocalDate}.
   *
   * @param before the value that returned {@link LocalDate} must be before
   * @return the random {@link LocalDate}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static LocalDate randomLocalDateBefore(LocalDate before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomInstantBefore(before.atStartOfDay(UTC).toInstant());
    return instant.atZone(UTC).toLocalDate();
  }

  /**
   * Returns a random {@link Date} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link Date}
   */
  public static Date randomDate() {
    return Date.from(randomInstant());
  }

  /**
   * Returns a random {@link Date} within the specified range.
   *
   * @param startInclusive the earliest {@link Date} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link Date}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static Date randomDate(Date startInclusive, Date endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    Instant startInstant = startInclusive.toInstant();
    Instant endInstant = endExclusive.toInstant();
    Instant instant = randomInstant(startInstant, endInstant);
    return Date.from(instant);
  }

  /**
   * Returns a random {@link Date} that is after the current system clock.
   *
   * @return the random {@link Date}
   */
  public static Date randomFutureDate() {
    return randomDateAfter(new Date());
  }

  /**
   * Returns a random {@link Date} that is after the given {@link Date}.
   *
   * @param after the value that returned {@link Date} must be after
   * @return the random {@link Date}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static Date randomDateAfter(Date after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomInstantAfter(after.toInstant());
    return Date.from(instant);
  }

  /**
   * Returns a random {@link Date} that is before the current system clock.
   *
   * @return the random {@link Date}
   */
  public static Date randomPastDate() {
    return randomDateBefore(new Date());
  }

  /**
   * Returns a random {@link Date} that is before the given {@link Date}.
   *
   * @param before the value that returned {@link Date} must be before
   * @return the random {@link Date}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static Date randomDateBefore(Date before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomInstantBefore(before.toInstant());
    return Date.from(instant);
  }

  /**
   * Returns a random {@link Instant} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link Instant}
   */
  public static Instant randomInstant() {
    return randomInstant(MIN_INSTANT, MAX_INSTANT);
  }

  /**
   * Returns a random {@link Instant} within the specified range.
   *
   * @param startInclusive the earliest {@link Instant} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link Instant}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static Instant randomInstant(Instant startInclusive, Instant endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    checkArgument(!startInclusive.isAfter(endExclusive), "End must be on or after start");
    checkArgument(
        startInclusive.equals(MIN_INSTANT) || startInclusive.isAfter(MIN_INSTANT),
        "Start must be on or after %s",
        MIN_INSTANT);
    checkArgument(
        endExclusive.equals(MAX_INSTANT) || endExclusive.isBefore(MAX_INSTANT),
        "End must be on or before %s",
        MAX_INSTANT);
    return Instant.ofEpochMilli(
        RandomUtils.nextLong(startInclusive.toEpochMilli(), endExclusive.toEpochMilli()));
  }

  /**
   * Returns a random {@link Instant} that is after the current system clock.
   *
   * @return the random {@link Instant}
   */
  public static Instant randomFutureInstant() {
    return randomInstantAfter(Instant.now());
  }

  /**
   * Returns a random {@link Instant} that is after the given {@link Instant}.
   *
   * @param after the value that returned {@link Instant} must be after
   * @return the random {@link Instant}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static Instant randomInstantAfter(Instant after) {
    checkArgument(after != null, "After must be non-null");
    checkArgument(after.isBefore(MAX_INSTANT), "Cannot produce date after %s", MAX_INSTANT);
    return randomInstant(after.plus(1, MILLIS), MAX_INSTANT);
  }

  /**
   * Returns a random {@link Instant} that is before the current system clock.
   *
   * @return the random {@link Instant}
   */
  public static Instant randomPastInstant() {
    return randomInstantBefore(Instant.now());
  }

  /**
   * Returns a random {@link Instant} that is before the given {@link Instant}.
   *
   * @param before the value that returned {@link Instant} must be before
   * @return the random {@link Instant}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static Instant randomInstantBefore(Instant before) {
    checkArgument(before != null, "Before must be non-null");
    checkArgument(before.isAfter(MIN_INSTANT), "Cannot produce date before %s", MIN_INSTANT);
    return randomInstant(MIN_INSTANT, before);
  }

  /**
   * Returns a random {@link LocalTime}.
   *
   * @return the random {@link LocalTime}
   */
  public static LocalTime randomLocalTime() {
    return LocalTime.ofNanoOfDay(random(NANO_OF_DAY));
  }

  /**
   * Returns a random {@link LocalTime} within the specified range.
   *
   * @param startInclusive the earliest {@link LocalTime} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link LocalTime}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static LocalTime randomLocalTime(LocalTime startInclusive, LocalTime endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    long nanoOfDay = random(NANO_OF_DAY, startInclusive.toNanoOfDay(), endExclusive.toNanoOfDay());
    return LocalTime.ofNanoOfDay(nanoOfDay);
  }

  /**
   * Returns a random {@link LocalTime} that is after the given {@link LocalTime}.
   *
   * @param after the value that the returned {@link LocalTime} must be after
   * @return the random {@link LocalTime}
   * @throws IllegalArgumentException if after is null or if after is before {@link LocalTime#MAX}
   */
  public static LocalTime randomLocalTimeAfter(LocalTime after) {
    checkArgument(after != null, "After must be non-null");
    checkArgument(after.isBefore(LocalTime.MAX), "After must be before %s", LocalTime.MAX);
    long nanoOfDay = randomAfter(NANO_OF_DAY, after.toNanoOfDay() + 1);
    return LocalTime.ofNanoOfDay(nanoOfDay);
  }

  /**
   * Returns a random {@link LocalTime} that is before the given {@link LocalTime}.
   *
   * @param before the value that returned {@link LocalTime} must be before
   * @return the random {@link LocalTime}
   * @throws IllegalArgumentException if before is null or if before is after {@link LocalTime#MIN}
   */
  public static LocalTime randomLocalTimeBefore(LocalTime before) {
    checkArgument(before != null, "Before must be non-null");
    checkArgument(before.isAfter(LocalTime.MIN), "Before must be after %s", LocalTime.MIN);
    long nanoOfDay = randomBefore(NANO_OF_DAY, before.toNanoOfDay());
    return LocalTime.ofNanoOfDay(nanoOfDay);
  }

  /**
   * Returns a random valid value for the given {@link TemporalField} between <code>
   * TemporalField.range().min()</code> and <code>TemporalField.range().max()</code>. For example,
   * <code>random({@link ChronoField#HOUR_OF_DAY})</code> will return a random value between 0-23.
   *
   * <p>Note: This will never return {@link Long#MAX_VALUE}. Even if it's a valid value for the
   * given {@link TemporalField}.
   *
   * @param field the {@link TemporalField} to return a valid value for
   * @return the random value
   */
  public static long random(TemporalField field) {
    long max = Math.min(field.range().getMaximum(), Long.MAX_VALUE - 1);
    return randomLong(field.range().getMinimum(), max + 1);
  }

  /**
   * Returns a random valid value for the given {@link TemporalField} between <code>
   * startInclusive</code> and <code>endExclusive</code>.
   *
   * @param field the {@link TemporalField} to return a valid value for
   * @param startInclusive the smallest value that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random value
   * @throws IllegalArgumentException if startInclusive is on or before <code>
   *     TemporalField.range().min()
   *     </code>, if endExclusive is on or after <code>TemporalField.range().max()</code>, or if
   *     startInclusive is after after endExclusive
   */
  public static long random(TemporalField field, long startInclusive, long endExclusive) {
    long min = field.range().getMinimum();
    long max = field.range().getMaximum();
    checkArgument(startInclusive >= min, "Start must be on or after %s", min);
    checkArgument(endExclusive <= max, "End must be on or before %s", max);
    checkArgument(startInclusive <= endExclusive, "End must be on or after start");
    min = Math.max(startInclusive, field.range().getMinimum());
    max = Math.min(endExclusive, field.range().getMaximum());
    return randomLong(min, max);
  }

  /**
   * Returns a random valid value for the given {@link TemporalField} between <code>
   * after</code> and <code>TemporalField.range().max()</code>. For example, <code>
   * randomAfter({@link ChronoField#HOUR_OF_DAY}, 12)</code> will return a random value between
   * 13-23.
   *
   * <p>Note: This will never return {@link Long#MAX_VALUE}. Even if it's a valid value for the
   * given {@link TemporalField}.
   *
   * @param field the {@link TemporalField} to return a valid value for
   * @param after the value that the returned value must be after
   * @return the random value
   * @throws IllegalArgumentException if after is before <code>
   *     TemporalField.range().min()
   *     </code> or if after is on or after <code>TemporalField.range().max()</code>
   */
  public static long randomAfter(TemporalField field, long after) {
    Long min = field.range().getMinimum();
    Long max = field.range().getMaximum();
    checkArgument(after < max, "After must be before %s", max);
    checkArgument(after >= min, "After must be on or after %s", min);
    return randomLong(after + 1, Math.min(max, Long.MAX_VALUE - 1) + 1);
  }

  /**
   * Returns a random valid value for the given {@link TemporalField} between <code>
   * TemporalField.range().min()</code> and <code>before</code>. For example, <code>
   * randomBefore({@link ChronoField#HOUR_OF_DAY}, 13)</code> will return a random value between
   * 0-12.
   *
   * @param field the {@link TemporalField} to return a valid value for
   * @param before the value that the returned value must be before
   * @return the random value
   * @throws IllegalArgumentException if before is after <code>
   *     TemporalField.range().max()
   *     </code> or if before is on or before <code>TemporalField.range().min()</code>
   */
  public static long randomBefore(TemporalField field, long before) {
    long min = field.range().getMinimum();
    long max = field.range().getMaximum();
    checkArgument(before > min, "Before must be after %s", min);
    checkArgument(before <= max, "Before must be on or before %s", max);
    return randomLong(min, before);
  }

  /**
   * Returns a random {@link MonthDay} between January 1st and December 31st. Includes leap day if
   * the current year is a leap year.
   *
   * @return the random {@link MonthDay}
   */
  public static MonthDay randomMonthDay() {
    return randomMonthDay(Year.now().isLeap());
  }

  /**
   * Returns a random {@link MonthDay} between January 1st and December 31st.
   *
   * @param includeLeapDay whether or not to include leap day
   * @return the random {@link MonthDay}
   */
  public static MonthDay randomMonthDay(boolean includeLeapDay) {
    Month month = randomMonth();
    int dayOfMonth = RandomUtils.nextInt(1, month.maxLength() + 1);
    MonthDay monthDay = MonthDay.of(month, dayOfMonth);
    if (!includeLeapDay && DateUtils.isLeapDay(monthDay)) {
      return randomMonthDay(false);
    }
    return monthDay;
  }

  /**
   * Returns a random {@link MonthDay} within the specified range. Includes leap day if the current
   * year is a leap year.
   *
   * @param startInclusive the earliest {@link MonthDay} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link MonthDay}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static MonthDay randomMonthDay(MonthDay startInclusive, MonthDay endExclusive) {
    return randomMonthDay(startInclusive, endExclusive, Year.now().isLeap());
  }

  /**
   * Returns a random {@link MonthDay} within the specified range.
   *
   * @param startInclusive the earliest {@link MonthDay} that can be returned
   * @param endExclusive the upper bound (not included)
   * @param includeLeapDay whether or not to include leap day
   * @return the random {@link MonthDay}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null, if endExclusive is
   *     earlier than startInclusive, or if startInclusive or endExclusive are leap day and
   *     includeLeapDay is false
   */
  public static MonthDay randomMonthDay(
      MonthDay startInclusive, MonthDay endExclusive, boolean includeLeapDay) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    checkArgument(
        includeLeapDay || !startInclusive.equals(LEAP_DAY) || !endExclusive.equals(LEAP_DAY),
        "Start and End can't both be leap day");
    int year = includeLeapDay ? LEAP_YEAR : LEAP_YEAR - 1;
    LocalDate start = startInclusive.atYear(year);
    LocalDate end = endExclusive.atYear(year);
    LocalDate localDate = randomLocalDate(start, end);
    return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
  }

  /**
   * Returns a random {@link MonthDay} that is after the given {@link MonthDay}. Includes leap day
   * if the current year is a leap year.
   *
   * @param after the value that returned {@link MonthDay} must be after
   * @return the random {@link MonthDay}
   * @throws IllegalArgumentException if after is null or if after is last day of year (December
   *     31st)
   */
  public static MonthDay randomMonthDayAfter(MonthDay after) {
    return randomMonthDayAfter(after, Year.now().isLeap());
  }

  /**
   * Returns a random {@link MonthDay} that is after the given {@link MonthDay}.
   *
   * @param after the value that returned {@link MonthDay} must be after
   * @param includeLeapDay whether or not to include leap day
   * @return the random {@link MonthDay}
   * @throws IllegalArgumentException if after is null or if after is last day of year (December
   *     31st)
   */
  public static MonthDay randomMonthDayAfter(MonthDay after, boolean includeLeapDay) {
    checkArgument(after != null, "After must be non-null");
    checkArgument(after.isBefore(MonthDay.of(DECEMBER, 31)), "After must be before December 31st");
    int year = includeLeapDay ? LEAP_YEAR : LEAP_YEAR - 1;
    LocalDate start = after.atYear(year).plus(1, DAYS);
    LocalDate end = Year.of(year + 1).atDay(1);
    LocalDate localDate = randomLocalDate(start, end);
    return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
  }

  /**
   * Returns a random {@link MonthDay} that is before the given {@link MonthDay}. Includes leap day
   * if the current year is a leap year.
   *
   * @param before the value that returned {@link MonthDay} must be before
   * @return the random {@link MonthDay}
   * @throws IllegalArgumentException if before is null or if before is first day of year (January
   *     1st)
   */
  public static MonthDay randomMonthDayBefore(MonthDay before) {
    return randomMonthDayBefore(before, Year.now().isLeap());
  }

  /**
   * Returns a random {@link MonthDay} that is before the given {@link MonthDay}.
   *
   * @param before the value that returned {@link MonthDay} must be before
   * @param includeLeapDay whether or not to include leap day
   * @return the random {@link MonthDay}
   * @throws IllegalArgumentException if before is null or if before is first day of year (January
   *     1st)
   */
  public static MonthDay randomMonthDayBefore(MonthDay before, boolean includeLeapDay) {
    checkArgument(before != null, "Before must be non-null");
    checkArgument(before.isAfter(MonthDay.of(JANUARY, 1)), "Before must be after January 1st");
    int year = includeLeapDay ? LEAP_YEAR : LEAP_YEAR - 1;
    LocalDate startOfYear = Year.of(year).atDay(1);
    LocalDate end = before.atYear(year);
    LocalDate localDate = randomLocalDate(startOfYear, end);
    return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
  }

  /**
   * Returns a random {@link YearMonth} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link YearMonth}
   */
  public static YearMonth randomYearMonth() {
    return YearMonth.of(randomYear().getValue(), randomMonth());
  }

  /**
   * Returns a random {@link YearMonth} within the specified range.
   *
   * @param startInclusive the earliest {@link YearMonth} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link YearMonth}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null or if endExclusive
   *     is earlier than startInclusive
   */
  public static YearMonth randomYearMonth(YearMonth startInclusive, YearMonth endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    LocalDate start = startInclusive.atDay(1);
    LocalDate end = endExclusive.atDay(1);
    LocalDate localDate = randomLocalDate(start, end);
    return YearMonth.of(localDate.getYear(), localDate.getMonth());
  }

  /**
   * Returns a random {@link YearMonth} that is after the current system clock.
   *
   * @return the random {@link YearMonth}
   */
  public static YearMonth randomFutureYearMonth() {
    return randomYearMonthAfter(YearMonth.now());
  }

  /**
   * Returns a random {@link YearMonth} that is after the given {@link YearMonth}.
   *
   * @param after the value that returned {@link YearMonth} must be after
   * @return the random {@link YearMonth}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static YearMonth randomYearMonthAfter(YearMonth after) {
    checkArgument(after != null, "After must be non-null");
    LocalDate start = after.plus(1, MONTHS).atDay(1);
    LocalDate localDate = randomLocalDateAfter(start);
    return YearMonth.of(localDate.getYear(), localDate.getMonth());
  }

  /**
   * Returns a random {@link YearMonth} that is before the current system clock.
   *
   * @return the random {@link YearMonth}
   */
  public static YearMonth randomPastYearMonth() {
    return randomYearMonthBefore(YearMonth.now());
  }

  /**
   * Returns a random {@link YearMonth} that is before the given {@link YearMonth}.
   *
   * @param before the value that returned {@link YearMonth} must be before
   * @return the random {@link YearMonth}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static YearMonth randomYearMonthBefore(YearMonth before) {
    checkArgument(before != null, "Before must be non-null");
    LocalDate start = before.atDay(1);
    LocalDate localDate = randomLocalDateBefore(start);
    return YearMonth.of(localDate.getYear(), localDate.getMonth());
  }

  /**
   * Returns a random {@link Year} between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random {@link Year}
   */
  public static Year randomYear() {
    return Year.of(RandomUtils.nextInt(MIN_YEAR, MAX_YEAR));
  }

  /**
   * Returns a random {@link Year} within the specified range.
   *
   * @param startInclusive the earliest {@link Year} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link Year}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null, if endExclusive is
   *     earlier than startInclusive, if either are before {@link RandomDateUtils#MIN_INSTANT}, or
   *     if either are after {@link RandomDateUtils#MAX_INSTANT}
   */
  public static Year randomYear(Year startInclusive, Year endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    return randomYear(startInclusive.getValue(), endExclusive.getValue());
  }

  /**
   * Returns a random {@link Year} within the specified range.
   *
   * @param startInclusive the earliest {@link Year} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link Year}
   * @throws IllegalArgumentException if endExclusive is earlier than startInclusive, if end is
   *     before {@link RandomDateUtils#MIN_INSTANT}, or if start is after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static Year randomYear(int startInclusive, int endExclusive) {
    checkArgument(startInclusive < MAX_YEAR, "Start must be before %s", MAX_YEAR);
    checkArgument(startInclusive >= MIN_YEAR, "Start must be on or after %s", MIN_YEAR);
    checkArgument(endExclusive > MIN_YEAR, "End must be after %s", MIN_YEAR);
    checkArgument(endExclusive <= MAX_YEAR, "End must be on or before %s", MAX_YEAR);
    checkArgument(startInclusive <= endExclusive, "End must be on or after start");
    return Year.of(RandomUtils.nextInt(startInclusive, endExclusive));
  }

  /**
   * Returns a random {@link Year} that is after the current system clock.
   *
   * @return the random {@link Year}
   */
  public static Year randomFutureYear() {
    return randomYearAfter(Year.now());
  }

  /**
   * Returns a random {@link Year} that is after the given {@link Year}.
   *
   * @param after the value that returned {@link Year} must be after
   * @return the random {@link Year}
   * @throws IllegalArgumentException if after is null or if after greater than or equal to {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static Year randomYearAfter(Year after) {
    checkArgument(after != null, "After must be non-null");
    return randomYearAfter(after.getValue());
  }

  /**
   * Returns a random {@link Year} that is after the given {@link Year}.
   *
   * @param after the value that returned {@link Year} must be after
   * @return the random {@link Year}
   * @throws IllegalArgumentException if after greater than or equal to {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static Year randomYearAfter(int after) {
    checkArgument(after < MAX_YEAR, "After must be before %s", MAX_YEAR);
    return Year.of(RandomUtils.nextInt(after + 1, MAX_YEAR));
  }

  /**
   * Returns a random {@link Year} that is before the current system clock.
   *
   * @return the random {@link Year}
   */
  public static Year randomPastYear() {
    return randomYearBefore(Year.now());
  }

  /**
   * Returns a random {@link Year} that is before the given {@link Year}.
   *
   * @param before the value that returned {@link Year} must be before
   * @return the random {@link Year}
   * @throws IllegalArgumentException if before is null or if before is less than or equal to {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static Year randomYearBefore(Year before) {
    checkArgument(before != null, "Before must be non-null");
    return randomYearBefore(before.getValue());
  }

  /**
   * Returns a random {@link Year} that is before the given {@link RandomDateUtils#MIN_INSTANT}.
   *
   * @param before the value that returned {@link Year} must be before
   * @return the random {@link Year}
   * @throws IllegalArgumentException if before is less than or equal to 1970
   */
  public static Year randomYearBefore(int before) {
    checkArgument(before > MIN_YEAR, "Before must be after %s", MIN_YEAR);
    return Year.of(RandomUtils.nextInt(MIN_YEAR, before));
  }

  /**
   * Returns a random {@link Clock} in the UTC {@link ZoneId} with a random instant in time.
   *
   * @return the random {@link Clock}
   */
  public static Clock randomFixedUtcClock() {
    return Clock.fixed(randomInstant(), UTC);
  }

  /**
   * Returns a random {@link Clock} in a random {@link ZoneId} with a random instant in time.
   *
   * @return the random {@link Clock}
   */
  public static Clock randomFixedClock() {
    return Clock.fixed(randomInstant(), randomZoneId());
  }

  /**
   * Returns a random {@link ZoneId} from {@link ZoneOffset#getAvailableZoneIds()}.
   *
   * @return the random {@link ZoneId}
   */
  public static ZoneId randomZoneId() {
    return ZoneId.of(IterableUtils.randomFrom(ZoneOffset.getAvailableZoneIds()));
  }

  /**
   * Returns a random {@link DayOfWeek}.
   *
   * @return the random {@link DayOfWeek}
   */
  public static DayOfWeek randomDayOfWeek() {
    return RandomEnumUtils.random(DayOfWeek.class);
  }

  /**
   * Returns a random {@link Month}.
   *
   * @return the random {@link Month}
   */
  public static Month randomMonth() {
    return RandomEnumUtils.random(Month.class);
  }

  /**
   * Returns a random {@link ZoneOffset} (-18:00 to +18:00).
   *
   * @return the random {@link ZoneOffset}
   */
  public static ZoneOffset randomZoneOffset() {
    int totalSeconds =
        MAX_ZONE_OFFSET_SECONDS - RandomUtils.nextInt(0, MAX_ZONE_OFFSET_SECONDS * 2 + 1);
    return ZoneOffset.ofTotalSeconds(totalSeconds);
  }

  /**
   * Returns a random {@link Period} which may be positive, negative, or {@link Period#ZERO}.
   *
   * @return the random {@link Period}
   */
  public static Period randomPeriod() {
    return Period.of(randomInt(), randomInt(), randomInt());
  }

  /**
   * Returns a random {@link Period} which will be positive.
   *
   * @return the random {@link Period}
   */
  public static Period randomPositivePeriod() {
    return Period.of(randomPositiveInt(), randomPositiveInt(), randomPositiveInt());
  }

  /**
   * Returns a random {@link Period} which will be negative.
   *
   * @return the random {@link Period}
   */
  public static Period randomNegativePeriod() {
    return Period.of(randomNegativeInt(), randomInt(), randomInt());
  }

  /**
   * Returns a random {@link Duration} which may be positive, negative, or {@link Duration#ZERO}.
   *
   * @return the random {@link Duration}
   */
  public static Duration randomDuration() {
    return Duration.ofNanos(randomLong());
  }

  /**
   * Returns a random {@link Duration} which will be positive.
   *
   * @return the random {@link Duration}
   */
  public static Duration randomPositiveDuration() {
    return Duration.ofNanos(randomPositiveLong());
  }

  /**
   * Returns a random {@link Duration} which will be negative.
   *
   * @return the random {@link Duration}
   */
  public static Duration randomNegativeDuration() {
    return Duration.ofNanos(randomNegativeLong());
  }
}
