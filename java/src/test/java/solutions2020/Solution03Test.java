package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution03Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(257, Solution03.partOne(FileReader.lines("2020/03-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(1744787392, Solution03.partTwo(FileReader.lines("2020/03-input.txt")));
  }

}