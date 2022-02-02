package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution23Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(12521, Solution23.partOne(FileReader.readAllLines("2021/23-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(14627, Solution23.partOne(FileReader.readAllLines("2021/23-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(44169, Solution23.partTwo(FileReader.readAllLines("2021/23-input-example-2.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(41591, Solution23.partTwo(FileReader.readAllLines("2021/23-input-2.txt")));
  }

}