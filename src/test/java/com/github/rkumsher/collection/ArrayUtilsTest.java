package com.github.rkumsher.collection;

import static com.github.rkumsher.collection.ArrayUtils.containsAll;
import static com.github.rkumsher.collection.ArrayUtils.randomFrom;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

public class ArrayUtilsTest {

  @Test
  public void randomFrom_WithEmptyArray_ThrowsIllegalArgumentException() {
    try {
      randomFrom(new Object[0]);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Array cannot be empty"));
    }
  }

  @Test
  public void randomFrom_WithArrayWithOneElement_ReturnsOnlyElement() {
    Object onlyElement = new Object();
    Object[] array = {onlyElement};
    assertThat(randomFrom(array), sameInstance(onlyElement));
  }

  @Test
  public void randomFrom_ReturnsElementFromGivenArray() {
    Object[] array = {new Object(), new Object()};
    assertThat(randomFrom(array), isIn(array));
  }

  @Test
  public void randomFrom_WhenArrayOnlyContainsExcludes_ThrowsIllegalArgumentException() {
    String[] array = {"1", "2", "2", "3"};
    try {
      randomFrom(array, "1", "2", "3");
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Array only consists of the given excludes"));
    }
  }

  @Test
  public void randomFrom_WithExcludes_ReturnsElementFromArrayNotInExcludes() {
    String[] array = {"1", "2", "2", "3"};
    assertThat(randomFrom(array, "1", "2"), is("3"));
  }

  @Test
  public void randomFrom_WithExcludesAndEmptyArray_ThrowsIllegalArgumentException() {
    try {
      String[] excludes = {"1", "2"};
      randomFrom(new Object[0], excludes);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Array cannot be empty"));
    }
  }

  @Test
  public void containsAll_WhenElementsToCheckForIsEmpty_ReturnsTrue() {
    assertThat(containsAll(new String[0], Collections.emptyList()), is(true));
  }

  @Test
  public void containsAll_WhenArraysEqual_ReturnsTrue() {
    String[] arrayToCheck = new String[] {"a"};
    String[] elementsToCheckFor = new String[] {"a"};
    assertThat(containsAll(arrayToCheck, elementsToCheckFor), is(true));
  }

  @Test
  public void containsAll_WhenArrayToCheckDoesNotContainElement_ReturnsFalse() {
    String[] arrayToCheck = new String[] {"a"};
    assertThat(containsAll(arrayToCheck, "b"), is(false));
  }

  @Test
  public void containsAll_WhenArrayToCheckContainsAllElements_ReturnsTrue() {
    String[] arrayToCheck = new String[] {"a", "b"};
    assertThat(containsAll(arrayToCheck, "a", "b", "a", "b"), is(true));
  }
}
