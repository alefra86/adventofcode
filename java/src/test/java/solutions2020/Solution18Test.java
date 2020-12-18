package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution18Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(6923486965641L, Solution18.partOne(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(71 + 51 + 26 + 437 + 12240 + 13632, Solution18.partOne(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartOneRec() {
    Assert.assertEquals(6923486965641L, Solution18.partOneRec(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartOneSampleRec() {
    Assert.assertEquals(71 + 51 + 26 + 437 + 12240 + 13632, Solution18.partOneRec(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(70722650566361L, Solution18.partTwo(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(231 + 51 + 46 + 1445 + 669060 + 23340, Solution18.partTwo(FileReader.readAllLines("2020/18-sample.txt")));
  }

  @Test
  public void testPartTwoRec() {
    Assert.assertEquals(70722650566361L, Solution18.partTwoRec(FileReader.readAllLines("2020/18-input.txt")));
  }

  @Test
  public void testPartTwoSampleRec() {
    Assert.assertEquals(231 + 51 + 46 + 1445 + 669060 + 23340,
      Solution18.partTwoRec(FileReader.readAllLines("2020/18-sample.txt")));
  }

}
