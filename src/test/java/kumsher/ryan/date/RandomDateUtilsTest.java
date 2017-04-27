package kumsher.ryan.date;

import static kumsher.ryan.date.RandomDateUtils.MAX_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.MIN_INSTANT;
import static kumsher.ryan.date.RandomDateUtils.randomFutureInstant;
import static kumsher.ryan.date.RandomDateUtils.randomInstant;
import static kumsher.ryan.date.RandomDateUtils.randomPastInstant;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class RandomDateUtilsTest {

  @Test
  public void randomInstant_ReturnsInstantBetweenMinAndMaxInstants() {
    Instant instant = randomInstant();
    assertTrue(instant.isAfter(MIN_INSTANT) || instant.equals(MIN_INSTANT));
    assertTrue(instant.isBefore(MAX_INSTANT));
  }

  @Test
  public void randomInstant_ReturnsInstantBetweenGivenInstant() {
    Instant start = Instant.now().minus(1, ChronoUnit.DAYS);
    Instant end = Instant.now().plus(1, ChronoUnit.DAYS);

    Instant instant = randomInstant(start, end);
    assertTrue(instant.isAfter(start) || instant.equals(start));
    assertTrue(instant.isBefore(end));
  }

  @Test
  public void randomInstant_WithInstantsOneMillisecondApart_ReturnsStartInstance() {
    Instant start = Instant.now();
    Instant end = start.plus(1, ChronoUnit.MILLIS);
    assertThat(randomInstant(start, end), is(start));
  }

  @Test
  public void randomInstant_WithEqualInstants_ReturnsStartInstant() {
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
  public void randomFutureInstant_WithAfterGiven_ReturnsInstantAfterGivenInstant() {
    Instant after = Instant.now();
    assertThat(randomFutureInstant(after).isAfter(after), is(true));
  }

  @Test
  public void randomFutureInstant_WithMaxInstant_ThrowsIllegalArgumentException() {
    try {
      RandomDateUtils.randomFutureInstant(MAX_INSTANT);
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
  public void randomPastInstant_WithBeforeGiven_ReturnsInstantBeforeGivenInstant() {
    Instant before = Instant.now();
    assertThat(randomPastInstant(before).isBefore(before), is(true));
  }

  @Test
  public void randomPastDate_WithMinInstant_ThrowsIllegalArgumentException() {
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
