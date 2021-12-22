package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution21Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(739785, Solution21.partOne(FileReader.readAllLines("2021/21-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(1073709, Solution21.partOne(FileReader.readAllLines("2021/21-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    Assert.assertEquals(444356092776315L, Solution21.partTwo(FileReader.readAllLines("2021/21-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(148747830493442L, Solution21.partTwo(FileReader.readAllLines("2021/21-input.txt")));
  }

}