package solutions2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;

public class Solution20 {

  public static long partOne(String input) {
    return solve(input, 2);
  }

  public static long partTwo(String input) {
    return solve(input, 50);
  }

  private static long solve(String input, int times) {
    String[] parts = input.split("\n\n");
    List<String> imageEnhancementAlg =
      Arrays.stream(parts[0].split("")).map(s -> s.equals("#") ? "1" : "0").collect(Collectors.toList());
    String[] inputImageLines = parts[1].split("\n");
    Map<Point, String> pixels = new HashMap<>();
    for (int i = 0; i < inputImageLines.length; i++) {
      String[] line = inputImageLines[i].split("");
      for (int j = 0; j < line.length; j++) {
        pixels.put(Point.of(i, j), line[j].equals("#") ? "1" : "0");
      }
    }
    String externalValue = "0";
    for (int time = 1; time <= times; time++) {
      Map<Point, String> temp = new HashMap<>();
      for (int x = -time; x < inputImageLines.length + time; x++) {
        for (int y = -time; y < inputImageLines.length + time; y++) {
          temp.put(Point.of(x, y), getOutputPixel(x, y, pixels, imageEnhancementAlg, externalValue));
        }
      }
      pixels = temp;
      externalValue = imageEnhancementAlg.get(externalValue.equals("0") ? 0 : 511);
    }
    return countLight(pixels);
  }

  private static long countLight(Map<Point, String> pixels) {
    return pixels.values().stream().filter(s -> s.equals("1")).count();
  }

  private static String getOutputPixel(int inputX, int inputY, Map<Point, String> pixels, List<String> imageEnhancementAlg,
    String defaultValue) {
    StringBuilder result = new StringBuilder();
    for (int x = inputX - 1; x <= inputX + 1; x++) {
      for (int y = inputY - 1; y <= inputY + 1; y++) {
        result.append(pixels.getOrDefault(Point.of(x, y), defaultValue));
      }
    }
    return imageEnhancementAlg.get(Integer.parseInt(result.toString(), 2));
  }

  @Data(staticConstructor = "of")
  private static class Point {

    private final int x;
    private final int y;

  }
}
