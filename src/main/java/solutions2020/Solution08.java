package solutions2020;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class Solution08 {

  public static int partOne(Stream<String> lines) {
    List<Instruction> instructions = getInstructions(lines);
    boolean[] seen = new boolean[instructions.size()];
    int i = 0;
    int accumulator = 0;
    while (!seen[i]) {
      seen[i] = true;
      Instruction instruction = instructions.get(i);
      i = instruction.nextIndex(i);
      accumulator = instruction.nextAccumulator(accumulator);
    }
    return accumulator;
  }

  private static List<Instruction> getInstructions(Stream<String> lines) {
    return lines.map(line -> {
      Matcher matcher = Pattern.compile("^(\\w{3}) ([+|-]\\d+)$").matcher(line);
      if (matcher.find()) {
        return new Instruction(Operation.valueOf(matcher.group(1).toUpperCase()), Integer.parseInt(matcher.group(2)));
      }
      return null;
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }

  public static int partTwo(Stream<String> lines) {
    List<Instruction> instructions = getInstructions(lines);
    int i = 0;
    int accumulator = 0;
    boolean[] seen = new boolean[instructions.size()];
    while (!seen[i]) {
      seen[i] = true;
      Instruction instruction = instructions.get(i);
      if (instruction.canChange()) {
        Integer innerAcc = getAccumulatorByChangingOperation(instructions, i, accumulator, seen);
        if (innerAcc != null) {
          return innerAcc;
        }
      }
      i = instruction.nextIndex(i);
      accumulator = instruction.nextAccumulator(accumulator);

    }
    return accumulator;
  }

  private static Integer getAccumulatorByChangingOperation(List<Instruction> instructions, int i, int accumulator, boolean[] seen) {
    Instruction innerInstruction = instructions.get(i).changeOperation();
    int j = innerInstruction.nextIndex(i);
    int innerAcc = innerInstruction.nextAccumulator(accumulator);
    boolean[] innerSeen = Arrays.copyOf(seen, seen.length);
    while (j >= 0 && j < instructions.size() && !innerSeen[j]) {
      innerSeen[j] = true;
      Instruction instruction = instructions.get(j);
      j = instruction.nextIndex(j);
      innerAcc = instruction.nextAccumulator(innerAcc);
    }
    if (j == instructions.size()) {
      return innerAcc;
    }
    return null;
  }

  private static class Instruction {

    private final Operation operation;
    private final int argument;

    public Instruction(Operation operation, int argument) {
      this.operation = operation;
      this.argument = argument;
    }

    public Integer nextAccumulator(int current) {
      return operation.getAccumulatorCalculator().apply(current, argument);
    }

    public Integer nextIndex(int current) {
      return operation.getIndexCalculator().apply(current, argument);
    }

    public boolean canChange() {
      return operation == Operation.NOP || operation == Operation.JMP;
    }

    public Instruction changeOperation() {
      return new Instruction(operation == Operation.NOP ? Operation.JMP : Operation.NOP, argument);
    }
  }

  private enum Operation {
    NOP((i, j) -> i + 1, (i, j) -> i),
    JMP(Integer::sum, (i, j) -> i),
    ACC((i, j) -> i + 1, Integer::sum);

    private final BiFunction<Integer, Integer, Integer> indexCalculator;
    private final BiFunction<Integer, Integer, Integer> accumulatorCalculator;

    Operation(BiFunction<Integer, Integer, Integer> indexCalculator, BiFunction<Integer, Integer, Integer> accumulatorCalculator) {
      this.indexCalculator = indexCalculator;
      this.accumulatorCalculator = accumulatorCalculator;
    }

    public BiFunction<Integer, Integer, Integer> getIndexCalculator() {
      return indexCalculator;
    }

    public BiFunction<Integer, Integer, Integer> getAccumulatorCalculator() {
      return accumulatorCalculator;
    }
  }

}
