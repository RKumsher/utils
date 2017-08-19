package com.github.rkumsher.collection;

import static com.github.rkumsher.collection.RandomCollectionUtils.randomListFrom;
import static com.github.rkumsher.collection.RandomCollectionUtils.randomSetFrom;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;

public class RandomCollectionUtilsTest {

  @Test
  public void randomSetFrom_WithConstantSupplier_ReturnsSetWithJustConstant() {
    Object onlyElement = new Object();
    Set<Object> set = randomSetFrom(() -> onlyElement, Range.singleton(2));
    assertThat(set, contains(onlyElement));
  }

  @Test
  public void randomSetFrom_WithZeroSize_ReturnsEmptySet() {
    Object onlyElement = new Object();
    Set<Object> set = randomSetFrom(() -> onlyElement, Range.singleton(0));
    assertThat(set, empty());
  }

  @Test
  public void randomSetFrom_WithSizeRangeContainsNegative_ThrowsIllegalArgumentException() {
    try {
      randomSetFrom(Object::new, Range.closedOpen(-1, 1));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size range must consist of only positive integers"));
    }
  }

  @Test
  public void randomSetFrom_WithNegativeSize_ThrowsIllegalArgumentException() {
    try {
      randomSetFrom(Object::new, -1);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size must be greater than or equal to zero"));
    }
  }

  @Test
  public void randomSetFrom_WithUnboundedLowerHowMany_ThrowsIllegalArgumentException() {
    try {
      randomSetFrom(Object::new, Range.all());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Size range must consist of only positive integers"));
    }
  }

  @Test
  public void randomSetFrom_WithEmptyCollectionWithSize_ThrowsIllegalArgumentException() {
    try {
      randomSetFrom(Collections.emptySet(), 2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Elements to populate from must not be empty"));
    }
  }

  @Test
  public void randomSetFrom_WithEmptyCollectionWithRange_ThrowsIllegalArgumentException() {
    try {
      randomSetFrom(Collections.emptySet(), Range.singleton(2));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Elements to populate from must not be empty"));
    }
  }

  @Test
  public void randomSetFrom_WithSingletonCollection_ReturnsSetFilledWithOnlyElement() {
    Object onlyElement = new Object();
    Set<Object> set = randomSetFrom(Collections.singleton(onlyElement), Range.singleton(2));
    assertThat(set, contains(onlyElement));
  }

  @Test
  public void randomSetFrom_WithCollection_ReturnsSetFilledWithElementsFromCollection() {
    Set<Object> from = Sets.newHashSet(new Object(), new Object());
    Set<Object> set = randomSetFrom(from, 10);
    assertThat(set, hasSize(lessThanOrEqualTo(2)));
    assertThat(set, everyItem(isIn(from)));
  }

  @Test
  public void randomSetFrom_WithSupplier_ReturnsSetFilledFromSupplier() {
    Set<Object> set = randomSetFrom(Object::new, 10);
    assertThat(set, hasSize(10));
  }

  @Test
  public void randomListFrom_WithConstantSupplier_ReturnsListFilledWithConstant() {
    Object onlyElement = new Object();
    List<Object> list = randomListFrom(() -> onlyElement, Range.singleton(2));
    assertThat(list, contains(onlyElement, onlyElement));
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
  public void randomListFrom_WithEmptyCollectionWithSize_ThrowsIllegalArgumentException() {
    try {
      randomListFrom(Collections.emptyList(), 2);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Elements to populate from must not be empty"));
    }
  }

  @Test
  public void randomListFrom_WithEmptyCollectionWithRange_ThrowsIllegalArgumentException() {
    try {
      randomListFrom(Collections.emptyList(), Range.singleton(2));
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Elements to populate from must not be empty"));
    }
  }

  @Test
  public void randomListFrom_WithSingletonCollection_ReturnsListFilledWithOnlyElement() {
    Object onlyElement = new Object();
    List<Object> list = randomListFrom(Collections.singleton(onlyElement), Range.singleton(2));
    assertThat(list, contains(onlyElement, onlyElement));
  }

  @Test
  public void randomListFrom_WithCollection_ReturnsListFilledWithElementsFromCollection() {
    List<Object> from = Lists.newArrayList(new Object(), new Object());
    List<Object> list = randomListFrom(from, 10);
    assertThat(list, hasSize(10));
    assertThat(list, everyItem(isIn(from)));
  }
}
