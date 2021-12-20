package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution19Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(79, Solution19.partOne(FileReader.readAllLines("2021/19-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(320, Solution19.partOne(FileReader.readAllLines("2021/19-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(3621, Solution19.partTwo(FileReader.readAllLines("2021/19-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(9655, Solution19.partTwo(FileReader.readAllLines("2021/19-input.txt")));
  }

}