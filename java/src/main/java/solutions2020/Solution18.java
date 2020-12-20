package solutions2020;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import solutions2020.Solution18.DefaultPrecedenceEvaluator.PrecedenceEvaluatorBuilder;

/**
 *
 */
public class Solution18 {

  public static class ParserCalculator {

    public long partOne(List<String> lines) {
      return lines.stream().map(expression -> resolveExpression(expression.replace(" ", ""), 0).getLeft()).reduce(0L, Long::sum);
    }

    public long partTwo(List<String> lines) {
      return lines.stream()
               .map(expression -> resolveExpressionPrecedence(expression.replace(" ", ""), 0).getLeft())
               .reduce(0L, Long::sum);
    }

    private long eval(long first, char operation, long second) {
      if (operation == '+') {
        return first + second;
      } else {
        return first * second;
      }
    }

    private Pair<Long, Integer> resolveAdd(String expression, int position) {
      Pair<Long, Integer> firstTerm = getTerm(expression, position, this::resolveExpressionPrecedence);
      long result = firstTerm.getLeft();
      position = firstTerm.getRight();
      while (isNotEndExpression(expression, position) && expression.charAt(position) != '*') {
        Pair<Long, Integer> secondTerm = getTerm(expression, position + 1, this::resolveExpressionPrecedence);
        result += secondTerm.getLeft();
        position = secondTerm.getRight();
      }
      return Pair.of(result, position);
    }

    private Pair<Long, Integer> resolveMul(String expression, int position) {
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

    private Pair<Long, Integer> resolveExpressionPrecedence(String expression, int position) {
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

    private Pair<Long, Integer> getTerm(String expression, int position,
      BiFunction<String, Integer, Pair<Long, Integer>> expressionSolver) {
      if (expression.charAt(position) == '(') {
        Pair<Long, Integer> resolvedExpression = expressionSolver.apply(expression, position + 1);
        return Pair.of(resolvedExpression.getLeft(), resolvedExpression.getRight() + 1);
      } else {
        return Pair.of((long) Character.getNumericValue(expression.charAt(position)), position + 1);
      }
    }

    private Pair<Long, Integer> resolveExpression(String expression, int position) {
      Pair<Long, Integer> firstTerm = getTerm(expression, position, this::resolveExpression);
      long result = firstTerm.getLeft();
      position = firstTerm.getRight();
      while (isNotEndExpression(expression, position)) {
        Pair<Long, Integer> secondTerm = getTerm(expression, position + 1, this::resolveExpression);
        result = eval(result, expression.charAt(position), secondTerm.getLeft());
        position = secondTerm.getRight();
      }
      return Pair.of(result, position);
    }

    private boolean isNotEndExpression(String expression, Integer position) {
      return position != expression.length() && expression.charAt(position) != ')';
    }
  }

  public static class PatternMatchingCalculator {

    private static final String PARENTHESIS = "\\(([^()]+)\\)";
    private static final String SUM = "(\\d+) \\+ (\\d+)";

    public PatternMatchingCalculator() {
    }

    public long partOne(List<String> lines) {
      return lines.stream().map(expression -> resolve(expression, Function.identity())).reduce(0L, Long::sum);
    }

    public long partTwo(List<String> lines) {
      return lines.stream().map(expression -> resolve(expression, this::resolveBeforeSum)).reduce(0L, Long::sum);
    }

    private long resolve(String expression, Function<String, String> precedenceResolver) {
      Matcher matcher = Pattern.compile(PARENTHESIS).matcher(expression);
      while (matcher.find()) {
        expression = expression.replaceFirst(PARENTHESIS, String.valueOf(resolve(precedenceResolver.apply(matcher.group(1)))));
        matcher = Pattern.compile(PARENTHESIS).matcher(expression);
      }
      return resolve(precedenceResolver.apply(expression));
    }

    private long resolve(String expression) {
      return (long) new Calculator(new PrecedenceEvaluatorBuilder().withOperatorPrecedence(Operator.PLUS, 1)
                                     .withOperatorPrecedence(Operator.MULTIPLY, 1)
                                     .withOperatorPrecedence(Operator.LP, 4)
                                     .withOperatorPrecedence(Operator.RP, 4)
                                     .build()).evaluate(expression);
    }

    private String resolveBeforeSum(String expression) {
      Matcher matcher = Pattern.compile(SUM).matcher(expression);
      while (matcher.find()) {
        expression = expression.replaceFirst(SUM, Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(2)) + "");
        matcher = Pattern.compile(SUM).matcher(expression);
      }
      return expression;
    }

  }

