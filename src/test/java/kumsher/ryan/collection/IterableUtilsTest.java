package kumsher.ryan.collection;

import static kumsher.ryan.collection.IterableUtils.randomFrom;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Test;

import com.google.common.collect.Sets;

public class IterableUtilsTest {

  @Test
  public void randomFrom_WithEmptyCollection_ThrowsIllegalArgumentException() {
    try {
      randomFrom(Collections.emptyList());
      fail("Should have thrown an IllegalArgumentException");
    } catch (IllegalArgumentException ex) {
      assertThat(ex.getMessage(), is("Iterable cannot be empty"));
    }
  }

  @Test
  public void randomFrom_WithSingletonCollection_ReturnsOnlyElement() {
    Object onlyElement = new Object();
    Iterable<Object> singletonCollection = Collections.singleton(onlyElement);
    assertThat(randomFrom(singletonCollection), sameInstance(onlyElement));
  }

  @Test
  public void randomFrom_ReturnsElementFromGivenCollection() {
    Collection<Object> collection = Sets.newHashSet(new Object(), new Object());
    assertThat(randomFrom(collection), isIn(collection));
  }
}
