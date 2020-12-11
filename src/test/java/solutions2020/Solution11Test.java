package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution11Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(2324, Solution11.partOne(FileReader.lines("2020/11-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(37, Solution11.partOne(FileReader.lines("2020/11-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(2068, Solution11.partTwo(FileReader.lines("2020/11-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(26, Solution11.partTwo(FileReader.lines("2020/11-sample.txt")));
  }

}
