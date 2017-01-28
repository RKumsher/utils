package kumsher.ryan;

import static kumsher.ryan.RandomEnumUtils.random;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

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
}
