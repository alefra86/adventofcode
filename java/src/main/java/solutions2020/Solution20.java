package solutions2020;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 */
public class Solution20 {

  private static final char[][] MONSTER = new char[3][10];
  private static final int MONSTER_SEA_SIZE;

  static {
    MONSTER[0] = "                  # ".toCharArray();
    MONSTER[1] = "#    ##    ##    ###".toCharArray();
    MONSTER[2] = " #  #  #  #  #  #   ".toCharArray();
    MONSTER_SEA_SIZE = 15;
  }

  public long partOne(List<String> lines) {
    lines.add("");
    Map<Integer, Tile> tiles = getTiles(lines);
    fillAllPossibleEdges(tiles);
    long result = 1;
    for (Entry<Integer, Tile> first : tiles.entrySet()) {
      int adjacents = 0;
      for (Entry<Integer, Tile> second : tiles.entrySet()) {
        if (!first.getKey().equals(second.getKey())) {
          if (first.getValue().getEdges().stream().anyMatch(f -> second.getValue().getEdges().contains(f))) {
            adjacents++;
          }
        }
      }
      if (adjacents == 2) {
        result *= first.getKey();
      }
    }
    return result;
  }

  public int partTwo(List<String> lines) {
    lines.add("");
    Map<Integer, Tile> tiles = getTiles(lines);
    List<char[][]> allOrientation = MatrixUtils.getAllOrientation(composeImage(lines, getTileByPosition(tiles)));
    int monstersNumber = 0;
    char[][] finalImage = null;
    boolean found = false;
    for (int or = 0; or < allOrientation.size() && !found; or++) {
      finalImage = allOrientation.get(or);
      for (int i = 0; i < finalImage.length - MONSTER.length; i++) {
        for (int j = 0; j < finalImage[i].length - MONSTER[0].length; j++) {
          if (matchMonster(finalImage, i, j)) {
            found = true;
            monstersNumber++;
          }
        }
      }
    }
    assert finalImage != null;
    return getNotPartOfMonster(monstersNumber, finalImage);
  }

  private Map<Pair<Integer, Integer>, Tile> getTileByPosition(Map<Integer, Tile> tiles) {
    Set<Integer> used = new HashSet<>();
    Map<Pair<Integer, Integer>, Tile> tileByPosition = new HashMap<>();
    Entry<Integer, Tile> first = tiles.entrySet().iterator().next();
    tileByPosition.put(Pair.of(0, 0), new Tile(first.getKey(), first.getValue().getBody()));
    used.add(first.getKey());
    while (used.size() != tiles.size()) {
      List<Pair<Integer, Integer>> positionsToAdd = new ArrayList<>();
      List<Tile> tileToAdd = new ArrayList<>();
      for (Entry<Pair<Integer, Integer>, Tile> entry : tileByPosition.entrySet()) {
        for (Entry<Integer, Tile> tile : tiles.entrySet()) {
          if (!used.contains(tile.getKey())) {
            for (char[][] orientation : MatrixUtils.getAllOrientation(tile.getValue().getBody())) {
              if (MatrixUtils.rightBorder(entry.getValue().getBody()).equals(MatrixUtils.leftBorder(orientation))) {
                used.add(tile.getKey());
                tileToAdd.add(new Tile(tile.getKey(), orientation));
                positionsToAdd.add(Pair.of(entry.getKey().getLeft(), entry.getKey().getRight() + 1));
                break;
              }
              if (MatrixUtils.leftBorder(entry.getValue().getBody()).equals(MatrixUtils.rightBorder(orientation))) {
                used.add(tile.getKey());
                tileToAdd.add(new Tile(tile.getKey(), orientation));
                positionsToAdd.add(Pair.of(entry.getKey().getLeft(), entry.getKey().getRight() - 1));
                break;
              }
              if (MatrixUtils.topBorder(entry.getValue().getBody()).equals(MatrixUtils.bottomBorder(orientation))) {
                used.add(tile.getKey());
                tileToAdd.add(new Tile(tile.getKey(), orientation));
                positionsToAdd.add(Pair.of(entry.getKey().getLeft() - 1, entry.getKey().getRight()));
                break;
              }
              if (MatrixUtils.bottomBorder(entry.getValue().getBody()).equals(MatrixUtils.topBorder(orientation))) {
                used.add(tile.getKey());
                tileToAdd.add(new Tile(tile.getKey(), orientation));
                positionsToAdd.add(Pair.of(entry.getKey().getLeft() + 1, entry.getKey().getRight()));
                break;
              }
            }
          }
        }
      }
      for (int i = 0; i < positionsToAdd.size(); i++) {
        tileByPosition.put(positionsToAdd.get(i), tileToAdd.get(i));
      }
    }
    return tileByPosition;
  }

