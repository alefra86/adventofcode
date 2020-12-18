package solutions2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Solution10 {

  public static int partOne(Stream<String> lines) {
    List<Integer> adapters = getAdapters(lines);
    int differenceByOne = 0;
    int differenceByThree = 1;
    for (int i = 1; i < adapters.size() && adapters.get(i) - adapters.get(i - 1) <= 3; i++) {
      if (adapters.get(i) - adapters.get(i - 1) == 1) {
        differenceByOne++;
      } else if (adapters.get(i) - adapters.get(i - 1) == 3) {
        differenceByThree++;
      }
    }
    return differenceByOne * differenceByThree;
  }

  public static long partTwo(Stream<String> lines) {
    return distinctWays(0, getAdapters(lines), new HashMap<>());
  }

  private static List<Integer> getAdapters(Stream<String> lines) {
    List<Integer> adapters = lines.map(Integer::parseInt).sorted().collect(Collectors.toList());
    adapters.add(0, 0);
    return adapters;
  }

  public static long distinctWays(int currentPosition, List<Integer> adapters, Map<Integer, Long> cache) {
    if (cache.get(currentPosition) != null) {
      return cache.get(currentPosition);
    }
    if (currentPosition == adapters.size() - 1) {
      cache.put(currentPosition, 1L);
      return 1;
    }
    int i = currentPosition + 1;
    int currentJolts = adapters.get(currentPosition);
    long ways = 0;
    while (i < adapters.size() && adapters.get(i) - currentJolts <= 3) {
      ways += distinctWays(i++, adapters, cache);
    }
    cache.put(currentPosition, ways);
    return ways;

  }

}
