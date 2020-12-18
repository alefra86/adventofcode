package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution12Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(962, Solution12.partOne(FileReader.lines("2020/12-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(25, Solution12.partOne(FileReader.lines("2020/12-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(56135, Solution12.partTwo(FileReader.lines("2020/12-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(286, Solution12.partTwo(FileReader.lines("2020/12-sample.txt")));
  }

}
