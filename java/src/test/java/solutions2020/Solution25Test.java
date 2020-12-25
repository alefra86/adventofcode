package solutions2020;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution25Test {

  @Test
  public void testPartOne() {
    Assert.assertEquals(1478097, Solution25.partOne(FileReader.readString("2020/25-input.txt")));
  }

  @Test
  public void testPartOneSample() {
    Assert.assertEquals(14897079, Solution25.partOne(FileReader.readString("2020/25-sample.txt")));
  }

}
