package solutions2020;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Solution23 {

  public static String partOneWithArrayList(String readString) {
    List<Integer> cups = new ArrayList<>();
    for (char c : readString.replace("\n", "").toCharArray()) {
      cups.add(Character.getNumericValue(c));
    }
    for (int i = 0; i < 100; i++) {
      Integer currentCup = cups.remove(0);
      Integer firstPick = cups.remove(0);
      Integer secondPick = cups.remove(0);
      Integer thirdPick = cups.remove(0);
      int destinationCup = getNext(currentCup);
      while (isDestinationCupPickedUp(destinationCup, firstPick, secondPick, thirdPick)) {
        destinationCup = getNext(destinationCup);
      }
      cups.addAll(cups.indexOf(destinationCup) + 1, Lists.newArrayList(firstPick, secondPick, thirdPick));
      cups.add(currentCup);
    }
    int index = cups.indexOf(1);
    String result = cups.stream().map(String::valueOf).collect(Collectors.joining());
    return result.substring(index + 1) + result.substring(0, index);
  }

  private static int getNext(int currentCup) {
    currentCup--;
    return currentCup > 0 ? currentCup : 9;
  }

  public static String partOne(String readString) {
    Map<Integer, Cup> cups = play(readString, 9, 100);
    Cup current = cups.get(1).getNext();
    StringBuilder result = new StringBuilder();
    while (current.getLabel() != 1) {
      result.append(current.getLabel());
      current = current.getNext();
    }
    return result.toString();
  }

  public static long partTwo(String readString) {
    Map<Integer, Cup> cups = play(readString, 1000000, 10000000);
    Cup cupOne = cups.get(1);
    Cup first = cupOne.getNext();
    Cup second = first.getNext();
    return (long) first.getLabel() * second.getLabel();
  }

  private static Map<Integer, Cup> play(String readString, int gameSize, int iteration) {
    Map<Integer, Cup> cups = new HashMap<>();
    Cup previous = null;
    for (char c : readString.replace("\n", "").toCharArray()) {
      int label = Character.getNumericValue(c);
      Cup current = new Cup(label);
      cups.put(label, current);
      if (previous != null) {
        previous.setNext(current);
      }
      previous = current;
    }
    assert previous != null;
    for (int i = 10; i <= gameSize; i++) {
      Cup current = new Cup(i);
      previous.setNext(current);
      cups.put(i, current);
      previous = current;
    }
    Cup currentCup = cups.get(Character.getNumericValue(readString.charAt(0)));
    previous.setNext(currentCup);
    for (int i = 0; i < iteration; i++) {
      Cup firstPick = currentCup.getNext();
      Cup secondPick = firstPick.getNext();
      Cup thirdPick = secondPick.getNext();

      currentCup.setNext(thirdPick.getNext());

      int destinationLabel = getNext(currentCup.getLabel(), gameSize);
      while (isDestinationCupPickedUp(destinationLabel, firstPick.getLabel(), secondPick.getLabel(), thirdPick.getLabel())) {
        destinationLabel = getNext(destinationLabel, gameSize);
      }
      Cup destinationCup = cups.get(destinationLabel);
      thirdPick.setNext(destinationCup.getNext());
      destinationCup.setNext(firstPick);
      currentCup = currentCup.getNext();
    }
    return cups;
  }

  private static int getNext(int currentCup, int size) {
    currentCup--;
    return currentCup > 0 ? currentCup : size;
  }

  private static boolean isDestinationCupPickedUp(int destinationCup, int firstPick, int secondPick, int thirdPick) {
    return destinationCup == firstPick || destinationCup == secondPick || destinationCup == thirdPick;
  }

  private static class Cup {

    private final int label;
    private Cup next;

    public Cup(int label) {
      this.label = label;
    }

    public void setNext(Cup next) {
      this.next = next;
    }

    public Cup getNext() {
      return next;
    }

    public int getLabel() {
      return label;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Cup)) {
        return false;
      }
      Cup cup = (Cup) o;
      return getLabel() == cup.getLabel();
    }

    @Override
    public int hashCode() {
      return Objects.hash(getLabel());
    }
  }
}
