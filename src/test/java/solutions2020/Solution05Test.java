package solutions2020;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

/**
 *
 */
public class Solution05Test {

  @Test
  public void testPartOne() {
    List<String> seats = FileReader.lines("2020/05-input.txt").distinct().collect(Collectors.toList());
    Assert.assertEquals(974, Solution05.partOne(seats));
  }

  @Test
  public void testPartTwo() {
    List<String> seats = FileReader.lines("2020/05-input.txt").distinct().collect(Collectors.toList());
    Assert.assertEquals(646, Solution05.partTwo(seats));
  }
}