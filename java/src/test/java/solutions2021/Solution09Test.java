package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution09Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(15, Solution09.partOne(FileReader.readAllLines("2021/09-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(570, Solution09.partOne(FileReader.readAllLines("2021/09-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(1134, Solution09.partTwo(FileReader.readAllLines("2021/09-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(899392, Solution09.partTwo(FileReader.readAllLines("2021/09-input.txt")));
  }

}