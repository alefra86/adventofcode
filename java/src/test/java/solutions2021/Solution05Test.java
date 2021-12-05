package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution05Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(5, Solution05.partOne(FileReader.lines("2021/05-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(5147, Solution05.partOne(FileReader.lines("2021/05-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(12, Solution05.partTwo(FileReader.lines("2021/05-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(16925, Solution05.partTwo(FileReader.lines("2021/05-input.txt")));
  }
}