package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution02Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(556, Solution02.partOne(FileReader.lines("2020/02-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(605, Solution02.partTwo(FileReader.lines("2020/02-input.txt")));
  }

}