package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution02Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(150, Solution02.partOne(FileReader.lines("2021/02-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(2073315, Solution02.partOne(FileReader.lines("2021/02-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(900, Solution02.partTwo(FileReader.lines("2021/02-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(1840311528, Solution02.partTwo(FileReader.lines("2021/02-input.txt")));
  }

}