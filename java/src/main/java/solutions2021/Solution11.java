package solutions2021;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution11 {

  public static int partOne(List<String> energyLevels) {
    int totalFlashes = 0;
    int[][] octopuses = getOctopuses(energyLevels);
    for (int step = 0; step < 100; step++) {
      Set<Pair<Integer, Integer>> alreadyFlashedInStep = new HashSet<>();
      for (int i = 0; i < octopuses.length; i++) {
        for (int j = 0; j < octopuses[i].length; j++) {
          totalFlashes += increaseAndGetFlashes(octopuses, i, j, alreadyFlashedInStep);
        }
      }
    }
    return totalFlashes;
  }

  public static int partTwo(List<String> energyLevels) {
    int[][] octopuses = getOctopuses(energyLevels);
    int step = 1;
    boolean sync = false;
    while (!sync) {
      Set<Pair<Integer, Integer>> alreadyFlashedInStep = new HashSet<>();
      for (int i = 0; i < octopuses.length; i++) {
        for (int j = 0; j < octopuses[i].length; j++) {
          increaseAndGetFlashes(octopuses, i, j, alreadyFlashedInStep);
        }
      }
      if (alreadyFlashedInStep.size() == 100) {
        sync = true;
      } else {
        step++;
      }
    }
    return step;
  }

  private static int increaseAndGetFlashes(int[][] octopuses, int i, int j, Set<Pair<Integer, Integer>> alreadyFlashed) {
    if (isNotInRange(i)) {
      return 0;
    }
    if (isNotInRange(j)) {
      return 0;
    }
    if (alreadyFlashed.contains(Pair.of(i, j))) {
      return 0;
    }
    octopuses[i][j] += 1;
    int totalFlashes = 0;
    if (octopuses[i][j] > 9) {
      octopuses[i][j] = 0;
      totalFlashes++;
      alreadyFlashed.add(Pair.of(i, j));
      for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
          if (x == 0 && y == 0) {
            continue;
          }
          totalFlashes += increaseAndGetFlashes(octopuses, i + x, j + y, alreadyFlashed);
        }
      }
    }
    return totalFlashes;
  }

  private static boolean isNotInRange(int coord) {
    return coord < 0 || coord > 9;
  }

  private static int[][] getOctopuses(List<String> energyLevels) {
    int[][] octopuses = new int[energyLevels.size()][];
    for (int i = 0; i < energyLevels.size(); i++) {
      octopuses[i] = (Arrays.stream(energyLevels.get(i).split("")).mapToInt(Integer::parseInt).toArray());
    }
    return octopuses;
  }

}
