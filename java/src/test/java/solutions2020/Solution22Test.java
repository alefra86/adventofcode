package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution22Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(33561, Solution22.partOne(FileReader.readString("2020/22-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(306, Solution22.partOne(FileReader.readString("2020/22-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(34594, Solution22.partTwo(FileReader.readString("2020/22-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(291, Solution22.partTwo(FileReader.readString("2020/22-sample.txt")));
  }

}
