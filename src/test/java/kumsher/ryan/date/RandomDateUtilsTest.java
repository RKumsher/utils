package kumsher.ryan.date;

import static java.time.Month.DECEMBER;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JANUARY;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.YEARS;
import static kumsher.ryan.date.RandomDateUtils.MAX_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.MIN_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.isLeapDay;
import static kumsher.ryan.date.RandomDateUtils.random;
import static kumsher.ryan.date.RandomDateUtils.randomDayOfWeek;
import static kumsher.ryan.date.RandomDateUtils.randomDuration;
import static kumsher.ryan.date.RandomDateUtils.randomFixedClock;
import static kumsher.ryan.date.RandomDateUtils.randomFixedUtcClock;
import static kumsher.ryan.date.RandomDateUtils.randomFourDigitYear;
import static kumsher.ryan.date.RandomDateUtils.randomFuture;
import static kumsher.ryan.date.RandomDateUtils.randomFutureFourDigitYear;
import static kumsher.ryan.date.RandomDateUtils.randomFutureInstant;
import static kumsher.ryan.date.RandomDateUtils.randomFutureLocalDate;
import static kumsher.ryan.date.RandomDateUtils.randomFutureLocalDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomFutureLocalTime;
import static kumsher.ryan.date.RandomDateUtils.randomFutureMonthDay;
import static kumsher.ryan.date.RandomDateUtils.randomFutureOffsetDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomFutureYearMonth;
import static kumsher.ryan.date.RandomDateUtils.randomFutureZonedDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomInstant;
import static kumsher.ryan.date.RandomDateUtils.randomLocalDate;
import static kumsher.ryan.date.RandomDateUtils.randomLocalDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomLocalTime;
import static kumsher.ryan.date.RandomDateUtils.randomMonth;
import static kumsher.ryan.date.RandomDateUtils.randomMonthDay;
import static kumsher.ryan.date.RandomDateUtils.randomNegativeDuration;
import static kumsher.ryan.date.RandomDateUtils.randomNegativePeriod;
import static kumsher.ryan.date.RandomDateUtils.randomOffsetDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomPast;
import static kumsher.ryan.date.RandomDateUtils.randomPastFourDigitYear;
import static kumsher.ryan.date.RandomDateUtils.randomPastInstant;
import static kumsher.ryan.date.RandomDateUtils.randomPastLocalDate;
import static kumsher.ryan.date.RandomDateUtils.randomPastLocalDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomPastLocalTime;
import static kumsher.ryan.date.RandomDateUtils.randomPastMonthDay;
import static kumsher.ryan.date.RandomDateUtils.randomPastOffsetDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomPastYearMonth;
import static kumsher.ryan.date.RandomDateUtils.randomPastZonedDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomPeriod;
import static kumsher.ryan.date.RandomDateUtils.randomPositiveDuration;
import static kumsher.ryan.date.RandomDateUtils.randomPositivePeriod;
import static kumsher.ryan.date.RandomDateUtils.randomYearMonth;
import static kumsher.ryan.date.RandomDateUtils.randomZoneId;
import static kumsher.ryan.date.RandomDateUtils.randomZoneOffset;
import static kumsher.ryan.date.RandomDateUtils.randomZonedDateTime;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

import org.junit.Test;