  interface PrecedenceEvaluator extends Comparator<Operator> {

  }

  static class DefaultPrecedenceEvaluator implements PrecedenceEvaluator {

    private final Map<Operator, Integer> precedence;

    private DefaultPrecedenceEvaluator(Map<Operator, Integer> precedence) {
      this.precedence = precedence;
    }

    public static class PrecedenceEvaluatorBuilder {

      private final Map<Operator, Integer> precedence = new HashMap<>();

      public PrecedenceEvaluatorBuilder withOperatorPrecedence(Operator operator, Integer precedence) {
        this.precedence.put(operator, precedence);
        return this;
      }

      public PrecedenceEvaluator build() {
        return new DefaultPrecedenceEvaluator(precedence);
      }

    }

    @Override
    public int compare(Operator o1, Operator o2) {
      return Integer.compare(precedence.get(o1), precedence.get(o2));
    }
  }

  public static class Calculator {

    private final Stack<Double> nums;
    private final Stack<Operator> ops;
    private final PrecedenceEvaluator precedenceEvaluator;

    public Calculator() {
      this(new PrecedenceEvaluatorBuilder().withOperatorPrecedence(Operator.PLUS, 1)
             .withOperatorPrecedence(Operator.MINUS, 1)
             .withOperatorPrecedence(Operator.MULTIPLY, 2)
             .withOperatorPrecedence(Operator.DIVIDE, 2)
             .withOperatorPrecedence(Operator.POW, 3)
             .withOperatorPrecedence(Operator.LP, 4)
             .withOperatorPrecedence(Operator.RP, 4)
             .build());
    }

    public Calculator(PrecedenceEvaluator precedenceEvaluator) {
      nums = new Stack<>();
      ops = new Stack<>();
      this.precedenceEvaluator = precedenceEvaluator;
    }

    public long evaluate(List<String> lines) {
      return lines.stream().map(expression -> (long) evaluate(expression)).reduce(0L, Long::sum);
    }

    public boolean isNumber(String str) {
      try {
        Double.parseDouble(str);
      } catch (NumberFormatException | NullPointerException e) {
        return false;
      }
      return true;
    }

    public double evaluate(String str) {
      str = str.replaceAll("\\(", "( ").replaceAll("\\)", " )");
      String[] tokens = str.split(" ");

      for (String token : tokens) {
        if (isNumber(token)) {
          nums.push(Double.parseDouble(token));
        } else if (token.equals(")")) {
          while (ops.peek() != Operator.LP) {
            eval();
          }
          ops.pop();
        } else {
          Operator operator = Operator.from(token.charAt(0));
          if (ops.isEmpty()) {
            ops.push(operator);
          } else if (precedenceEvaluator.compare(operator, ops.peek()) > 0) {
            ops.push(operator);
          } else if (precedenceEvaluator.compare(operator, ops.peek()) <= 0) {
            if (ops.peek() != Operator.LP) {
              eval();
            }
            ops.push(operator);
          }
        }
      }

      while (!ops.isEmpty() && nums.size() > 1) {
        eval();
      }
      assert nums.size() == 1;
      return nums.pop();
    }

    private void eval() {
      double second = nums.pop();
      double first = nums.pop();
      nums.add(ops.pop().getOperation().apply(first, second));
    }

  }

  enum Operator {
    PLUS('+', Double::sum),
    MINUS('-', (a, b) -> a - b),
    MULTIPLY('*', (a, b) -> a * b),
    DIVIDE('/', (a, b) -> a / b),
    POW('^', Math::pow),
    LP('(', (a, b) -> {
      throw new IllegalArgumentException("Not a valid operator");
    }),
    RP(')', (a, b) -> {
      throw new IllegalArgumentException("Not a valid operator");
    });

    private static final Map<Character, Operator> symbolToOperator;

    static {
      symbolToOperator = Arrays.stream(Operator.values()).collect(Collectors.toMap(Operator::getSymbol, Function.identity()));
    }

    private final char symbol;
    private final BiFunction<Double, Double, Double> operation;

    Operator(char symbol, BiFunction<Double, Double, Double> operation) {
      this.symbol = symbol;
      this.operation = operation;
    }

    public static Operator from(char symbol) {
      return symbolToOperator.get(symbol);
    }

    public BiFunction<Double, Double, Double> getOperation() {
      return operation;
    }

    public char getSymbol() {
      return symbol;
    }
  }

}
