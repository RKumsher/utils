package kumsher.ryan.date;

import static com.google.common.base.Preconditions.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.RandomUtils;

/**
 * Utility library to return random dates, e.g., {@link Instant}s, {@link ZonedDateTime}s, {@link
 * LocalDate}s, etc..
 */
public class RandomDateUtils {

  private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
  public static final Instant MIN_INSTANT = Instant.ofEpochMilli(0);
  /* Limiting to just 4-digit years */
  public static final Instant MAX_INSTANT =
      Instant.ofEpochMilli(
          LocalDate.of(10000, 1, 1).atStartOfDay(DEFAULT_ZONE).toInstant().toEpochMilli());

  /**
   * Returns a random instant between {@link RandomDateUtils#MIN_INSTANT} and {@link
   * RandomDateUtils#MAX_INSTANT}.
   *
   * @return the random instant
   */
  public static Instant randomInstant() {
    return randomInstant(MIN_INSTANT, MAX_INSTANT);
  }

  /**
   * Returns a random instant within the specified range.
   *
   * @param startInclusive the earliest instant that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random instant
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
   * Returns a random instant that is after the current system clock.
   *
   * @return the random instant
   */
  public static Instant randomFutureInstant() {
    return randomInstant(Instant.now().plus(1, ChronoUnit.MILLIS), MAX_INSTANT);
  }

  /**
   * Returns a random instant that is after the given instant.
   *
   * @param after the value that returned instant must be after
   * @return the random instant
   * @throws IllegalArgumentException if after is null or if after is equal to or after {@link
   *     RandomDateUtils#MAX_INSTANT}
   */
  public static Instant randomFutureInstant(Instant after) {
    checkArgument(after != null, "After must be non-null");
    checkArgument(after.isBefore(MAX_INSTANT), "Cannot produce date after " + MAX_INSTANT);
    return randomInstant(after, MAX_INSTANT);
  }

  /**
   * Returns a random instant that is before the current system clock.
   *
   * @return the random instant
   */
  public static Instant randomPastInstant() {
    return randomInstant(MIN_INSTANT, Instant.now());
  }

  /**
   * Returns a random instant that is before the given instant.
   *
   * @param before the value that returned instant must be before
   * @return the random instant
   * @throws IllegalArgumentException if before is null or if before is equal to or before {@link
   *     RandomDateUtils#MIN_INSTANT}
   */
  public static Instant randomPastInstant(Instant before) {
    checkArgument(before != null, "Before must be non-null");
    checkArgument(before.isAfter(MIN_INSTANT), "Cannot produce date before " + MIN_INSTANT);
    return randomInstant(MIN_INSTANT, before);
  }
}
