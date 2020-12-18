package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution07Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(302, Solution07.partOne(FileReader.lines("2020/07-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(4165, Solution07.partTwo(FileReader.lines("2020/07-input.txt")));
  }
}