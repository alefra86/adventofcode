package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution04Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(256, Solution04.partOne(FileReader.lines("2020/04-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(198, Solution04.partTwo(FileReader.lines("2020/04-input.txt")));
  }

  @Test
  public void testInvalid() {
    Assert.assertEquals(0, Solution04.partTwo(FileReader.lines("2020/04-invalid.txt")));
  }

}