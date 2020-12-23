package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution23Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals("32658947", Solution23.partOne(FileReader.readString("2020/23-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals("67384529", Solution23.partOne(FileReader.readString("2020/23-sample.txt")));
  }

  @Test
  public void testPartOneList() {
    Assert.assertEquals("32658947", Solution23.partOneWithArrayList(FileReader.readString("2020/23-input.txt")));
  }

  @Test
  public void testPartOneSampleList() {
    Assert.assertEquals("67384529", Solution23.partOneWithArrayList(FileReader.readString("2020/23-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(683486010900L, Solution23.partTwo(FileReader.readString("2020/23-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(149245887792L, Solution23.partTwo(FileReader.readString("2020/23-sample.txt")));
  }

}
