package solutions2020;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class Solution15 {

  public static int partOne(List<String> lines) {
    return getLastSpokenNumber(lines, 2020);
  }

  public static int partTwo(List<String> lines) {
    return getLastSpokenNumber(lines, 30000000);
  }

  private static Integer getLastSpokenNumber(List<String> lines, int lastSpokenNumber) {
    List<Integer> spokenNumber =
      Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    Map<Integer, Integer> lastPosition =
      IntStream.range(0, spokenNumber.size() - 1).boxed().collect(Collectors.toMap(spokenNumber::get, i -> i));
    while (spokenNumber.size() < lastSpokenNumber) {
      int i = spokenNumber.size() - 1;
      int lastNumber = spokenNumber.get(i);
      Integer lastNumberPosition = lastPosition.get(lastNumber);
      if (Objects.nonNull(lastNumberPosition)) {
        spokenNumber.add(i - lastNumberPosition);
      } else {
        spokenNumber.add(0);
      }
      lastPosition.put(lastNumber, i);
    }
    return spokenNumber.get(spokenNumber.size() - 1);
  }
}
