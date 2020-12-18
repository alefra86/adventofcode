package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 */
public class Solution07 {

  private static final String EMPTY_BAG = "no other";
  private static final String MY_BAG = "shiny gold";

  public static int partOne(Stream<String> lines) {
    Map<String, List<String>> bags = new HashMap<>();
    Map<String, Boolean> result = new HashMap<>();
    lines.forEach(line -> {
      line = line.replaceAll(" bag[s]*", "").replace(".", "").replaceAll("[0-9] ", "");
      String[] split = line.split(" contain ");
      if (!MY_BAG.equals(split[0])) {
        bags.computeIfAbsent(split[0], bag -> new ArrayList<>()).addAll(Arrays.asList(split[1].split(", ")));
      }
    });
    bags.keySet().forEach(bag -> result.put(bag, visit(bag, bags, result)));
    return (int) result.values().stream().filter(b -> b).count();
  }

  private static boolean visit(String bag, Map<String, List<String>> bags, Map<String, Boolean> result) {
    if (result.get(bag) != null) {
      return result.get(bag);
    }
    if (bag.equals(MY_BAG)) {
      return true;
    }
    List<String> innerBags = bags.get(bag);
    if (innerBags.isEmpty() || (innerBags.size() == 1 && EMPTY_BAG.equals(innerBags.get(0)))) {
      result.put(bag, false);
      return false;
    }
    result.put(bag, innerBags.stream().map(innerBag -> visit(innerBag, bags, result)).reduce(Boolean.FALSE, (a, b) -> a || b));
    return result.get(bag);
  }

  public static int partTwo(Stream<String> lines) {
    Map<String, Map<String, Integer>> bags = new HashMap<>();
    lines.forEach(line -> {
      line = line.replaceAll(" bag[s]*", "").replace(".", "");
      String[] split = line.split(" contain ");
      if (!EMPTY_BAG.equals(split[1])) {
        Arrays.stream(split[1].split(", "))
          .forEach(innerBag -> bags.computeIfAbsent(split[0], bag -> new HashMap<>())
                                 .put(innerBag.substring(2), Character.getNumericValue(innerBag.charAt(0))));
      }
    });
    return count(MY_BAG, bags, new HashMap<>());
  }

  private static int count(String bag, Map<String, Map<String, Integer>> bags, Map<String, Integer> result) {
    if (result.get(bag) != null) {
      return result.get(bag);
    }
    Map<String, Integer> innerBags = bags.get(bag);
    if (innerBags == null || innerBags.isEmpty()) {
      result.put(bag, 0);
      return 0;
    }
    result.put(bag, innerBags.entrySet().stream().map(e -> {
      int count = count(e.getKey(), bags, result);
      return e.getValue() + e.getValue() * count;
    }).reduce(0, Integer::sum));
    return result.get(bag);
  }

}
