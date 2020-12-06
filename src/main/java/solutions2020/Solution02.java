package solutions2020;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 *
 */
public class Solution02 {

  public static int partOne(Stream<String> lines) {
    AtomicInteger validPasswords = new AtomicInteger();
    lines.forEach(line -> {
      String[] parts = line.split(" ");
      String[] range = parts[0].split("-");
      char policyChar = parts[1].charAt(0);
      String password = parts[2];
      int counter = 0;
      for (char passwordChar : password.toCharArray()) {
        if (policyChar == passwordChar) {
          counter += 1;
        }
      }
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
