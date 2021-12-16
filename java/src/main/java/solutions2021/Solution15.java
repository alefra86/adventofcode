package solutions2021;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution15 {

  public static long partOne(Stream<String> caveRiskLevel) {
    int[][] grid = caveRiskLevel.map(line -> line.codePoints().map(Character::getNumericValue).toArray()).toArray(int[][]::new);
    return djikstra(grid, grid.length, grid[0].length);
  }

  public static long partTwo(Stream<String> caveRiskLevel) {
    int[][] grid = caveRiskLevel.map(line -> line.codePoints().map(Character::getNumericValue).toArray()).toArray(int[][]::new);
    return djikstra(grid, grid.length * 5, grid[0].length * 5);
  }

  private static int djikstra(int[][] grid, int maxX, int maxY) {
    int[][] result = new int[maxX][maxY];
    Pair<Integer, Integer> dest = Pair.of(maxX - 1, maxY - 1);
    PriorityQueue<CoordByRisk> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.risk));
    Set<Pair<Integer, Integer>> seen = new HashSet<>();
    queue.add(CoordByRisk.of(Pair.of(0, 0), 0));
    result[0][0] = 0;
    while (!queue.isEmpty()) {
      CoordByRisk node = queue.poll();
      Pair<Integer, Integer> coord = node.getCoord();
      if (seen.contains(coord) || coord.equals(dest)) {
        continue;
      }
      seen.add(coord);
      for (Pair<Integer, Integer> neighbor : getNeighbors(coord, maxX, maxY)) {
        int dist = result[coord.getLeft()][coord.getRight()] + getRisk(grid, neighbor);
        if (result[neighbor.getLeft()][neighbor.getRight()] == 0 || dist < result[neighbor.getLeft()][neighbor.getRight()]) {
          result[neighbor.getLeft()][neighbor.getRight()] = dist;
          queue.add(CoordByRisk.of(neighbor, dist));
        }
      }
    }
    return result[maxX - 1][maxY - 1];
  }

  private static List<Pair<Integer, Integer>> getNeighbors(Pair<Integer, Integer> node, int maxX, int maxY) {
    List<Pair<Integer, Integer>> neighbors = new ArrayList<>();
    if (node.getLeft() - 1 >= 0) {
      neighbors.add(Pair.of(node.getLeft() - 1, node.getRight()));
    }
    if (node.getLeft() + 1 < maxX) {
      neighbors.add(Pair.of(node.getLeft() + 1, node.getRight()));
    }
    if (node.getRight() + 1 < maxY) {
      neighbors.add(Pair.of(node.getLeft(), node.getRight() + 1));
    }
    if (node.getRight() - 1 >= 0) {
      neighbors.add(Pair.of(node.getLeft(), node.getRight() - 1));
    }
    return neighbors;
  }

  private static int getRisk(int[][] grid, Pair<Integer, Integer> neighbor) {
    int x = neighbor.getLeft() % grid.length;
    int y = neighbor.getRight() % grid[0].length;
    int risk = grid[x][y];
    while (x < neighbor.getLeft()) {
      risk = (risk % 9) + 1;
      x += grid.length;
    }
    while (y < neighbor.getRight()) {
      risk = (risk % 9) + 1;
      y += grid[0].length;
    }
    return risk;
  }

  @RequiredArgsConstructor(staticName = "of")
  @Getter
  private static class CoordByRisk {

    private final Pair<Integer, Integer> coord;
    private final int risk;

  }

}
