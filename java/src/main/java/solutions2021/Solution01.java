package solutions2021;

import java.util.List;
import java.util.stream.IntStream;

/**
 *
 */
public class Solution01 {

  public static int partOne(List<Integer> reports) {
    return IntStream.range(0, reports.size()).reduce(0, (a, b) -> b > 0 ? reports.get(b) > reports.get(b - 1) ? a + 1 : a : 0);
  }

  public static int partTwo(List<Integer> reports) {
    int counter = 0;
    int lastWindow = reports.get(0) + reports.get(1) + reports.get(2);
    for (int i = 0; i < reports.size() - 3; i++) {
      int nextWindow = lastWindow - reports.get(i) + reports.get(i + 3);
      if (nextWindow > lastWindow) {
        counter++;
      }
      lastWindow = nextWindow;
    }
    return counter;
  }

}
