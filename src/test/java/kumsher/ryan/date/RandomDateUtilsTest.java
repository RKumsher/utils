package kumsher.ryan.date;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static kumsher.ryan.date.RandomDateUtils.MAX_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.MIN_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.randomFixedClock;
import static kumsher.ryan.date.RandomDateUtils.randomFixedUtcClock;
import static kumsher.ryan.date.RandomDateUtils.randomFutureInstant;
import static kumsher.ryan.date.RandomDateUtils.randomFutureLocalDate;
import static kumsher.ryan.date.RandomDateUtils.randomFutureLocalDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomFutureOffsetDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomFutureZonedDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomInstant;
import static kumsher.ryan.date.RandomDateUtils.randomLocalDate;
import static kumsher.ryan.date.RandomDateUtils.randomLocalDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomOffsetDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomPastInstant;
import static kumsher.ryan.date.RandomDateUtils.randomPastLocalDate;
import static kumsher.ryan.date.RandomDateUtils.randomPastLocalDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomPastOffsetDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomPastZonedDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomZoneId;
import static kumsher.ryan.date.RandomDateUtils.randomZonedDateTime;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Test;

public class RandomDateUtilsTest {

  private static final Clock CLOCK = Clock.systemUTC();
  private static final ZoneId UTC = CLOCK.getZone();
  private static final ZonedDateTime MIN_ZONED_DATE_TIME =
      ZonedDateTime.ofInstant(MIN_INSTANT, UTC);
  private static final ZonedDateTime MAX_ZONED_DATE_TIME =
      ZonedDateTime.ofInstant(MAX_INSTANT, UTC);
  private static final OffsetDateTime MIN_OFFSET_DATE_TIME = MIN_ZONED_DATE_TIME.toOffsetDateTime();
  private static final OffsetDateTime MAX_OFFSET_DATE_TIME = MAX_ZONED_DATE_TIME.toOffsetDateTime();
  private static final LocalDateTime MIN_LOCAL_DATE_TIME = MIN_ZONED_DATE_TIME.toLocalDateTime();
  private static final LocalDateTime MAX_LOCAL_DATE_TIME = MAX_ZONED_DATE_TIME.toLocalDateTime();
  private static final LocalDate MIN_LOCAL_DATE = MIN_LOCAL_DATE_TIME.toLocalDate();
  private static final LocalDate MAX_LOCAL_DATE = MAX_LOCAL_DATE_TIME.toLocalDate();

  @Test
  public void randomZonedDateTime_ReturnsZonedDateTimeBetweenMinAndMaxInstants() {
    ZonedDateTime randomZonedDateTime = randomZonedDateTime();
    assertTrue(
        randomZonedDateTime.isAfter(MIN_ZONED_DATE_TIME)
            || randomZonedDateTime.equals(MIN_ZONED_DATE_TIME));
    assertTrue(randomZonedDateTime.isBefore(MAX_ZONED_DATE_TIME));
  }

  @Test
  public void randomZonedDateTime_ReturnsZonedDateTimeBetweenGivenZonedDateTimes() {
    ZonedDateTime start = ZonedDateTime.now(CLOCK).minus(1, DAYS);
    ZonedDateTime end = ZonedDateTime.now(CLOCK).plus(1, DAYS);

    ZonedDateTime zonedDateTime = randomZonedDateTime(start, end);
    assertTrue(zonedDateTime.isAfter(start) || zonedDateTime.equals(start));
    assertTrue(zonedDateTime.isBefore(end));
  }

  @Test
  public void randomZonedDateTime_WithZonedDateTimesOneMillisecondApart_ReturnsStart() {
    ZonedDateTime start = ZonedDateTime.now(CLOCK);
    ZonedDateTime end = start.plus(1, MILLIS);
    assertThat(randomZonedDateTime(start, end), is(start));
  }

  @Test
  public void randomZonedDateTime_WithEqualZonedDateTimes_ReturnsStartZonedDateTime() {
    ZonedDateTime zonedDateTime = ZonedDateTime.now(CLOCK);
    assertThat(randomZonedDateTime(zonedDateTime, zonedDateTime), is(zonedDateTime));
  }

