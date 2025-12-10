package com.coekie.aoc2025;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Location;
import org.apache.commons.math3.util.Combinations;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

// https://adventofcode.com/2025/day/9
public class Day9 {
  static long solveA(String input) {
    List<Location> redTiles = parseInput(input);
    return redTiles.stream().flatMapToLong(c1 -> redTiles.stream()
            .mapToLong(c2 -> ((long) Math.abs(c1.x() - c2.x()) + 1) * (Math.abs(c1.y() - c2.y()) + 1)))
        .max().orElseThrow();
  }

  private static List<Location> parseInput(String input) {
    return input.lines()
        .map(line -> line.split(","))
        .map(split -> new Location(parseInt(split[0]), parseInt(split[1])))
        .toList();
  }

  static long solveB(String input) {
    List<Location> redTiles = parseInput(input);
    List<Deadzone> deadzones = new ArrayList<>();

    for (int i = 0; i < redTiles.size(); i++) {
      Location a = redTiles.get(i);
      Location b = redTiles.get((i + 1) % redTiles.size());
      // direction of line from a to b
      Direction dir = a.x() == b.x()
          ? (a.y() > b.y() ? Direction.N : Direction.S)
          : ((a.x() > b.x() ? Direction.W : Direction.E));

      // mark the edge along the left size of the line from a to b (excluding one tile at each end) as deadzone
      // ..b
      // .X.
      // .X.
      // ..a
      deadzones.add(new Deadzone(
          a.step(dir.left()).step(dir),
          b.step(dir.left()).step(dir, -1)));
    }

    // find max size for rectangle that does not overlap any deadzone
    long maxSize = 0;
    next:
    for (int[] combo : new Combinations(redTiles.size(), 2)) {
      Location a = redTiles.get(combo[0]);
      Location b = redTiles.get(combo[1]);
      long size = ((long) Math.abs(a.x() - b.x()) + 1) * (Math.abs(a.y() - b.y()) + 1);
      if (size > maxSize) {
        int minX = Math.min(a.x(), b.x());
        int maxX = Math.max(a.x(), b.x());
        int minY = Math.min(a.y(), b.y());
        int maxY = Math.max(a.y(), b.y());
        for (Deadzone deadzone : deadzones) {
          if (deadzone.maxX >= minX && deadzone.minX <= maxX
              && deadzone.maxY >= minY && deadzone.minY <= maxY) {
            continue next;
          }
        }
        maxSize = size;
      }
    }

    return maxSize;
  }

  record Deadzone(int minX, int maxX, int minY, int maxY) {
    public Deadzone(Location a, Location b) {
      this(Math.min(a.x(), b.x()), Math.max(a.x(), b.x()),
          Math.min(a.y(), b.y()), Math.max(a.y(), b.y()));
    }
  }
}
