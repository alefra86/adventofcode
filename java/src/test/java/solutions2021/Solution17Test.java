package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution17Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(45, Solution17.partOne(FileReader.readString("2021/17-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(4186, Solution17.partOne(FileReader.readString("2021/17-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(112, Solution17.partTwo(FileReader.readString("2021/17-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(2709, Solution17.partTwo(FileReader.readString("2021/17-input.txt")));
  }

}