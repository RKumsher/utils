package com.github.rkumsher.number;

import static com.github.rkumsher.number.RandomNumberUtils.randomDouble;
import static com.github.rkumsher.number.RandomNumberUtils.randomDoubleGreaterThan;
import static com.github.rkumsher.number.RandomNumberUtils.randomDoubleLessThan;
import static com.github.rkumsher.number.RandomNumberUtils.randomInt;
import static com.github.rkumsher.number.RandomNumberUtils.randomIntGreaterThan;
import static com.github.rkumsher.number.RandomNumberUtils.randomIntLessThan;
import static com.github.rkumsher.number.RandomNumberUtils.randomLong;
import static com.github.rkumsher.number.RandomNumberUtils.randomLongGreaterThan;
import static com.github.rkumsher.number.RandomNumberUtils.randomLongLessThan;
import static com.github.rkumsher.number.RandomNumberUtils.randomNegativeDouble;
import static com.github.rkumsher.number.RandomNumberUtils.randomNegativeInt;
import static com.github.rkumsher.number.RandomNumberUtils.randomNegativeLong;
import static com.github.rkumsher.number.RandomNumberUtils.randomPositiveDouble;
import static com.github.rkumsher.number.RandomNumberUtils.randomPositiveInt;
import static com.github.rkumsher.number.RandomNumberUtils.randomPositiveLong;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

import org.junit.Test;

public class RandomNumberUtilsTest {

  @Test
  public void randomInt_ReturnsRandomInt() {
    assertThat(
        randomInt(),
        allOf(greaterThanOrEqualTo(Integer.MIN_VALUE), lessThanOrEqualTo(Integer.MAX_VALUE)));
  }

  @Test
  public void randomPositiveInt_ReturnsIntGreaterThanZero() {
    assertThat(randomPositiveInt(), greaterThan(0));
  }

  @Test
  public void randomNegativeInt_ReturnsIntLessThanZero() {
    assertThat(randomNegativeInt(), lessThan(0));
  }

  @Test
  public void randomInt_ReturnsIntBetweenGivenInts() {
    int start = -10;
    int end = 10;
    assertThat(randomInt(start, end), allOf(greaterThanOrEqualTo(start), lessThan(end)));
  }

  @Test
  public void randomInt_WithEqualInts_ReturnsStart() {
    int start = 10;
    int end = 10;
    assertThat(randomInt(start, end), is(start));
  }

