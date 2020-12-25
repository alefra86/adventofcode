package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution24Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(375, Solution24.partOne(FileReader.readString("2020/24-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(10, Solution24.partOne(FileReader.readString("2020/24-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(3937, Solution24.partTwo(FileReader.readString("2020/24-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(2208, Solution24.partTwo(FileReader.readString("2020/24-sample.txt")));
  }

}
