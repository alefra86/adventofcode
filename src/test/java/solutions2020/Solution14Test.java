package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution14Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(10885823581193L, Solution14.partOne(FileReader.readAlllines("2020/14-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(165, Solution14.partOne(FileReader.readAlllines("2020/14-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(3816594901962L, Solution14.partTwo(FileReader.readAlllines("2020/14-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(208, Solution14.partTwo(FileReader.readAlllines("2020/14-sample-2.txt")));
  }

}
