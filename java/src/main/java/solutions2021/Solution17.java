package solutions2021;

import lombok.Data;

/**
 *
 */
public class Solution17 {

  public static int partOne(String targetArea) {
    Area area = getArea(targetArea);
    return Math.abs(area.getMinY()) * (Math.abs(area.getMinY()) - 1) / 2;
  }

  public static long partTwo(String targetArea) {
    Area area = getArea(targetArea);

    int counter = 0;
    for (int i = 1; i <= area.getMaxX(); i++) {
      for (int j = area.getMinY(); j < Math.abs(area.getMinY()); j++) {
        Position position = Position.of(0, 0);
        Velocity velocity = Velocity.of(i, j);
        while (!area.hasPassThrough(position)) {
          position = Position.of(position.getX() + velocity.getX(), position.getY() + velocity.getY());
          velocity = Velocity.of(Math.max(0, velocity.getX() - 1), velocity.getY() - 1);
          if (area.isInArea(position)) {
            counter++;
            break;
          }
        }
      }
    }

    return counter;
  }

  private static Area getArea(String targetArea) {
    String[] areaPart = targetArea.replace("target area: ", "").split(",");
    String[] xPart = areaPart[0].trim().replace("x=", "").split("\\.\\.");
    String[] yPart = areaPart[1].trim().replace("y=", "").split("\\.\\.");

    return Area.of(Integer.parseInt(xPart[0]), Integer.parseInt(xPart[1]), Integer.parseInt(yPart[0]), Integer.parseInt(yPart[1]));
  }

  private static int getHighestPosition(int y) {
    return y * (y + 1) / 2;
  }

  @Data(staticConstructor = "of")
  private static class Area {

    private final int minX;
    private final int maxX;
    private final int minY;
    private final int maxY;

    public boolean isInArea(Position position) {
      int y = position.getY();
      return position.getX() >= minX && position.getX() <= maxX && y >= minY && y <= maxY;
    }

    public boolean hasPassThrough(Position position) {
      return position.getX() > maxX || position.getY() < minY;
    }

  }

  @Data(staticConstructor = "of")
  private static class Position {

    private final int x;
    private final int y;

  }

  @Data(staticConstructor = "of")
  private static class Velocity {

    private final int x;
    private final int y;

  }

}
