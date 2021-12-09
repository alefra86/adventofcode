package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution08Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(26, Solution08.partOne(FileReader.lines("2021/08-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(272, Solution08.partOne(FileReader.lines("2021/08-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(61229, Solution08.partTwo(FileReader.lines("2021/08-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(1007675, Solution08.partTwo(FileReader.lines("2021/08-input.txt")));
  }

}