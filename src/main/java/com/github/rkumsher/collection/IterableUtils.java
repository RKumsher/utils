package com.github.rkumsher.collection;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/** Utility library for working with {@link Iterable}s. */
public final class IterableUtils {

  private static final Random RANDOM = new Random();

  private IterableUtils() {}

  /**
   * Returns a random element from the given {@link Iterable}.
   *
   * @param iterable {@link Iterable} to return random element from
   * @param <T> the type of elements in the given iterable
   * @return random element from the given {@link Iterable}
   * @throws IllegalArgumentException if the iterable is empty
   */
  public static <T> T randomFrom(Iterable<T> iterable) {
    checkArgument(!Iterables.isEmpty(iterable), "Iterable cannot be empty");
    int randomIndex = RANDOM.nextInt(Iterables.size(iterable));
    return Iterables.get(iterable, randomIndex);
  }

  /**
   * Returns a random element from the given {@link Iterable} that's not in the values to exclude.
   *
   * @param iterable {@link Iterable} to return random element from
   * @param excludes values to exclude
   * @param <T> the type of elements in the given iterable
   * @return random element from the given {@link Iterable} that's not in the values to exclude
   * @throws IllegalArgumentException if the iterable is empty
   */
  @SafeVarargs
  public static <T> T randomFrom(Iterable<T> iterable, T... excludes) {
    return randomFrom(iterable, Arrays.asList(excludes));
  }

  /**
   * Returns a random element from the given {@link Iterable} that's not in the values to exclude.
   *
   * @param iterable {@link Iterable} to return random element from
   * @param excludes values to exclude
   * @param <T> the type of elements in the given iterable
   * @return random element from the given {@link Iterable} that's not in the values to exclude
   * @throws IllegalArgumentException if the iterable is empty
   */
  public static <T> T randomFrom(Iterable<T> iterable, Collection<T> excludes) {
    checkArgument(!Iterables.isEmpty(iterable), "Iterable cannot be empty");
    Iterable<T> copy = Lists.newArrayList(iterable);
    Iterables.removeAll(copy, excludes);
    checkArgument(!Iterables.isEmpty(copy), "Iterable only consists of the given excludes");
    return randomFrom(copy);
  }
}
