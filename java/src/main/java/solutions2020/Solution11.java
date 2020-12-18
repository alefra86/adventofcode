package solutions2020;

import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 *
 */
public class Solution11 {

  public static int partOne(Stream<String> lines) {
    return getOccupiedSeats(lines, new AdjacentSeatStrategy(), 4);
  }

  public static int partTwo(Stream<String> lines) {
    return getOccupiedSeats(lines, new FirstVisibleSeatStrategy(), 5);
  }

  private static int getOccupiedSeats(Stream<String> lines, SeatToCheckStrategy seatToCheckStrategy, int tolerance) {
    char[][] seats = lines.map(String::toCharArray).toArray(char[][]::new);
    boolean change = true;
    int occupiedSeats = 0;
    while (change) {
      change = false;
      char[][] temp = new char[seats.length][seats[0].length];
      for (int i = 0; i < seats.length; i++) {
        temp[i] = Arrays.copyOf(seats[i], seats[i].length);
        for (int j = 0; j < seats[i].length; j++) {
          int occupied = occupied(seats, i, j, seatToCheckStrategy);
          if (seats[i][j] == 'L' && occupied == 0) {
            change = true;
            temp[i][j] = '#';
            occupiedSeats++;
          } else if (seats[i][j] == '#' && occupied >= tolerance) {
            change = true;
            temp[i][j] = 'L';
            occupiedSeats--;
          }
        }
      }
      seats = temp;
    }
    return occupiedSeats;
  }

  private static boolean valid(char[][] seats, int i, int j) {
    return i >= 0 && i < seats.length && j >= 0 && j < seats[i].length;
  }

  private static int occupied(char[][] seats, int i, int j, SeatToCheckStrategy seatToCheckStrategy) {
    int occupied = 0;
    for (Direction direction : Direction.values()) {
      ImmutablePair<Integer, Integer> seat = seatToCheckStrategy.seatToCheck(seats, i, j, direction);
      int row = seat.left;
      int column = seat.right;
      if (valid(seats, row, column) && seats[row][column] == '#') {
        occupied++;
      }
    }
    return occupied;
  }

  enum Direction {
    TOP_LEFT(-1, -1),
    TOP(-1, 0),
    TOP_RIGHT(-1, +1),
    RIGHT(0, +1),
    BOTTOM_RIGHT(+1, +1),
    BOTTOM(+1, 0),
    BOTTOM_LEFT(+1, -1),
    LEFT(0, -1);
    private final int verticalStep;
    private final int horizontalStep;

    Direction(int verticalStep, int horizontalStep) {
      this.verticalStep = verticalStep;
      this.horizontalStep = horizontalStep;
    }

    public int getVerticalStep() {
      return verticalStep;
    }

    public int getHorizontalStep() {
      return horizontalStep;
    }
  }

  interface SeatToCheckStrategy {

    ImmutablePair<Integer, Integer> seatToCheck(char[][] seats, int i, int j, Direction direction);

  }

  static class AdjacentSeatStrategy implements SeatToCheckStrategy {

    @Override
    public ImmutablePair<Integer, Integer> seatToCheck(char[][] seats, int i, int j, Direction direction) {
      return ImmutablePair.of(i + direction.getVerticalStep(), j + direction.getHorizontalStep());
    }
  }

  static class FirstVisibleSeatStrategy implements SeatToCheckStrategy {

    @Override
    public ImmutablePair<Integer, Integer> seatToCheck(char[][] seats, int i, int j, Direction direction) {
      int row = i + direction.getVerticalStep();
      int column = j + direction.getHorizontalStep();
      while (valid(seats, row, column) && seats[row][column] == '.') {
        row += direction.getVerticalStep();
        column += direction.getHorizontalStep();
      }
      return ImmutablePair.of(row, column);
    }
  }

}
