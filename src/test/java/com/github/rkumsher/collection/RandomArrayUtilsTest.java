package com.github.rkumsher.collection;

import static com.github.rkumsher.collection.RandomArrayUtils.randomArrayFrom;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.collect.Range;

public class RandomArrayUtilsTest {

  @Test
  public void randomArrayFrom_WithConstantSupplier_ReturnsArrayFilledWithConstant() {
    Object onlyElement = new Object();
    Object[] array = randomArrayFrom(() -> onlyElement, Range.singleton(2));
    assertThat(array, arrayContaining(onlyElement, onlyElement));
  }

  @Test
  public void randomArrayFrom_WithZeroSize_ReturnsEmptyArray() {
    Object onlyElement = new Object();
    Object[] array = randomArrayFrom(() -> onlyElement, Range.singleton(0));
    assertThat(array, emptyArray());
  }

  @Test
  public void randomArrayFrom_WithSizeRangeContainsNegative_ThrowsIllegalArgumentException() {
    try {
      randomArrayFrom(Object::new, Range.closedOpen(-1, 1));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size range must consist of only positive integers"));
    }
  }

  @Test
  public void randomArrayFrom_WithNegativeSize_ThrowsIllegalArgumentException() {
    try {
      randomArrayFrom(Object::new, -1);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size must be greater than or equal to zero"));
    }
  }

  @Test
  public void randomArrayFrom_WithUnboundedLowerHowMany_ThrowsIllegalArgumentException() {
    try {
      randomArrayFrom(Object::new, Range.all());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size range must consist of only positive integers"));
    }
  }

  @Test
  public void randomArrayFrom_WithEmptyArrayWithSize_ThrowsIllegalArgumentException() {
    try {
      randomArrayFrom(new Object[0], 2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Elements to populate random array from must not be empty"));
    }
  }

  @Test
  public void randomArrayFrom_WithEmptyArrayWithRange_ThrowsIllegalArgumentException() {
    try {
      randomArrayFrom(new Object[0], Range.singleton(2));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Elements to populate random array from must not be empty"));
    }
  }

  @Test
  public void randomArrayFrom_WithSingletonCollection_ReturnsArrayFilledWithOnlyElement() {
    Object onlyElement = new Object();
    Object[] array = randomArrayFrom(new Object[] {onlyElement}, Range.singleton(2));
    assertThat(array, arrayContaining(onlyElement, onlyElement));
  }

  @Test
  public void randomArrayFrom_WithCollection_ReturnsArrayFilledWithElementsFromCollection() {
    Object[] from = new Object[] {new Object(), new Object()};
    Object[] array = randomArrayFrom(from, 10);
    assertThat(array, arrayWithSize(10));
    assertThat(Arrays.asList(array), everyItem(isIn(from)));
  }
}
