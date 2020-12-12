package solutions2020;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Solution12 {

  private static final List<String> ALL_DIRECTIONS = Arrays.stream(Direction.values()).map(Enum::name).collect(Collectors.toList());
  private static final Pattern PATTERN = Pattern.compile("([NSEWLRF])([0-9]{1,3})");

  public static int partOne(Stream<String> lines) {
    Ship ship = new Ship(0, 0, Direction.E);
    lines.forEach(line -> {
      Matcher matcher = PATTERN.matcher(line);
      if (matcher.find()) {
        String action = matcher.group(1);
        int value = Integer.parseInt(matcher.group(2));
        if (isDirection(action)) {
          ship.move(Direction.valueOf(action.toUpperCase()), value);
        } else if ("F".equals(action)) {
          ship.move(value);
        } else {
          ship.turn(Turn.valueOf(action.toUpperCase()), value);
        }
      }
    });
    return ship.getManhattanDistance();
  }

  public static int partTwo(Stream<String> lines) {
    Ship ship = new Ship(0, 0, Direction.E);
    WayPoint wayPoint = new WayPoint(10, 1);
    lines.forEach(line -> {
      Matcher matcher = PATTERN.matcher(line);
      if (matcher.find()) {
        String action = matcher.group(1);
        int value = Integer.parseInt(matcher.group(2));
        if (isDirection(action)) {
          wayPoint.move(Direction.valueOf(action.toUpperCase()), value);
        } else if ("F".equals(action)) {
          ship.move(wayPoint, value);
        } else {
          wayPoint.turn(Turn.valueOf(action.toUpperCase()), value);
        }
      }
    });
    return ship.getManhattanDistance();
  }

  private static boolean isDirection(String action) {
    return ALL_DIRECTIONS.contains(action);
  }

  static class Ship {

    private int x;
    private int y;
    private Direction direction;

    public Ship(int x, int y, Direction direction) {
      this.x = x;
      this.y = y;
      this.direction = direction;
    }

    public void move(int value) {
      move(direction, value);
    }

    public void move(Direction direction, int value) {
      x += direction.getMoveX() * value;
      y += direction.getMoveY() * value;
    }

    public void move(WayPoint wayPoint, int value) {
      x += wayPoint.getX() * value;
      y += wayPoint.getY() * value;
    }

    public void turn(Turn turn, int degree) {
      int i = Arrays.asList(Direction.values()).indexOf(direction);
      i = (i + (turn.getTurn() * degree / 90) + 4) % 4;
      direction = Direction.values()[i];
    }

    public int getManhattanDistance() {
      return Math.abs(x) + Math.abs(y);
    }
  }

  static class WayPoint {

    private int x;
    private int y;

    public WayPoint(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public void move(Direction direction, int value) {
      x += direction.getMoveX() * value;
      y += direction.getMoveY() * value;
    }

    public void turn(Turn turn, int degree) {
      if (turn.getTurn() == 1) {
        turnClockwise(degree / 90);
      } else {
        turnCounterClockwise(degree / 90);
      }

    }

    public void turnClockwise(int number) {
      for (int i = 0; i < number; i++) {
        int tmp = x;
        x = y;
        y = -tmp;
      }
    }

    public void turnCounterClockwise(int number) {
      for (int i = 0; i < number; i++) {
        int tmp = x;
        x = -y;
        y = tmp;
      }
    }

  }

  enum Turn {
    L(-1),
    R(1);

    private final int turn;

    Turn(int turn) {
      this.turn = turn;
    }

    public int getTurn() {
      return turn;
    }
  }

  enum Direction {
    W(-1, 0),
    N(0, 1),
    E(1, 0),
    S(0, -1);

    private final int moveX;
    private final int moveY;

    Direction(int moveX, int moveY) {
      this.moveX = moveX;
      this.moveY = moveY;
    }

    public int getMoveX() {
      return moveX;
    }

    public int getMoveY() {
      return moveY;
    }
  }

}
