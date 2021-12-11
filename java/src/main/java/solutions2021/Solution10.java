package solutions2021;

import com.google.common.collect.Lists;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
public class Solution10 {

  private static final List<Character> openSymbols = Lists.newArrayList('(', '[', '{', '<');
  private static final Map<Character, Integer> scoreByCorruptedSymbol = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);
  private static final Map<Character, Long> scoreByIncompleteSymbol = Map.of(')', 1L, ']', 2L, '}', 3L, '>', 4L);
  private static final Map<Character, Character> opposite = Map.of('(', ')', '[', ']', '{', '}', '<', '>');

  public static long partOne(Stream<String> chunks) {
    return chunks.map(Solution10::validate)
      .filter(SyntaxValidation::isCorrupted)
      .map(SyntaxValidation::getValidationErrorScore)
      .reduce(0L, Long::sum);
  }

  private static SyntaxValidation validate(String chunk) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char current : chunk.toCharArray()) {
      if (stack.isEmpty() || isOpenSymbol(current)) {
        stack.push(current);
      } else {
        Character openSymbol = stack.pop();
        if (!isRightSymbolFor(openSymbol, current)) {
          return new SyntaxValidation(true, false, scoreByCorruptedSymbol.get(current));
        }
      }
    }
    return new SyntaxValidation(false, true,
      stack.stream().map(opposite::get).map(scoreByIncompleteSymbol::get).reduce(0L, (a, b) -> a * 5 + b));
  }

  private static boolean isRightSymbolFor(char openSymbol, Character closeSymbol) {
    return opposite.get(openSymbol) == closeSymbol;
  }

  private static boolean isOpenSymbol(char c) {
    return openSymbols.contains(c);
  }

  public static long partTwo(Stream<String> chunks) {
    List<Long> incompletChunksScores = chunks.map(Solution10::validate)
      .filter(SyntaxValidation::isIncomplete)
      .map(SyntaxValidation::getValidationErrorScore)
      .sorted()
      .collect(Collectors.toList());
    return incompletChunksScores.get(incompletChunksScores.size() / 2);
  }

  @Getter
  @RequiredArgsConstructor
  private static class SyntaxValidation {

    private final boolean isCorrupted;
    private final boolean isIncomplete;
    private final long validationErrorScore;

  }

}
