package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution06Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(5934, Solution06.partOne(FileReader.readString("2021/06-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(352151, Solution06.partOne(FileReader.readString("2021/06-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(26984457539L, Solution06.partTwo(FileReader.readString("2021/06-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(1601616884019L, Solution06.partTwo(FileReader.readString("2021/06-input.txt")));
  }

}