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
    List<Integer> adapters = lines.map(Integer::parseInt).sorted().collect(Collectors.toList());
    int differenceByOne = 0;
    int differenceByThree = 1;
    int jolts = 0;
    for (Integer adapter : adapters) {
      if (adapter - jolts <= 3) {
        if (adapter - jolts == 1) {
          differenceByOne++;
        } else if (adapter - jolts == 3) {
          differenceByThree++;
        }
        jolts = adapter;
      } else {
        break;
      }
    }
    return differenceByOne * differenceByThree;
  }

  public static long partTwo(Stream<String> lines) {
    List<Integer> adapters = lines.map(Integer::parseInt).sorted().collect(Collectors.toList());
    adapters.add(0, 0);
    return distinctWays(0, adapters, new HashMap<>());
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
      ways += distinctWays(i, adapters, cache);
      i++;
    }
    cache.put(currentPosition, ways);
    return ways;

  }

}
