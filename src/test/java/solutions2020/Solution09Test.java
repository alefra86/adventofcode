package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution09Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(20874512, Solution09.partOne(FileReader.lines("2020/09-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(3012420, Solution09.partTwo(FileReader.lines("2020/09-input.txt")));
  }
}