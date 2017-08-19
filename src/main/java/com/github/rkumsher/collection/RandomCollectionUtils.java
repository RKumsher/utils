package com.github.rkumsher.collection;

import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Iterables.isEmpty;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;

/** Utility library to generate random collections. */
public final class RandomCollectionUtils {

  private RandomCollectionUtils() {}

  /**
   * Returns a list filled randomly from the given elements.
   *
   * @param elements elements to randomly fill list from
   * @param size range that the size of the collection will be randomly chosen from
   * @param <T> the type of elements in the given iterable
   * @return list filled randomly from the given elements
   * @throws IllegalArgumentException if the elements to fill list from is empty or if the size
   *     range contains negative integers
   */
  public static <T> List<T> randomListFrom(Iterable<T> elements, Range<Integer> size) {
    checkArgument(!isEmpty(elements), "Elements to populate random list from must not be empty");
    return randomListFrom(() -> IterableUtils.randomFrom(elements), size);
  }

  /**
   * Returns a list filled randomly from the given elements.
   *
   * @param elements elements to randomly fill list from
   * @param size of the random collection to return
   * @param <T> the type of elements in the given iterable
   * @return list filled randomly from the given elements
   * @throws IllegalArgumentException if the elements to fill list from is empty or if the size is
   *     negative
   */
  public static <T> List<T> randomListFrom(Iterable<T> elements, int size) {
    checkArgument(!isEmpty(elements), "Elements to populate random list from must not be empty");
    return randomListFrom(() -> IterableUtils.randomFrom(elements), size);
  }

  /**
   * Returns a list filled from the given element supplier.
   *
   * @param elementSupplier element supplier to fill list from
   * @param size range that the size of the collection will be randomly chosen from
   * @param <T> the type of element the given supplier returns
   * @return list filled from the given elements
   * @throws IllegalArgumentException if the size range contains negative integers
   */
  public static <T> List<T> randomListFrom(Supplier<T> elementSupplier, Range<Integer> size) {
    checkArgument(
        size.hasLowerBound() && size.lowerEndpoint() >= 0,
        "Size range must consist of only positive integers");
    Set<Integer> rangeSet = ContiguousSet.create(size, DiscreteDomain.integers());
    int limit = IterableUtils.randomFrom(rangeSet);
    return randomListFrom(elementSupplier, limit);
  }

  /**
   * Returns a list filled from the given element supplier.
   *
   * @param elementSupplier element supplier to fill list from
   * @param size of the random collection to return
   * @param <T> the type of element the given supplier returns
   * @return list filled from the given elements
   * @throws IllegalArgumentException if the size is negative
   */
  public static <T> List<T> randomListFrom(Supplier<T> elementSupplier, int size) {
    checkArgument(size >= 0, "Size must be greater than or equal to zero");
    return Stream.generate(elementSupplier).limit(size).collect(Collectors.toList());
  }
}
