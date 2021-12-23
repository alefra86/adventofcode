package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution22Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(474140, Solution22.partOne(FileReader.readAllLines("2021/22-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(547648, Solution22.partOne(FileReader.readAllLines("2021/22-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(2758514936282235L, Solution22.partTwo(FileReader.readAllLines("2021/22-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(148747830493442L, Solution22.partTwo(FileReader.readAllLines("2021/22-input.txt")));
  }

}