package solutions2020;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public class Solution05 {

  private final static int ROWS = 127;
  private final static int COLUMNS = 7;

  public static int partOne(List<String> seats) {
    return seats.stream().mapToInt(Solution05::getSeatId).max().getAsInt();
  }

  public static int partTwo(List<String> seats) {
    List<Integer> seatIds = seats.stream().map(Solution05::getSeatId).sorted().collect(Collectors.toList());
    for (int i = 0, j = i + 1; j < seatIds.size(); i++, j++) {
      if (seatIds.get(j) - seatIds.get(i) == 2) {
        return seatIds.get(i) + 1;
      }
    }
    return 0;
  }

  private static int getSeatId(String seat) {
    return getRow(seat) * 8 + getColumn(seat);
  }

  private static int getColumn(String seat) {
    String column = seat.substring(7);
    int startIndex = 0;
    int endIndex = COLUMNS;
    for (char c : column.toCharArray()) {
      int newIndex = (endIndex + startIndex) / 2;
      if (c == 'L') {
        endIndex = newIndex;
      } else {
        startIndex = newIndex + 1;
      }
    }
    return startIndex;
  }

  private static int getRow(String seat) {
    String row = seat.substring(0, 7);
    int startIndex = 0, endIndex = ROWS;
    for (char c : row.toCharArray()) {
      int newIndex = (endIndex + startIndex) / 2;
      if (c == 'F') {
        endIndex = newIndex;
      } else {
        startIndex = newIndex + 1;
      }
    }
    return startIndex;
  }

}
