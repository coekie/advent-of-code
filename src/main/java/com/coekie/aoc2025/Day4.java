package com.coekie.aoc2025;

import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

public class Day4 {
  static long solveA(String input) {
    Grid grid = Grid.parse(input);
    return grid.locations()
        .filter(loc -> grid.has(loc, '@') && countNeighbors(grid, loc) < 4)
        .count();
  }

  private static int countNeighbors(Grid grid, Location loc) {
    return (int) eightNeighbors(loc).filter(neighbor -> grid.has(neighbor, '@')).count();
  }

  static Stream<Location> eightNeighbors(Location loc) {
    return Stream.of(
        new Location(loc.x() - 1, loc.y() - 1),
        new Location(loc.x(), loc.y() - 1),
        new Location(loc.x() + 1, loc.y() - 1),
        new Location(loc.x() - 1, loc.y()),
        new Location(loc.x() + 1, loc.y()),
        new Location(loc.x() - 1, loc.y() + 1),
        new Location(loc.x(), loc.y() + 1),
        new Location(loc.x() + 1, loc.y() + 1));
  }

  static int solveB(String input) {
    Deque<Location> toRemove = new ArrayDeque<>();
    Grid grid = Grid.parse(input);
    int[][] neighbors = new int[grid.height()][grid.width()];
    grid.locations().forEach(loc -> {
      int count = grid.has(loc, '@') ? countNeighbors(grid, loc) : Integer.MAX_VALUE;
      neighbors[loc.y()][loc.x()] = count;
      if (count < 4) {
        toRemove.add(loc);
      }
    });

    int result = 0;
    Location removing;
    while ((removing = toRemove.pollFirst()) != null) {
      result++;
      eightNeighbors(removing).filter(grid::isInBounds).forEach(n -> {
        if (--neighbors[n.y()][n.x()] == 3) {
          toRemove.add(n);
        }
      });
    }
    return result;
  }
}
