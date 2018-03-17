package com.github.rkumsher.enums;

import java.util.Optional;

/** Utility library for working with {@link Enum}s. */
public final class EnumUtils {

  private EnumUtils() {}

  /**
   * Returns an optional enum constant for the given type, using {@link Enum#valueOf}. If the
   * constant does not exist, {@link Optional#empty()} is returned.
   *
   * @param enumType - the Class object of the enum type from which to search for a constant
   * @param name - the name of the constant to search for
   * @return optional enum constant for the given type. If the constant does not exist, {@link
   *     Optional#empty()} is returned.
   */
  public static <T extends Enum<T>> Optional<T> getIfPresent(Class<T> enumType, String name) {
    try {
      return Optional.of(Enum.valueOf(enumType, name));
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}
