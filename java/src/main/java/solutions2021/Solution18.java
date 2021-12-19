package solutions2021;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Solution18 {

  public static int partOne(List<String> homeworkAssignement) {
    SnailfishNumber result = parse(homeworkAssignement.get(0));
    for (int i = 1; i < homeworkAssignement.size(); i++) {
      String line = homeworkAssignement.get(i);
      result = result.add(parse(line));
    }
    return result.getMagnitude();
  }

  public static long partTwo(List<String> homeworkAssignement) {
    List<SnailfishNumber> allNumbers = homeworkAssignement.stream().map(Solution18::parse).collect(Collectors.toList());
    int largest = -1;
    for (SnailfishNumber n1 : allNumbers) {
      for (SnailfishNumber n2 : allNumbers) {
        if (n1.equals(n2)) {
          continue;
        }
        largest = Math.max(largest, n1.clone().add(n2.clone()).getMagnitude());
        largest = Math.max(largest, n2.clone().add(n1.clone()).getMagnitude());
      }
    }
    return largest;
  }

  private static class SnailfishNumber implements Cloneable {

    private Integer value;
    private SnailfishNumber left;
    private SnailfishNumber right;
    private SnailfishNumber parent;

    public SnailfishNumber(SnailfishNumber left, SnailfishNumber right) {
      this.left = left;
      this.right = right;
      left.parent = this;
      right.parent = this;
    }

    public SnailfishNumber(int value) {
      this.value = value;
    }

    public int getMagnitude() {
      if (isDigit()) {
        return value;
      }
      return 3 * left.getMagnitude() + 2 * right.getMagnitude();
    }

    public SnailfishNumber add(SnailfishNumber toAdd) {
      SnailfishNumber sn = new SnailfishNumber(this, toAdd);
      sn.reduce();
      return sn;
    }

    private void reduce() {
      while (explode() || split()) {
        ;
      }
    }

    private boolean explode() {
      if (!isDigit()) {
        if (getLevel() == 4) {
          getNearestNumber(left, -1).ifPresent(sn -> sn.value += left.value);
          getNearestNumber(right, 1).ifPresent(sn -> sn.value += right.value);
          left = null;
          right = null;
          value = 0;
          return true;
        } else {
          return left.explode() || right.explode();
        }
      }
      return false;
    }

    private int getLevel() {
      if (parent == null) {
        return 0;
      }
      return parent.getLevel() + 1;
    }

    private Optional<SnailfishNumber> getNearestNumber(SnailfishNumber number, int delta) {
      List<SnailfishNumber> digits = getOrigin().getAllNumbers();
      return getNearestIfExist(digits, digits.indexOf(number) + delta);
    }

    private SnailfishNumber getOrigin() {
      SnailfishNumber current = this;
      while (current.parent != null) {
        current = current.parent;
      }
      return current;
    }

    private List<SnailfishNumber> getAllNumbers() {
      if (isDigit()) {
        return List.of(this);
      }
      List<SnailfishNumber> all = new ArrayList<>();
      all.addAll(left.getAllNumbers());
      all.addAll(right.getAllNumbers());
      return all;
    }

    private Optional<SnailfishNumber> getNearestIfExist(List<SnailfishNumber> list, int i) {
      return (i < 0 || i >= list.size()) ? Optional.empty() : Optional.of(list.get(i));
    }

    private boolean split() {
      if (isDigit()) {
        if (value >= 10) {
          left = new SnailfishNumber(value / 2);
          right = new SnailfishNumber((int) Math.ceil((double) value / 2));
          left.parent = this;
          right.parent = this;
          value = null;
          return true;
        }
      } else {
        return left.split() || right.split();
      }
      return false;
    }

    private boolean isDigit() {
      return value != null;
    }

    @Override
    public String toString() {
      if (isDigit()) {
        return value.toString();
      }
      return "[" + left + "," + right + "]";
    }

    @Override
    protected SnailfishNumber clone() {
      if (isDigit()) {
        return new SnailfishNumber(value);
      }
      return new SnailfishNumber(left.clone(), right.clone());
    }
  }

  private static SnailfishNumber parse(String s) {
    if (s.length() == 1) {
      return new SnailfishNumber(Integer.parseInt(s));
    }
    int level = 0;
    int splitAt = -1;
    for (int i = 0; i < s.length() && splitAt == -1; i++) {
      switch (s.charAt(i)) {
        case '[':
          level++;
          break;
        case ']':
          level--;
          break;
        case ',':
          if (level == 1) {
            splitAt = i;
          }
      }
    }
    return new SnailfishNumber(parse(s.substring(1, splitAt)), parse(s.substring(splitAt + 1, s.length() - 1)));
  }

}
