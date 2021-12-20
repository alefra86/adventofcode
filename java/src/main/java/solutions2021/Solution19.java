package solutions2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math.geometry.Vector3D;

public class Solution19 {

  public static int partOne(List<String> lines) {
    Water water = getWater(lines);
    water.buildRegion();
    return water.getBeacons().size();
  }

  public static int partTwo(List<String> lines) {
    Water water = getWater(lines);
    water.buildRegion();
    List<Vector3D> scannerPositions =
      water.getScanners().stream().map(Scanner::getRelativePositionFromZero).collect(Collectors.toList());
    int maxDistance = 0;
    for (Vector3D first : scannerPositions) {
      for (Vector3D second : scannerPositions) {
        if (first.equals(second)) {
          continue;
        }
        int distance = getManhattanDistance(first, second);
        maxDistance = Math.max(maxDistance, distance);
      }
    }
    return maxDistance;
  }

  public static int getManhattanDistance(Vector3D a, Vector3D b) {
    return (int) (Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY()) + Math.abs(a.getZ() - b.getZ()));
  }

  public static Water getWater(List<String> lines) {
    List<Scanner> scanners = new ArrayList<>();
    for (int i = 0; i < lines.size(); i++) {
      String line = lines.get(i);
      String scannerId = "";
      if (line.contains("scanner")) {
        scannerId = line.replace("-", "").trim().replace("scanner ", "");
      }
      i++;
      line = lines.get(i);
      List<Vector3D> measurements = new ArrayList<>();
      while (!"".equals(line) && line != null) {
        List<Integer> vector = Arrays.stream(line.split(",")).map(Integer::valueOf).collect(Collectors.toList());
        measurements.add(new Vector3D(vector.get(0), vector.get(1), vector.get(2)));
        if (i < lines.size() - 1) {
          i++;
          line = lines.get(i);
        } else {
          line = null;
        }
      }
      scanners.add(new Scanner(scannerId, measurements));
    }
    return new Water(scanners);
  }

  private static class Water {

    private final List<Scanner> scanners;

    private final Set<Vector3D> beacons = new HashSet<>();
    private final List<Scanner> done = new ArrayList<>();

    public Water(List<Scanner> scanners) {
      this.scanners = scanners;
    }

    private void buildRegion() {
      done.add(scanners.get(0));
      int checkIndex = 0;
      while (done.size() < scanners.size()) {
        Scanner current = done.get(checkIndex);
        for (Scanner scanner : scanners) {
          if (done.contains(scanner)) {
            continue;
          }
          Transformation transformation = current.getTransformationIfExists(scanner);
          if (transformation != null) {
            scanner.getTransformationToZero().addAll(current.getTransformationToZero());
            scanner.getTransformationToZero().add(transformation);
            done.add(scanner);
          }
        }
        checkIndex++;
      }
      done.stream().map(Scanner::getPointsRelativeToZero).forEach(beacons::addAll);
    }

    public Set<Vector3D> getBeacons() {
      return beacons;
    }

    public List<Scanner> getScanners() {
      return scanners;
    }
  }

  public static class Scanner {

    @Getter
    private final String scannerId;
    private final List<Vector3D> givenMeasurements;
    private final List<Point> points;
    @Getter
    private final List<Transformation> transformationToZero = new ArrayList<>();

    public Scanner(String scannerId, List<Vector3D> givenMeasurements) {
      this.scannerId = scannerId;
      this.givenMeasurements = givenMeasurements;
      points = givenMeasurements.stream().map(mes -> new Point(mes, givenMeasurements)).collect(Collectors.toList());
    }

    public Vector3D getRelativePositionFromZero() {
      Vector3D transformed = new Vector3D(0, 0, 0);
      for (int i = transformationToZero.size() - 1; i >= 0; i--) {
        transformed = transformationToZero.get(i).apply(transformed);
      }
      return transformed;
    }

    List<Vector3D> getPointsRelativeToZero() {
      return givenMeasurements.stream().map(gm -> {
        Vector3D transformed = gm;
        for (int i = transformationToZero.size() - 1; i >= 0; i--) {
          transformed = transformationToZero.get(i).apply(transformed);
        }
        return transformed;
      }).collect(Collectors.toList());
    }

    public Transformation getTransformationIfExists(Scanner scanner) {
      Pair<List<VectorDistance>, List<VectorDistance>> commons = null;
      for (Point m : points) {
        if (commons != null) {
          break;
        }
        for (Point s : scanner.points) {
          if (commons != null) {
            break;
          }
          commons = m.getCommon12Points(s);
        }
      }
      if (commons == null) {
        return null;
      }
      return calculateTransformation(commons);
    }

    private Transformation calculateTransformation(Pair<List<VectorDistance>, List<VectorDistance>> commons) {
      for (int i = 1; i < 25; i++) {
        Transformation transformation = Transformation.of(i);
        Vector3D move = transformation.matches(commons);
        if (move != null) {
          transformation.setMove(move);
          return transformation;
        }
      }
      return null;
    }

  }

  public static class Transformation {

    Function<Vector3D, Double> getX;
    Function<Vector3D, Double> getY;
    Function<Vector3D, Double> getZ;

    @Setter
    Vector3D move;

    public Transformation(Function<Vector3D, Double> getX, Function<Vector3D, Double> getY, Function<Vector3D, Double> getZ) {
      this.getX = getX;
      this.getY = getY;
      this.getZ = getZ;
    }

    public Vector3D apply(Vector3D to) {
      Vector3D rotated = new Vector3D(getX.apply(to), getY.apply(to), getZ.apply(to));
      return move == null ? rotated : rotated.add(move);
    }

    public static Transformation of(int num) {
      switch (num) {
        case 1:
          return new Transformation(v -> v.getX(), v -> v.getY(), v -> v.getZ());
        case 2:
          return new Transformation(v -> v.getX(), v -> v.getZ(), v -> -v.getY());
        case 3:
          return new Transformation(v -> v.getX(), v -> -v.getY(), v -> -v.getZ());
        case 4:
          return new Transformation(v -> v.getX(), v -> -v.getZ(), v -> v.getY());///
        case 5:
          return new Transformation(v -> -v.getX(), v -> -v.getY(), v -> v.getZ());
        case 6:
          return new Transformation(v -> -v.getX(), v -> v.getZ(), v -> v.getY());
        case 7:
          return new Transformation(v -> -v.getX(), v -> v.getY(), v -> -v.getZ());
        case 8:
          return new Transformation(v -> -v.getX(), v -> -v.getZ(), v -> -v.getY());///
        case 9:
          return new Transformation(v -> v.getY(), v -> -v.getX(), v -> v.getZ());
        case 10:
          return new Transformation(v -> v.getY(), v -> v.getZ(), v -> v.getX());
        case 11:
          return new Transformation(v -> v.getY(), v -> v.getX(), v -> -v.getZ());
        case 12:
          return new Transformation(v -> v.getY(), v -> -v.getZ(), v -> -v.getX());///
        case 13:
          return new Transformation(v -> -v.getY(), v -> v.getX(), v -> v.getZ());
        case 14:
          return new Transformation(v -> -v.getY(), v -> -v.getZ(), v -> v.getX());
        case 15:
          return new Transformation(v -> -v.getY(), v -> -v.getX(), v -> -v.getZ());
        case 16:
          return new Transformation(v -> -v.getY(), v -> v.getZ(), v -> -v.getX());///
        case 17:
          return new Transformation(v -> v.getZ(), v -> v.getY(), v -> -v.getX());
        case 18:
          return new Transformation(v -> v.getZ(), v -> v.getX(), v -> v.getY());
        case 19:
          return new Transformation(v -> v.getZ(), v -> -v.getY(), v -> v.getX());
        case 20:
          return new Transformation(v -> v.getZ(), v -> -v.getX(), v -> -v.getY());///
        case 21:
          return new Transformation(v -> -v.getZ(), v -> v.getY(), v -> v.getX());
        case 22:
          return new Transformation(v -> -v.getZ(), v -> -v.getX(), v -> v.getY());
        case 23:
          return new Transformation(v -> -v.getZ(), v -> -v.getY(), v -> -v.getX());
        case 24:
          return new Transformation(v -> -v.getZ(), v -> v.getX(), v -> -v.getY());
      }
      throw new IllegalStateException("Transformation num should be between 1 and 24!");
    }

    public Vector3D matches(Pair<List<VectorDistance>, List<VectorDistance>> commons) {
      Vector3D newOrigin = apply(commons.getRight().get(0).getFrom());
      List<VectorDistance> transformed = commons.getRight()
        .stream()
        .map(vd -> new VectorDistance(newOrigin, apply(vd.getTo()), vd.getDistance()))
        .collect(Collectors.toList());
      int errorLimit = transformed.size() - 11;
      //If there are common distances, it will yield error, since it checks with all
      Map<Integer, Long> grouped = commons.getLeft()
        .stream()
        .map(VectorDistance::getDistance)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
      for (Long val : grouped.values()) {
        if (val > 1) {
          //For 2 it will fail once on both runs, for 3 it will fail 2 out of 3 on all 3 runs
          errorLimit += val * (val - 1);
        }
      }
      Vector3D dist = commons.getLeft().get(0).getFrom().subtract(transformed.get(0).getFrom());
      int errCount = 0;
      for (int i = 0; i < commons.getLeft().size(); i++) {
        VectorDistance left = commons.getLeft().get(i);
        for (VectorDistance right : transformed) {
          if (left.getDistance().equals(right.getDistance())) {
            Vector3D currentDist = left.getTo().subtract(right.getTo());
            if (!dist.equals(currentDist)) {
              errCount++;
            }
          }
          if (errCount > errorLimit) {
            return null;
          }
        }
      }
      return dist;
    }
  }

  private static class Point {

    @Getter
    private final Vector3D point;

    @Getter
    private final List<VectorDistance> others;

    public Point(Vector3D point, List<Vector3D> others) {
      this.point = point;
      this.others = new ArrayList<>();
      for (Vector3D v : others) {
        if (v != point) {
          this.others.add(new VectorDistance(point, v));
        }
      }
    }

    public Pair<List<VectorDistance>, List<VectorDistance>> getCommon12Points(Point m2) {
      Set<Integer> m2Dists = m2.others.stream().map(VectorDistance::getDistance).collect(Collectors.toSet());
      List<Integer> commonDists =
        others.stream().map(VectorDistance::getDistance).filter(m2Dists::contains).collect(Collectors.toList());
      if (commonDists.size() < 11) {
        return null;
      }
      List<VectorDistance> ofThis = others.stream().filter(vd -> commonDists.contains(vd.distance)).collect(Collectors.toList());
      List<VectorDistance> ofOther =
        m2.others.stream().filter(vd -> commonDists.contains(vd.distance)).collect(Collectors.toList());
      return Pair.of(ofThis, ofOther);
    }
  }

  private static class VectorDistance {

    @Getter
    private final Vector3D from;
    @Getter
    private final Vector3D to;
    @Getter
    private final Integer distance;

    public VectorDistance(Vector3D from, Vector3D to) {
      this.from = from;
      this.to = to;
      distance = (int) to.subtract(from).getNorm1();
    }

    public VectorDistance(Vector3D from, Vector3D to, Integer distance) {
      this.from = from;
      this.to = to;
      this.distance = distance;
    }
  }
}
