package solutions2021;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution16 {

  public static long partOne(String transmission) {
    String packet = Arrays.stream(transmission.split(""))
      .map(s -> String.format("%4s", new BigInteger(s, 16).toString(2)).replace(' ', '0'))
      .collect(Collectors.joining());

    return parsePacket(packet, 0).getVersionSum();
  }

  public static long partTwo(String transmission) {
    String packet = Arrays.stream(transmission.split(""))
      .map(s -> String.format("%4s", new BigInteger(s, 16).toString(2)).replace(' ', '0'))
      .collect(Collectors.joining());

    return parsePacket(packet, 0).getPacket().getValue();
  }

  private static ParserResult parsePacket(String packet, int position) {
    int version = Integer.parseInt(packet.substring(position, position + 3), 2);
    int packetType = Integer.parseInt(packet.substring(position + 3, position + 6), 2);
    Packet p;
    if (isLiteralValue(packetType)) {
      Pair<Integer, Packet> result = parseLiteralValue(packet, position + 6, version);
      position = result.getLeft();
      p = result.getRight();
    } else {
      ParserResult result = parseOperatorPacket(packet, position + 6, version, packetType);
      position = result.getLastPosition();
      version += result.getVersionSum();
      p = result.getPacket();
    }
    return ParserResult.of(version, position, p);
  }

  private static Pair<Integer, Packet> parseLiteralValue(String packet, int startPosition, int version) {
    int endPosition = startPosition + 5;
    String part = packet.substring(startPosition, endPosition);
    StringBuilder number = new StringBuilder();
    while (part.startsWith("1")) {
      number.append(part.substring(1));
      startPosition = endPosition;
      endPosition += 5;
      part = packet.substring(startPosition, endPosition);
    }
    number.append(part.substring(1));
    return Pair.of(endPosition, new LiteralPacket(version, Long.parseLong(number.toString(), 2)));
  }

  private static ParserResult parseOperatorPacket(String packet, int startPosition, int version, int packetType) {
    OperatorPacket p = getOperatorPacket(version, packetType);
    int sumVersion = 0;
    if (Integer.parseInt(packet.substring(startPosition, startPosition + 1)) == 0) {
      int subPacketsLength = Integer.parseInt(packet.substring(startPosition + 1, startPosition + 16), 2) + startPosition + 16;
      startPosition += 16;
      while (startPosition < subPacketsLength) {
        ParserResult result = parsePacket(packet, startPosition);
        startPosition = result.getLastPosition();
        sumVersion += result.getVersionSum();
        p.addPacket(result.getPacket());
      }
    } else {
      int numberOfSubPacket = Integer.parseInt(packet.substring(startPosition + 1, startPosition + 12), 2);
      startPosition += 12;
      int num = 0;
      while (num < numberOfSubPacket) {
        ParserResult result = parsePacket(packet, startPosition);
        startPosition = result.getLastPosition();
        sumVersion += result.getVersionSum();
        num++;
        p.addPacket(result.getPacket());
      }
    }
    return ParserResult.of(sumVersion, startPosition, p);
  }

  private static OperatorPacket getOperatorPacket(int version, int packetType) {
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

  private static boolean isLiteralValue(int packetType) {
    return packetType == 4;
  }

  @Data(staticConstructor = "of")
  private static class ParserResult {

    private final int versionSum;
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

  @Data
  private static class LiteralPacket implements Packet {

    private final int version;
    private final long value;

  }

  @Data
  private static class SumPacket implements OperatorPacket {

    private final int version;
    private List<Packet> subPackets = new ArrayList<>();

    @Override
    public long getValue() {
      return subPackets.stream().mapToLong(Packet::getValue).sum();
    }

    @Override
    public void addPacket(Packet packet) {
      subPackets.add(packet);
    }
  }

  @Data
  private static class ProductPacket implements OperatorPacket {

    private final int version;
    private List<Packet> subPackets = new ArrayList<>();

    @Override
    public long getValue() {
      return subPackets.stream().mapToLong(Packet::getValue).reduce(1, (a, b) -> a * b);
    }

    @Override
    public void addPacket(Packet packet) {
      subPackets.add(packet);
    }
  }

  @Data
  private static class MinimumPacket implements OperatorPacket {

    private final int version;
    private List<Packet> subPackets = new ArrayList<>();

    @Override
    public long getValue() {
      return subPackets.stream().mapToLong(Packet::getValue).min().orElse(0);
    }

    @Override
    public void addPacket(Packet packet) {
      subPackets.add(packet);
    }
  }

  @Data
  private static class MaximumPacket implements OperatorPacket {

    private final int version;
    private List<Packet> subPackets = new ArrayList<>();

    @Override
    public long getValue() {
      return subPackets.stream().mapToLong(Packet::getValue).max().orElse(0);
    }

    @Override
    public void addPacket(Packet packet) {
      subPackets.add(packet);
    }
  }

  @Data
  private static class GreaterThanPacket implements OperatorPacket {

    private final int version;
    private Packet first;
    private Packet second;

    @Override
    public long getValue() {
      return first.getValue() > second.getValue() ? 1 : 0;
    }

    @Override
    public void addPacket(Packet packet) {
      if (first == null) {
        first = packet;
      } else {
        second = packet;
      }
    }
  }

  @Data
  private static class LessThanPacket implements OperatorPacket {

    private final int version;
    private Packet first;
    private Packet second;

    @Override
    public long getValue() {
      return first.getValue() < second.getValue() ? 1 : 0;
    }

    @Override
    public void addPacket(Packet packet) {
      if (first == null) {
        first = packet;
      } else {
        second = packet;
      }
    }
  }

  @Data
  private static class EqualToPacket implements OperatorPacket {

    private final int version;
    private Packet first;
    private Packet second;

    @Override
    public long getValue() {
      return first.getValue() == second.getValue() ? 1 : 0;
    }

    @Override
    public void addPacket(Packet packet) {
      if (first == null) {
        first = packet;
      } else {
        second = packet;
      }
    }
  }

}
