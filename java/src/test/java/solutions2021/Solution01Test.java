package solutions2021;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution01Test {

  @Test
  public void testPartOneExample() {
    List<Integer> reports = FileReader.lines("2021/01-input-example.txt").map(Integer::parseInt).collect(Collectors.toList());
    Assert.assertEquals(7, Solution01.partOne(reports));
  }

  @Test
  public void testPartOne() {
    List<Integer> reports = FileReader.lines("2021/01-input.txt").map(Integer::parseInt).collect(Collectors.toList());
    Assert.assertEquals(1529, Solution01.partOne(reports));
  }

  @Test
  public void testPartTwoExample() {
    List<Integer> reports = FileReader.lines("2021/01-input-example.txt").map(Integer::parseInt).collect(Collectors.toList());
    Assert.assertEquals(5, Solution01.partTwo(reports));
  }

  @Test
  public void testPartTwo() {
    List<Integer> reports = FileReader.lines("2021/01-input.txt").map(Integer::parseInt).collect(Collectors.toList());
    Assert.assertEquals(1567, Solution01.partTwo(reports));
  }

}