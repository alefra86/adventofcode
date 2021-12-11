package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution10Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(26397, Solution10.partOne(FileReader.lines("2021/10-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(323613, Solution10.partOne(FileReader.lines("2021/10-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(288957, Solution10.partTwo(FileReader.lines("2021/10-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(3103006161L, Solution10.partTwo(FileReader.lines("2021/10-input.txt")));
  }

}