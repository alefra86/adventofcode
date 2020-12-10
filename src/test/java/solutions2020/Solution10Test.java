package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution10Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(2277, Solution10.partOne(FileReader.lines("2020/10-input.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(37024595836928L, Solution10.partTwo(FileReader.lines("2020/10-input.txt")));
  }

  @Test
  public void testInputMid() {
    Assert.assertEquals(19208, Solution10.partTwo(FileReader.lines("2020/10-input-mid.txt")));
  }

  @Test
  public void testInputLittle() {
    Assert.assertEquals(8, Solution10.partTwo(FileReader.lines("2020/10-input-little.txt")));
  }

}
