package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution04Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(4512, Solution04.partOne(FileReader.readString("2021/04-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(60368, Solution04.partOne(FileReader.readString("2021/04-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(1924, Solution04.partTwo(FileReader.readString("2021/04-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(17435, Solution04.partTwo(FileReader.readString("2021/04-input.txt")));
  }

}