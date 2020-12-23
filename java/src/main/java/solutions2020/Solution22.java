package solutions2020;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class Solution22 {

  public static int partOne(String input) {
    return playGame(input, Solution22::playGameOne);
  }

  public static int partTwo(String input) {
    return playGame(input, Solution22::playGameTwo);
  }

  private static int playGame(String input, BiFunction<List<Integer>, List<Integer>, Integer> gameFunction) {
    String[] split = input.split("\n\n");
    List<Integer> firstDeck = Arrays.stream(split[0].split("\n"))
                                .filter(cs -> StringUtils.isNotEmpty(cs) && !cs.contains(":"))
                                .map(Integer::valueOf)
                                .collect(Collectors.toCollection(ArrayList::new));
    List<Integer> secondDeck = Arrays.stream(split[1].split("\n"))
                                 .filter(cs -> StringUtils.isNotEmpty(cs) && !cs.contains(":"))
                                 .map(Integer::valueOf)
                                 .collect(Collectors.toCollection(ArrayList::new));
    gameFunction.apply(firstDeck, secondDeck);
    firstDeck.addAll(secondDeck);
    int size = firstDeck.size();
    return IntStream.range(0, size).map(i -> (size - i) * firstDeck.remove(0)).reduce(0, Integer::sum);
  }

  private static int playGameOne(List<Integer> firstDeck, List<Integer> secondDeck) {
    while (!firstDeck.isEmpty() && !secondDeck.isEmpty()) {
      Integer firstCard = firstDeck.remove(0);
      Integer secondCard = secondDeck.remove(0);
      if (firstCard < secondCard) {
        secondDeck.add(secondCard);
        secondDeck.add(firstCard);
      } else {
        firstDeck.add(firstCard);
        firstDeck.add(secondCard);
      }
    }
    return firstDeck.isEmpty() ? 2 : 1;
  }

  private static int playGameTwo(List<Integer> firstDeck, List<Integer> secondDeck) {
    Map<List<Integer>, List<Integer>> snapshots = new HashMap<>();
    while (!firstDeck.isEmpty() && !secondDeck.isEmpty()) {
      if (snapshots.put(new ArrayList<>(firstDeck), new ArrayList<>(secondDeck)) != null) {
        return 1;
      }
      Integer firstCard = firstDeck.remove(0);
      Integer secondCard = secondDeck.remove(0);
      int winner = firstCard > secondCard ? 1 : 2;
      if (firstCard <= firstDeck.size() && secondCard <= secondDeck.size()) {
        winner = playGameTwo(getDeckCopy(firstDeck, firstCard), getDeckCopy(secondDeck, secondCard));
      }
      if (winner == 1) {
        firstDeck.add(firstCard);
        firstDeck.add(secondCard);
      } else {
        secondDeck.add(secondCard);
        secondDeck.add(firstCard);
      }
    }
    return firstDeck.isEmpty() ? 2 : 1;

  }

  private static List<Integer> getDeckCopy(List<Integer> deck, Integer card) {
    return new ArrayList<>(deck.subList(0, card));
  }
}
