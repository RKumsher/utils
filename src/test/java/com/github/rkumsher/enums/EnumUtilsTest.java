package com.github.rkumsher.enums;

import static com.github.rkumsher.enums.EnumUtils.getIfPresent;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

public class EnumUtilsTest {

  private enum SingletonEnum {
    ONLY_ELEMENT
  }

  @Test
  public void getIfPresent_WhenEnumConstantFound_ReturnsOptionalOfEnumConstant() {
    assertThat(
        getIfPresent(SingletonEnum.class, "ONLY_ELEMENT"),
        is(Optional.of(SingletonEnum.ONLY_ELEMENT)));
  }

  @Test
  public void getIfPresent_WhenEnumConstantNotFound_ReturnsEmptyOptional() {
    assertThat(getIfPresent(SingletonEnum.class, "Does not exist"), is(Optional.empty()));
  }
}
