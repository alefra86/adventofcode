package solutions2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
public class Solution06 {

  public static long partOne(String lanternfishAges) {
    return countFish(lanternfishAges, 80);
  }

  private static long countFish(String lanternfishAges, int days) {
    Map<Integer, Long> fishByAge = Arrays.stream(lanternfishAges.split(","))
      .mapToInt(Integer::parseInt)
      .boxed()
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    int day = 1;
    while (day <= days) {
      Map<Integer, Long> next = new HashMap<>();
      Long numberOfFish = fishByAge.getOrDefault(0, 0L);
      next.put(8, numberOfFish);
      for (int i = 1; i <= 8; i++) {
        next.put(i - 1, fishByAge.getOrDefault(i, 0L));
      }
      next.merge(6, numberOfFish, Long::sum);
      fishByAge = next;
      day++;
    }
    return fishByAge.values().stream().reduce(0L, Long::sum);
  }

  public static long partTwo(String lanternfishAges) {
    return countFish(lanternfishAges, 256);

  }

}
