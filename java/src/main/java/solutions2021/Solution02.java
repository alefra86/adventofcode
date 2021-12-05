package solutions2021;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
public class Solution02 {

  public static int partOne(Stream<String> commands) {
    PositionOne position = PositionOne.of();
    getInstructions(commands).forEach(i -> {
      i.updatePosition(position);
    });
    return position.getResultPosition();
  }

  private static List<Instruction> getInstructions(Stream<String> commands) {
    return commands.map(line -> {
      Matcher matcher = Pattern.compile("^(\\w*) (\\d)$").matcher(line);
      if (matcher.find()) {
        return new Instruction(Operations.valueOf(matcher.group(1).toUpperCase()), Integer.parseInt(matcher.group(2)));
      }
      return null;
    }).filter(Objects::nonNull).collect(Collectors.toList());
  }

  public static int partTwo(Stream<String> commands) {
    PositionTwo position = PositionTwo.of();
    getInstructions(commands).forEach(i -> {
      i.updatePosition(position);
    });
    return position.getResultPosition();
  }

  private static class Instruction {

    private final Operations operation;
    private final int argument;

    public Instruction(Operations operation, int argument) {
      this.operation = operation;
      this.argument = argument;
    }

    public void updatePosition(Position current) {
      operation.getCommand().apply(current, argument);
    }

  }

  @FunctionalInterface
  private interface Operation {

    void apply(Position position, int units);

  }

  private enum Operations {
    FORWARD((p, i) -> p.increaseHorizontal(i)),
    UP((p, i) -> p.decreaseDepth(i)),
    DOWN((p, i) -> p.increaseDepth(i));

    private final Operation command;

    Operations(Operation command) {
      this.command = command;
    }

    public Operation getCommand() {
      return command;
    }

  }

  public interface Position {

    void increaseHorizontal(int units);

    void increaseDepth(int units);

    void decreaseDepth(int units);

    int getResultPosition();
  }

  @Getter
  @RequiredArgsConstructor(staticName = "of")
  @EqualsAndHashCode
  private static class PositionOne implements Position {

    private int horizontal;
    private int depth;

    @Override
    public void increaseHorizontal(int units) {
      horizontal += units;
    }

    @Override
    public void increaseDepth(int units) {
      depth += units;
    }

    @Override
    public void decreaseDepth(int units) {
      depth -= units;
    }

    @Override
    public int getResultPosition() {
      return horizontal * depth;
    }

  }

  @RequiredArgsConstructor(staticName = "of")
  @EqualsAndHashCode(callSuper = true)
  private static class PositionTwo extends PositionOne {

    private int aim;

    @Override
    public void increaseHorizontal(int units) {
      super.increaseHorizontal(units);
      super.increaseDepth(aim * units);
    }

    @Override
    public void increaseDepth(int units) {
      aim += units;
    }

    @Override
    public void decreaseDepth(int units) {
      aim -= units;
    }

  }

}
