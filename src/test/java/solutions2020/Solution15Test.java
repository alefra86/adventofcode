package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution15Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(1009, Solution15.partOne(FileReader.readAllLines("2020/15-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(436, Solution15.partOne(FileReader.readAllLines("2020/15-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(62714, Solution15.partTwo(FileReader.readAllLines("2020/15-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(175594, Solution15.partTwo(FileReader.readAllLines("2020/15-sample.txt")));
  }

}