  @Test
  public void randomZonedDateTime_WithNullEndZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomZonedDateTime(ZonedDateTime.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomZonedDateTime_WithNullStartZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomZonedDateTime(null, ZonedDateTime.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomZonedDateTime_WithStartAfterEndZonedDateTime_ThrowsIllegalArgumentException() {
    ZonedDateTime start = ZonedDateTime.now(CLOCK).plus(1, DAYS);
    ZonedDateTime end = ZonedDateTime.now(CLOCK).minus(1, DAYS);
    try {
      randomZonedDateTime(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureZonedDateTime_WithAfterGiven_ReturnsZonedDateTimeAfterGiven() {
    ZonedDateTime after = ZonedDateTime.now(CLOCK);
    assertThat(randomFutureZonedDateTime(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureZonedDateTime_WithMaxZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureZonedDateTime(MAX_ZONED_DATE_TIME);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date after " + MAX_INSTANT));
    }
  }

  @Test
  public void randomFutureZonedDateTime_WithNullZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureZonedDateTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomFutureZonedDateTime_ReturnsZonedDateTimeAfterCurrentSystemClock() {
    ZonedDateTime now = ZonedDateTime.now(CLOCK);
    assertThat(randomFutureZonedDateTime().isAfter(now), is(true));
  }

  @Test
  public void randomPastZonedDateTime_WithBeforeGiven_ReturnsZonedDateTimeBeforeGiven() {
    ZonedDateTime before = ZonedDateTime.now(CLOCK);
    assertThat(randomPastZonedDateTime(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastZonedDateTime_WithMinZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomPastZonedDateTime(MIN_ZONED_DATE_TIME);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date before " + MIN_INSTANT));
    }
  }

  @Test
  public void randomPastZonedDateTime_WithNullZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomPastZonedDateTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomPastZonedDateTime_ReturnsZonedDateTimeBeforeCurrentSystemClock() {
    ZonedDateTime now = ZonedDateTime.now(CLOCK);
    assertThat(randomPastZonedDateTime().isBefore(now), is(true));
  }

  @Test
  public void randomOffsetDateTime_ReturnsOffsetDateTimeBetweenMinAndMaxInstants() {
    OffsetDateTime randomOffsetDateTime = randomOffsetDateTime();
    assertTrue(
        randomOffsetDateTime.isAfter(MIN_OFFSET_DATE_TIME)
            || randomOffsetDateTime.equals(MIN_OFFSET_DATE_TIME));
    assertTrue(randomOffsetDateTime.isBefore(MAX_OFFSET_DATE_TIME));
  }

  @Test
  public void randomOffsetDateTime_ReturnsOffsetDateTimeBetweenGivenOffsetDateTimes() {
    OffsetDateTime start = OffsetDateTime.now(CLOCK).minus(1, DAYS);
    OffsetDateTime end = OffsetDateTime.now(CLOCK).plus(1, DAYS);

    OffsetDateTime OffsetDateTime = randomOffsetDateTime(start, end);
    assertTrue(OffsetDateTime.isAfter(start) || OffsetDateTime.equals(start));
    assertTrue(OffsetDateTime.isBefore(end));
  }

  @Test
  public void randomOffsetDateTime_WithOffsetDateTimesOneMillisecondApart_ReturnsStart() {
    OffsetDateTime start = OffsetDateTime.now(CLOCK);
    OffsetDateTime end = start.plus(1, MILLIS);
    assertThat(randomOffsetDateTime(start, end), is(start));
  }

  @Test
  public void randomOffsetDateTime_WithEqualOffsetDateTimes_ReturnsStartOffsetDateTime() {
    OffsetDateTime OffsetDateTime = ZonedDateTime.now(CLOCK).toOffsetDateTime();
    assertThat(randomOffsetDateTime(OffsetDateTime, OffsetDateTime), is(OffsetDateTime));
  }

  @Test
  public void randomOffsetDateTime_WithNullEndOffsetDateTime_ThrowsIllegalArgumentException() {
    try {
      randomOffsetDateTime(OffsetDateTime.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomOffsetDateTime_WithNullStartOffsetDateTime_ThrowsIllegalArgumentException() {
    try {
      randomOffsetDateTime(null, OffsetDateTime.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void
      randomOffsetDateTime_WithStartAfterEndOffsetDateTime_ThrowsIllegalArgumentException() {
    OffsetDateTime start = OffsetDateTime.now(CLOCK).plus(1, DAYS);
    OffsetDateTime end = OffsetDateTime.now(CLOCK).minus(1, DAYS);
    try {
      randomOffsetDateTime(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureOffsetDateTime_WithAfterGiven_ReturnsOffsetDateTimeAfterGiven() {
    OffsetDateTime after = OffsetDateTime.now(CLOCK);
    assertThat(randomFutureOffsetDateTime(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureOffsetDateTime_WithMaxOffsetDateTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureOffsetDateTime(MAX_OFFSET_DATE_TIME);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date after " + MAX_INSTANT));
    }
  }

  @Test
  public void randomFutureOffsetDateTime_WithNullOffsetDateTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureOffsetDateTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomFutureOffsetDateTime_ReturnsOffsetDateTimeAfterCurrentSystemClock() {
    OffsetDateTime now = OffsetDateTime.now(CLOCK);
    assertThat(randomFutureOffsetDateTime().isAfter(now), is(true));
  }

  @Test
  public void randomPastOffsetDateTime_WithBeforeGiven_ReturnsOffsetDateTimeBeforeGiven() {
    OffsetDateTime before = OffsetDateTime.now(CLOCK);
    assertThat(randomPastOffsetDateTime(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastOffsetDateTime_WithMinOffsetDateTime_ThrowsIllegalArgumentException() {
    try {
      randomPastOffsetDateTime(MIN_OFFSET_DATE_TIME);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date before " + MIN_INSTANT));
    }
  }

  @Test
  public void randomPastOffsetDateTime_WithNullOffsetDateTime_ThrowsIllegalArgumentException() {
    try {
      randomPastOffsetDateTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomPastOffsetDateTime_ReturnsOffsetDateTimeBeforeCurrentSystemClock() {
    OffsetDateTime now = OffsetDateTime.now(CLOCK);
    assertThat(randomPastOffsetDateTime().isBefore(now), is(true));
  }

  @Test
  public void randomLocalDateTime_ReturnsLocalDateTimeBetweenMinAndMaxInstants() {
    LocalDateTime randomLocalDateTime = randomLocalDateTime();
    assertTrue(
        randomLocalDateTime.isAfter(MIN_LOCAL_DATE_TIME)
            || randomLocalDateTime.equals(MIN_LOCAL_DATE_TIME));
    assertTrue(randomLocalDateTime.isBefore(MAX_LOCAL_DATE_TIME));
  }

  @Test
  public void randomLocalDateTime_ReturnsLocalDateTimeBetweenGivenLocalDateTimes() {
    LocalDateTime start = LocalDateTime.now(CLOCK).minus(1, DAYS);
    LocalDateTime end = LocalDateTime.now(CLOCK).plus(1, DAYS);

    LocalDateTime zonedDateTime = randomLocalDateTime(start, end);
    assertTrue(zonedDateTime.isAfter(start) || zonedDateTime.equals(start));
    assertTrue(zonedDateTime.isBefore(end));
  }

  @Test
  public void randomLocalDateTime_WithLocalDateTimesOneMillisecondApart_ReturnsStart() {
    LocalDateTime start = LocalDateTime.now(CLOCK);
    LocalDateTime end = start.plus(1, MILLIS);
    assertThat(randomLocalDateTime(start, end), is(start));
  }

  @Test
  public void randomLocalDateTime_WithEqualLocalDateTimes_ReturnsStartLocalDateTime() {
    LocalDateTime zonedDateTime = LocalDateTime.now(CLOCK);
    assertThat(randomLocalDateTime(zonedDateTime, zonedDateTime), is(zonedDateTime));
  }

  @Test
  public void randomLocalDateTime_WithNullEndLocalDateTime_ThrowsIllegalArgumentException() {
    try {
      randomLocalDateTime(LocalDateTime.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomLocalDateTime_WithNullStartLocalDateTime_ThrowsIllegalArgumentException() {
    try {
      randomLocalDateTime(null, LocalDateTime.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomLocalDateTime_WithStartAfterEndLocalDateTime_ThrowsIllegalArgumentException() {
    LocalDateTime start = LocalDateTime.now(CLOCK).plus(1, DAYS);
    LocalDateTime end = LocalDateTime.now(CLOCK).minus(1, DAYS);
    try {
      randomLocalDateTime(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureLocalDateTime_WithAfterGiven_ReturnsLocalDateTimeAfterGiven() {
    LocalDateTime after = LocalDateTime.now(CLOCK);
    assertThat(randomFutureLocalDateTime(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureLocalDateTime_WithMaxLocalDateTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureLocalDateTime(MAX_LOCAL_DATE_TIME);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date after " + MAX_INSTANT));
    }
  }

  @Test
  public void randomFutureLocalDateTime_WithNullLocalDateTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureLocalDateTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomFutureLocalDateTime_ReturnsLocalDateTimeAfterCurrentSystemClock() {
    LocalDateTime now = LocalDateTime.now(CLOCK);
    assertThat(randomFutureLocalDateTime().isAfter(now), is(true));
  }

  @Test
  public void randomPastLocalDateTime_WithBeforeGiven_ReturnsLocalDateTimeBeforeGiven() {
    LocalDateTime before = LocalDateTime.now(CLOCK);
    assertThat(randomPastLocalDateTime(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastLocalDateTime_WithMinLocalDateTime_ThrowsIllegalArgumentException() {
    try {
      randomPastLocalDateTime(MIN_LOCAL_DATE_TIME);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date before " + MIN_INSTANT));
    }
  }

  @Test
  public void randomPastLocalDateTime_WithNullLocalDateTime_ThrowsIllegalArgumentException() {
    try {
      randomPastLocalDateTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomPastLocalDateTime_ReturnsLocalDateTimeBeforeCurrentSystemClock() {
    LocalDateTime now = LocalDateTime.now(CLOCK);
    assertThat(randomPastLocalDateTime().isBefore(now), is(true));
  }

  @Test
  public void randomLocalDate_ReturnsLocalDateBetweenMinAndMaxInstants() {
    LocalDate randomLocalDate = randomLocalDate();
    assertTrue(randomLocalDate.isAfter(MIN_LOCAL_DATE) || randomLocalDate.equals(MIN_LOCAL_DATE));
    assertTrue(randomLocalDate.isBefore(MAX_LOCAL_DATE));
  }

  @Test
  public void randomLocalDate_ReturnsLocalDateBetweenGivenLocalDates() {
    LocalDate start = LocalDate.now(CLOCK).minus(1, DAYS);
    LocalDate end = LocalDate.now(CLOCK).plus(1, DAYS);

    LocalDate zonedDateTime = randomLocalDate(start, end);
    assertTrue(zonedDateTime.isAfter(start) || zonedDateTime.equals(start));
    assertTrue(zonedDateTime.isBefore(end));
  }

  @Test
  public void randomLocalDate_WithLocalDatesOneDayApart_ReturnsStart() {
    LocalDate start = LocalDate.now(CLOCK);
    LocalDate end = start.plus(1, DAYS);
    assertThat(randomLocalDate(start, end), is(start));
  }

  @Test
  public void randomLocalDate_WithEqualLocalDates_ReturnsStartLocalDate() {
    LocalDate zonedDateTime = LocalDate.now(CLOCK);
    assertThat(randomLocalDate(zonedDateTime, zonedDateTime), is(zonedDateTime));
  }

  @Test
  public void randomLocalDate_WithNullEndLocalDate_ThrowsIllegalArgumentException() {
    try {
      randomLocalDate(LocalDate.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomLocalDate_WithNullStartLocalDate_ThrowsIllegalArgumentException() {
    try {
      randomLocalDate(null, LocalDate.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomLocalDate_WithStartAfterEndLocalDate_ThrowsIllegalArgumentException() {
    LocalDate start = LocalDate.now(CLOCK).plus(1, DAYS);
    LocalDate end = LocalDate.now(CLOCK).minus(1, DAYS);
    try {
      randomLocalDate(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureLocalDate_WithAfterGiven_ReturnsLocalDateAfterGiven() {
    LocalDate after = LocalDate.now(CLOCK);
    assertThat(randomFutureLocalDate(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureLocalDate_WithMaxLocalDate_ThrowsIllegalArgumentException() {
    try {
      randomFutureLocalDate(MAX_LOCAL_DATE);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date after " + MAX_INSTANT));
    }
  }

  @Test
  public void randomFutureLocalDate_WithNullLocalDate_ThrowsIllegalArgumentException() {
    try {
      randomFutureLocalDate(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomFutureLocalDate_ReturnsLocalDateAfterCurrentSystemClock() {
    LocalDate now = LocalDate.now(CLOCK);
    assertThat(randomFutureLocalDate().isAfter(now), is(true));
  }

  @Test
  public void randomPastLocalDate_WithBeforeGiven_ReturnsLocalDateBeforeGiven() {
    LocalDate before = LocalDate.now(CLOCK);
    assertThat(randomPastLocalDate(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastLocalDate_WithMinLocalDate_ThrowsIllegalArgumentException() {
    try {
      randomPastLocalDate(MIN_LOCAL_DATE);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date before " + MIN_INSTANT));
    }
  }

  @Test
  public void randomPastLocalDate_WithNullLocalDate_ThrowsIllegalArgumentException() {
    try {
      randomPastLocalDate(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomPastLocalDate_ReturnsLocalDateBeforeCurrentSystemClock() {
    LocalDate now = LocalDate.now(CLOCK);
    assertThat(randomPastLocalDate().isBefore(now), is(true));
  }

  @Test
  public void randomInstant_ReturnsInstantBetweenMinAndMaxInstants() {
    Instant instant = randomInstant();
    assertTrue(instant.isAfter(MIN_INSTANT) || instant.equals(MIN_INSTANT));
    assertTrue(instant.isBefore(MAX_INSTANT));
  }

  @Test
  public void randomInstant_ReturnsInstantBetweenGivenInstants() {
    Instant start = Instant.now(CLOCK).minus(1, DAYS);
    Instant end = Instant.now(CLOCK).plus(1, DAYS);

    Instant instant = randomInstant(start, end);
    assertTrue(instant.isAfter(start) || instant.equals(start));
    assertTrue(instant.isBefore(end));
  }

  @Test
  public void randomInstant_WithInstantsOneMillisecondApart_ReturnsStart() {
    Instant start = Instant.now(CLOCK);
    Instant end = start.plus(1, MILLIS);
    assertThat(randomInstant(start, end), is(start));
  }

  @Test
  public void randomInstant_WithEqualInstants_ReturnsStart() {
    Instant instant = Instant.now(CLOCK);
    assertThat(randomInstant(instant, instant), is(instant));
  }

  @Test
  public void randomInstant_WithNullEndInstant_ThrowsIllegalArgumentException() {
    try {
      randomInstant(Instant.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomInstant_WithNullStartInstant_ThrowsIllegalArgumentException() {
    try {
      randomInstant(null, Instant.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomInstant_WithStartAfterEndInstant_ThrowsIllegalArgumentException() {
    Instant start = Instant.now(CLOCK).plus(1, DAYS);
    Instant end = Instant.now(CLOCK).minus(1, DAYS);
    try {
      randomInstant(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureInstant_WithAfterGiven_ReturnsInstantAfterGiven() {
    Instant after = Instant.now(CLOCK);
    assertThat(randomFutureInstant(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureInstant_WithMaxInstant_ThrowsIllegalArgumentException() {
    try {
      randomFutureInstant(MAX_INSTANT);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date after " + MAX_INSTANT));
    }
  }

  @Test
  public void randomFutureInstant_WithNullInstant_ThrowsIllegalArgumentException() {
    try {
      randomFutureInstant(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomFutureInstant_ReturnsInstantAfterCurrentSystemClock() {
    Instant now = Instant.now(CLOCK);
    assertThat(randomFutureInstant().isAfter(now), is(true));
  }

  @Test
  public void randomPastInstant_WithBeforeGiven_ReturnsInstantBeforeGiven() {
    Instant before = Instant.now(CLOCK);
    assertThat(randomPastInstant(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastInstant_WithMinInstant_ThrowsIllegalArgumentException() {
    try {
      randomPastInstant(MIN_INSTANT);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date before " + MIN_INSTANT));
    }
  }

  @Test
  public void randomPastInstant_WithNullInstant_ThrowsIllegalArgumentException() {
    try {
      randomPastInstant(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomPastInstant_ReturnsInstantBeforeCurrentSystemClock() {
    Instant now = Instant.now(CLOCK);
    assertThat(randomPastInstant().isBefore(now), is(true));
  }

  @Test
  public void randomFixedClock_ReturnsClockWithZoneIdFromAvailableZoneIds() {
    Clock clock = randomFixedClock();
    assertThat(clock.getZone().getId(), isIn(ZoneOffset.getAvailableZoneIds()));
  }

  @Test
  public void randomFixedClock_ReturnsClockWithInstantBetweenMinAndMaxInstants() {
    Clock clock = randomFixedClock();
    Instant instant = clock.instant();
    assertTrue(instant.isAfter(MIN_INSTANT) || instant.equals(MIN_INSTANT));
    assertTrue(instant.isBefore(MAX_INSTANT));
  }

  @Test
  public void randomFixedUtcClock_ReturnsClockWithUtcZoneId() {
    Clock clock = randomFixedUtcClock();
    assertThat(clock.getZone(), is(UTC));
  }

  @Test
  public void randomFixedUtcClock_ReturnsClockWithInstantBetweenMinAndMaxInstants() {
    Clock clock = randomFixedUtcClock();
    Instant instant = clock.instant();
    assertTrue(instant.isAfter(MIN_INSTANT) || instant.equals(MIN_INSTANT));
    assertTrue(instant.isBefore(MAX_INSTANT));
  }

  @Test
  public void randomZoneId_ReturnsZoneIdFromAvailableZoneIds() {
    ZoneId zoneId = randomZoneId();
    assertThat(zoneId.getId(), isIn(ZoneOffset.getAvailableZoneIds()));
  }
}
