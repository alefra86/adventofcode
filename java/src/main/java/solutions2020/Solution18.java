package solutions2020;

import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution18 {

  public static final String PARENTHESIS = "\\(([^()]+)\\)";
  public static final String SUM = "(\\d+) \\+ (\\d+)";

  enum Operator {
    PLUS(Long::sum),
    MULTIPLY((a, b) -> a * b);

    private final BiFunction<Long, Long, Long> function;

    Operator(BiFunction<Long, Long, Long> function) {
      this.function = function;
    }

    public BiFunction<Long, Long, Long> getFunction() {
      return function;
    }
  }

  public static long partOne(List<String> lines) {
    return lines.stream().map(expression -> resolve(expression, Function.identity())).reduce(0L, Long::sum);
  }

  public static long partTwo(List<String> lines) {
    return lines.stream().map(expression -> resolve(expression, Solution18::resolveBeforeSum)).reduce(0L, Long::sum);
  }

  public static long partOneRec(List<String> lines) {
    return lines.stream().map(expression -> resolveExpression(expression.replace(" ", ""), 0).getLeft()).reduce(0L, Long::sum);
  }

  public static long partTwoRec(List<String> lines) {
    return lines.stream()
             .map(expression -> resolveExpressionPrecedence(expression.replace(" ", ""), 0).getLeft())
             .reduce(0L, Long::sum);
  }

  private static long eval(long first, char operation, long second) {
    if (operation == '+') {
      return first + second;
    } else {
      return first * second;
    }
  }

  private static Pair<Long, Integer> resolveAdd(String expression, int position) {
    Pair<Long, Integer> firstTerm = getTerm(expression, position, Solution18::resolveExpressionPrecedence);
    long result = firstTerm.getLeft();
    position = firstTerm.getRight();
    while (isNotEndExpression(expression, position) && expression.charAt(position) != '*') {
      Pair<Long, Integer> secondTerm = getTerm(expression, position + 1, Solution18::resolveExpressionPrecedence);
      result += secondTerm.getLeft();
      position = secondTerm.getRight();
    }
    return Pair.of(result, position);
  }

  private static Pair<Long, Integer> resolveMul(String expression, int position) {
    Pair<Long, Integer> firstTerm = resolveAdd(expression, position);
    long result = firstTerm.getLeft();
    position = firstTerm.getRight();
    while (isNotEndExpression(expression, position)) {
      Pair<Long, Integer> secondTerm = resolveAdd(expression, position + 1);
      result *= secondTerm.getLeft();
      position = secondTerm.getRight();
    }
    return Pair.of(result, position);
  }

  private static Pair<Long, Integer> resolveExpressionPrecedence(String expression, int position) {
    Pair<Long, Integer> firstTerm = resolveMul(expression, position);
    long result = firstTerm.getLeft();
    position = firstTerm.getRight();
    while (isNotEndExpression(expression, position)) {
      Pair<Long, Integer> secondTerm = resolveMul(expression, position + 1);
      result = eval(result, expression.charAt(position), secondTerm.getLeft());
      position = secondTerm.getRight();
    }
    return Pair.of(result, position);
  }

  private static Pair<Long, Integer> getTerm(String expression, int position,
    BiFunction<String, Integer, Pair<Long, Integer>> expressionSolver) {
    if (expression.charAt(position) == '(') {
      Pair<Long, Integer> resolvedExpression = expressionSolver.apply(expression, position + 1);
      return Pair.of(resolvedExpression.getLeft(), resolvedExpression.getRight() + 1);
    } else {
      return Pair.of((long) Character.getNumericValue(expression.charAt(position)), position + 1);
    }
  }

  private static Pair<Long, Integer> resolveExpression(String expression, int position) {
    Pair<Long, Integer> firstTerm = getTerm(expression, position, Solution18::resolveExpression);
    long result = firstTerm.getLeft();
    position = firstTerm.getRight();
    while (isNotEndExpression(expression, position)) {
      Pair<Long, Integer> secondTerm = getTerm(expression, position + 1, Solution18::resolveExpression);
      result = eval(result, expression.charAt(position), secondTerm.getLeft());
      position = secondTerm.getRight();
    }
    return Pair.of(result, position);
  }

  private static boolean isNotEndExpression(String expression, Integer position) {
    return position != expression.length() && expression.charAt(position) != ')';
  }

  private static long resolve(String expression, Function<String, String> precedenceResolver) {
    Matcher matcher = Pattern.compile(PARENTHESIS).matcher(expression);
    while (matcher.find()) {
      expression = expression.replaceFirst(PARENTHESIS, String.valueOf(resolve(precedenceResolver.apply(matcher.group(1)))));
      matcher = Pattern.compile(PARENTHESIS).matcher(expression);
    }
    return resolve(precedenceResolver.apply(expression));
  }

  private static String resolveBeforeSum(String expression) {
    Matcher matcher = Pattern.compile(SUM).matcher(expression);
    while (matcher.find()) {
      expression = expression.replaceFirst(SUM, Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(2)) + "");
      matcher = Pattern.compile(SUM).matcher(expression);
    }
    return expression;
  }

  private static long resolve(String expression) {
    Stack<Long> stack = new Stack<>();
    Stack<Operator> operation = new Stack<>();
    String[] charArray = expression.split(" ");
    for (String c : charArray) {
      switch (c) {
        case "+":
          operation.push(Operator.PLUS);
          break;
        case "*":
          operation.push(Operator.MULTIPLY);
          break;
        default:
          stack.push(Long.valueOf(c));
          if (stack.size() > 1) {
            stack.push(operation.pop().getFunction().apply(stack.pop(), stack.pop()));
          }
          break;
      }
    }
    return stack.pop();
  }

}
