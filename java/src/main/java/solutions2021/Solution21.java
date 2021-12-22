package solutions2021;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Data;

public class Solution21 {

  private static final Pattern PATTERN = Pattern.compile("Player [1-2] starting position: ([\\d])");

  private static final Map<Integer, Integer> dice = Map.of(6, 7, 5, 6, 7, 6, 4, 3, 8, 3, 3, 1, 9, 1);

  public static int partOne(List<String> input) {
    Matcher matcher = PATTERN.matcher(input.get(0));
    int firstPosition = 1;
    if (matcher.find()) {
      firstPosition = Integer.parseInt(matcher.group(1));
    }
    matcher = PATTERN.matcher(input.get(1));
    int secondPosition = 1;
    if (matcher.find()) {
      secondPosition = Integer.parseInt(matcher.group(1));
    }
    int firstScore = 0, secondScore = 0;

    int iteration = 0;

    int diceScore = 0;

    int losing;

    while (true) {
      iteration += 3;
      firstPosition = getNewPosition(firstPosition, getDiceScore(diceScore));
      firstScore += firstPosition;
      if (firstScore >= 1000) {
        losing = secondScore;
        break;
      }
      diceScore = increaseDice(diceScore, 100);
      iteration += 3;
      secondPosition = getNewPosition(secondPosition, getDiceScore(diceScore));
      secondScore += secondPosition;
      if (secondScore >= 1000) {
        losing = firstScore;
        break;
      }
      diceScore = increaseDice(diceScore, 100);
    }

    return iteration * losing;
  }

  public static long partTwo(List<String> input) {
    Matcher matcher = PATTERN.matcher(input.get(0));
    int firstPosition = 1;
    if (matcher.find()) {
      firstPosition = Integer.parseInt(matcher.group(1));
    }
    matcher = PATTERN.matcher(input.get(1));
    int secondPosition = 1;
    if (matcher.find()) {
      secondPosition = Integer.parseInt(matcher.group(1));
    }
    return solve(firstPosition, secondPosition);
  }

  private static Long solve(int firstPosition, int secondPosition) {

    long first = 0, second = 0;
    Map<Game, Long> universe = new HashMap<>();
    universe.put(Game.of(firstPosition, secondPosition, 0, 0), 1L);

    while (!universe.isEmpty()) {
      Map<Game, Long> temp = new HashMap<>();
      for (Entry<Game, Long> state : universe.entrySet()) {

        for (Entry<Integer, Integer> rollFirst : dice.entrySet()) {
          int p1 = getNewPosition(state.getKey().getFirstPosition(), rollFirst.getKey());
          int s1 = state.getKey().getFirstScore() + p1;
          if (s1 >= 21) {
            first += state.getValue() * rollFirst.getValue();
            continue;
          }
          for (Entry<Integer, Integer> rollSecond : dice.entrySet()) {
            int p2 = getNewPosition(state.getKey().getSecondPosition(), rollSecond.getKey());
            int s2 = state.getKey().getSecondScore() + p2;
            if (s2 >= 21) {
              second += state.getValue() * rollFirst.getValue() * rollSecond.getValue();
              continue;
            }
            temp.merge(Game.of(p1, p2, s1, s2), state.getValue() * rollFirst.getValue() * rollSecond.getValue(), Long::sum);
          }
        }

      }
      universe = temp;

    }
    return Math.max(first, second);
  }

  @Data(staticConstructor = "of")
  private static class Game {

    private final int firstPosition;
    private final int secondPosition;
    private final int firstScore;
    private final int secondScore;

  }

  private static int getNewPosition(int startPosition, int diceScore) {
    return (startPosition + diceScore - 1) % 10 + 1;
  }

  private static int getDiceScore(int diceScore) {
    int score = 0;
    for (int i = 1; i <= 3; i++) {
      score += (diceScore + i) % 100;
      if (score == 0) {
        score = 100;
      }
    }
    return score;
  }

  private static int increaseDice(int diceScore, int sides) {
    diceScore = (diceScore + 3) % sides;
    if (diceScore == 0) {
      diceScore = sides;
    }
    return diceScore;
  }

}
