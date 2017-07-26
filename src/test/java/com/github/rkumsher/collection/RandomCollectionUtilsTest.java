package com.github.rkumsher.collection;

import static com.github.rkumsher.collection.RandomCollectionUtils.randomListFrom;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;

public class RandomCollectionUtilsTest {

  @Test
  public void randomListFrom_WithConstantSupplier_ReturnsListFilledWithConstant() {
    Object onlyElement = new Object();
    List<Object> list = randomListFrom(() -> onlyElement, Range.singleton(2));
    assertThat(list, is(Lists.newArrayList(onlyElement, onlyElement)));
  }

  @Test
  public void randomListFrom_WithZeroSize_ReturnsEmptyList() {
    Object onlyElement = new Object();
    List<Object> list = randomListFrom(() -> onlyElement, Range.singleton(0));
    assertThat(list, empty());
  }

  @Test
  public void randomListFrom_WithSizeRangeContainsNegative_ThrowsIllegalArgumentException() {
    try {
      randomListFrom(Object::new, Range.closedOpen(-1, 1));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size range must consist of only positive integers"));
    }
  }

  @Test
  public void randomListFrom_WithNegativeSize_ThrowsIllegalArgumentException() {
    try {
      randomListFrom(Object::new, -1);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size must be greater than or equal to zero"));
    }
  }

  @Test
  public void randomListFrom_WithUnboundedLowerHowMany_ThrowsIllegalArgumentException() {
    try {
      randomListFrom(Object::new, Range.all());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size range must consist of only positive integers"));
    }
  }

  @Test
  public void randomListFrom_WithEmptyCollection_ThrowsIllegalArgumentException() {
    try {
      randomListFrom(Collections.emptyList(), Range.singleton(2));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Elements to populate random list from must not be empty"));
    }
  }

  @Test
  public void randomListFrom_WithSingletonCollection_ReturnsListFilledWithOnlyElement() {
    Object onlyElement = new Object();
    List<Object> list = randomListFrom(Collections.singleton(onlyElement), Range.singleton(2));
    assertThat(list, is(Lists.newArrayList(onlyElement, onlyElement)));
  }

  @Test
  public void randomListFrom_WithCollection_ReturnsListFilledWithElementsFromCollection() {
    List<Object> from = Lists.newArrayList(new Object(), new Object());
    List<Object> list = randomListFrom(from, 10);
    assertThat(list, hasSize(10));
    assertThat(list, everyItem(isIn(from)));
  }
}
