package solutions2021;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class Solution03 {

  public static int partOne(List<String> reports) {
    List<Integer> bitCounts = IntStream.range(0, reports.get(0).length())
      .boxed()
      .map(i -> reports.stream().reduce(0, (a, b) -> a + (Character.getNumericValue(b.charAt(i)) == 0 ? -1 : 1), (a, b) -> a))
      .collect(Collectors.toList());
    int gamma = Integer.parseInt(bitCounts.stream().map(i -> i > 0 ? "1" : "0").collect(Collectors.joining("")), 2);
    int epsilon = Integer.parseInt(bitCounts.stream().map(i -> i < 0 ? "1" : "0").collect(Collectors.joining("")), 2);

    return gamma * epsilon;

  }

  public static int partTwo(List<String> reports) {
    int oxygen = getRating(reports, BitCriteria.MOST_COMMON);
    int co2 = getRating(reports, BitCriteria.LEAST_COMMON);

    return oxygen * co2;
  }

  private static int getRating(List<String> reports, BitCriteria bitCriteria) {
    List<String> list = new ArrayList<>(reports);
    for (int i = 0; i < reports.get(0).length(); i++) {
      list = filter(list, i, bitCriteria);
      if (list.size() == 1) {
        break;
      }
    }
    return Integer.parseInt(list.get(0), 2);
  }

  private static List<String> filter(List<String> list, int i, BitCriteria bitCriteria) {
    Integer reduce = list.stream().reduce(0, (a, b) -> a + (Character.getNumericValue(b.charAt(i)) == 0 ? -1 : 1), (a, b) -> a);
    char bit = bitCriteria == BitCriteria.MOST_COMMON ^ reduce >= 0 ? '1' : '0';
    return list.stream().filter(r -> r.charAt(i) == bit).collect(Collectors.toList());
  }

  private enum BitCriteria {
    MOST_COMMON,
    LEAST_COMMON
  }

}
