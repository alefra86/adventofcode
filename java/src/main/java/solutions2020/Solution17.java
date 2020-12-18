package solutions2020;

import com.google.common.collect.Lists;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 *
 */
public class Solution17 {

  public static final int MAX_CYCLES = 6;

  public static int partOne(List<String> lines) {
    Set<Cube> active = getInitialActiveCubes(lines, (x, y) -> new ThreeDimHyperCube(x, y, 0));
    for (int cycles = 1; cycles <= MAX_CYCLES; cycles++) {
      int size = lines.size() + cycles;
      int finalCycles = cycles;
      Set<Cube> newActive = new HashSet<>();
      IntStream.rangeClosed(-size, size).forEach(x -> {
        IntStream.rangeClosed(-size, size).forEach(y -> {
          IntStream.rangeClosed(-finalCycles, finalCycles).forEach(z -> {
            AtomicInteger activeNeighbors = new AtomicInteger();
            IntStream.rangeClosed(-1, 1).forEach(dx -> {
              IntStream.rangeClosed(-1, 1).forEach(dy -> {
                IntStream.rangeClosed(-1, 1).forEach(dz -> {
                  if (dx != 0 || dy != 0 || dz != 0) {
                    if (active.contains(new ThreeDimHyperCube(x + dx, y + dy, z + dz))) {
                      activeNeighbors.getAndIncrement();
                    }
                  }
                });
              });
            });
            getActive(() -> new ThreeDimHyperCube(x, y, z), active, activeNeighbors).ifPresent(newActive::add);

          });
        });
      });
      active.clear();
      active.addAll(newActive);
    }
    return active.size();
  }

  private static Set<Cube> getInitialActiveCubes(List<String> lines, BiFunction<Integer, Integer, Cube> supplier) {
    Set<Cube> active = new HashSet<>();
    for (int x = 0, linesSize = lines.size(); x < linesSize; x++) {
      String line = lines.get(x);
      for (int y = 0; y < line.toCharArray().length; y++) {
        if (line.charAt(y) == '#') {
          active.add(supplier.apply(x, y));
        }
      }
    }
    return active;
  }

  public static int partTwo(List<String> lines) {
    Set<Cube> active = getInitialActiveCubes(lines, (x, y) -> new FourDimHyperCube(x, y, 0, 0));
    for (int cycles = 1; cycles <= MAX_CYCLES; cycles++) {
      int maxSize = lines.size() + cycles;
      int finalCycles = cycles;
      Set<Cube> newActive = new HashSet<>();
      IntStream.rangeClosed(-maxSize, maxSize).forEach(x -> {
        IntStream.rangeClosed(-maxSize, maxSize).forEach(y -> {
          IntStream.rangeClosed(-finalCycles, finalCycles).forEach(z -> {
            IntStream.rangeClosed(-finalCycles, finalCycles).forEach(w -> {
              AtomicInteger activeNeighbors = new AtomicInteger();
              IntStream.rangeClosed(-1, 1).forEach(dx -> {
                IntStream.rangeClosed(-1, 1).forEach(dy -> {
                  IntStream.rangeClosed(-1, 1).forEach(dz -> {
                    IntStream.rangeClosed(-1, 1).forEach(dw -> {
                      if (dx != 0 || dy != 0 || dz != 0 || dw != 0) {
                        if (active.contains(new FourDimHyperCube(x + dx, y + dy, z + dz, w + dw))) {
                          activeNeighbors.getAndIncrement();
                        }
                      }
                    });
                  });
                });
              });
              getActive(() -> new FourDimHyperCube(x, y, z, w), active, activeNeighbors).ifPresent(newActive::add);
            });
          });
        });
      });
      active.clear();
      active.addAll(newActive);
    }
    return active.size();
  }

  private static Optional<Cube> getActive(Supplier<Cube> cubeSupplier, Set<Cube> active, AtomicInteger activeNeighbors) {
    Cube cube = cubeSupplier.get();
    if (active.contains(cube)) {
      if (Lists.newArrayList(2, 3).contains(activeNeighbors.get())) {
        return Optional.of(cube);
      }
    } else {
      if (activeNeighbors.get() == 3) {
        return Optional.of(cube);
      }
    }
    return Optional.empty();
  }

  private interface Cube {

  }

  private static class ThreeDimHyperCube implements Cube {

    private final int x;
    private final int y;
    private final int z;

    private ThreeDimHyperCube(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof ThreeDimHyperCube)) {
        return false;
      }
      ThreeDimHyperCube cube = (ThreeDimHyperCube) o;
      return x == cube.x && y == cube.y && z == cube.z;
    }

    @Override
    public int hashCode() {
      return Objects.hash(x, y, z);
    }
  }

  private static class FourDimHyperCube extends ThreeDimHyperCube implements Cube {

    private final int w;

    private FourDimHyperCube(int x, int y, int z, int w) {
      super(x, y, z);
      this.w = w;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof FourDimHyperCube)) {
        return false;
      }
      if (!super.equals(o)) {
        return false;
      }
      FourDimHyperCube hyperCube = (FourDimHyperCube) o;
      return w == hyperCube.w;
    }

    @Override
    public int hashCode() {
      return Objects.hash(super.hashCode(), w);
    }
  }
}
