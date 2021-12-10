package solutions2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution09 {

  public static long partOne(List<String> heightmap) {
    int result = 0;
    int[][] caves = getCaves(heightmap);

    for (int i = 0; i < caves.length; i++) {
      for (int j = 0; j < caves[i].length; j++) {
        int cave = caves[i][j];
        if (isLowPoint(i, j, caves)) {
          result += cave + 1;
        }
      }
    }

    return result;
  }

  public static int partTwo(List<String> heightmap) {
    int result = 0;
    int[][] caves = getCaves(heightmap);

    List<Integer> basins = new ArrayList<>();
    for (int i = 0; i < caves.length; i++) {
      for (int j = 0; j < caves[i].length; j++) {
        if (caves[i][j] == 9) {
          continue;
        }
        Queue<Pair<Integer, Integer>> positions = new LinkedList<>();
        positions.add(Pair.of(i, j));
        int size = 0;
        while (!positions.isEmpty()) {
          Pair<Integer, Integer> position = positions.poll();
          for (Pair<Integer, Integer> adj : Adjacent.getValidAdjacents(position.getKey(), position.getValue(), caves.length,
            caves[0].length)) {
            if (caves[adj.getLeft()][adj.getRight()] < 9) {
              positions.add(adj);
              caves[adj.getLeft()][adj.getRight()] = 9;
              size++;
            }
          }
        }
        basins.add(size);
      }
    }
    basins.sort(Collections.reverseOrder());
    return basins.get(0) * basins.get(1) * basins.get(2);
  }

  private static int[][] getCaves(List<String> heightmap) {
    int[][] caves = new int[heightmap.size()][];
    for (int i = 0; i < heightmap.size(); i++) {
      caves[i] = (Arrays.stream(heightmap.get(i).split("")).mapToInt(Integer::parseInt).toArray());
    }
    return caves;
  }

  private static boolean isLowPoint(int x, int y, int[][] caves) {
    for (Pair<Integer, Integer> adj : Adjacent.getValidAdjacents(x, y, caves.length, caves[0].length)) {
      if (caves[x][y] >= caves[adj.getLeft()][adj.getRight()]) {
        return false;
      }
    }
    return true;
  }

  private static enum Adjacent {
    TOP(-1, 0),
    BOTTOM(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);
    private final int x;
    private final int y;

    Adjacent(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public static List<Pair<Integer, Integer>> getValidAdjacents(int i, int j, int rowSize, int columnSize) {
      List<Pair<Integer, Integer>> result = new ArrayList<>();
      for (Adjacent adj : Adjacent.values()) {
        if (inRange(i, adj.getX(), rowSize) && inRange(j, adj.getY(), columnSize)) {
          result.add(Pair.of(i + adj.getX(), j + adj.getY()));
        }
      }
      return result;
    }

    private static boolean inRange(int i, int offset, int length) {
      return i + offset >= 0 && i + offset < length;
    }
  }

}
