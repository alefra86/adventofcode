package solutions2020;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution19Test {

  private Solution19 sut;

  @Before
  public void setUp() {
    sut = new Solution19();
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(198, sut.partOne(FileReader.lines("2020/19-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(2, sut.partOne(FileReader.lines("2020/19-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals(372, sut.partTwo(FileReader.lines("2020/19-input2.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals(12, sut.partTwo(FileReader.lines("2020/19-sample2.txt")));
  }

}
