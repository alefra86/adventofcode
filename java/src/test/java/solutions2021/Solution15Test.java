package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution15Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(40, Solution15.partOne(FileReader.lines("2021/15-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(811, Solution15.partOne(FileReader.lines("2021/15-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(315, Solution15.partTwo(FileReader.lines("2021/15-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(3012, Solution15.partTwo(FileReader.lines("2021/15-input.txt")));
  }

}