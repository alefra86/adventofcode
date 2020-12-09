package solutions2020;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 *
 */
public class Solution09 {

  private static final int PREAMBLE_SIZE = 25;

  public static long partOne(Stream<String> lines) {
    Map<Long, Boolean> seen = new HashMap<>();
    Queue<Long> queue = new LinkedList<>();
    AtomicInteger i = new AtomicInteger(1);
    return lines.map(Long::parseLong).filter(number -> {
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
    Map<Long, Boolean> seen = new HashMap<>();
    Queue<Long> queue = new LinkedList<>();
    List<Long> allNumber = new ArrayList<>();
    AtomicInteger i = new AtomicInteger(1);
    Long invalidNumber = lines.map(Long::parseLong).filter(number -> {
      allNumber.add(number);
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
    return getEncryptionWeakness(allNumber, invalidNumber);
  }

  private static long getEncryptionWeakness(List<Long> allNumber, Long invalidNumber) {
    long sum = allNumber.get(0);
    int j;
    int k = 1;
    for (j = 0; j < allNumber.size() && sum != invalidNumber; j++) {
      while (k < allNumber.size() && sum < invalidNumber) {
        sum += allNumber.get(k);
        if (sum == invalidNumber) {
          break;
        }
        k++;
      }
      sum -= allNumber.get(j);
    }
    List<Long> contiguosNumbers = allNumber.subList(j, k);
    contiguosNumbers.sort(Comparator.naturalOrder());
    return contiguosNumbers.get(0) + contiguosNumbers.get(contiguosNumbers.size() - 1);
  }
}
