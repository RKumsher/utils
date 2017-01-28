package kumsher.ryan;

import java.util.EnumSet;

import kumsher.ryan.collection.IterableUtils;

/** Utility library to return random elements from enum instances. */
public class RandomEnumUtils {

  /**
   * Returns a random element from the given enum class.
   *
   * @param enumClass enum class to return random element from
   * @return random element from the given enum class
   */
  public static <T extends Enum<T>> T random(Class<T> enumClass) {
    EnumSet<T> enums = EnumSet.allOf(enumClass);
    return IterableUtils.randomFrom(enums);
  }
}
