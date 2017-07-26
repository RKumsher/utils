package kumsher.ryan.enums;

import java.util.Collection;
import java.util.EnumSet;

import kumsher.ryan.collection.IterableUtils;

/** Utility library to retrieve random elements from enum instances. */
public final class RandomEnumUtils {

  private RandomEnumUtils() {}

  /**
   * Returns a random element from the given enum class.
   *
   * @param enumClass enum class to return random element from
   * @return random element from the given enum class
   * @throws IllegalArgumentException if the given enumClass has no values
   */
  public static <T extends Enum<T>> T random(Class<T> enumClass) {
    EnumSet<T> enums = EnumSet.allOf(enumClass);
    return IterableUtils.randomFrom(enums);
  }

  /**
   * Returns a random element from the given enum class that's not in the values to exclude.
   *
   * @param enumClass enum class to return random element from
   * @param excludes values to exclude
   * @return random element from the given enum class that's not in the values to exclude.
   * @throws IllegalArgumentException if the given enumClass has no values
   */
  public static <T extends Enum<T>> T random(Class<T> enumClass, T... excludes) {
    EnumSet<T> enums = EnumSet.allOf(enumClass);
    return IterableUtils.randomFrom(enums, excludes);
  }

  /**
   * Returns a random element from the given enum class that's not in the values to exclude.
   *
   * @param enumClass enum class to return random element from
   * @param excludes values to exclude
   * @return random element from the given enum class that's not in the values to exclude.
   * @throws IllegalArgumentException if the given enumClass has no values
   */
  public static <T extends Enum<T>> T random(Class<T> enumClass, Collection<T> excludes) {
    EnumSet<T> enums = EnumSet.allOf(enumClass);
    return IterableUtils.randomFrom(enums, excludes);
  }
}
