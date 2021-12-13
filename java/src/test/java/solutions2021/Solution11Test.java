package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution11Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(1656, Solution11.partOne(FileReader.readAllLines("2021/11-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(1613, Solution11.partOne(FileReader.readAllLines("2021/11-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(195, Solution11.partTwo(FileReader.readAllLines("2021/11-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(510, Solution11.partTwo(FileReader.readAllLines("2021/11-input.txt")));
  }

}