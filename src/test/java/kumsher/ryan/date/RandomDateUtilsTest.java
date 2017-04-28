package kumsher.ryan.date;

import static kumsher.ryan.date.RandomDateUtils.DEFAULT_ZONE;
import static kumsher.ryan.date.RandomDateUtils.MAX_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.MIN_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.randomFutureInstant;
import static kumsher.ryan.date.RandomDateUtils.randomFutureZonedDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomInstant;
import static kumsher.ryan.date.RandomDateUtils.randomPastInstant;
import static kumsher.ryan.date.RandomDateUtils.randomPastZonedDateTime;
import static kumsher.ryan.date.RandomDateUtils.randomZonedDateTime;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class RandomDateUtilsTest {

  private static final ZonedDateTime MIN_ZONED_DATE_TIME =
      ZonedDateTime.ofInstant(MIN_INSTANT, DEFAULT_ZONE);
  private static final ZonedDateTime MAX_ZONED_DATE_TIME =
      ZonedDateTime.ofInstant(MAX_INSTANT, DEFAULT_ZONE);

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
    ZonedDateTime start = ZonedDateTime.now().minus(1, ChronoUnit.DAYS);
    ZonedDateTime end = ZonedDateTime.now().plus(1, ChronoUnit.DAYS);

    ZonedDateTime zonedDateTime = randomZonedDateTime(start, end);
    assertTrue(zonedDateTime.isAfter(start) || zonedDateTime.equals(start));
    assertTrue(zonedDateTime.isBefore(end));
  }

  @Test
  public void randomZonedDateTime_WithZonedDateTimesOneMillisecondApart_ReturnsStart() {
    ZonedDateTime start = ZonedDateTime.now();
    ZonedDateTime end = start.plus(1, ChronoUnit.MILLIS);
    assertThat(randomZonedDateTime(start, end), is(start));
  }

  @Test
  public void randomZonedDateTime_WithEqualZonedDateTimes_ReturnsStartZonedDateTime() {
    ZonedDateTime zonedDateTime = ZonedDateTime.now();
    assertThat(randomZonedDateTime(zonedDateTime, zonedDateTime), is(zonedDateTime));
  }

  @Test
  public void randomZonedDateTime_WithNullEndZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomZonedDateTime(ZonedDateTime.now(), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomZonedDateTime_WithNullStartZonedDateTime_ThrowsIllegalArgumentException() {
    try {
      randomZonedDateTime(null, ZonedDateTime.now());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomZonedDateTime_WithStartAfterEndZonedDateTime_ThrowsIllegalArgumentException() {
    ZonedDateTime start = ZonedDateTime.now().plus(1, ChronoUnit.DAYS);
    ZonedDateTime end = ZonedDateTime.now().minus(1, ChronoUnit.DAYS);
    try {
      randomZonedDateTime(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureZonedDateTime_WithAfterGiven_ReturnsZonedDateTimeAfterGiven() {
    ZonedDateTime after = ZonedDateTime.now();
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
    ZonedDateTime now = ZonedDateTime.now();
    assertThat(randomFutureZonedDateTime().isAfter(now), is(true));
  }

  @Test
  public void randomPastZonedDateTime_WithBeforeGiven_ReturnsZonedDateTimeBeforeGiven() {
    ZonedDateTime before = ZonedDateTime.now();
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
    ZonedDateTime now = ZonedDateTime.now();
    assertThat(randomPastZonedDateTime().isBefore(now), is(true));
  }

  @Test
  public void randomInstant_ReturnsInstantBetweenMinAndMaxInstants() {
    Instant instant = randomInstant();
    assertTrue(instant.isAfter(MIN_INSTANT) || instant.equals(MIN_INSTANT));
    assertTrue(instant.isBefore(MAX_INSTANT));
  }

  @Test
  public void randomInstant_ReturnsInstantBetweenGivenInstants() {
    Instant start = Instant.now().minus(1, ChronoUnit.DAYS);
    Instant end = Instant.now().plus(1, ChronoUnit.DAYS);

    Instant instant = randomInstant(start, end);
    assertTrue(instant.isAfter(start) || instant.equals(start));
    assertTrue(instant.isBefore(end));
  }

  @Test
  public void randomInstant_WithInstantsOneMillisecondApart_ReturnsStart() {
    Instant start = Instant.now();
    Instant end = start.plus(1, ChronoUnit.MILLIS);
    assertThat(randomInstant(start, end), is(start));
  }

  @Test
  public void randomInstant_WithEqualInstants_ReturnsStart() {
    Instant instant = Instant.now();
    assertThat(randomInstant(instant, instant), is(instant));
  }

  @Test
  public void randomInstant_WithNullEndInstant_ThrowsIllegalArgumentException() {
    try {
      randomInstant(Instant.now(), null);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must be non-null"));
    }
  }

  @Test
  public void randomInstant_WithNullStartInstant_ThrowsIllegalArgumentException() {
    try {
      randomInstant(null, Instant.now());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Start must be non-null"));
    }
  }

  @Test
  public void randomInstant_WithStartAfterEndInstant_ThrowsIllegalArgumentException() {
    Instant start = Instant.now().plus(1, ChronoUnit.DAYS);
    Instant end = Instant.now().minus(1, ChronoUnit.DAYS);
    try {
      randomInstant(start, end);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("End must come on or after start"));
    }
  }

  @Test
  public void randomFutureInstant_WithAfterGiven_ReturnsInstantAfterGiven() {
    Instant after = Instant.now();
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
    Instant now = Instant.now();
    assertThat(randomFutureInstant().isAfter(now), is(true));
  }

  @Test
  public void randomPastInstant_WithBeforeGiven_ReturnsInstantBeforeGiven() {
    Instant before = Instant.now();
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
    Instant now = Instant.now();
    assertThat(randomPastInstant().isBefore(now), is(true));
  }
}
