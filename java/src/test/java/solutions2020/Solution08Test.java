package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution08Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(1317, Solution08.partOne(FileReader.lines("2020/08-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(1033, Solution08.partTwo(FileReader.lines("2020/08-input.txt")));
  }
}