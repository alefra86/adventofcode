package solutions2021;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution13 {

  public static int partOne(String manual) {

    String[] split = manual.split("\n\n");
    String[] lines = split[0].split("\n");

    Set<Pair<Integer, Integer>> mark = new HashSet<>();

    for (String line : lines) {
      String[] dot = line.split(",");
      int x = Integer.parseInt(dot[0]);
      int y = Integer.parseInt(dot[1]);
      mark.add(Pair.of(x, y));
    }

    String[] instructions = split[1].split("\n");

    String[] fold = instructions[0].replace("fold along ", "").split("=");
    String foldAxe = fold[0];
    int foldLine = Integer.parseInt(fold[1]);

    Function<Pair<Integer, Integer>, Integer> getAxeToCheck = p -> foldAxe.equals("x") ? p.getLeft() : p.getRight();
    Function<Pair<Integer, Integer>, Integer> getNewX = p -> foldAxe.equals("x") ? 2 * foldLine - p.getLeft() : p.getLeft();
    Function<Pair<Integer, Integer>, Integer> getNewY = p -> foldAxe.equals("x") ? p.getRight() : 2 * foldLine - p.getRight();

    Set<Pair<Integer, Integer>> next = new HashSet<>(mark);
    for (Pair<Integer, Integer> marked : mark) {
      if (getAxeToCheck.apply(marked) > foldLine) {
        next.remove(marked);
        int newX = getNewX.apply(marked);
        int newY = getNewY.apply(marked);
        next.add(Pair.of(newX, newY));
      }
    }

    return next.size();
  }

  public static String partTwo(String manual) {
    String[] split = manual.split("\n\n");
    String[] lines = split[0].split("\n");

    Set<Pair<Integer, Integer>> mark = new HashSet<>();

    for (String line : lines) {
      String[] dot = line.split(",");
      int x = Integer.parseInt(dot[0]);
      int y = Integer.parseInt(dot[1]);
      mark.add(Pair.of(x, y));
    }

    String[] instructions = split[1].split("\n");

    for (String instruction : instructions) {
      String[] fold = instruction.replace("fold along ", "").split("=");
      String foldAxe = fold[0];
      int foldLine = Integer.parseInt(fold[1]);

      Function<Pair<Integer, Integer>, Integer> getAxeToCheck = p -> foldAxe.equals("x") ? p.getLeft() : p.getRight();
      Function<Pair<Integer, Integer>, Integer> getNewX = p -> foldAxe.equals("x") ? 2 * foldLine - p.getLeft() : p.getLeft();
      Function<Pair<Integer, Integer>, Integer> getNewY = p -> foldAxe.equals("x") ? p.getRight() : 2 * foldLine - p.getRight();

      Set<Pair<Integer, Integer>> next = new HashSet<>(mark);
      for (Pair<Integer, Integer> marked : mark) {
        if (getAxeToCheck.apply(marked) > foldLine) {
          next.remove(marked);
          int newX = getNewX.apply(marked);
          int newY = getNewY.apply(marked);
          next.add(Pair.of(newX, newY));
        }
      }
      mark = next;
    }

    return getCode(mark);
  }

  private static String getCode(Set<Pair<Integer, Integer>> mark) {
    int maxX = mark.stream().map(Pair::getLeft).mapToInt(a -> a).max().orElse(-1);
    int maxY = mark.stream().map(Pair::getRight).mapToInt(a -> a).max().orElse(-1);

    StringBuilder result = new StringBuilder();
    for (int i = 0; i <= maxY; i++) {
      StringBuilder row = new StringBuilder();
      for (int j = 0; j <= maxX; j++) {
        if (mark.contains(Pair.of(j, i))) {
          row.append("#");
        } else {
          row.append(".");
        }
      }
      result.append(row).append("\n");
    }

    return result.toString().trim();
  }

}
