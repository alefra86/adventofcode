package solutions2021;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
public class Solution05 {

  private static final Pattern MATCHER = Pattern.compile("^(\\d+),(\\d+) -> (\\d+),(\\d+)$");

  public static int partOne(Stream<String> lines) {
    return countOverlappedPoints(lines, l -> !l.isDiagonal());
  }

  public static int partTwo(Stream<String> lines) {
    return countOverlappedPoints(lines, l -> true);

  }

  private static int countOverlappedPoints(Stream<String> lines, Predicate<Line> filter) {
    Map<Position, Integer> counter = new HashMap<>();

    lines.map(l -> {
      Matcher matcher = MATCHER.matcher(l);
      if (matcher.find()) {
        return Line.of(Position.of(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
          Position.of(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))));
      }
      throw new IllegalArgumentException("Invalid input line");
    }).filter(filter).forEach(l -> {
      int xDir = l.getP1().getX().compareTo(l.getP2().getX()) * -1;
      int yDir = l.getP1().getY().compareTo(l.getP2().getY()) * -1;
      int diff = Math.max(Math.abs(l.getP1().getX() - l.getP2().getX()), Math.abs(l.getP1().getY() - l.getP2().getY()));
      int valueX = l.getP1().getX();
      int valueY = l.getP1().getY();
      for (int i = 0; i <= diff; i++) {
        counter.merge(Position.of(valueX, valueY), 1, Integer::sum);
        valueX += xDir;
        valueY += yDir;
      }

    });
    return (int) counter.entrySet().stream().filter(e -> e.getValue() >= 2).count();
  }

  @Getter
  @RequiredArgsConstructor(staticName = "of")
  private static class Line {

    private final Position p1;
    private final Position p2;

    public boolean isDiagonal() {
      return Math.abs(p1.getX() - p2.getX()) == Math.abs(p1.getY() - p2.getY());
    }
  }

  @Getter
  @RequiredArgsConstructor(staticName = "of")
  @EqualsAndHashCode
  private static class Position {

    private final Integer x;
    private final Integer y;
  }

}
