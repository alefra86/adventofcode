package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution16Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(20091, Solution16.partOne(FileReader.lines("2020/16-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(71, Solution16.partOne(FileReader.lines("2020/16-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(2325343130651L, Solution16.partTwo(FileReader.readAllLines("2020/16-input.txt")));
  }

}
