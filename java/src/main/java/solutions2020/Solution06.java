package solutions2020;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class Solution06 {

  public static int partOne(Stream<String> lines) {
    Set<Character> anyoneAnswers = new HashSet<>();
    AtomicInteger anyoneCounter = new AtomicInteger();
    lines.forEach(line -> {
      if (StringUtils.isEmpty(line)) {
        anyoneCounter.addAndGet(anyoneAnswers.size());
        anyoneAnswers.clear();
      } else {
        anyoneAnswers.addAll(line.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
      }
    });
    return anyoneCounter.addAndGet(anyoneAnswers.size());
  }

  public static int partTwo(Stream<String> lines) {
    List<Set<Character>> everyoneAnswers = new ArrayList<>();
    AtomicInteger everyoneCounter = new AtomicInteger();
    lines.forEach(line -> {
      if (StringUtils.isEmpty(line)) {
        everyoneCounter.addAndGet(getIntersection(everyoneAnswers));
        everyoneAnswers.clear();
      } else {
        everyoneAnswers.add(line.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
      }
    });
    return everyoneCounter.addAndGet(getIntersection(everyoneAnswers));
  }

  private static int getIntersection(List<Set<Character>> everyoneAnswers) {
    return everyoneAnswers.stream().reduce(everyoneAnswers.get(0), (a, b) -> {
      a.retainAll(b);
      return a;
    }).size();
  }

}
