package com.github.rkumsher.enums;

import static com.github.rkumsher.enums.RandomEnumUtils.random;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

import java.util.EnumSet;

import org.junit.Test;

public class RandomEnumUtilsTest {

  private enum EmptyEnum {}

  private enum SingletonEnum {
    ONLY_ELEMENT
  }

  private enum EnumWithTwoElements {
    FIRST_ELEMENT,
    SECOND_ELEMENT
  }

  @Test
  public void random_WithEmptyEnum_ThrowsIllegalArgumentException() {
    try {
      random(EmptyEnum.class);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      // Expected
    }
  }

  @Test
  public void random_WithSingletonEnum_ReturnsOnlyElement() {
    assertThat(random(SingletonEnum.class), sameInstance(SingletonEnum.ONLY_ELEMENT));
  }

  @Test
  public void random_ReturnsElementFromGivenEnumClass() {
    assertThat(random(EnumWithTwoElements.class), isIn(EnumWithTwoElements.values()));
  }

  @Test
  public void random_WithExcludes_ReturnsElementFromGivenEnumClassNotInExcludes() {
    assertThat(
        random(EnumWithTwoElements.class, EnumWithTwoElements.FIRST_ELEMENT),
        sameInstance(EnumWithTwoElements.SECOND_ELEMENT));
  }

  @Test
  public void random_WithExcludeCollection_ReturnsElementFromGivenEnumClassNotInExcludes() {
    assertThat(
        random(EnumWithTwoElements.class, EnumSet.of(EnumWithTwoElements.FIRST_ELEMENT)),
        sameInstance(EnumWithTwoElements.SECOND_ELEMENT));
  }

  @Test
  public void random_WhenEnumClassOnlyContainsExcludes_ThrowsIllegalArgumentException() {
    try {
      random(SingletonEnum.class, SingletonEnum.ONLY_ELEMENT);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      // Expected
    }
  }
}