import kumsher.ryan.enums.RandomEnumUtils;

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
  private static final MonthDay MIN_MONTH_DAY = MonthDay.of(JANUARY, 1);
  private static final MonthDay MAX_MONTH_DAY = MonthDay.of(DECEMBER, 31);
  private static final Year MAX_YEAR = Year.of(9_999);
  private static final Year MIN_YEAR = Year.of(1_000);
  private static final YearMonth MAX_YEAR_MONTH = YearMonth.of(9_999, DECEMBER);
  private static final YearMonth MIN_YEAR_MONTH = YearMonth.of(1_000, JANUARY);

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
  public void randomLocalTime_ReturnsRandomLocalTime() {
    LocalTime localTime = randomLocalTime();
    assertThat(localTime, notNullValue());
  }

  @Test
  public void randomLocalTime_ReturnsLocalTimeBetweenGivenLocalTimes() {
    LocalTime start = LocalTime.NOON.minus(1, HOURS);
    LocalTime end = LocalTime.NOON.plus(1, HOURS);
    LocalTime localTime = randomLocalTime(start, end);
    assertTrue(localTime.isAfter(start) || localTime.equals(start));
    assertTrue(localTime.isBefore(end));
  }

  @Test
  public void randomLocalTime_WithLocalTimesOneNanoApart_ReturnsStart() {
    LocalTime start = LocalTime.NOON;
    LocalTime end = start.plus(1, NANOS);
    assertThat(randomLocalTime(start, end), is(start));
  }

  @Test
  public void randomLocalTime_WithEqualLocalTimes_ReturnsStartLocalTime() {
    LocalTime localTime = LocalTime.NOON;
    assertThat(randomLocalTime(localTime, localTime), is(localTime));
  }

  @Test
  public void randomLocalTime_WithNullEndLocalTime_ThrowsIllegalArgumentException() {
    try {
      randomLocalTime(LocalTime.NOON, null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomLocalTime_WithNullStartLocalTime_ThrowsIllegalArgumentException() {
    try {
      randomLocalTime(null, LocalTime.NOON);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomLocalTime_WithStartAfterEndLocalTime_ThrowsIllegalArgumentException() {
    LocalTime start = LocalTime.NOON.plus(1, HOURS);
    LocalTime end = LocalTime.NOON;
    try {
      randomLocalTime(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureLocalTime_ReturnsLocalTimeAfterGiven() {
    LocalTime after = LocalTime.NOON;
    assertThat(randomFutureLocalTime(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureLocalTime_WithMaxLocalTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureLocalTime(LocalTime.MAX);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be before " + LocalTime.MAX));
    }
  }

  @Test
  public void randomFutureLocalTime_WithNullLocalTime_ThrowsIllegalArgumentException() {
    try {
      randomFutureLocalTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomPastLocalTime_ReturnsLocalTimeBeforeGiven() {
    LocalTime before = LocalTime.NOON;
    assertThat(randomPastLocalTime(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastLocalTime_WithMinLocalTime_ThrowsIllegalArgumentException() {
    try {
      randomPastLocalTime(LocalTime.MIN);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be after " + LocalTime.MIN));
    }
  }

  @Test
  public void randomPastLocalTime_WithNullLocalTime_ThrowsIllegalArgumentException() {
    try {
      randomPastLocalTime(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void random_ReturnsValidValueForGivenTemporalField() {
    TemporalField field = RandomEnumUtils.random(ChronoField.class);
    long value = random(field);
    assertThat(field.range().isValidValue(value), is(true));
  }

  @Test
  public void random_ReturnsValidValueBetweenGiven() {
    long start = SECOND_OF_MINUTE.range().getMinimum();
    long end = SECOND_OF_MINUTE.range().getMaximum();

    long value = random(SECOND_OF_MINUTE, start, end);
    assertTrue(value >= start);
    assertTrue(value < end);
  }

  @Test
  public void random_WithStartAndEndOneApart_ReturnsStart() {
    long start = 1;
    long end = 2;
    assertThat(random(SECOND_OF_MINUTE, start, end), is(start));
  }

  @Test
  public void random_WithEqualStartAndEnd_ReturnsStart() {
    long start = 1;
    long end = 1;
    assertThat(random(SECOND_OF_MINUTE, start, end), is(start));
  }

  @Test
  public void random_WithStartAfterEnd_ThrowsIllegalArgumentException() {
    try {
      random(SECOND_OF_MINUTE, 2, 1);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void random_WithStartBeforeMin_ThrowsIllegalArgumentException() {
    long start = SECOND_OF_MINUTE.range().getMinimum() - 1;
    long end = 2;
    try {
      random(SECOND_OF_MINUTE, start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be on or after 0"));
    }
  }

  @Test
  public void random_WithAfterMax_ThrowsIllegalArgumentException() {
    long start = 1;
    long end = SECOND_OF_MINUTE.range().getMaximum() + 1;
    try {
      random(SECOND_OF_MINUTE, start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be on or before 59"));
    }
  }

  @Test
  public void randomFuture_ReturnsValueAfterGiven() {
    ChronoField field = RandomEnumUtils.random(ChronoField.class);
    long after = randomPast(field, field.range().getMaximum());
    assertThat(randomFuture(field, after) > after, is(true));
  }

  @Test
  public void randomFuture_WithMaxValue_ThrowsIllegalArgumentException() {
    ChronoField field = RandomEnumUtils.random(ChronoField.class);
    try {
      randomFuture(field, field.range().getMaximum());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be before " + field.range().getMaximum()));
    }
  }

  @Test
  public void randomPast_ReturnsValueBeforeGiven() {
    ChronoField field = RandomEnumUtils.random(ChronoField.class);
    long before = randomFuture(field, field.range().getMinimum());
    assertThat(randomPast(field, before) < (before), is(true));
  }

  @Test
  public void randomPast_WithMinValue_ThrowsIllegalArgumentException() {
    ChronoField field = RandomEnumUtils.random(ChronoField.class);
    try {
      randomPast(field, field.range().getMinimum());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be after " + field.range().getMinimum()));
    }
  }

  @Test
  public void isLeapDay_WithFebruaryTwentyNinth_ReturnsTrue() {
    MonthDay monthDay = MonthDay.of(FEBRUARY, 29);
    assertThat(isLeapDay(monthDay), is(true));
  }

  @Test
  public void isLeapDay_WithNotFebruaryTwentyNinth_ReturnsFalse() {
    MonthDay monthDay = randomMonthDay(false);
    assertThat(isLeapDay(monthDay), is(false));
  }

  @Test
  public void randomMonthDay_DoesNotReturnLeapDayIfCurrentYearIsNotLeapYear() {
    MonthDay monthDay = randomMonthDay();
    assertThat(monthDay, notNullValue());
    if (!Year.now().isLeap()) {
      assertThat(monthDay, not(isLeapDay(monthDay)));
    }
  }

  @Test
  public void randomMonthDay_ReturnsMonthDayBetweenGivenMonthDays() {
    LocalDate baseline = randomLocalDateBetweenFirstAndLastDayOfYear();
    MonthDay start = convertLocalDateToMonthDay(baseline.minus(1, DAYS));
    MonthDay end = convertLocalDateToMonthDay(baseline.plus(1, DAYS));
    MonthDay monthDay = randomMonthDay(start, end);
    assertTrue(monthDay.isAfter(start) || monthDay.equals(start));
    assertTrue(monthDay.isBefore(end));
  }

  private LocalDate randomLocalDateBetweenFirstAndLastDayOfYear() {
    LocalDate secondDayOfYear = LocalDate.of(Year.now().getValue(), JANUARY, 2);
    LocalDate lastDayOfYear = LocalDate.of(Year.now().getValue(), DECEMBER, 31);
    return randomLocalDate(secondDayOfYear, lastDayOfYear);
  }

  private MonthDay convertLocalDateToMonthDay(LocalDate date) {
    return MonthDay.of(date.getMonth(), date.getDayOfMonth());
  }

  @Test
  public void randomMonthDay_WithMonthDaysOneDayApart_ReturnsStart() {
    LocalDate baseline = randomLocalDateBetweenFirstAndLastDayOfYear();
    MonthDay start = convertLocalDateToMonthDay(baseline);
    MonthDay end = convertLocalDateToMonthDay(baseline.plus(1, DAYS));
    assertThat(randomMonthDay(start, end), is(start));
  }

  @Test
  public void randomMonthDay_WithEqualMonthDays_ReturnsStartMonthDay() {
    MonthDay monthDay = MonthDay.now(CLOCK);
    assertThat(randomMonthDay(monthDay, monthDay), is(monthDay));
  }

  @Test
  public void randomMonthDay_WithNullEndMonthDay_ThrowsIllegalArgumentException() {
    try {
      randomMonthDay(MonthDay.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomMonthDay_WithNullStartMonthDay_ThrowsIllegalArgumentException() {
    try {
      randomMonthDay(null, MonthDay.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomMonthDay_WithStartAfterEndMonthDay_ThrowsIllegalArgumentException() {
    LocalDate baseline = randomLocalDateBetweenFirstAndLastDayOfYear();
    MonthDay start = convertLocalDateToMonthDay(baseline.plus(1, DAYS));
    MonthDay end = convertLocalDateToMonthDay(baseline.minus(1, DAYS));
    try {
      randomMonthDay(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureMonthDay_ReturnsMonthDayAfterGiven() {
    MonthDay after = MonthDay.now(CLOCK);
    assertThat(randomFutureMonthDay(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureMonthDay_WithMaxMonthDay_ThrowsIllegalArgumentException() {
    try {
      randomFutureMonthDay(MAX_MONTH_DAY);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be before December 31st"));
    }
  }

  @Test
  public void randomFutureMonthDay_WithNullMonthDay_ThrowsIllegalArgumentException() {
    try {
      randomFutureMonthDay(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomPastMonthDay_ReturnsMonthDayBeforeGiven() {
    MonthDay before = MonthDay.now(CLOCK);
    assertThat(randomPastMonthDay(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastMonthDay_WithMinMonthDay_ThrowsIllegalArgumentException() {
    try {
      randomPastMonthDay(MIN_MONTH_DAY);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be after January 1st"));
    }
  }

  @Test
  public void randomPastMonthDay_WithNullMonthDay_ThrowsIllegalArgumentException() {
    try {
      randomPastMonthDay(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomYearMonth_ReturnsYearMonthWithFourDigitYear() {
    YearMonth yearMonth = randomYearMonth();
    assertThat(yearMonth.getYear() >= 1_000, is(true));
    assertThat(yearMonth.getYear() < 10_000, is(true));
  }

  @Test
  public void randomYearMonth_ReturnsYearMonthBetweenGivenYearMonths() {
    YearMonth start = YearMonth.now(CLOCK).minus(1, MONTHS);
    YearMonth end = YearMonth.now(CLOCK).plus(1, MONTHS);
    YearMonth yearMonth = randomYearMonth(start, end);
    assertTrue(yearMonth.isAfter(start) || yearMonth.equals(start));
    assertTrue(yearMonth.isBefore(end));
  }

  @Test
  public void randomYearMonth_WithYearMonthsOneDayApart_ReturnsStart() {
    YearMonth start = YearMonth.now(CLOCK);
    YearMonth end = YearMonth.now(CLOCK).plus(1, MONTHS);
    assertThat(randomYearMonth(start, end), is(start));
  }

  @Test
  public void randomYearMonth_WithEqualYearMonths_ReturnsStartYearMonth() {
    YearMonth yearMonth = YearMonth.now(CLOCK);
    assertThat(randomYearMonth(yearMonth, yearMonth), is(yearMonth));
  }

  @Test
  public void randomYearMonth_WithNullEndYearMonth_ThrowsIllegalArgumentException() {
    try {
      randomYearMonth(YearMonth.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomYearMonth_WithNullStartYearMonth_ThrowsIllegalArgumentException() {
    try {
      randomYearMonth(null, YearMonth.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomYearMonth_WithStartAfterEndYearMonth_ThrowsIllegalArgumentException() {
    YearMonth start = YearMonth.now(CLOCK).plus(1, MONTHS);
    YearMonth end = YearMonth.now(CLOCK).minus(1, MONTHS);
    try {
      randomYearMonth(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureYearMonth_WithAfterGiven_ReturnsYearMonthAfterGiven() {
    YearMonth after = YearMonth.now(CLOCK);
    assertThat(randomFutureYearMonth(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureYearMonth_WithMaxYearMonth_ThrowsIllegalArgumentException() {
    try {
      randomFutureYearMonth(MAX_YEAR_MONTH);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date after " + MAX_INSTANT));
    }
  }

  @Test
  public void randomFutureYearMonth_WithNullYearMonth_ThrowsIllegalArgumentException() {
    try {
      randomFutureYearMonth(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomFutureYearMonth_ReturnsYearMonthAfterCurrentSystemClock() {
    YearMonth now = YearMonth.now(CLOCK);
    assertThat(randomFutureYearMonth().isAfter(now), is(true));
  }

  @Test
  public void randomPastYearMonth_WithBeforeGiven_ReturnsYearMonthBeforeGiven() {
    YearMonth before = YearMonth.now(CLOCK);
    assertThat(randomPastYearMonth(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastYearMonth_WithMinYearMonth_ThrowsIllegalArgumentException() {
    try {
      randomPastYearMonth(MIN_YEAR_MONTH);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Cannot produce date before " + MIN_INSTANT));
    }
  }

  @Test
  public void randomPastYearMonth_WithNullYearMonth_ThrowsIllegalArgumentException() {
    try {
      randomPastYearMonth(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomPastYearMonth_ReturnsYearMonthBeforeCurrentSystemClock() {
    YearMonth now = YearMonth.now(CLOCK);
    assertThat(randomPastYearMonth().isBefore(now), is(true));
  }

  @Test
  public void randomFourDigitYear_ReturnsRandomFourDigitYear() {
    Year year = randomFourDigitYear();
    assertTrue(year.isAfter(Year.of(999)));
    assertTrue(year.isBefore(Year.of(10_000)));
  }

  @Test
  public void randomFourDigitYear_ReturnsFourDigitYearBetweenGivenYears() {
    Year start = Year.now(CLOCK).minus(1, YEARS);
    Year end = Year.now(CLOCK).plus(1, YEARS);

    Year year = randomFourDigitYear(start, end);
    assertTrue(year.isAfter(start) || year.equals(start));
    assertTrue(year.isBefore(end));
  }

  @Test
  public void randomFourDigitYear_WithYearsOneYearApart_ReturnsStart() {
    Year start = Year.now(CLOCK);
    Year end = Year.now(CLOCK).plus(1, YEARS);
    assertThat(randomFourDigitYear(start, end), is(start));
  }

  @Test
  public void randomFourDigitYear_WithEqualYears_ReturnsStartYear() {
    Year monthDay = Year.now(CLOCK);
    assertThat(randomFourDigitYear(monthDay, monthDay), is(monthDay));
  }

  @Test
  public void randomFourDigitYear_WithNullEndYear_ThrowsIllegalArgumentException() {
    try {
      randomFourDigitYear(Year.now(CLOCK), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomFourDigitYear_WithNullStartYear_ThrowsIllegalArgumentException() {
    try {
      randomFourDigitYear(null, Year.now(CLOCK));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomFourDigitYear_WithStartAfterEndYear_ThrowsIllegalArgumentException() {
    Year start = Year.now(CLOCK).plus(1, YEARS);
    Year end = Year.now(CLOCK).minus(1, YEARS);
    try {
      randomFourDigitYear(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFourDigitYear_WithStartAfterMaxYear_ThrowsIllegalArgumentException() {
    Year start = Year.of(10_000);
    Year end = start.plus(1, YEARS);
    try {
      randomFourDigitYear(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must before 10,000"));
    }
  }

  @Test
  public void randomFourDigitYear_WithEndBeforeMinYear_ThrowsIllegalArgumentException() {
    Year start = Year.of(998);
    Year end = Year.of(999);
    try {
      randomFourDigitYear(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be after 999"));
    }
  }

  @Test
  public void randomFutureFourDigitYear_WithAfterGiven_ReturnsYearAfterGiven() {
    Year after = Year.now(CLOCK);
    assertThat(randomFutureFourDigitYear(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureFourDigitYear_WithMaxYear_ThrowsIllegalArgumentException() {
    try {
      randomFutureFourDigitYear(MAX_YEAR);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be before 9,999"));
    }
  }

  @Test
  public void randomFutureFourDigitYear_WithNullYear_ThrowsIllegalArgumentException() {
    try {
      randomFutureFourDigitYear(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("After must be non-null"));
    }
  }

  @Test
  public void randomFutureFourDigitYear_ReturnsYearAfterCurrentSystemClock() {
    Year now = Year.now(CLOCK);
    assertThat(randomFutureFourDigitYear().isAfter(now), is(true));
  }

  @Test
  public void randomPastFourDigitYear_WithBeforeGiven_ReturnsYearBeforeGiven() {
    Year before = Year.now(CLOCK);
    assertThat(randomPastFourDigitYear(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastFourDigitYear_WithMinYear_ThrowsIllegalArgumentException() {
    try {
      randomPastFourDigitYear(MIN_YEAR);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be after 1,000"));
    }
  }

  @Test
  public void randomPastFourDigitYear_WithNullYear_ThrowsIllegalArgumentException() {
    try {
      randomPastFourDigitYear(null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Before must be non-null"));
    }
  }

  @Test
  public void randomPastFourDigitYear_ReturnsYearBeforeCurrentSystemClock() {
    Year now = Year.now(CLOCK);
    assertThat(randomPastFourDigitYear().isBefore(now), is(true));
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

  @Test
  public void randomDayOfWeek_ReturnsRandomEnumFromDayOfWeek() {
    assertThat(randomDayOfWeek(), isIn(DayOfWeek.values()));
  }

  @Test
  public void randomMonth_ReturnsRandomEnumFromMonth() {
    assertThat(randomMonth(), isIn(Month.values()));
  }

  @Test
  public void randomZoneOffset_ReturnsRandomOffset() {
    assertThat(randomZoneOffset(), notNullValue());
  }

  @Test
  public void randomPeriod_ReturnsPeriodWhichMayBePositiveOrNegativeOrZero() {
    assertThat(randomPeriod().isNegative(), isOneOf(true, false));
  }

  @Test
  public void randomPositivePeriod_ReturnsPeriodWhichIsPositive() {
    assertThat(randomPositivePeriod().isNegative(), is(false));
  }

  @Test
  public void randomPositivePeriod_ReturnsPeriodWhichIsNotZero() {
    assertThat(randomPositivePeriod().isZero(), is(false));
  }

  @Test
  public void randomNegativePeriod_ReturnsPeriodWhichIsNegative() {
    assertThat(randomNegativePeriod().isNegative(), is(true));
  }

  @Test
  public void randomNegativePeriod_ReturnsPeriodWhichIsNotZero() {
    assertThat(randomNegativePeriod().isNegative(), is(true));
  }

  @Test
  public void randomDuration_ReturnsDurationWhichMayBePositiveOrNegativeOrZero() {
    assertThat(randomDuration().isNegative(), isOneOf(true, false));
  }

  @Test
  public void randomPositiveDuration_ReturnsDurationWhichIsPositive() {
    assertThat(randomPositiveDuration().isNegative(), is(false));
  }

  @Test
  public void randomPositiveDuration_ReturnsDurationWhichIsNotZero() {
    assertThat(randomPositiveDuration().isZero(), is(false));
  }

  @Test
  public void randomNegativeDuration_ReturnsDurationWhichIsNegative() {
    assertThat(randomNegativeDuration().isNegative(), is(true));
  }

  @Test
  public void randomNegativeDuration_ReturnsDurationWhichIsNotZero() {
    assertThat(randomNegativeDuration().isZero(), is(false));
  }
}
