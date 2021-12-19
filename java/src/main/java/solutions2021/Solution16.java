package solutions2021;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Data;

/**
 *
 */
public class Solution16 {

  public static long partOne(String transmission) {
    String packet = Arrays.stream(transmission.split(""))
      .map(s -> String.format("%4s", new BigInteger(s, 16).toString(2)).replace(' ', '0'))
      .collect(Collectors.joining());

    return new Parser(packet).parse().getVersion();
  }

  public static long partTwo(String transmission) {
    String packet = Arrays.stream(transmission.split(""))
      .map(s -> String.format("%4s", new BigInteger(s, 16).toString(2)).replace(' ', '0'))
      .collect(Collectors.joining());

    return new Parser(packet).parse().getValue();
  }

  private static boolean isLiteralValue(int packetType) {
    return packetType == 4;
  }

  private static class Parser {

    private final Deque<String> bits;

    private Parser(String packet) {
      this.bits = Arrays.stream(packet.split("")).collect(Collectors.toCollection(ArrayDeque::new));
    }

    public Packet parse() {
      int version = Integer.parseInt(getBits(3), 2);
      int packetType = Integer.parseInt(getBits(3), 2);
      Packet packet;
      if (isLiteralValue(packetType)) {
        packet = new LiteralPacket(version, parseLiteralValue());
      } else {
        packet = parseOperatorPacket(version, packetType);
      }
      return packet;
    }

    private long parseLiteralValue() {
      String part = getBits(5);
      StringBuilder number = new StringBuilder();
      while (part.startsWith("1")) {
        number.append(part.substring(1));
        part = getBits(5);
      }
      number.append(part.substring(1));
      return Long.parseLong(number.toString(), 2);
    }

    private String getBits(int number) {
      return IntStream.range(0, number).mapToObj(i -> bits.pop()).collect(Collectors.joining());
    }

    private Packet parseOperatorPacket(int version, int packetType) {
      OperatorPacket packet = getOperatorPacket(version, packetType);
      if (Integer.parseInt(getBits(1)) == 0) {
        int subPacketsLength = Integer.parseInt(getBits(15), 2);
        int end = bits.size() - subPacketsLength;
        while (bits.size() > end) {
          packet.addPacket(parse());
        }
      } else {
        int numberOfSubPacket = Integer.parseInt(getBits(11), 2);
        int num = 0;
        while (num < numberOfSubPacket) {
          packet.addPacket(parse());
          num++;
        }
      }
      return packet;
    }

    private OperatorPacket getOperatorPacket(int version, int packetType) {
      OperatorPacket p = null;
      switch (packetType) {
        case 0:
          p = new SumPacket(version);
          break;
        case 1:
          p = new ProductPacket(version);
          break;
        case 2:
          p = new MinimumPacket(version);
          break;
        case 3:
          p = new MaximumPacket(version);
          break;
        case 5:
          p = new GreaterThanPacket(version);
          break;
        case 6:
          p = new LessThanPacket(version);
          break;
        case 7:
          p = new EqualToPacket(version);
          break;
      }
      return p;
    }

  }

  @Data(staticConstructor = "of")
  private static class ParserResult {

    private final int lastPosition;
    private final Packet packet;

  }

  private interface Packet {

    int getVersion();

    long getValue();
  }

  private interface OperatorPacket extends Packet {

    void addPacket(Packet packet);

  }

  private abstract static class MultiOperatorPacket implements OperatorPacket {

    private final int version;
    private final List<Packet> subPackets = new ArrayList<>();

    protected MultiOperatorPacket(int version) {
      this.version = version;
    }

    @Override
    public void addPacket(Packet packet) {
      subPackets.add(packet);
    }

    @Override
    public long getValue() {
      return getValue(subPackets);
    }

    @Override
    public int getVersion() {
      return version + subPackets.stream().mapToInt(Packet::getVersion).reduce(0, Integer::sum);
    }

    protected abstract long getValue(List<Packet> subPackets);
  }

  private abstract static class TwoOperatorPacket implements OperatorPacket {

    private final int version;
    private Packet first;
    private Packet second;

    protected TwoOperatorPacket(int version) {
      this.version = version;
    }

    @Override
    public void addPacket(Packet packet) {
      if (first == null) {
        first = packet;
      } else {
        second = packet;
      }
    }

    @Override
    public long getValue() {
      return getValue(first, second);
    }

    protected abstract long getValue(Packet first, Packet second);

    @Override
    public int getVersion() {
      return version + first.getVersion() + second.getVersion();
    }
  }

  @Data
  private static class LiteralPacket implements Packet {

    private final int version;
    private final long value;

  }

  private static class SumPacket extends MultiOperatorPacket {

    protected SumPacket(int version) {
      super(version);
    }

    @Override
    public long getValue(List<Packet> subPackets) {
      return subPackets.stream().mapToLong(Packet::getValue).sum();
    }

  }

  private static class ProductPacket extends MultiOperatorPacket {

    protected ProductPacket(int version) {
      super(version);
    }

    @Override
    public long getValue(List<Packet> subPackets) {
      return subPackets.stream().mapToLong(Packet::getValue).reduce(1, (a, b) -> a * b);
    }

  }

  private static class MinimumPacket extends MultiOperatorPacket {

    protected MinimumPacket(int version) {
      super(version);
    }

    @Override
    public long getValue(List<Packet> subPackets) {
      return subPackets.stream().mapToLong(Packet::getValue).min().orElse(0);
    }

  }

  private static class MaximumPacket extends MultiOperatorPacket {

    protected MaximumPacket(int version) {
      super(version);
    }

    @Override
    public long getValue(List<Packet> subPackets) {
      return subPackets.stream().mapToLong(Packet::getValue).max().orElse(0);
    }

  }

  private static class GreaterThanPacket extends TwoOperatorPacket {

    private GreaterThanPacket(int version) {
      super(version);
    }

    @Override
    public long getValue(Packet first, Packet second) {
      return first.getValue() > second.getValue() ? 1 : 0;
    }

  }

  private static class LessThanPacket extends TwoOperatorPacket {

    protected LessThanPacket(int version) {
      super(version);
    }

    @Override
    public long getValue(Packet first, Packet second) {
      return first.getValue() < second.getValue() ? 1 : 0;
    }

  }

  private static class EqualToPacket extends TwoOperatorPacket {

    protected EqualToPacket(int version) {
      super(version);
    }

    @Override
    public long getValue(Packet first, Packet second) {
      return first.getValue() == second.getValue() ? 1 : 0;
    }

  }

}
