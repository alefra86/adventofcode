package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution17Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(276, Solution17.partOne(FileReader.readAllLines("2020/17-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(112, Solution17.partOne(FileReader.readAllLines("2020/17-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(2136, Solution17.partTwo(FileReader.readAllLines("2020/17-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(848, Solution17.partTwo(FileReader.readAllLines("2020/17-sample.txt")));
  }

}
