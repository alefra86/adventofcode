package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution16Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(31, Solution16.partOne(FileReader.readString("2021/16-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(955, Solution16.partOne(FileReader.readString("2021/16-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(54, Solution16.partTwo(FileReader.readString("2021/16-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(158135423448L, Solution16.partTwo(FileReader.readString("2021/16-input.txt")));
  }

}