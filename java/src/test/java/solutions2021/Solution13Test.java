package solutions2021;

import org.junit.Assert;
import org.junit.Test;
import utils.FileReader;

public class Solution13Test {

  @Test
  public void testPartOneExample() {
    Assert.assertEquals(17, Solution13.partOne(FileReader.readString("2021/13-input-example.txt")));
  }

  @Test
  public void testPartOne() {
    Assert.assertEquals(810, Solution13.partOne(FileReader.readString("2021/13-input.txt")));
  }

  @Test
  public void testPartTwoExample() {
    String result = String.join("\n", "#####", "#...#", "#...#", "#...#", "#####");
    Assert.assertEquals(result, Solution13.partTwo(FileReader.readString("2021/13-input-example.txt")));
  }

  @Test
  public void testPartTwo() {
    String result = String.join("\n", "#..#.#....###..#..#.###...##..####.###.", "#..#.#....#..#.#..#.#..#.#..#.#....#..#",
      "####.#....###..#..#.###..#....###..#..#", "#..#.#....#..#.#..#.#..#.#.##.#....###.",
      "#..#.#....#..#.#..#.#..#.#..#.#....#.#.", "#..#.####.###...##..###...###.#....#..#");
    Assert.assertEquals(result, Solution13.partTwo(FileReader.readString("2021/13-input.txt")));
  }

}