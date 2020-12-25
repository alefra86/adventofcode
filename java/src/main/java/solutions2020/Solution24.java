package solutions2020;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution24 {

  private static final List<Pair<Integer, Integer>> DIRECTIONS =
    Lists.newArrayList(Pair.of(-1, 0), Pair.of(1, 0), Pair.of(-1, -1), Pair.of(0, -1), Pair.of(0, 1), Pair.of(1, 1));

  public static int partOne(String lines) {
    return getBlackTiles(lines).size();
  }

  public static int partTwo(String lines) {
    Set<Pair<Integer, Integer>> status = getBlackTiles(lines);
    Stopwatch stopWatch = Stopwatch.createUnstarted();
    Stopwatch stopWatchVisit = Stopwatch.createUnstarted();
    Stopwatch stopWatchMove = Stopwatch.createUnstarted();
    for (int cycle = 1; cycle <= 100; cycle++) {
      Set<Pair<Integer, Integer>> temp = new HashSet<>();
      Set<Pair<Integer, Integer>> tileToCheck = new HashSet<>();
      stopWatch.start();
      for (Pair<Integer, Integer> position : status) {
        tileToCheck.add(position);
        for (Pair<Integer, Integer> direction : DIRECTIONS) {
          tileToCheck.add(move(position, direction));
        }
      }
      stopWatch.stop();
      for (Pair<Integer, Integer> tilePosition : tileToCheck) {
        int countBlack = countBlack(status, stopWatchMove, tilePosition);
        stopWatchVisit.start();
        if (isTileToAdd(tilePosition, countBlack, status)) {
          temp.add(tilePosition);
        }
        stopWatchVisit.stop();
      }
      status = temp;
    }
    System.out.println(stopWatch.elapsed(TimeUnit.MILLISECONDS));
    System.out.println(stopWatchVisit.elapsed(TimeUnit.MILLISECONDS));
    System.out.println(stopWatchMove.elapsed(TimeUnit.MILLISECONDS));
    return status.size();

  }

  private static boolean isTileToAdd(Pair<Integer, Integer> tilePosition, int countBlack, Set<Pair<Integer, Integer>> status) {
    boolean contains = status.contains(tilePosition);
    if (!contains && countBlack == 2) {
      return true;
    } else {
      return contains && (countBlack == 1 || countBlack == 2);
    }
  }

  private static int countBlack(Set<Pair<Integer, Integer>> status, Stopwatch stopWatchMove, Pair<Integer, Integer> tilePosition) {
    int countBlack = 0;
    for (Pair<Integer, Integer> direction : DIRECTIONS) {
      Pair<Integer, Integer> move = move(tilePosition, direction);
      stopWatchMove.start();
      if (status.contains(move)) {
        countBlack++;
      }
      stopWatchMove.stop();
    }
    return countBlack;
  }

  private static Set<Pair<Integer, Integer>> getBlackTiles(String lines) {
    Set<Pair<Integer, Integer>> status = new HashSet<>();
    for (String line : lines.split("\n")) {
      Pair<Integer, Integer> referenceTile = getReferenceTile(line);
      if (!status.add(referenceTile)) {
        status.remove(referenceTile);
      }
    }
    return status;
  }

  private static Pair<Integer, Integer> getReferenceTile(String line) {
    int positionX = 0;
    int positionY = 0;
    int i = -1;
    while (++i < line.length()) {
      char nextChar = line.charAt(i);
      if (nextChar == 'n') {
        positionY--;
        if (line.charAt(++i) == 'w') {
          positionX--;
        }
      } else if (nextChar == 's') {
        positionY++;
        if (line.charAt(++i) == 'e') {
          positionX++;
        }
      } else {
        if (nextChar == 'w') {
          positionX--;
        } else {
          positionX++;
        }
      }
    }
    return Pair.of(positionX, positionY);
  }

  private static Pair<Integer, Integer> move(Pair<Integer, Integer> tilePosition, Pair<Integer, Integer> direction) {
    return Pair.of(tilePosition.getLeft() + direction.getLeft(), tilePosition.getRight() + direction.getRight());
  }
}


