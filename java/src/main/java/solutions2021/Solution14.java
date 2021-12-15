package solutions2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class Solution14 {

  public static long partOne(String polymerManual) {
    return count(polymerManual, 10);
  }

  public static long partTwo(String polymerManual) {
    return count(polymerManual, 40);
  }

  private static long count(String polymerManual, int steps) {
    String[] split = polymerManual.split("\n\n");

    String polymerTemplate = split[0];

    long[] counter = new long[26];

    for (char element : polymerTemplate.toCharArray()) {
      counter[element - 'A']++;
    }

    Map<String, Long> pairs = IntStream.range(0, polymerTemplate.length() - 1)
      .mapToObj(i -> polymerTemplate.substring(i, i + 2))
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    Map<String, String> rules = new HashMap<>();
    for (String rule : split[1].split("\n")) {
      String[] rulePart = rule.split(" -> ");
      rules.put(rulePart[0], rulePart[1]);
    }

    for (int i = 0; i < steps; i++) {
      Map<String, Long> nextPairs = new HashMap<>();
      for (Entry<String, Long> entry : pairs.entrySet()) {
        String pair = entry.getKey();
        Long count = entry.getValue();
        String toInsert = rules.get(pair);
        nextPairs.merge(pair.charAt(0) + toInsert, count, Long::sum);
        nextPairs.merge(toInsert + pair.charAt(1), count, Long::sum);
        counter[toInsert.charAt(0) - 'A'] += count;
      }
      pairs = nextPairs;
    }

    counter = Arrays.stream(counter).filter(l -> l > 0).sorted().toArray();

    return counter[counter.length - 1] - counter[0];
  }

}
