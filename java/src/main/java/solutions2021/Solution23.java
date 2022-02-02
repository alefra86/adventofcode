package solutions2021;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class Solution23 {

  private final static Map<Integer, List<Integer>> ROOM_DISTANCE = new HashMap<>();
  private final static Map<State, Long> costToTheEnd = new HashMap<>();

  static {
    ROOM_DISTANCE.put(0, Lists.newArrayList(2, 1, 1, 3, 5, 7, 8));
    ROOM_DISTANCE.put(1, Lists.newArrayList(4, 3, 1, 1, 3, 5, 6));
    ROOM_DISTANCE.put(2, Lists.newArrayList(6, 5, 3, 1, 1, 3, 4));
    ROOM_DISTANCE.put(3, Lists.newArrayList(8, 7, 5, 3, 1, 1, 2));
  }

  public static long partOne(List<String> diagram) {
    int[] first = diagram.get(2).trim().replace("#", "").chars().map(c -> c - 65).toArray();
    int[] second = diagram.get(3).trim().replace("#", "").chars().map(c -> c - 65).toArray();
    Room[] rooms =
      IntStream.range(0, 4).boxed().map(i -> Room.of(i, 2, Lists.newArrayList(first[i], second[i]))).toArray(Room[]::new);
    int[] hallwaySpots = new int[7];
    Arrays.fill(hallwaySpots, -1);
    return solve(new State(hallwaySpots, rooms, 0), 2);
  }

  public static long partTwo(List<String> diagram) {
    int[] first = diagram.get(2).trim().replace("#", "").chars().map(c -> c - 65).toArray();
    int[] second = diagram.get(3).trim().replace("#", "").chars().map(c -> c - 65).toArray();
    int[] third = diagram.get(4).trim().replace("#", "").chars().map(c -> c - 65).toArray();
    int[] fourth = diagram.get(5).trim().replace("#", "").chars().map(c -> c - 65).toArray();
    Room[] rooms = IntStream.range(0, 4)
      .boxed()
      .map(i -> Room.of(i, 4, Lists.newArrayList(first[i], second[i], third[i], fourth[i])))
      .toArray(Room[]::new);
    int[] hallwaySpots = new int[7];
    Arrays.fill(hallwaySpots, -1);
    return solve(new State(hallwaySpots, rooms, 0), 4);
  }

  private static long solve(State state, int roomSize) {
    if (state.isCompleted()) {
      return 0L;
    }
    long result = Integer.MAX_VALUE;
    for (State nextState : getNextStates(state, roomSize)) {
      long costToTheEndNextState;
      if (costToTheEnd.containsKey(nextState)) {
        costToTheEndNextState = costToTheEnd.get(nextState);
      } else {
        costToTheEndNextState = solve(nextState, roomSize);
        costToTheEnd.put(nextState, costToTheEndNextState);
      }
      result = Math.min(nextState.getMoveCost() + costToTheEndNextState, result);
    }
    return result;
  }

  private static List<State> getNextStates(State state, int roomSize) {
    List<State> result = getNextStatesMovingToRoom(state, roomSize);
    result.addAll(getNextStatesMovingToHallway(state, roomSize));
    return result;
  }

  private static List<State> getNextStatesMovingToHallway(State state, int roomSize) {
    List<State> result = new ArrayList<>();
    Room[] rooms = state.getRooms();
    int[] hallwaySpots = state.getHallwaySpots();
    for (Room room : rooms) {
      if (!room.canMoveOut()) {
        continue;
      }
      room.getNextAmphipod().ifPresent(toMove -> {
        IntStream.range(0, hallwaySpots.length).forEach(hallwayIndex -> {
          getMovesCost(state, room.getRoomIndex(), hallwayIndex, false, roomSize).ifPresent(cost -> {
            result.add(state.fromRoomToHallway(room.getRoomIndex(), hallwayIndex, cost));
          });
        });
      });
    }
    return result;
  }

  private static List<State> getNextStatesMovingToRoom(State state, int roomSize) {
    List<State> result = new ArrayList<>();
    Room[] rooms = state.getRooms();
    int[] hallwaySpots = state.getHallwaySpots();
    IntStream.range(0, hallwaySpots.length).forEach(hallwayIndex -> {
      int amphipod = hallwaySpots[hallwayIndex];
      if (amphipod != -1) {
        for (Room room : rooms) {
          if (room.canMoveIn(amphipod)) {
            getMovesCost(state, room.getRoomIndex(), hallwayIndex, true, roomSize).ifPresent(cost -> {
              result.add(state.fromHallwayToRoom(hallwayIndex, room.getRoomIndex(), cost));
            });
          }
        }
      }
    });
    return result;
  }

  private static Optional<Long> getMovesCost(State state, int roomIndex, int hallwayIndex, boolean toRoom, int roomSize) {
    int start, end;
    if (roomIndex + 1 < hallwayIndex) {
      start = roomIndex + 2;
      end = hallwayIndex + (toRoom ? 0 : 1);
    } else {
      start = hallwayIndex + (toRoom ? 1 : 0);
      end = roomIndex + 2;
    }
    int[] hallwaySpots = state.getHallwaySpots();
    for (; start < end; start++) {
      if (hallwaySpots[start] != -1) {
        return Optional.empty();
      }
    }
    int obj = toRoom ? hallwaySpots[hallwayIndex] : state.getRooms()[roomIndex].getNextAmphipod().get();
    return Optional.of((long) (Math.pow(10, obj) * getPathLength(state, roomIndex, hallwayIndex, toRoom, roomSize)));
  }

  private static int getPathLength(State state, int roomIndex, int hallwayIndex, boolean toRoom, int roomSize) {
    return ROOM_DISTANCE.get(roomIndex).get(hallwayIndex) + getRoomSteps(state, roomIndex, toRoom, roomSize);
  }

  private static int getRoomSteps(State state, int roomIndex, boolean toRoom, int roomSize) {
    return roomSize + (toRoom ? 0 : 1) - state.getRooms()[roomIndex].getSize();
  }

  @Data
  @EqualsAndHashCode(exclude = {"moveCost"})
  static class State {

    private final int[] hallwaySpots;
    private final Room[] rooms;
    private final long moveCost;

    public State fromRoomToHallway(int roomIndex, int hallwayIndex, long cost) {
      int[] newHallwaySpots = Arrays.stream(hallwaySpots).toArray();
      Room[] newRooms = Arrays.stream(rooms).map(Room::clone).toArray(Room[]::new);
      newHallwaySpots[hallwayIndex] = newRooms[roomIndex].getNextAmphipod().get();
      newRooms[roomIndex] = newRooms[roomIndex].removeElement();
      return new State(newHallwaySpots, newRooms, cost);
    }

    public State fromHallwayToRoom(int hallwayIndex, int roomIndex, long cost) {
      int[] newHallwaySpots = Arrays.stream(hallwaySpots).toArray();
      Room[] newRooms = Arrays.stream(rooms).map(Room::clone).toArray(Room[]::new);
      newRooms[roomIndex] = newRooms[roomIndex].addElement(newHallwaySpots[hallwayIndex]);
      newHallwaySpots[hallwayIndex] = -1;
      return new State(newHallwaySpots, newRooms, cost);
    }

    public boolean isCompleted() {
      return Arrays.stream(rooms).allMatch(Room::isCompleted);
    }

    public long getMoveCost() {
      return moveCost;
    }

  }

  @Data(staticConstructor = "of")
  static class Room implements Cloneable {

    private final int roomIndex;
    private final int roomSize;
    private final List<Integer> amphipods;

    public Room removeElement() {
      List<Integer> a = new ArrayList<>(amphipods);
      a.remove(0);
      return Room.of(roomIndex, roomSize, a);
    }

    public Room addElement(int amphipod) {
      List<Integer> a = new ArrayList<>(amphipods);
      a.add(0, amphipod);
      return Room.of(roomIndex, roomSize, a);
    }

    public boolean canMoveOut() {
      return amphipods.size() > 0 && amphipods.stream().anyMatch(a -> a != roomIndex);
    }

    public boolean isCompleted() {
      return amphipods.size() == roomSize && amphipods.stream().allMatch(a -> a == roomIndex);
    }

    public Optional<Integer> getNextAmphipod() {
      if (amphipods.size() == 0) {
        return Optional.empty();
      }
      return Optional.of(amphipods.get(0));
    }

    public int getSize() {
      return amphipods.size();
    }

    public boolean canMoveIn(int amphipod) {
      return roomIndex == amphipod && amphipods.stream().allMatch(a -> a == amphipod);
    }

    @Override
    public Room clone() {
      return Room.of(roomIndex, roomSize, new ArrayList<>(amphipods));
    }
  }
}

