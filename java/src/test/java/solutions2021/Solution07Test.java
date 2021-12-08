package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution07Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(37, Solution07.partOne(FileReader.readString("2021/07-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(340987, Solution07.partOne(FileReader.readString("2021/07-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(168, Solution07.partTwo(FileReader.readString("2021/07-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(96987874, Solution07.partTwo(FileReader.readString("2021/07-input.txt")));
  }

}