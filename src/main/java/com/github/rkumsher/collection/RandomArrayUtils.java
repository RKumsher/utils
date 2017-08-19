package com.github.rkumsher.collection;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Iterables.isEmpty;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/** Utility library to generate random arrays. */
public final class RandomArrayUtils {

  private RandomArrayUtils() {}

  /**
   * Returns an array filled randomly from the given elements.
   *
   * @param elements elements to randomly fill array from
   * @param size range that the size of the array will be randomly chosen from
   * @param <T> the type of elements in the given iterable
   * @return array filled randomly from the given elements
   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size
   *     range contains negative integers
   */
  public static <T> T[] randomArrayFrom(T[] elements, Range<Integer> size) {
    return randomArrayFrom(Arrays.asList(elements), size);
  }

  /**
   * Returns an array filled randomly from the given elements.
   *
   * @param elements elements to randomly fill array from
   * @param size range that the size of the array will be randomly chosen from
   * @param <T> the type of elements in the given iterable
   * @return array filled randomly from the given elements
   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size
   *     range contains negative integers
   */
  public static <T> T[] randomArrayFrom(Iterable<T> elements, Range<Integer> size) {
    checkArgument(!isEmpty(elements), "Elements to populate random array from must not be empty");
    return randomArrayFrom(() -> IterableUtils.randomFrom(elements), size);
  }

  /**
   * Returns an array filled randomly from the given elements.
   *
   * @param elements elements to randomly fill array from
   * @param size of the random array to return
   * @param <T> the type of elements in the given iterable
   * @return array filled randomly from the given elements
   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size is
   *     negative
   */
  public static <T> T[] randomArrayFrom(T[] elements, int size) {
    return randomArrayFrom(Arrays.asList(elements), size);
  }

  /**
   * Returns an array filled randomly from the given elements.
   *
   * @param elements elements to randomly fill array from
   * @param size of the random array to return
   * @param <T> the type of elements in the given iterable
   * @return array filled randomly from the given elements
   * @throws IllegalArgumentException if the elements to fill array from is empty or if the size is
   *     negative
   */
  public static <T> T[] randomArrayFrom(Iterable<T> elements, int size) {
    checkArgument(!isEmpty(elements), "Elements to populate random array from must not be empty");
    return randomArrayFrom(() -> IterableUtils.randomFrom(elements), size);
  }

  /**
   * Returns an array filled from the given element supplier.
   *
   * @param elementSupplier element supplier to fill array from
   * @param size range that the size of the array will be randomly chosen from
   * @param <T> the type of element the given supplier returns
   * @return array filled from the given elements
   * @throws IllegalArgumentException if the size range contains negative integers
   */
  public static <T> T[] randomArrayFrom(Supplier<T> elementSupplier, Range<Integer> size) {
    checkArgument(
        size.hasLowerBound() && size.lowerEndpoint() >= 0,
        "Size range must consist of only positive integers");
    Set<Integer> rangeSet = ContiguousSet.create(size, DiscreteDomain.integers());
    int limit = IterableUtils.randomFrom(rangeSet);
    return randomArrayFrom(elementSupplier, limit);
  }

  /**
   * Returns an array filled from the given element supplier.
   *
   * @param elementSupplier element supplier to fill array from
   * @param size of the random array to return
   * @param <T> the type of element the given supplier returns
   * @return array filled from the given elements
   * @throws IllegalArgumentException if the size is negative
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] randomArrayFrom(Supplier<T> elementSupplier, int size) {
    checkArgument(size >= 0, "Size must be greater than or equal to zero");
    return (T[]) Stream.generate(elementSupplier).limit(size).toArray(Object[]::new);
  }
}
