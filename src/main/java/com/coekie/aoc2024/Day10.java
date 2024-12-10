package com.coekie.aoc2024;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

// https://adventofcode.com/2024/day/10
public class Day10 {

  @SuppressWarnings("unchecked") // generic array
  static long solveA(String input) {
    Grid grid = Grid.parse(input);
    Set<Location>[][] nines = new Set[grid.height()][grid.width()];
    return grid.locations()
        .filter(s -> grid.get(s) == '0')
        .mapToLong(s -> nines(grid, nines, s).size())
        .sum();
  }

  static Set<Location> nines(Grid grid, Set<Location>[][] nines, Location location) {
    char v = grid.get(location);
    if (v == '9') return Set.of(location);
    if (nines[location.y()][location.x()] == null) {
      nines[location.y()][location.x()] = Stream.of(Direction.values()).map(location::step)
          .filter(loc -> grid.isInBounds(loc) && grid.get(loc) == v + 1)
          .map(loc -> nines(grid, nines, loc))
          .filter(s -> !s.isEmpty()) // optimization
          .reduce((a, b) -> Set.copyOf(Sets.union(a, b)))
          .orElse(Set.of());
    }
    return nines[location.y()][location.x()];
  }

  static long solveB(String input) {
    Grid grid = Grid.parse(input);
    long[][] paths = new long[grid.height()][grid.width()];
    Stream.of(paths).forEach(p -> Arrays.fill(p, -1));
    return grid.locations()
        .filter(s -> grid.get(s) == '0')
        .mapToLong(s -> paths(grid, paths, s))
        .sum();
  }

  static long paths(Grid grid, long[][] paths, Location location) {
    char v = grid.get(location);
    if (v == '9') return 1;
    if (paths[location.y()][location.x()] == -1) {
      paths[location.y()][location.x()] = Stream.of(Direction.values()).map(location::step)
          .filter(loc -> grid.isInBounds(loc) && grid.get(loc) == v + 1)
          .mapToLong(loc -> paths(grid, paths, loc))
          .sum();
    }
    return paths[location.y()][location.x()];
  }
}
