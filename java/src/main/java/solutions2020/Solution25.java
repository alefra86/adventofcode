package solutions2020;

import java.math.BigInteger;

/**
 *
 */
public class Solution25 {

  private static final int SUBJECT_NUMBER = 7;
  private static final int TRANSFORMER = 20201227;

  public static int partOne(String lines) {
    String[] keys = lines.split("\n");
    return new BigInteger(keys[1]).modPow(BigInteger.valueOf(getLoopSize(Integer.parseInt(keys[0]))),
      BigInteger.valueOf(TRANSFORMER)).intValue();
  }

  private static int getLoopSize(int cardPublicKey) {
    int loopSize = 1, value = 1;
    while ((value = (value * SUBJECT_NUMBER) % TRANSFORMER) != cardPublicKey) {
      loopSize++;
    }
    return loopSize;
  }

}


