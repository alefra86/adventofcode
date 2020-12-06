package solutions2020;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;

/**
 *
 */
public class Solution02 {

  public static int partOne(Stream<String> lines) {
    AtomicInteger validPasswords = new AtomicInteger();
    lines.forEach(line -> {
      String[] parts = line.split(" ");
      String[] range = parts[0].split("-");
      int counter = StringUtils.countMatches(parts[2], String.valueOf(parts[1].charAt(0)));
      if (Integer.parseInt(range[0]) <= counter && Integer.parseInt(range[1]) >= counter) {
        validPasswords.addAndGet(1);
      }
    });
    return validPasswords.get();
  }

  public static int partTwo(Stream<String> lines) {
    AtomicInteger validPasswords = new AtomicInteger();
    lines.forEach(line -> {
      String[] parts = line.split(" ");
      String[] range = parts[0].split("-");
      char policyChar = parts[1].charAt(0);
      String password = parts[2];
      char firstChar = password.charAt(Integer.parseInt(range[0]) - 1);
      char secondChar = password.charAt(Integer.parseInt(range[1]) - 1);
      if (firstChar != secondChar && (firstChar == policyChar || secondChar == policyChar)) {
        validPasswords.addAndGet(1);
      }
    });
    return validPasswords.get();
  }

}
