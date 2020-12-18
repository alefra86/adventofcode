package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 */
public class Solution13 {

  public static int partOne(List<String> lines) {
    int timestamp = Integer.parseInt(lines.get(0));
    List<Integer> buses = Arrays.stream(lines.get(1).split(","))
                            .filter(b -> !b.equals("x"))
                            .mapToInt(Integer::parseInt)
                            .sorted()
                            .boxed()
                            .collect(Collectors.toList());
    int earliestTimestamp = timestamp;
    while (true) {
      for (Integer bus : buses) {
        if (earliestTimestamp % bus == 0) {
          return (earliestTimestamp - timestamp) * bus;
        }
      }
      earliestTimestamp++;
    }

  }

  public static long partTwo(List<String> lines) {
    String[] busesToString = lines.get(1).split(",");
    Map<Integer, Integer> buses = IntStream.range(0, busesToString.length)
                                    .filter(i -> !busesToString[i].equals("x"))
                                    .boxed()
                                    .collect(
                                      Collectors.toMap(Function.identity(), i -> Integer.parseInt(busesToString[i]), (a, b) -> a,
                                        LinkedHashMap::new));
    long timestamp = buses.values().iterator().next();
    long multiply = 1;
    List<Integer> toRemove = new ArrayList<>();
    while (true) {
      boolean found = true;
      for (Entry<Integer, Integer> bus : buses.entrySet()) {
        long module = (timestamp + bus.getKey()) % bus.getValue();
        if (module != 0) {
          timestamp += multiply;
          found = false;
          break;
        }
        multiply *= bus.getValue();
        toRemove.add(bus.getKey());
      }
      if (found) {
        return timestamp;
      } else {
        toRemove.forEach(buses::remove);
        toRemove.clear();
      }
    }
  }

}
