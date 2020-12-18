package solutions2020;

import java.util.List;

/**
 *
 */
public class Solution01 {

  public static int partOne(List<Integer> expenseReport) {
    for (int i = 0; i < expenseReport.size(); i++) {
      for (int j = i + 1; j < expenseReport.size(); j++) {
        if (expenseReport.get(i) + expenseReport.get(j) == 2020) {
          return expenseReport.get(i) * expenseReport.get(j);
        }
      }
    }
    return 0;
  }

  public static int partTwo(List<Integer> expenseReport) {
    for (int i = 0; i < expenseReport.size(); i++) {
      for (int j = i + 1; j < expenseReport.size(); j++) {
        for (int k = j + 1; k < expenseReport.size(); k++) {
          if (expenseReport.get(i) + expenseReport.get(j) + expenseReport.get(k) == 2020) {
            return expenseReport.get(i) * expenseReport.get(j) * expenseReport.get(k);
          }
        }
      }
    }
    return 0;
  }

}
