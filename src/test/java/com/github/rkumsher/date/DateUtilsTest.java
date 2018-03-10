package com.github.rkumsher.date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

  private static final Date TODAY = new Date();
  private static final LocalDateTime START_OF_TODAY = LocalDateTime.now().with(LocalTime.MIN);
  private static final LocalDateTime END_OF_TODAY = LocalDateTime.now().with(LocalTime.MAX);

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
