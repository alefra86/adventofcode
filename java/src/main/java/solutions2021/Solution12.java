package solutions2021;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

/**
 *
 */
public class Solution12 {

  public static int partOne(List<String> cavesMap) {
    return visit("start", getPaths(cavesMap), new HashMap<>(), (c, seen) -> seen.getOrDefault(c, 0) == 1);
  }

  public static int partTwo(List<String> cavesMap) {
    return visit("start", getPaths(cavesMap), new HashMap<>(), (c, seen) -> seen.getOrDefault(c, 0) >= 1 && seen.containsValue(2));
  }

  private static Map<String, Set<String>> getPaths(List<String> cavesMap) {
    Map<String, Set<String>> paths = new HashMap<>();
    for (String connection : cavesMap) {
      String[] caves = connection.split("-");
      paths.computeIfAbsent(caves[0], k -> new HashSet<>()).add(caves[1]);
      paths.computeIfAbsent(caves[1], k -> new HashSet<>()).add(caves[0]);
    }
    return paths;
  }

  private static int visit(String cave, Map<String, Set<String>> paths, Map<String, Integer> smallCavesAlreadyVisit,
    BiPredicate<String, Map<String, Integer>> visitStrategy) {
    if (isLowerCase(cave) && visitStrategy.test(cave, smallCavesAlreadyVisit)) {
      return 0;
    }

    if (cave.equals("end")) {
      return 1;
    }

    if (!cave.equals("start") && isLowerCase(cave)) {
      smallCavesAlreadyVisit.merge(cave, 1, Integer::sum);
    }
    Set<String> connectedCaves = paths.get(cave);
    int pathToEnd = 0;
    for (String outCave : connectedCaves) {
      if (outCave.equals("start")) {
        continue;
      }
      pathToEnd += visit(outCave, paths, smallCavesAlreadyVisit, visitStrategy);
    }
    smallCavesAlreadyVisit.merge(cave, -1, Integer::sum);
    return pathToEnd;
  }

  private static boolean isLowerCase(String cave) {
    return cave.equals(cave.toLowerCase());
  }

}
