package com.coekie.aoc2024;

import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.List;
import java.util.stream.Stream;

// https://adventofcode.com/2024/day/4
public class Day4 {
  private static final List<Location> directions =
      Stream.of(-1, 0, 1).flatMap(x -> Stream.of(-1, 0, 1).map(y -> new Location(x, y)))
          .filter(loc -> loc.x() != 0 || loc.y() != 0)
          .toList();

  static long solveA(String input) {
    Grid grid = Grid.parse(input);
    return grid.locations()
        .mapToLong(start ->
            directions.stream().filter(direction -> Day4.xmas(grid, start, direction)).count())
        .sum();
  }

  private static boolean xmas(Grid grid, Location start, Location direction) {
    String xmas = "XMAS";
    Location location = start;
    for (int i = 0; i < xmas.length(); i++, location = plus(location, direction)) {
      if (!(grid.isInBounds(location) && grid.get(location) == xmas.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  private static Location plus(Location a, Location b) {
    return new Location(a.x() + b.x(), a.y() + b.y());
  }

  static long solveB(String input) {
    Grid grid = Grid.parse(input);
    return grid.locations()
        .filter(p -> grid.get(p) == 'A'
            && isMS(getOrDot(grid, p.x() - 1, p.y() - 1), getOrDot(grid, p.x() + 1, p.y() + 1))
            && isMS(getOrDot(grid, p.x() - 1, p.y() + 1), getOrDot(grid, p.x() + 1, p.y() - 1)))
        .count();
  }

  private static char getOrDot(Grid grid, int x, int y) {
    Location location = new Location(x, y);
    return grid.isInBounds(location) ? grid.get(location) : '.';
  }

  private static boolean isMS(char a, char b) {
    return (a == 'M' && b == 'S') || (a == 'S' && b == 'M');
  }
}