  private char[][] composeImage(List<String> lines, Map<Pair<Integer, Integer>, Tile> tileByPosition) {
    int tileSizeWithoutBorders = lines.get(1).length() - 2;
    int matrixSize = (int) Math.sqrt(tileByPosition.size()) * tileSizeWithoutBorders;
    char[][] image = new char[matrixSize][matrixSize];
    int minRow = tileByPosition.keySet().stream().map(Pair::getLeft).min(Comparator.comparingInt(v -> v)).get();
    int maxRow = tileByPosition.keySet().stream().map(Pair::getLeft).max(Comparator.comparingInt(v -> v)).get();
    int minCol = tileByPosition.keySet().stream().map(Pair::getRight).min(Comparator.comparingInt(v -> v)).get();
    int maxCol = tileByPosition.keySet().stream().map(Pair::getRight).max(Comparator.comparingInt(v -> v)).get();
    for (int r = minRow, i = 0; r <= maxRow; r++, i += tileSizeWithoutBorders) {
      for (int c = minCol, j = 0; c <= maxCol; c++, j += tileSizeWithoutBorders) {
        char[][] tile = MatrixUtils.removeBorders(tileByPosition.get(Pair.of(r, c)).getBody());
        for (int z = 0; z < tile.length; z++) {
          System.arraycopy(tile[z], 0, image[i + z], j, tile[z].length);
        }
      }
    }
    return image;
  }

  private int getNotPartOfMonster(int monstersNumber, char[][] finalImage) {
    int notPartOfMonster = 0;
    for (char[] row : finalImage) {
      for (char col : row) {
        if (col == '#') {
          notPartOfMonster++;
        }
      }
    }
    notPartOfMonster -= (monstersNumber * MONSTER_SEA_SIZE);
    return notPartOfMonster;
  }

  private boolean matchMonster(char[][] possibleImage, int i, int j) {
    for (int k = 0; k < MONSTER.length; k++) {
      for (int z = 0; z < MONSTER[k].length; z++) {
        if (MONSTER[k][z] == '#' && possibleImage[i + k][j + z] != '#') {
          return false;
        }
      }
    }
    return true;
  }

  private Map<Integer, Tile> getTiles(List<String> lines) {
    Map<Integer, Tile> tiles = new HashMap<>();
    int tileNumber = 0;
    int counter = 0;
    int matrixSize = lines.get(1).length();
    char[][] tile = new char[matrixSize][matrixSize];
    for (String line : lines) {
      if (line.contains(":")) {
        tileNumber = Integer.parseInt(line.split(" ")[1].replace(":", ""));
      } else if (line.isEmpty()) {
        tiles.put(tileNumber, new Tile(tileNumber, tile));
        counter = 0;
        tile = new char[matrixSize][matrixSize];
      } else {
        tile[counter] = line.toCharArray();
        counter++;
      }
    }
    return tiles;
  }

