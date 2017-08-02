package com.github.rkumsher.collection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class IterableUtilsTest {

  @Test
  public void randomFrom_WithEmptyCollection_ThrowsIllegalArgumentException() {
    try {
      IterableUtils.randomFrom(Collections.emptyList());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Iterable cannot be empty"));
    }
  }

  @Test
  public void randomFrom_WithSingletonCollection_ReturnsOnlyElement() {
    Object onlyElement = new Object();
    Iterable<Object> singletonCollection = Collections.singleton(onlyElement);
    assertThat(IterableUtils.randomFrom(singletonCollection), sameInstance(onlyElement));
  }

  @Test
  public void randomFrom_ReturnsElementFromGivenCollection() {
    Collection<Object> collection = Lists.newArrayList(new Object(), new Object());
    assertThat(IterableUtils.randomFrom(collection), isIn(collection));
  }

  @Test
  public void randomFrom_WhenCollectionOnlyContainsExcludes_ThrowsIllegalArgumentException() {
    Collection<String> collection = Lists.newArrayList("1", "2", "2", "3");
    try {
      IterableUtils.randomFrom(collection, "1", "2", "3");
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Iterable only consists of the given excludes"));
    }
  }

  @Test
  public void randomFrom_WithExcludes_ReturnsElementFromCollectionNotInExcludes() {
    Collection<String> collection = Lists.newArrayList("1", "2", "2", "3");
    assertThat(IterableUtils.randomFrom(collection, "1", "2"), is("3"));
  }

  @Test
  public void randomFrom_WithExcludes_WorksWithImmutableCollection() {
    Collection<String> immutableCollection =
        ImmutableList.<String>builder().add("1").add("2").build();
    assertThat(IterableUtils.randomFrom(immutableCollection, "1"), is("2"));
  }

  @Test
  public void randomFrom_WithExcludesAndEmptyCollection_ThrowsIllegalArgumentException() {
    try {
      String[] excludes = {"1", "2"};
      IterableUtils.randomFrom(Collections.emptyList(), excludes);
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Iterable cannot be empty"));
    }
  }
}
