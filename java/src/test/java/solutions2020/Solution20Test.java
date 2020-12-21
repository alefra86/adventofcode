package solutions2020;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution20Test {

  private Solution20 sut;

  @Before
  public void setUp() {
    sut = new Solution20();
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(5775714912743L, sut.partOne(FileReader.readAllLines("2020/20-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(20899048083289L, sut.partOne(FileReader.readAllLines("2020/20-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(1836, sut.partTwo(FileReader.readAllLines("2020/20-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(273, sut.partTwo(FileReader.readAllLines("2020/20-sample.txt")));
  }

}
