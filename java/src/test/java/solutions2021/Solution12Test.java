package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution12Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(19, Solution12.partOne(FileReader.readAllLines("2021/12-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(4720, Solution12.partOne(FileReader.readAllLines("2021/12-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(103, Solution12.partTwo(FileReader.readAllLines("2021/12-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(147848, Solution12.partTwo(FileReader.readAllLines("2021/12-input.txt")));
  }

}