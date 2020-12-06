package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution06Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(6782, Solution06.partOne(FileReader.lines("2020/06-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(3596, Solution06.partTwo(FileReader.lines("2020/06-input.txt")));
  }
}