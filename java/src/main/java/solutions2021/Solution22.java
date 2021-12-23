package solutions2021;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math.geometry.Vector3D;

public class Solution22 {

  private static final Pattern PATTERN =
    Pattern.compile("(on|off) x=([-]*\\d+)\\.\\.([-]*\\d+),y=([-]*\\d+)\\.\\.([-]*\\d+),z=([-]*\\d" + "+)\\.\\.([-]*\\d+)");

  public static long partOne(List<String> rebootStepsInput) {
    Map<Vector3D, Integer> rebootCount = new HashMap<>();
    for (String rebootStep : rebootStepsInput) {
      Matcher matcher = PATTERN.matcher(rebootStep);
      if (matcher.find()) {
        String op = matcher.group(1);
        for (int x = Math.max(-50, Integer.parseInt(matcher.group(2))); x <= Math.min(50, Integer.parseInt(matcher.group(3)));
          x++) {
          for (int y = Math.max(-50, Integer.parseInt(matcher.group(4))); y <= Math.min(50, Integer.parseInt(matcher.group(5)));
            y++) {
            for (int z = Math.max(-50, Integer.parseInt(matcher.group(6))); z <= Math.min(50, Integer.parseInt(matcher.group(7)));
              z++) {
              rebootCount.put(new Vector3D(x, y, z), op.equals("on") ? 1 : 0);

            }
          }

        }
      }

    }
    return rebootCount.values().stream().filter(i -> i == 1).count();
  }

  public static long partTwo(List<String> rebootStepsInput) {

    List<Cube> cubes = new ArrayList<>();

    for (String rebootStep : rebootStepsInput) {
      Matcher matcher = PATTERN.matcher(rebootStep);
      if (matcher.find()) {
        String op = matcher.group(1);

        Cube cube =
          Cube.of(Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)),
            Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)), Integer.parseInt(matcher.group(7)),
            op.equals("on"));

        List<Cube> temp = new ArrayList<>();
        for (Cube value : cubes) {
          cube.getOverlappedCube(value).ifPresent(temp::add);
        }

        cubes.addAll(temp);

        if (cube.isOn()) {
          cubes.add(cube);
        }
      }
    }
    return cubes.stream().map(Cube::getVolume).reduce(0L, Long::sum);
  }

  @Data(staticConstructor = "of")
  private static class Cube {

    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;
    private final boolean on;

    public long getVolume() {
      return (xMax - xMin + 1L) * (yMax - yMin + 1L) * (zMax - zMin + 1L) * (on ? 1L : -1L);
    }

    public Optional<Cube> getOverlappedCube(Cube other) {
      int minX = Math.max(xMin, other.xMin);
      int maxX = Math.min(xMax, other.xMax);
      if (minX > maxX) {
        return Optional.empty();
      }
      int minY = Math.max(yMin, other.yMin);
      int maxY = Math.min(yMax, other.yMax);
      if (minY > maxY) {
        return Optional.empty();
      }
      int minZ = Math.max(zMin, other.zMin);
      int maxZ = Math.min(zMax, other.zMax);
      if (minZ > maxZ) {
        return Optional.empty();
      }
      return Optional.of(Cube.of(minX, maxX, minY, maxY, minZ, maxZ, !other.isOn()));
    }

    @Override
    public String toString() {
      return (on ? "on" : "off") + " " + "x=" + xMin + ".." + (xMax) + "," + "y" + yMin + ".." + (yMax) + "," + "z=" + zMin + ".." +
               (zMax);
    }

  }

  private static boolean overlaps(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
    return first.getLeft() <= second.getRight() || second.getLeft() <= first.getRight();
  }

}
