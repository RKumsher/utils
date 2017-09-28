package com.github.rkumsher.number;

import static com.google.common.base.Preconditions.*;

import java.util.Random;

/**
 * Utility library to return random numbers. Unlike Apaches RandomUtils, this supports negative
 * numbers.
 */
public class RandomNumberUtils {

  private static final Random RANDOM = new Random();

  private RandomNumberUtils() {}

  /**
   * Returns a random int which may be positive, negative, or zero.
   *
   * @return the random int
   */
  public static int randomInt() {
    return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  /**
   * Returns a random int which will be positive.
   *
   * @return the random int
   */
  public static int randomPositiveInt() {
    return randomInt(1, Integer.MAX_VALUE);
  }

  /**
   * Returns a random int which will be negative.
   *
   * @return the random int
   */
  public static int randomNegativeInt() {
    return randomInt(Integer.MIN_VALUE, 0);
  }

  /**
   * Returns a random int within the specified range.
   *
   * @param startInclusive the earliest int that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random int
   * @throws IllegalArgumentException if endExclusive is less than startInclusive
   */
  public static int randomInt(int startInclusive, int endExclusive) {
    checkArgument(startInclusive <= endExclusive, "End must be greater than or equal to start");
    if (startInclusive == endExclusive) {
      return startInclusive;
    }
    return RANDOM.ints(1, startInclusive, endExclusive).sum();
  }

  /**
   * Returns a random int that is greater than the given int.
   *
   * @param minExclusive the value that returned int must be greater than
   * @return the random int
   * @throws IllegalArgumentException if minExclusive is greater than or equal to {@link
   *     Integer#MAX_VALUE}
   */
  public static int randomIntGreaterThan(int minExclusive) {
    checkArgument(
        minExclusive < Integer.MAX_VALUE, "Cannot produce int greater than %s", Integer.MAX_VALUE);
    return randomInt(minExclusive + 1, Integer.MAX_VALUE);
  }

  /**
   * Returns a random int that is less than the given int.
   *
   * @param maxExclusive the value that returned int must be less than
   * @return the random int
   * @throws IllegalArgumentException if maxExclusive is less than or equal to {@link
   *     Integer#MIN_VALUE}
   */
  public static int randomIntLessThan(int maxExclusive) {
    checkArgument(
        maxExclusive > Integer.MIN_VALUE, "Cannot produce int less than %s", Integer.MIN_VALUE);
    return randomInt(Integer.MIN_VALUE, maxExclusive);
  }

  /**
   * Returns a random long which may be positive, negative, or zero.
   *
   * @return the random long
   */
  public static long randomLong() {
    return randomLong(Long.MIN_VALUE, Long.MAX_VALUE);
  }

  /**
   * Returns a random long which will be positive.
   *
   * @return the random long
   */
  public static long randomPositiveLong() {
    return randomLong(1, Long.MAX_VALUE);
  }

  /**
   * Returns a random long which will be negative.
   *
   * @return the random long
   */
  public static long randomNegativeLong() {
    return randomLong(Long.MIN_VALUE, 0);
  }

  /**
   * Returns a random long within the specified range.
   *
   * @param startInclusive the earliest long that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random long
   * @throws IllegalArgumentException if endExclusive is less than startInclusive
   */
  public static long randomLong(long startInclusive, long endExclusive) {
    checkArgument(startInclusive <= endExclusive, "End must be greater than or equal to start");
    if (startInclusive == endExclusive) {
      return startInclusive;
    }
    return RANDOM.longs(1, startInclusive, endExclusive).sum();
  }

  /**
   * Returns a random long that is greater than the given long.
   *
   * @param minExclusive the value that returned long must be greater than
   * @return the random long
   * @throws IllegalArgumentException if minExclusive is greater than or equal to {@link
   *     Long#MAX_VALUE}
   */
  public static long randomLongGreaterThan(long minExclusive) {
    checkArgument(
        minExclusive < Long.MAX_VALUE, "Cannot produce long greater than %s", Long.MAX_VALUE);
    return randomLong(minExclusive + 1, Long.MAX_VALUE);
  }

  /**
   * Returns a random long that is less than the given long.
   *
   * @param maxExclusive the value that returned long must be less than
   * @return the random long
   * @throws IllegalArgumentException if maxExclusive is less than or equal to {@link
   *     Long#MIN_VALUE}
   */
  public static long randomLongLessThan(long maxExclusive) {
    checkArgument(
        maxExclusive > Long.MIN_VALUE, "Cannot produce long less than %s", Long.MIN_VALUE);
    return randomLong(Long.MIN_VALUE, maxExclusive);
  }

  /**
   * Returns a random double which may be positive, negative, or zero.
   *
   * @return the random double
   */
  public static double randomDouble() {
    return randomDouble(-Double.MAX_VALUE, Double.MAX_VALUE);
  }

  /**
   * Returns a random double which will be positive.
   *
   * @return the random double
   */
  public static double randomPositiveDouble() {
    return randomDouble(1, Double.MAX_VALUE);
  }

  /**
   * Returns a random double which will be negative.
   *
   * @return the random double
   */
  public static double randomNegativeDouble() {
    return randomDouble(-Double.MAX_VALUE, 0);
  }

  /**
   * Returns a random double within the specified range.
   *
   * @param startInclusive the earliest double that can be returned
   * @param endExclusive the upper bound (not included)
   * @return the random double
   * @throws IllegalArgumentException if endExclusive is less than startInclusive
   */
  public static double randomDouble(double startInclusive, double endExclusive) {
    checkArgument(startInclusive <= endExclusive, "End must be greater than or equal to start");
    if (startInclusive == endExclusive) {
      return startInclusive;
    }
    return RANDOM.doubles(1, startInclusive, endExclusive).sum();
  }

  /**
   * Returns a random double that is greater than the given double.
   *
   * @param minExclusive the value that returned double must be greater than
   * @return the random double
   * @throws IllegalArgumentException if minExclusive is greater than or equal to {@link
   *     Double#MAX_VALUE}
   */
  public static double randomDoubleGreaterThan(double minExclusive) {
    checkArgument(
        minExclusive < Double.MAX_VALUE, "Cannot produce double greater than %s", Double.MAX_VALUE);
    return randomDouble(minExclusive + 1, Double.MAX_VALUE);
  }

  /**
   * Returns a random double that is less than the given double.
   *
   * @param maxExclusive the value that returned double must be less than
   * @return the random double
   * @throws IllegalArgumentException if maxExclusive is less than or equal to negative {@link
   *     Double#MAX_VALUE}
   */
  public static double randomDoubleLessThan(double maxExclusive) {
    checkArgument(
        maxExclusive > -Double.MAX_VALUE, "Cannot produce double less than %s", -Double.MAX_VALUE);
    return randomDouble(-Double.MAX_VALUE, maxExclusive);
  }
}
