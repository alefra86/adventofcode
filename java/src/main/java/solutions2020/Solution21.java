package solutions2020;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Solution21 {

  private static final Pattern PATTERN = Pattern.compile("(.*) \\(contains (.*?)\\)");

  public static int partOne(Stream<String> readAllLines) {
    Map<String, Set<String>> ingredientsPerAllergens = new HashMap<>();
    Map<String, Integer> times = new HashMap<>();
    readAllLines.forEach(line -> {
      Matcher matcher = PATTERN.matcher(line);
      if (matcher.find()) {
        List<String> ingredients = Arrays.asList(matcher.group(1).split(" "));
        ingredients.forEach(i -> times.put(i, times.getOrDefault(i, 0) + 1));
        String[] allergens = matcher.group(2).split(", ");
        for (String allergen : allergens) {
          Set<String> ingredientsPerAllergen = ingredientsPerAllergens.get(allergen);
          if (ingredientsPerAllergen == null) {
            ingredientsPerAllergens.put(allergen, new HashSet<>(ingredients));
          } else {
            ingredientsPerAllergen.retainAll(ingredients);
          }
        }
      }
    });
    return times.entrySet()
             .stream()
             .filter(
               e -> ingredientsPerAllergens.values().stream().flatMap(Collection::stream).noneMatch(s -> s.equals(e.getKey())))
             .map(Entry::getValue)
             .reduce(0, Integer::sum);
  }

  public static String partTwo(List<String> readAllLines) {
    Map<String, Set<String>> ingredientsPerAllergens = new HashMap<>();
    readAllLines.forEach(line -> {
      Matcher matcher = PATTERN.matcher(line);
      if (matcher.find()) {
        List<String> ingredients = Arrays.asList(matcher.group(1).split(" "));
        String[] allergens = matcher.group(2).split(", ");
        for (String allergen : allergens) {
          Set<String> ingredientsPerAllergen = ingredientsPerAllergens.get(allergen);
          if (ingredientsPerAllergen == null) {
            ingredientsPerAllergens.put(allergen, new HashSet<>(ingredients));
          } else {
            ingredientsPerAllergen.retainAll(ingredients);
          }
        }
      }
    });
    Map<String, String> allergensAssigned = new HashMap<>();
    while (allergensAssigned.size() < ingredientsPerAllergens.size()) {
      for (Entry<String, Set<String>> entry : ingredientsPerAllergens.entrySet()) {
        if (!allergensAssigned.containsKey(entry.getKey()) && entry.getValue().size() == 1) {
          allergensAssigned.put(entry.getKey(), entry.getValue().iterator().next());
        } else {
          entry.getValue().removeAll(allergensAssigned.values());
        }
      }
    }
    return allergensAssigned.entrySet()
             .stream()
             .sorted(Entry.comparingByKey())
             .map(Entry::getValue)
             .collect(Collectors.joining(","));
  }
}
