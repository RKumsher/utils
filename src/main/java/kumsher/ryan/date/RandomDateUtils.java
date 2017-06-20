package kumsher.ryan.date;

import static com.google.common.base.Preconditions.*;
import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MONTHS;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.apache.commons.lang3.RandomUtils;

import kumsher.ryan.collection.IterableUtils;
import kumsher.ryan.enums.RandomEnumUtils;

/**
 * Utility library to return random dates, e.g., {@link Instant}s, {@link ZonedDateTime}s, {@link
 * LocalDate}s, etc..
 */
public class RandomDateUtils {

  private static final ZoneId UTC = ZoneId.of("UTC").normalized();
  private static final ZoneOffset UTC_OFFSET = ZoneOffset.UTC;
  private static final int LEAP_YEAR = 2004;
  private static final MonthDay LEAP_DAY = MonthDay.of(FEBRUARY, 29);
  private static final int MAX_ZONE_OFFSET_SECONDS = 64800;
  static final Instant MIN_INSTANT = Instant.ofEpochMilli(0);
  /* December 31st, 9999.  Limiting to just 4-digit years. */
  static final Instant MAX_INSTANT =
      Instant.ofEpochMilli(LocalDate.of(9999, 12, 31).atStartOfDay(UTC).toInstant().toEpochMilli());

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
    Instant instant = randomInstant(Instant.now().plus(1, MILLIS), MAX_INSTANT);
    return ZonedDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link ZonedDateTime} that is after the given {@link ZonedDateTime}.
   *
   * @param after the value that returned {@link ZonedDateTime} must be after
   * @return the random {@link ZonedDateTime}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static ZonedDateTime randomFutureZonedDateTime(ZonedDateTime after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomFutureInstant(after.toInstant());
    return ZonedDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link ZonedDateTime} that is before the current system clock.
   *
   * @return the random {@link ZonedDateTime}
   */
  public static ZonedDateTime randomPastZonedDateTime() {
    Instant instant = randomInstant(MIN_INSTANT, Instant.now());
    return ZonedDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link ZonedDateTime} that is before the given {@link ZonedDateTime}.
   *
   * @param before the value that returned {@link ZonedDateTime} must be before
   * @return the random {@link ZonedDateTime}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static ZonedDateTime randomPastZonedDateTime(ZonedDateTime before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomPastInstant(before.toInstant());
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
    Instant instant = randomInstant(Instant.now().plus(1, MILLIS), MAX_INSTANT);
    return OffsetDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link OffsetDateTime} that is after the given {@link OffsetDateTime}.
   *
   * @param after the value that returned {@link OffsetDateTime} must be after
   * @return the random {@link OffsetDateTime}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static OffsetDateTime randomFutureOffsetDateTime(OffsetDateTime after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomFutureInstant(after.toInstant());
    return OffsetDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link OffsetDateTime} that is before the current system clock.
   *
   * @return the random {@link OffsetDateTime}
   */
  public static OffsetDateTime randomPastOffsetDateTime() {
    Instant instant = randomInstant(MIN_INSTANT, Instant.now());
    return OffsetDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link OffsetDateTime} that is before the given {@link OffsetDateTime}.
   *
   * @param before the value that returned {@link OffsetDateTime} must be before
   * @return the random {@link OffsetDateTime}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static OffsetDateTime randomPastOffsetDateTime(OffsetDateTime before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomPastInstant(before.toInstant());
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
    Instant instant = randomInstant(Instant.now().plus(1, MILLIS), MAX_INSTANT);
    return LocalDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link LocalDateTime} that is after the given {@link LocalDateTime}.
   *
   * @param after the value that returned {@link LocalDateTime} must be after
   * @return the random {@link LocalDateTime}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static LocalDateTime randomFutureLocalDateTime(LocalDateTime after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomFutureInstant(after.toInstant(UTC_OFFSET));
    return LocalDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link LocalDateTime} that is before the current system clock.
   *
   * @return the random {@link LocalDateTime}
   */
  public static LocalDateTime randomPastLocalDateTime() {
    Instant instant = randomInstant(MIN_INSTANT, Instant.now());
    return LocalDateTime.ofInstant(instant, UTC);
  }

  /**
   * Returns a random {@link LocalDateTime} that is before the given {@link LocalDateTime}.
   *
   * @param before the value that returned {@link LocalDateTime} must be before
   * @return the random {@link LocalDateTime}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static LocalDateTime randomPastLocalDateTime(LocalDateTime before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomPastInstant(before.toInstant(UTC_OFFSET));
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
    Instant instant = randomInstant(Instant.now().plus(1, MILLIS), MAX_INSTANT);
    return instant.atZone(UTC).toLocalDate();
  }

  /**
   * Returns a random {@link LocalDate} that is after the given {@link LocalDate}.
   *
   * @param after the value that returned {@link LocalDate} must be after
   * @return the random {@link LocalDate}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static LocalDate randomFutureLocalDate(LocalDate after) {
    checkArgument(after != null, "After must be non-null");
    Instant instant = randomFutureInstant(after.atStartOfDay(UTC).toInstant());
    return instant.atZone(UTC).toLocalDate();
  }

  /**
   * Returns a random {@link LocalDate} that is before the current system clock.
   *
   * @return the random {@link LocalDate}
   */
  public static LocalDate randomPastLocalDate() {
    Instant instant = randomInstant(MIN_INSTANT, Instant.now());
    return instant.atZone(UTC).toLocalDate();
  }

  /**
   * Returns a random {@link LocalDate} that is before the given {@link LocalDate}.
   *
   * @param before the value that returned {@link LocalDate} must be before
   * @return the random {@link LocalDate}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static LocalDate randomPastLocalDate(LocalDate before) {
    checkArgument(before != null, "Before must be non-null");
    Instant instant = randomPastInstant(before.atStartOfDay(UTC).toInstant());
    return instant.atZone(UTC).toLocalDate();
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
    checkArgument(!startInclusive.isAfter(endExclusive), "End must come on or after start");
    return Instant.ofEpochMilli(
        RandomUtils.nextLong(startInclusive.toEpochMilli(), endExclusive.toEpochMilli()));
  }

  /**
   * Returns a random {@link Instant} that is after the current system clock.
   *
   * @return the random {@link Instant}
   */
  public static Instant randomFutureInstant() {
    return randomInstant(Instant.now().plus(1, MILLIS), MAX_INSTANT);
  }

  /**
   * Returns a random {@link Instant} that is after the given {@link Instant}.
   *
   * @param after the value that returned {@link Instant} must be after
   * @return the random {@link Instant}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static Instant randomFutureInstant(Instant after) {
    checkArgument(after != null, "After must be non-null");
    checkArgument(after.isBefore(MAX_INSTANT), "Cannot produce date after " + MAX_INSTANT);
    return randomInstant(after, MAX_INSTANT);
  }

  /**
   * Returns a random {@link Instant} that is before the current system clock.
   *
   * @return the random {@link Instant}
   */
  public static Instant randomPastInstant() {
    return randomInstant(MIN_INSTANT, Instant.now());
  }

  /**
   * Returns a random {@link Instant} that is before the given {@link Instant}.
   *
   * @param before the value that returned {@link Instant} must be before
   * @return the random {@link Instant}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static Instant randomPastInstant(Instant before) {
    checkArgument(before != null, "Before must be non-null");
    checkArgument(before.isAfter(MIN_INSTANT), "Cannot produce date before " + MIN_INSTANT);
    return randomInstant(MIN_INSTANT, before);
  }

  /**
   * Returns a random {@link MonthDay} between January 1st an December 31st. Includes leap day if
   * the current year is a leap year.
   *
   * @return the random {@link MonthDay}
   */
  public static MonthDay randomMonthDay() {
    return randomMonthDay(Year.now().isLeap());
  }

  /**
   * Returns a random {@link MonthDay} between January 1st an December 31st.
   *
   * @param includeLeapDay whether or not to include leap day
   * @return the random {@link MonthDay}
   */
  public static MonthDay randomMonthDay(boolean includeLeapDay) {
    Month month = randomMonth();
    int dayOfMonth = RandomUtils.nextInt(1, month.maxLength() + 1);
    MonthDay monthDay = MonthDay.of(month, dayOfMonth);
    if (!includeLeapDay && isLeapDay(monthDay)) {
      return randomMonthDay(false);
    }
    return monthDay;
  }

  /**
   * Returns whether or not the given {@link MonthDay} is leap day (February 29th).
   *
   * @return whether or not the given {@link MonthDay} is leap day
   */
  static boolean isLeapDay(MonthDay monthDay) {
    return LEAP_DAY.equals(monthDay);
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
  public static MonthDay randomFutureMonthDay(MonthDay after) {
    return randomFutureMonthDay(after, Year.now().isLeap());
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
  public static MonthDay randomFutureMonthDay(MonthDay after, boolean includeLeapDay) {
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
  public static MonthDay randomPastMonthDay(MonthDay before) {
    return randomPastMonthDay(before, Year.now().isLeap());
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
  public static MonthDay randomPastMonthDay(MonthDay before, boolean includeLeapDay) {
    checkArgument(before != null, "Before must be non-null");
    checkArgument(before.isAfter(MonthDay.of(JANUARY, 1)), "Before must be after January 1st");
    int year = includeLeapDay ? LEAP_YEAR : LEAP_YEAR - 1;
    LocalDate startOfYear = Year.of(year).atDay(1);
    LocalDate end = before.atYear(year);
    LocalDate localDate = randomLocalDate(startOfYear, end);
    return MonthDay.of(localDate.getMonth(), localDate.getDayOfMonth());
  }

  /**
   * Returns a random {@link YearMonth} between 1,000 and 10,000.
   *
   * @return the random {@link YearMonth}
   */
  public static YearMonth randomYearMonth() {
    return YearMonth.of(randomFourDigitYear().getValue(), randomMonth());
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
    return randomFutureYearMonth(YearMonth.now());
  }

  /**
   * Returns a random {@link YearMonth} that is after the given {@link YearMonth}.
   *
   * @param after the value that returned {@link YearMonth} must be after
   * @return the random {@link YearMonth}
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static YearMonth randomFutureYearMonth(YearMonth after) {
    checkArgument(after != null, "After must be non-null");
    LocalDate start = after.plus(1, MONTHS).atDay(1);
    LocalDate localDate = randomFutureLocalDate(start);
    return YearMonth.of(localDate.getYear(), localDate.getMonth());
  }

  /**
   * Returns a random {@link YearMonth} that is before the current system clock.
   *
   * @return the random {@link YearMonth}
   */
  public static YearMonth randomPastYearMonth() {
    return randomPastYearMonth(YearMonth.now());
  }

  /**
   * Returns a random {@link YearMonth} that is before the given {@link YearMonth}.
   *
   * @param before the value that returned {@link YearMonth} must be before
   * @return the random {@link YearMonth}
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static YearMonth randomPastYearMonth(YearMonth before) {
    checkArgument(before != null, "Before must be non-null");
    LocalDate start = before.atDay(1);
    LocalDate localDate = randomPastLocalDate(start);
    return YearMonth.of(localDate.getYear(), localDate.getMonth());
  }

  /**
   * Returns a random {@link Year} between 1,000 (inclusive) and 10,000 (exclusive).
   *
   * @return the random {@link Year}
   */
  public static Year randomFourDigitYear() {
    return Year.of(RandomUtils.nextInt(1_000, 10_000));
  }

  /**
   * Returns a random {@link Year} within the specified range.
   *
   * @param startInclusive the earliest {@link Year} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link Year}
   * @throws IllegalArgumentException if startInclusive or endExclusive are null, if endExclusive is
   *     earlier than startInclusive, if either are before 1,000, or if either are after 9,999
   */
  public static Year randomFourDigitYear(Year startInclusive, Year endExclusive) {
    checkArgument(startInclusive != null, "Start must be non-null");
    checkArgument(endExclusive != null, "End must be non-null");
    return randomFourDigitYear(startInclusive.getValue(), endExclusive.getValue());
  }

  /**
   * Returns a random {@link Year} within the specified range.
   *
   * @param startInclusive the earliest {@link Year} that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random {@link Year}
   * @throws IllegalArgumentException if endExclusive is earlier than startInclusive, if end is
   *     before 1,000, or if start is after 9,999
   */
  public static Year randomFourDigitYear(int startInclusive, int endExclusive) {
    checkArgument(startInclusive < 10_000, "Start must before 10,000");
    checkArgument(endExclusive > 999, "End must be after 999");
    checkArgument(!(startInclusive > endExclusive), "End must come on or after start");
    return Year.of(RandomUtils.nextInt(startInclusive, endExclusive));
  }

  /**
   * Returns a random {@link Year} that is after the current system clock.
   *
   * @return the random {@link Year}
   */
  public static Year randomFutureFourDigitYear() {
    return Year.of(RandomUtils.nextInt(Year.now().getValue() + 1, 10_000));
  }

  /**
   * Returns a random {@link Year} that is after the given {@link Year}.
   *
   * @param after the value that returned {@link Year} must be after
   * @return the random {@link Year}
   * @throws IllegalArgumentException if after is null, if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}, or if after >= 9,999
   */
  public static Year randomFutureFourDigitYear(Year after) {
    checkArgument(after != null, "After must be non-null");
    return randomFutureFourDigitYear(after.getValue());
  }

  /**
   * Returns a random {@link Year} that is after the given {@link Year}.
   *
   * @param after the value that returned {@link Year} must be after
   * @return the random {@link Year}
   * @throws IllegalArgumentException if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT} or if after >= 9,999
   */
  public static Year randomFutureFourDigitYear(int after) {
    checkArgument(after < 9_999, "After must be before 9,999");
    return Year.of(RandomUtils.nextInt(after, 9_999));
  }

  /**
   * Returns a random {@link Year} that is before the current system clock.
   *
   * @return the random {@link Year}
   */
  public static Year randomPastFourDigitYear() {
    return Year.of(RandomUtils.nextInt(1_000, Year.now().getValue()));
  }

  /**
   * Returns a random {@link Year} that is before the given {@link Year}.
   *
   * @param before the value that returned {@link Year} must be before
   * @return the random {@link Year}
   * @throws IllegalArgumentException if before is null, if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}, or if before is <= 1,000
   */
  public static Year randomPastFourDigitYear(Year before) {
    checkArgument(before != null, "Before must be non-null");
    return randomPastFourDigitYear(before.getValue());
  }

  /**
   * Returns a random {@link Year} that is before the given {@link Year}.
   *
   * @param before the value that returned {@link Year} must be before
   * @return the random {@link Year}
   * @throws IllegalArgumentException if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}, or if before is <= 1,000
   */
  public static Year randomPastFourDigitYear(int before) {
    checkArgument(before > 1_000, "Before must be after 1,000");
    return Year.of(RandomUtils.nextInt(1_000, before));
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
        MAX_ZONE_OFFSET_SECONDS - RandomUtils.nextInt(0, (MAX_ZONE_OFFSET_SECONDS * 2) + 1);
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

  private static int randomInt() {
    return RandomUtils.nextInt() * (RandomUtils.nextBoolean() ? -1 : 1);
  }

  private static int randomPositiveInt() {
    return RandomUtils.nextInt(1, Integer.MAX_VALUE);
  }

  private static int randomNegativeInt() {
    return -RandomUtils.nextInt(1, Integer.MAX_VALUE);
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
   * Returns a random {@link Duration} which will positive.
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

  private static long randomLong() {
    return RandomUtils.nextLong() * (RandomUtils.nextBoolean() ? -1 : 1);
  }

  private static long randomPositiveLong() {
    return RandomUtils.nextLong(1, Long.MAX_VALUE);
  }

  private static long randomNegativeLong() {
    return -RandomUtils.nextLong(1, Long.MAX_VALUE);
  }
}
