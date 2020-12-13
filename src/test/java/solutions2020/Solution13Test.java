package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution13Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(2238, Solution13.partOne(FileReader.readAlllines("2020/13-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(295, Solution13.partOne(FileReader.readAlllines("2020/13-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(560214575859998L, Solution13.partTwo(FileReader.readAlllines("2020/13-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(1068781, Solution13.partTwo(FileReader.readAlllines("2020/13-sample.txt")));
  }

}
