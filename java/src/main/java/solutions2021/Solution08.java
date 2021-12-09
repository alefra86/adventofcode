package solutions2021;

import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Solution08 {

  public static long partOne(Stream<String> signalPatterns) {
    return signalPatterns.map(s -> Arrays.stream(s.split(" \\| ")[1].split(" "))
      .filter(ov -> ov.length() >= 2 && ov.length() <= 4 || ov.length() == 7)
      .count()).reduce(0L, Long::sum);
  }

  public static int partTwo(Stream<String> signalPatterns) {
    return signalPatterns.map(Solution08::getOutputValue).reduce(0, Integer::sum);
  }

  private static int getOutputValue(String signal) {
    String[] splitted = signal.split(" \\| ");
    Map<Integer, List<String>> digitByLength =
      Arrays.stream(splitted[0].split(" ")).map(Solution08::sortString).collect(Collectors.groupingBy(String::length));
    Map<Integer, String> charsByNum = new HashMap<>();
    charsByNum.put(1, digitByLength.get(2).get(0));
    charsByNum.put(7, digitByLength.get(3).get(0));
    charsByNum.put(4, digitByLength.get(4).get(0));
    charsByNum.put(8, digitByLength.get(7).get(0));
    Set<Character> oneChars = charsByNum.get(1).chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    Set<Character> fourChars = charsByNum.get(4).chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
    Set<Character> difference = Sets.difference(fourChars, oneChars);
    for (String digit : digitByLength.get(5)) {
      if (includesAll(digit, oneChars)) {
        charsByNum.put(3, digit);
      } else if (includesAll(digit, difference)) {
        charsByNum.put(5, digit);
      } else {
        charsByNum.put(2, digit);
      }
    }
    for (String digit : digitByLength.get(6)) {
      if (includesAll(digit, fourChars)) {
        charsByNum.put(9, digit);
      } else if (includesAll(digit, difference)) {
        charsByNum.put(6, digit);
      } else {
        charsByNum.put(0, digit);
      }
    }
    List<String> outputValues = Arrays.stream(splitted[1].split(" ")).map(Solution08::sortString).collect(Collectors.toList());
    List<String> result = new ArrayList<>();
    for (String outputValue : outputValues) {
      for (Entry<Integer, String> entry : charsByNum.entrySet()) {
        if (entry.getValue().equals(outputValue)) {
          result.add(entry.getKey() + "");
          break;
        }
      }
    }
    return Integer.parseInt(String.join("", result));
  }

  private static boolean includesAll(String digit, Set<Character> chars) {
    for (char c : chars) {
      if (digit.indexOf(c) == -1) {
        return false;
      }
    }
    return true;
  }

  private static String sortString(String toSort) {
    return toSort.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
  }

}
