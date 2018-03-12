package com.github.rkumsher.date;

import static com.github.rkumsher.date.DateUtils.isLeapDay;
import static com.github.rkumsher.date.RandomDateUtils.randomMonthDay;
import static java.time.Month.FEBRUARY;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

  private static final Date TODAY = new Date();
  private static final LocalDateTime START_OF_TODAY = LocalDateTime.now().with(LocalTime.MIN);
  private static final LocalDateTime END_OF_TODAY = LocalDateTime.now().with(LocalTime.MAX);

  @Test
  public void isLeapDay_WhenAccessorDoesNotSupportMonthOfYear_ThrowsIllegalArgumentException() {
    Year year = Year.now();
    try {
      isLeapDay(year);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is(year + " does not support MONTH_OF_YEAR"));
    }
  }

  @Test
  public void isLeapDay_WhenAccessorDoesNotSupportDayOfMonth_ThrowsIllegalArgumentException() {
    Month month = Month.FEBRUARY;
    try {
      isLeapDay(month);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage(), is(month + " does not support DAY_OF_MONTH"));
    }
  }

  @Test
  public void isLeapDay_WithFebruaryTwentyNinth_MonthDay_ReturnsTrue() {
    MonthDay monthDay = MonthDay.of(FEBRUARY, 29);
    assertThat(isLeapDay(monthDay), is(true));
  }

  @Test
  public void isLeapDay_WithFebruaryTwentyEighth_MonthDay_ReturnsFalse() {
    MonthDay monthDay = randomMonthDay(false);
    assertThat(isLeapDay(monthDay), is(false));
  }

  @Test
  public void isLeapDay_WithFebruaryTwentyNinth_LocalDate_ReturnsTrue() {
    LocalDate localDate =
        LocalDate.now().withYear(2000).withMonth(FEBRUARY.getValue()).withDayOfMonth(29);
    assertThat(isLeapDay(localDate), is(true));
  }

  @Test
  public void isLeapDay_WithFebruaryTwentyEighth_LocalDate_ReturnsFalse() {
    LocalDate localDate = LocalDate.now().withMonth(FEBRUARY.getValue()).withDayOfMonth(28);
    assertThat(isLeapDay(localDate), is(false));
  }

  @Test
  public void atStartOfDay_WithToday_ReturnsStartOfToday() {
    Date startOfDay = DateUtils.atStartOfDay(TODAY);
    assertThat(startOfDay, is(DateUtils.localDateTimeToDate(START_OF_TODAY)));
  }

  @Test
  public void atEndOfDay_WithToday_ReturnsEndOfToday() {
    Date endOfDay = DateUtils.atEndOfDay(TODAY);
    assertThat(endOfDay, is(DateUtils.localDateTimeToDate(END_OF_TODAY)));
  }
}
