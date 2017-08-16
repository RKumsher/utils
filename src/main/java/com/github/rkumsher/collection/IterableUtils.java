package com.github.rkumsher.collection;

import static com.google.common.base.Preconditions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.stream.StreamSupport;

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
    checkArgument(!containsAll(excludes, iterable), "Iterable only consists of the given excludes");
    Iterable<T> copy = Lists.newArrayList(iterable);
    Iterables.removeAll(copy, excludes);
    return randomFrom(copy);
  }

  /**
   * Returns whether or not the given {@link Iterable} contains all the given elements to check for.
   *
   * <pre>
   *   IterableUtils.containsAll(Collections.emptyList()) = true;
   *   IterableUtils.containsAll(Lists.newArrayList("a"), "a") = true;
   *   IterableUtils.containsAll(Lists.newArrayList("a"), "b") = false;
   *   IterableUtils.containsAll(Lists.newArrayList("a", "b"), "a", "b", "a", "b") = true;
   * </pre>
   *
   * @param iterableToCheck {@link Iterable} to to check
   * @param elementsToCheckFor elements to check for
   * @param <T> the type of elements in the given iterables
   * @return whether or not the given {@link Iterable} contains all the given elements to check for.
   */
  @SafeVarargs
  public static <T> boolean containsAll(Iterable<T> iterableToCheck, T... elementsToCheckFor) {
    return containsAll(iterableToCheck, Arrays.asList(elementsToCheckFor));
  }

  /**
   * Returns whether or not the given {@link Iterable} contains all the given elements to check for.
   *
   * <pre>
   *   IterableUtils.containsAll(Collections.emptyList(), Collections.emptyList()) = true;
   *   IterableUtils.containsAll(Lists.newArrayList("a"), Lists.newArrayList("a")) = true;
   *   IterableUtils.containsAll(Lists.newArrayList("a"), Lists.newArrayList("b")) = false;
   *   IterableUtils.containsAll(Lists.newArrayList("a", "b"), Lists.newArrayList("a", "b", "a", "b")) = true;
   * </pre>
   *
   * @param iterableToCheck {@link Iterable} to to check
   * @param elementsToCheckFor elements to check for
   * @param <T> the type of elements in the given iterables
   * @return whether or not the given {@link Iterable} contains all the given elements to check for.
   */
  public static <T> boolean containsAll(
      Iterable<T> iterableToCheck, Iterable<T> elementsToCheckFor) {
    return StreamSupport.stream(elementsToCheckFor.spliterator(), false)
        .allMatch(element -> Iterables.contains(iterableToCheck, element));
  }
}
