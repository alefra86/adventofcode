package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution03Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(198, Solution03.partOne(FileReader.readAllLines("2021/03-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(4160394, Solution03.partOne(FileReader.readAllLines("2021/03-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(230, Solution03.partTwo(FileReader.readAllLines("2021/03-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(4125600, Solution03.partTwo(FileReader.readAllLines("2021/03-input.txt")));
  }

}