  private void fillAllPossibleEdges(Map<Integer, Tile> tiles) {
    for (Entry<Integer, Tile> entry : tiles.entrySet()) {
      char[][] value = entry.getValue().getBody();
      StringBuilder top = new StringBuilder();
      StringBuilder bottom = new StringBuilder();
      StringBuilder left = new StringBuilder();
      StringBuilder right = new StringBuilder();
      for (int i = 0; i < value.length; i++) {
        top.append(value[0][i]);
        bottom.append(value[value.length - 1][i]);
        left.append(value[i][0]);
        right.append(value[i][value.length - 1]);
      }
      List<String> allEdges = new ArrayList<>();
      allEdges.add(top.toString());
      allEdges.add(top.reverse().toString());
      allEdges.add(bottom.toString());
      allEdges.add(bottom.reverse().toString());
      allEdges.add(left.toString());
      allEdges.add(left.reverse().toString());
      allEdges.add(right.toString());
      allEdges.add(right.reverse().toString());
      entry.getValue().setEdges(allEdges);
    }
  }

  static class Tile {

    private final int id;
    private final char[][] body;
    private List<String> edges;

    Tile(int id, char[][] body) {
      this.id = id;
      this.body = body;
    }

    public void setEdges(List<String> edges) {
      this.edges = edges;
    }

    public int getId() {
      return id;
    }

    public char[][] getBody() {
      return body;
    }

    public List<String> getEdges() {
      return edges;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Tile)) {
        return false;
      }
      Tile tile = (Tile) o;
      return getId() == tile.getId();
    }

    @Override
    public int hashCode() {
      return Objects.hash(getId());
    }
  }

  static class MatrixUtils {

    public static char[][] rotate(char[][] matrix) {
      int row = matrix.length;
      char[][] result = transpose(matrix);
      for (int i = 0; i < row; i++) {
        for (int j = 0; j < row / 2; j++) {
          char temp = result[i][j];
          result[i][j] = result[i][row - 1 - j];
          result[i][row - 1 - j] = temp;
        }
      }
      return result;
    }

    public static char[][] transpose(char[][] matrix) {
      int size = matrix.length;
      char[][] result = new char[size][size];
      for (int i = 0; i < size; i++) {
        for (int j = i; j < size; j++) {
          char temp = matrix[i][j];
          result[i][j] = matrix[j][i];
          result[j][i] = temp;
        }
      }
      return result;
    }

    public static char[][] removeBorders(char[][] matrix) {
      int size = matrix.length - 2;
      char[][] result = new char[size][size];
      for (int i = 1; i < matrix.length - 1; i++) {
        System.arraycopy(matrix[i], 1, result[i - 1], 0, size);
      }
      return result;
    }

    public static List<char[][]> getAllOrientation(char[][] body) {
      List<char[][]> list = new ArrayList<>();
      list.add(body);
      list.add(MatrixUtils.transpose(body));
      char[][] rotateMatrix = MatrixUtils.rotate(body);
      list.add(rotateMatrix);
      list.add(MatrixUtils.transpose(rotateMatrix));
      rotateMatrix = MatrixUtils.rotate(rotateMatrix);
      list.add(rotateMatrix);
      list.add(MatrixUtils.transpose(rotateMatrix));
      rotateMatrix = MatrixUtils.rotate(rotateMatrix);
      list.add(rotateMatrix);
      list.add(MatrixUtils.transpose(rotateMatrix));
      return list;
    }

    public static String rightBorder(char[][] value) {
      StringBuilder right = new StringBuilder();
      for (char[] chars : value) {
        right.append(chars[value.length - 1]);
      }
      return right.toString();
    }

    public static String leftBorder(char[][] value) {
      StringBuilder left = new StringBuilder();
      for (char[] chars : value) {
        left.append(chars[0]);
      }
      return left.toString();
    }

    public static String topBorder(char[][] value) {
      return String.valueOf(value[0]);
    }

    public static String bottomBorder(char[][] value) {
      return String.valueOf(value[value.length - 1]);
    }

    public static void printMatrix(char[][] matrix) {
      for (char[] row : matrix) {
        for (char j : row) {
          System.out.print(j);
        }
        System.out.println();
      }
      System.out.println();
    }
  }
}
