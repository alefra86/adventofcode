package solutions2020;

import java.util.stream.Stream;

/**
 *
 */
public class Solution03 {

  public static int partOne(Stream<String> lines) {
    char[][] grid = lines.map(String::toCharArray).toArray(char[][]::new);
    return getEncounteredTrees(grid, 1, 3);
  }

  public static int partTwo(Stream<String> lines) {
    char[][] grid = lines.map(String::toCharArray).toArray(char[][]::new);
    return getEncounteredTrees(grid, 1, 1) * getEncounteredTrees(grid, 1, 3) * getEncounteredTrees(grid, 1, 5) *
             getEncounteredTrees(grid, 1, 7) * getEncounteredTrees(grid, 2, 1);
  }

  private static int getEncounteredTrees(char[][] grid, int downStep, int rightStep) {
    int encounteredTrees = 0;
    for (int i = 0, j = 0; i < grid.length; i += downStep) {
      if (grid[i][j] == '#') {
        encounteredTrees++;
      }
      j = (j + rightStep) % grid[0].length;
    }
    return encounteredTrees;
  }

}
