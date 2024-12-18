package com.coekie.aoc2024;

import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparing;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// https://adventofcode.com/2024/day/18
public class Day18 {
  record Path(Location loc, int score) {
  }

  static long solveA(String input, int size, int corrupted) {
    Grid grid = new Grid(new char[size][size]);
    input.lines().limit(corrupted).forEach(line -> {
      String[] split = line.split(",");
      grid.set(parseInt(split[0]), parseInt(split[1]), '#');
    });
    Path start = new Path(new Location(0, 0), 0);
    Location destination = new Location(grid.width() - 1, grid.height() - 1);
    Map<Location, Path> bestPaths = new HashMap<>();
    PriorityQueue<Path> queue = new PriorityQueue<>(comparing(Path::score));
    queue.add(start);
    while (!queue.isEmpty()) {
      Path current = queue.remove();
      Path bestPath = bestPaths.get(current.loc);
      if (bestPath == null) {
        if (current.loc.equals(destination)) {
          return current.score;
        }
        bestPaths.put(current.loc, current);
        for (Direction dir : Direction.values()) {
          Location loc = current.loc.step(dir);
          if (grid.has(loc, '\0')) {
            queue.add(new Path(loc, current.score + 1));
          }
        }
      }
    }
    throw new RuntimeException("No path found");
  }

  static String solveB(String input, int size) {
    List<Location> corrupted = input.lines().map(line -> line.split(","))
        .map(split -> new Location(parseInt(split[0]), parseInt(split[1])))
        .toList();
    Grid grid = new Grid(new char[size][size]);
    corrupted.forEach(loc -> grid.set(loc, '#')); // mark all as corrupted
    fill(grid, new Location(0, 0)); // mark origin as reachable
    // in reverse order, one by one clear a corrupted location
    for (Location uncorrupt : corrupted.reversed()) {
      grid.set(uncorrupt, '\0');
      for (Direction dir : Direction.values()) {
        Location neighbor = uncorrupt.step(dir);
        if (grid.has(neighbor, 'O') && fill(grid, uncorrupt)) {
          return uncorrupt.x() + "," + uncorrupt.y();
        }
      }
    }
    throw new RuntimeException("No path found");
  }

  static boolean fill(Grid grid, Location start) {
    grid.set(start, 'O');
    ArrayDeque<Location> queue = new ArrayDeque<>(List.of(start));
    Location loc;
    while ((loc = queue.poll()) != null) {
      for (Direction dir : Direction.values()) {
        Location next = loc.step(dir);
        if (grid.has(next, '\0')) {
          if (next.x() == grid.width() - 1 && next.y() == grid.width() - 1) {
            return true;
          }
          grid.set(next, 'O');
          queue.add(next);
        }
      }
    }
    return false;
  }
}
