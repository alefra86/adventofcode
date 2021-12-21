package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution20Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(35, Solution20.partOne(FileReader.readString("2021/20-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(5391, Solution20.partOne(FileReader.readString("2021/20-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(3351, Solution20.partTwo(FileReader.readString("2021/20-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(16383, Solution20.partTwo(FileReader.readString("2021/20-input.txt")));
  }

}