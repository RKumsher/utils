package com.github.rkumsher.collection;

import static com.google.common.base.Preconditions.*;
import static org.apache.commons.lang3.ArrayUtils.isNotEmpty;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/** Utility library for working with arrays. */
public final class ArrayUtils {

  private ArrayUtils() {}

  /**
   * Returns a random element from the given array.
   *
   * @param array array to return random element from
   * @param <T> the type of elements in the given array
   * @return random element from the given array
   * @throws IllegalArgumentException if the array is empty
   */
  public static <T> T randomFrom(T[] array) {
    checkArgument(isNotEmpty(array), "Array cannot be empty");
    return IterableUtils.randomFrom(Arrays.asList(array));
  }

  /**
   * Returns a random element from the given array that's not in the values to exclude.
   *
   * @param array array to return random element from
   * @param excludes values to exclude
   * @param <T> the type of elements in the given array
   * @return random element from the given array that's not in the values to exclude
   * @throws IllegalArgumentException if the array is empty
   */
  @SafeVarargs
  public static <T> T randomFrom(T[] array, T... excludes) {
    return randomFrom(array, Arrays.asList(excludes));
  }

  /**
   * Returns a random element from the given array that's not in the values to exclude.
   *
   * @param array array to return random element from
   * @param excludes values to exclude
   * @param <T> the type of elements in the given array
   * @return random element from the given array that's not in the values to exclude
   * @throws IllegalArgumentException if the array is empty
   */
  public static <T> T randomFrom(T[] array, Collection<T> excludes) {
    checkArgument(isNotEmpty(array), "Array cannot be empty");
    List<T> list = Arrays.asList(array);
    Iterable<T> copy = Lists.newArrayList(list);
    Iterables.removeAll(copy, excludes);
    checkArgument(!Iterables.isEmpty(copy), "Array only consists of the given excludes");
    return IterableUtils.randomFrom(list, excludes);
  }

  /**
   * Returns whether or not the given array contains all the given elements to check for.
   *
   * <pre>
   *   ArrayUtils.containsAll(new String[] {}) = true;
   *   ArrayUtils.containsAll(new String[] {"a"}, "a") = true;
   *   ArrayUtils.containsAll(new String[] {"a"}, "b") = false;
   *   ArrayUtils.containsAll(new String[] {"a", "b"}, "a", "b", "a", "b") = true;
   * </pre>
   *
   * @param arrayToCheck array to to check
   * @param elementsToCheckFor elements to check for
   * @param <T> the type of elements in the given array
   * @return whether or not the given array contains all the given elements to check for.
   */
  @SafeVarargs
  public static <T> boolean containsAll(T[] arrayToCheck, T... elementsToCheckFor) {
    return containsAll(arrayToCheck, Arrays.asList(elementsToCheckFor));
  }

  /**
   * Returns whether or not the given array contains all the given elements to check for.
   *
   * <pre>
   *   ArrayUtils.containsAll(new String[] {}, Collections.emptyList()) = true;
   *   ArrayUtils.containsAll(new String[] {"a"}, Lists.newArrayList("a")) = true;
   *   ArrayUtils.containsAll(new String[] {"a"}, Lists.newArrayList("b")) = false;
   *   ArrayUtils.containsAll(new String[] {"a", "b"}, Lists.newArrayList("a", "b", "a", "b")) = true;
   * </pre>
   *
   * @param arrayToCheck array to to check
   * @param elementsToCheckFor elements to check for
   * @param <T> the type of elements in the given array
   * @return whether or not the given array contains all the given elements to check for.
   */
  public static <T> boolean containsAll(T[] arrayToCheck, Iterable<T> elementsToCheckFor) {
    return IterableUtils.containsAll(Arrays.asList(arrayToCheck), elementsToCheckFor);
  }
}
