package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution18Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(4140, Solution18.partOne(FileReader.readAllLines("2021/18-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(4184, Solution18.partOne(FileReader.readAllLines("2021/18-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(3993, Solution18.partTwo(FileReader.readAllLines("2021/18-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(4731, Solution18.partTwo(FileReader.readAllLines("2021/18-input.txt")));
  }

}