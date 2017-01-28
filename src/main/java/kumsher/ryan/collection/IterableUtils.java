package kumsher.ryan.collection;

import static com.google.common.base.Preconditions.*;

import java.util.Random;

import com.google.common.collect.Iterables;

/** Utility library for working with {@link Iterable}s. */
public class IterableUtils {

  private static final Random RANDOM = new Random();

  /**
   * Returns a random element from the given {@link Iterable}.
   *
   * @param iterable {@link Iterable} to return random element from
   * @return random element from the given {@link Iterable}
   */
  public static <T> T randomFrom(Iterable<T> iterable) {
    checkArgument(!Iterables.isEmpty(iterable), "Iterable cannot be empty");
    int randomIndex = RANDOM.nextInt(Iterables.size(iterable));
    return Iterables.get(iterable, randomIndex);
  }
}
