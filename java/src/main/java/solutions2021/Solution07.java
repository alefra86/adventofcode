package solutions2021;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 *
 */
public class Solution07 {

  public static int partOne(String crabPositions) {
    List<Integer> crabs = Arrays.stream(crabPositions.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    int maxValue = crabs.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
    int fuelToSpend = Integer.MAX_VALUE;
    for (int i = 0; i <= maxValue; i++) {
      int fuel = 0;
      for (Integer crab : crabs) {
        fuel += Math.abs(crab - i);
      }
      fuelToSpend = Math.min(fuelToSpend, fuel);
    }
    return fuelToSpend;
  }

  public static int partTwo(String crabPositions) {
    List<Integer> crabs = Arrays.stream(crabPositions.split(",")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    int maxValue = crabs.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new);
    int fuelToSpend = Integer.MAX_VALUE;
    for (int i = 0; i <= maxValue; i++) {
      int fuel = 0;
      for (Integer crab : crabs) {
        int distance = Math.abs(crab - i);
        fuel += (distance * (distance + 1) / 2);
      }
      fuelToSpend = Math.min(fuelToSpend, fuel);
    }
    return fuelToSpend;
  }

}
