package solutions2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Solution14 {

  public static final Pattern PATTERN = Pattern.compile("^mem\\[([0-9]*)] = (.*)$");

  public static long partOne(List<String> lines) {
    char[] mask = new char[36];
    Map<Integer, Long> mem = new HashMap<>();
    for (String line : lines) {
      if (line.startsWith("mask")) {
        mask = line.split(" = ")[1].toCharArray();
      } else {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
          long value = Integer.parseInt(matcher.group(2));
          long result = 0;
          for (int i = mask.length - 1, j = 0; i >= 0; i--, j++) {
            long binaryValueFromMask = 1L << j;
            if (mask[i] == 'X') {
              result += value & binaryValueFromMask;
            } else if (mask[i] == '1') {
              result += binaryValueFromMask;
            }
          }
          mem.put(Integer.parseInt(matcher.group(1)), result);
        }
      }
    }
    return mem.values().stream().reduce(0L, Long::sum);
  }

  public static long partTwo(List<String> lines) {
    char[] mask = new char[36];
    Map<Long, Long> mem = new HashMap<>();
    for (String line : lines) {
      if (line.startsWith("mask")) {
        mask = line.split(" = ")[1].toCharArray();
      } else {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
          int position = Integer.parseInt(matcher.group(1));
          long newPosition = 0L;
          List<Integer> floatingBits = new ArrayList<>();
          for (int i = mask.length - 1, j = 0; i >= 0; i--, j++) {
            if (mask[i] == 'X') {
              floatingBits.add(j);
            } else {
              long binaryValueFromMask = 1L << j;
              if (mask[i] == '1') {
                newPosition += binaryValueFromMask;
              } else if (mask[i] == '0') {
                newPosition += position & binaryValueFromMask;
              }
            }
          }
          getAllPossibleValues(newPosition, floatingBits, 0).forEach(
            pos -> mem.put(pos, (long) Integer.parseInt(matcher.group(2))));
        }
      }
    }
    return mem.values().stream().reduce(0L, Long::sum);
  }

  private static List<Long> getAllPossibleValues(Long idx, List<Integer> floatingBits, int i) {
    if (i == floatingBits.size()) {
      return Collections.singletonList(idx);
    }
    Integer floatingNumber = floatingBits.get(i);
    return Stream.concat(getAllPossibleValues(idx, floatingBits, i + 1).stream(),
      getAllPossibleValues(idx + (1L << floatingNumber), floatingBits, i + 1).stream()).collect(Collectors.toList());
  }
}
