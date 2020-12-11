package solutions2020;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Solution09 {

  private static final int PREAMBLE_SIZE = 25;

  public static long partOne(Stream<String> lines) {
    return getInvalidNumber(lines.map(Long::parseLong).collect(Collectors.toList()));
  }

  private static Long getInvalidNumber(List<Long> allNumber) {
    Map<Long, Boolean> seen = new HashMap<>();
    Queue<Long> queue = new LinkedList<>();
    AtomicInteger i = new AtomicInteger(1);
    return allNumber.stream().filter(number -> {
      queue.add(number);
      if (i.get() <= PREAMBLE_SIZE) {
        seen.put(number, true);
        i.getAndIncrement();
      } else {
        boolean valid = queue.stream().anyMatch(previous -> Boolean.TRUE.equals(seen.get(Math.abs(number - previous))));
        if (valid) {
          seen.put(queue.poll(), false);
          seen.put(number, true);
        } else {
          return true;
        }
      }
      return false;
    }).findFirst().get();
  }

  public static long partTwo(Stream<String> lines) {
    List<Long> allNumber = lines.map(Long::parseLong).collect(Collectors.toList());
    return getEncryptionWeakness(allNumber, getInvalidNumber(allNumber));
  }

  private static long getEncryptionWeakness(List<Long> allNumber, Long invalidNumber) {
    long sum = allNumber.get(0) + allNumber.get(1);
    int left = 0;
    int right = 2;
    while (right < allNumber.size() && sum != invalidNumber) {
      if (sum < invalidNumber) {
        sum += allNumber.get(right);
        right++;
      } else {
        sum -= allNumber.get(left);
        left++;
      }
    }
    List<Long> contiguosNumbers = allNumber.subList(left, right);
    contiguosNumbers.sort(Comparator.naturalOrder());
    return contiguosNumbers.get(0) + contiguosNumbers.get(contiguosNumbers.size() - 1);
  }
}
