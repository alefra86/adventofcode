package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution21Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(2614, Solution21.partOne(FileReader.lines("2020/21-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(5, Solution21.partOne(FileReader.lines("2020/21-sample.txt")));
  }

  @Test
  public void testPartTwo() {
    Assert.assertEquals("qhvz,kbcpn,fzsl,mjzrj,bmj,mksmf,gptv,kgkrhg",
      Solution21.partTwo(FileReader.readAllLines("2020/21-input.txt")));
  }

  @Test
  public void testPartTwoSample() {
    Assert.assertEquals("mxmxvkd,sqjhc,fvjkl", Solution21.partTwo(FileReader.readAllLines("2020/21-sample.txt")));
  }

}
