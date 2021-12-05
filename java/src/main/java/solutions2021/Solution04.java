package solutions2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 *
 */
public class Solution04 {

  public static int partOne(String bingoInput) {
    Bingo bingo = getBingo(bingoInput);

    for (Integer number : bingo.getNumbers()) {
      for (Board board : bingo.getBoards()) {
        board.mark(number);
        if (board.isWinning()) {
          return board.getSumUnmarkedNumbers() * number;
        }
      }
    }
    return 0;
  }

  private static Bingo getBingo(String bingoInput) {
    String[] split = bingoInput.split("\n\n");
    Bingo bingo = new Bingo();
    bingo.setNumbers(Arrays.stream(split[0].split(",")).map(Integer::parseInt).collect(Collectors.toList()));

    List<Board> boards = new ArrayList<>();

    for (int boardId = 1; boardId < split.length; boardId++) {
      Board board = Board.of(boardId);
      String[] rows = split[boardId].split("\n");
      for (int row = 0; row < rows.length; row++) {
        String[] columns = rows[row].trim().split("[ ]+");
        for (int column = 0; column < columns.length; column++) {
          int value = Integer.parseInt(columns[column]);
          board.addNumber(value, Number.of(value, row, column));
        }
      }
      boards.add(board);
    }

    bingo.setBoards(boards);
    return bingo;
  }

  public static int partTwo(String bingoInput) {

    Bingo bingo = getBingo(bingoInput);

    Set<Board> boards = new HashSet<>(bingo.getBoards());

    for (Integer number : bingo.getNumbers()) {
      for (Board board : bingo.getBoards()) {
        if (boards.contains(board)) {
          board.mark(number);
          if (board.isWinning()) {
            if (boards.size() == 1) {
              return board.getSumUnmarkedNumbers() * number;
            }
            boards.remove(board);
          }
        }
      }
    }
    return 0;

  }

  @Getter
  @Setter
  private static class Bingo {

    private List<Integer> numbers;

    private List<Board> boards;

  }

  @RequiredArgsConstructor(staticName = "of")
  @Getter
  private static class Number {

    private final int value;
    private final int row;
    private final int column;
    @Setter
    private boolean marked;

  }

  @RequiredArgsConstructor(staticName = "of")
  @EqualsAndHashCode(onlyExplicitlyIncluded = true)
  private static class Board {

    @EqualsAndHashCode.Include
    private final int boardId;

    private final Map<Integer, Number> numberByPositions = new HashMap<>();

    private final Number[][] marked = new Number[5][5];

    boolean isWinning = false;

    public void addNumber(Integer value, Number number) {
      numberByPositions.put(value, number);
      marked[number.getRow()][number.getColumn()] = number;
    }

    public void mark(int number) {
      Number position = numberByPositions.get(number);
      if (position != null) {
        marked[position.getRow()][position.getColumn()].setMarked(true);
        isWinning = Stream.of(marked[position.getRow()]).allMatch(Number::isMarked) ||
                      IntStream.range(0, 5).boxed().map(i -> marked[i][position.getColumn()]).allMatch(Number::isMarked);
      }
    }

    public boolean isWinning() {
      return isWinning;
    }

    public int getSumUnmarkedNumbers() {
      int result = 0;
      for (Number[] row : marked) {
        for (Number element : row) {
          if (!element.isMarked()) {
            result += element.getValue();
          }
        }
      }
      return result;
    }

  }
}
