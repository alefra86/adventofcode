package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution14Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(1588, Solution14.partOne(FileReader.readString("2021/14-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(2590, Solution14.partOne(FileReader.readString("2021/14-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(2188189693529L, Solution14.partTwo(FileReader.readString("2021/14-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(2875665202438L, Solution14.partTwo(FileReader.readString("2021/14-input.txt")));
  }

}