package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import solutions2020.Solution18.Calculator;
import solutions2020.Solution18.DefaultPrecedenceEvaluator.PrecedenceEvaluatorBuilder;
import solutions2020.Solution18.Operator;
import solutions2020.Solution18.ParserCalculator;
import solutions2020.Solution18.PatternMatchingCalculator;
import solutions2020.Solution18.PrecedenceEvaluator;
import utils.FileReader;

/**
 *
 */
public class Solution18Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(6923486965641L, new PatternMatchingCalculator().partOne(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(71 + 51 + 26 + 437 + 12240 + 13632,
      new PatternMatchingCalculator().partOne(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(70722650566361L, new PatternMatchingCalculator().partTwo(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(231 + 51 + 46 + 1445 + 669060 + 23340,
      new PatternMatchingCalculator().partTwo(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartOneRec() {
    Assert.assertEquals(6923486965641L, new ParserCalculator().partOne(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartOneSampleRec() {
    Assert.assertEquals(71 + 51 + 26 + 437 + 12240 + 13632,
      new ParserCalculator().partOne(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartTwoRec() {
    Assert.assertEquals(70722650566361L, new ParserCalculator().partTwo(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartTwoSampleRec() {
    Assert.assertEquals(231 + 51 + 46 + 1445 + 669060 + 23340,
      new ParserCalculator().partTwo(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartOneCalculator() {
    Assert.assertEquals(6923486965641L,
      new Calculator(getSamePrecedenceEvaluator()).evaluate(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartOneSampleCalculator() {
    Assert.assertEquals(71 + 51 + 26 + 437 + 12240 + 13632,
      new Calculator(getSamePrecedenceEvaluator()).evaluate(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartTwoSampleCalculator() {
    Assert.assertEquals(231 + 51 + 46 + 1445 + 669060 + 23340,
      new Calculator(getInversePrecedenceEvaluator()).evaluate(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartTwoCalculator() {
    Assert.assertEquals(70722650566361L,
      new Calculator(getInversePrecedenceEvaluator()).evaluate(FileReader.readAllLines("2020/18-input.txt")));
  }

  private PrecedenceEvaluator getSamePrecedenceEvaluator() {
    return new PrecedenceEvaluatorBuilder().withOperatorPrecedence(Operator.PLUS, 1)
             .withOperatorPrecedence(Operator.MULTIPLY, 1)
             .withOperatorPrecedence(Operator.LP, 4)
             .withOperatorPrecedence(Operator.RP, 4)
             .build();
  }

  private PrecedenceEvaluator getInversePrecedenceEvaluator() {
    return new PrecedenceEvaluatorBuilder().withOperatorPrecedence(Operator.PLUS, 2)
             .withOperatorPrecedence(Operator.MULTIPLY, 1)
             .withOperatorPrecedence(Operator.LP, 4)
             .withOperatorPrecedence(Operator.RP, 4)
             .build();
  }

}
