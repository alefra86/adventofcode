package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution16 {

  private static final String NEARBY_TICKETS_LABEL = "nearby tickets";
  private static final String YOUR_TICKET_LABEL = "your ticket:";
  private static final String DEPARTURE_LABEL = "departure";

  public static int partOne(Stream<String> lines) {
    AtomicBoolean nearByTicket = new AtomicBoolean(false);
    AtomicInteger sum = new AtomicInteger();
    Map<String, Predicate<Integer>> rules = new HashMap<>();
    lines.filter(s -> !s.isEmpty()).forEach(line -> {
      if (nearByTicket.get()) {
        int[] ticket = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
        assert rules.size() == ticket.length;
        for (int number : ticket) {
          if (!isValid(number, rules)) {
            sum.addAndGet(number);
          }
        }
      } else {
        fillRules(rules, line);
      }
      if (line.contains(NEARBY_TICKETS_LABEL)) {
        nearByTicket.set(true);
      }
    });
    return sum.get();
  }

  public static long partTwo(List<String> lines) {
    AtomicBoolean nearByTicket = new AtomicBoolean(false);
    Map<String, Predicate<Integer>> rules = new HashMap<>();
    List<int[]> nearbyTicket = new ArrayList<>();
    lines.stream().filter(s -> !s.isEmpty()).forEach(line -> {
      if (nearByTicket.get()) {
        int[] ticket = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
        assert rules.size() == ticket.length;
        boolean ticketValid = true;
        for (int number : ticket) {
          ticketValid &= isValid(number, rules);
        }
        if (ticketValid) {
          nearbyTicket.add(ticket);
        }
      } else {
        fillRules(rules, line);
      }
      if (line.contains(NEARBY_TICKETS_LABEL)) {
        nearByTicket.set(true);
      }
    });
    long[] myTicket =
      Arrays.stream(lines.get(lines.indexOf(YOUR_TICKET_LABEL) + 1).split(",")).mapToLong(Long::parseLong).toArray();
    return getFieldPosition(nearbyTicket, rules).entrySet()
             .stream()
             .filter(e -> e.getKey().startsWith(DEPARTURE_LABEL))
             .map(Entry::getValue)
             .map(i -> myTicket[i])
             .reduce(1L, (a, b) -> a * b);
  }

  private static boolean isValid(int number, Map<String, Predicate<Integer>> rules) {
    boolean valid = false;
    for (Predicate<Integer> value : rules.values()) {
      if (value.test(number)) {
        valid = true;
      }
    }
    return valid;
  }

  private static void fillRules(Map<String, Predicate<Integer>> rules, String line) {
    Matcher matcher = Pattern.compile("^(.*): (\\d+)-(\\d+).*?(\\d+)-(\\d+).*$").matcher(line);
    if (matcher.find()) {
      rules.put(matcher.group(1), (i) -> Integer.parseInt(matcher.group(2)) <= i && i <= Integer.parseInt(matcher.group(3)) ||
                                           Integer.parseInt(matcher.group(4)) <= i && i <= Integer.parseInt(matcher.group(5)));
    }
  }

  private static Map<String, Integer> getFieldPosition(List<int[]> nearbyTicket, Map<String, Predicate<Integer>> rules) {
    Map<String, Set<Integer>> fieldPosition = new HashMap<>();
    rules.keySet()
      .forEach(k -> fieldPosition.put(k, IntStream.range(0, rules.keySet().size()).boxed().collect(Collectors.toSet())));
    for (int[] ticket : nearbyTicket) {
      for (int i = 0, ticketLength = ticket.length; i < ticketLength; i++) {
        int number = ticket[i];
        for (Entry<String, Predicate<Integer>> entry : rules.entrySet()) {
          if (!entry.getValue().test(number)) {
            fieldPosition.get(entry.getKey()).remove(i);
          }
        }
      }
    }
    return getPositionPerRule(fieldPosition, rules);
  }

  private static Map<String, Integer> getPositionPerRule(Map<String, Set<Integer>> fieldPosition,
    Map<String, Predicate<Integer>> rules) {
    Map<String, Integer> result = new HashMap<>();
    Set<Integer> alreadyAssigned = new HashSet<>();
    while (alreadyAssigned.size() != rules.keySet().size()) {
      for (Entry<String, Set<Integer>> rule : fieldPosition.entrySet()) {
        rule.getValue().removeAll(alreadyAssigned);
        if (rule.getValue().size() == 1) {
          alreadyAssigned.addAll(rule.getValue());
          result.put(rule.getKey(), rule.getValue().iterator().next());
        }
      }
    }
    return result;
  }

}