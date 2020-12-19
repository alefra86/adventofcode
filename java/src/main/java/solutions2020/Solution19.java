package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public class Solution19 {

  private final Map<Integer, List<List<Integer>>> allRules = new HashMap<>();
  private final Map<Integer, Character> terminalRules = new HashMap<>();
  private Predicate<Integer> containsLoop;

  public int partOne(Stream<String> lines) {
    containsLoop = i -> false;
    return solve(lines);
  }

  public int partTwo(Stream<String> lines) {
    containsLoop = rulePosition -> rulePosition == 8 || rulePosition == 11;
    return solve(lines);
  }

  private int solve(Stream<String> lines) {
    AtomicInteger result = new AtomicInteger();
    lines.forEach(line -> {
      if (line.contains(":")) {
        Matcher matcher = Pattern.compile("^(\\d+): (.*?)$").matcher(line);
        if (matcher.find()) {
          Integer position = Integer.valueOf(matcher.group(1));
          String value = matcher.group(2);
          if (value.charAt(0) == '"') {
            terminalRules.put(position, value.charAt(1));
          } else {
            allRules.put(position, Arrays.stream(value.split(" \\| "))
                                     .map(r -> Arrays.stream(r.split(" ")).map(Integer::valueOf).collect(Collectors.toList()))
                                     .collect(Collectors.toList()));
          }
        }
      } else if (StringUtils.isNotEmpty(line)) {
        List<Pair<Boolean, Integer>> valid = match(line, 0, Collections.singletonList(0));
        result.addAndGet((int) valid.stream().filter(v -> v.getLeft() && v.getRight() == line.length()).count());
      }
    });
    return result.get();
  }

  private List<Pair<Boolean, Integer>> match(String message, Integer rulePosition, List<Integer> positions) {
    List<Pair<Boolean, Integer>> allValid = new ArrayList<>();
    for (Integer position : positions) {
      if (position >= message.length()) {
        allValid.add(Pair.of(false, position));
      } else if (terminalRules.containsKey(rulePosition)) {
        if (message.charAt(position) == terminalRules.get(rulePosition)) {
          allValid.addAll(Collections.singletonList(Pair.of(true, position + 1)));
        } else {
          allValid.addAll(Collections.singletonList(Pair.of(false, position)));
        }
      } else {
        for (List<Integer> alternativeSubRules : allRules.get(rulePosition)) {
          List<Pair<Boolean, Integer>> valid = match(message, alternativeSubRules, position);
          if (valid.stream().anyMatch(Pair::getLeft) && !containsLoop.test(rulePosition)) {
            return valid;
          }
          allValid.addAll(valid);
        }
      }
    }
    return allValid;
  }

  private List<Pair<Boolean, Integer>> match(String message, List<Integer> rules, int position) {
    List<Integer> positions = Collections.singletonList(position);
    for (Integer subRule : rules) {
      List<Pair<Boolean, Integer>> ruleValidation = match(message, subRule, positions);
      List<Pair<Boolean, Integer>> validSubRule = ruleValidation.stream().filter(Pair::getLeft).collect(Collectors.toList());
      if (validSubRule.isEmpty()) {
        return Collections.singletonList(Pair.of(false, position));
      }
      positions = validSubRule.stream().map(Pair::getRight).collect(Collectors.toList());
    }
    return positions.stream().map(p -> Pair.of(true, p)).collect(Collectors.toList());
  }

}
