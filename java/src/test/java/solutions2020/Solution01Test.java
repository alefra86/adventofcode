package solutions2020;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution01Test {

  @Test
  public void testPartOne() {
    List<Integer> expenseReport =
      FileReader.lines("2020/01-input.txt").map(Integer::parseInt).distinct().collect(Collectors.toList());
    Assert.assertEquals(138379, Solution01.partOne(expenseReport));
  }

  @Test
  public void testPartTwo() {
    List<Integer> expenseReport =
      FileReader.lines("2020/01-input.txt").map(Integer::parseInt).distinct().collect(Collectors.toList());
    Assert.assertEquals(85491920, Solution01.partTwo(expenseReport));
  }
}