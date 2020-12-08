package solutions2020;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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
        return Operation.valueOf(matcher.group(1).toUpperCase()).getRetriever().apply(Integer.parseInt(matcher.group(2)));
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

  private interface Instruction {

    Integer nextAccumulator(int current);

    Integer nextIndex(int current);

    boolean canChange();

    default Instruction changeOperation() {
      return this;
    }
  }

  private static class NopInstruction implements Instruction {

    private final int argument;

    private NopInstruction(int argument) {
      this.argument = argument;
    }

    @Override
    public Integer nextAccumulator(int current) {
      return current;
    }

    @Override
    public Integer nextIndex(int current) {
      return current + 1;
    }

    @Override
    public boolean canChange() {
      return true;
    }

    @Override
    public Instruction changeOperation() {
      return new JmpInstruction(argument);
    }
  }

  private static class JmpInstruction implements Instruction {

    private final int argument;

    private JmpInstruction(int argument) {
      this.argument = argument;
    }

    @Override
    public Integer nextAccumulator(int current) {
      return current;
    }

    @Override
    public Integer nextIndex(int current) {
      return current + argument;
    }

    @Override
    public boolean canChange() {
      return true;
    }

    @Override
    public Instruction changeOperation() {
      return new NopInstruction(argument);
    }
  }

  private static class AccInstruction implements Instruction {

    private final int argument;

    private AccInstruction(int argument) {
      this.argument = argument;
    }

    @Override
    public Integer nextAccumulator(int current) {
      return current + argument;
    }

    @Override
    public Integer nextIndex(int current) {
      return current + 1;
    }

    @Override
    public boolean canChange() {
      return false;
    }
  }

  private enum Operation {
    NOP(NopInstruction::new),
    JMP(JmpInstruction::new),
    ACC(AccInstruction::new);

    private final Function<Integer, Instruction> retriever;

    Operation(Function<Integer, Instruction> retriever) {
      this.retriever = retriever;
    }

    public Function<Integer, Instruction> getRetriever() {
      return retriever;
    }
  }

}
