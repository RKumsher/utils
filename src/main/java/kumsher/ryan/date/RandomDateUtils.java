package kumsher.ryan.date;

import static com.google.common.base.Preconditions.*;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

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
    Instant instant = randomInstant(Instant.now().plus(1, ChronoUnit.MILLIS), MAX_INSTANT);
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
    Instant instant = randomInstant(Instant.now().plus(1, ChronoUnit.MILLIS), MAX_INSTANT);
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
    Instant instant = randomInstant(Instant.now().plus(1, ChronoUnit.MILLIS), MAX_INSTANT);
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
    Instant instant = randomInstant(Instant.now().plus(1, ChronoUnit.MILLIS), MAX_INSTANT);
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
    return randomInstant(Instant.now().plus(1, ChronoUnit.MILLIS), MAX_INSTANT);
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
}