  @Test
  public void randomInt_WithStartAfterEnd_ThrowsIllegalArgumentException() {
    int start = 10;
    int end = -10;
    try {
      randomInt(start, end);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("End must be greater than or equal to start"));
    }
  }

  @Test
  public void randomIntGreaterThan_ReturnsIntGreaterThanGiven() {
    int minExclusive = 10;
    assertThat(randomIntGreaterThan(minExclusive), greaterThan(minExclusive));
  }

  @Test
  public void randomIntGreaterThan_WithMaxValue_ThrowsIllegalArgumentException() {
    try {
      randomIntGreaterThan(Integer.MAX_VALUE);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("Cannot produce int greater than " + Integer.MAX_VALUE));
    }
  }

  @Test
  public void randomIntLessThan_ReturnsIntLessThanGiven() {
    int maxExclusive = 10;
    assertThat(randomIntLessThan(maxExclusive), lessThan(maxExclusive));
  }

  @Test
  public void randomIntLessThan_WithMinValue_ThrowsIllegalArgumentException() {
    try {
      randomIntLessThan(Integer.MIN_VALUE);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("Cannot produce int less than " + Integer.MIN_VALUE));
    }
  }

  @Test
  public void randomLong_ReturnsRandomLong() {
    assertThat(
        randomLong(),
        allOf(greaterThanOrEqualTo(Long.MIN_VALUE), lessThanOrEqualTo(Long.MAX_VALUE)));
  }

  @Test
  public void randomPositiveLong_ReturnsLongGreaterThanZero() {
    assertThat(randomPositiveLong(), greaterThan(0L));
  }

  @Test
  public void randomNegativeLong_ReturnsLongLessThanZero() {
    assertThat(randomNegativeLong(), lessThan(0L));
  }

  @Test
  public void randomLong_ReturnsLongBetweenGivenLongs() {
    long start = -10;
    long end = 10;
    assertThat(randomLong(start, end), allOf(greaterThanOrEqualTo(start), lessThan(end)));
  }

  @Test
  public void randomLong_WithEqualLongs_ReturnsStart() {
    long start = 10;
    long end = 10;
    assertThat(randomLong(start, end), is(start));
  }

  @Test
  public void randomLong_WithStartAfterEnd_ThrowsIllegalArgumentException() {
    long start = 10;
    long end = -10;
    try {
      randomLong(start, end);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("End must be greater than or equal to start"));
    }
  }

  @Test
  public void randomLongGreaterThan_ReturnsLongGreaterThanGiven() {
    long minExclusive = 10;
    assertThat(randomLongGreaterThan(minExclusive), greaterThan(minExclusive));
  }

  @Test
  public void randomLongGreaterThan_WithMaxValue_ThrowsIllegalArgumentException() {
    try {
      randomLongGreaterThan(Long.MAX_VALUE);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("Cannot produce long greater than " + Long.MAX_VALUE));
    }
  }

  @Test
  public void randomLongLessThan_ReturnsLongLessThanGiven() {
    long maxExclusive = 10;
    assertThat(randomLongLessThan(maxExclusive), lessThan(maxExclusive));
  }

  @Test
  public void randomLongLessThan_WithMinValue_ThrowsIllegalArgumentException() {
    try {
      randomLongLessThan(Long.MIN_VALUE);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("Cannot produce long less than " + Long.MIN_VALUE));
    }
  }

  @Test
  public void randomDouble_ReturnsRandomDouble() {
    assertThat(
        randomDouble(),
        allOf(greaterThanOrEqualTo(-Double.MAX_VALUE), lessThanOrEqualTo(Double.MAX_VALUE)));
  }

  @Test
  public void randomPositiveDouble_ReturnsDoubleGreaterThanZero() {
    assertThat(randomPositiveDouble(), greaterThan(0d));
  }

  @Test
  public void randomNegativeDouble_ReturnsDoubleLessThanZero() {
    assertThat(randomNegativeDouble(), lessThan(0d));
  }

  @Test
  public void randomDouble_ReturnsDoubleBetweenGivenDoubles() {
    double start = -10;
    double end = 10;
    assertThat(randomDouble(start, end), allOf(greaterThanOrEqualTo(start), lessThan(end)));
  }

  @Test
  public void randomDouble_WithEqualDoubles_ReturnsStart() {
    double start = 10;
    double end = 10;
    assertThat(randomDouble(start, end), is(start));
  }

  @Test
  public void randomDouble_WithStartAfterEnd_ThrowsIllegalArgumentException() {
    double start = 10;
    double end = -10;
    try {
      randomDouble(start, end);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("End must be greater than or equal to start"));
    }
  }

  @Test
  public void randomDoubleGreaterThan_ReturnsDoubleGreaterThanGiven() {
    double minExclusive = 10;
    assertThat(randomDoubleGreaterThan(minExclusive), greaterThan(minExclusive));
  }

  @Test
  public void randomDoubleGreaterThan_WithMaxValue_ThrowsIllegalArgumentException() {
    try {
      randomDoubleGreaterThan(Double.MAX_VALUE);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("Cannot produce double greater than " + Double.MAX_VALUE));
    }
  }

  @Test
  public void randomDoubleLessThan_ReturnsDoubleLessThanGiven() {
    double maxExclusive = 10;
    assertThat(randomDoubleLessThan(maxExclusive), lessThan(maxExclusive));
  }

  @Test
  public void randomDoubleLessThan_WithMinValue_ThrowsIllegalArgumentException() {
    try {
      randomDoubleLessThan(-Double.MAX_VALUE);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is("Cannot produce double less than " + -Double.MAX_VALUE));
    }
  }
}
