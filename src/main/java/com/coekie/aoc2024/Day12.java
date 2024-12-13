package com.coekie.aoc2024;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

// https://adventofcode.com/2024/day/12
public class Day12 {
  static long solveA(String input) {
    return solve(input, true);
  }

  static long solveB(String input) {
    return solve(input, false);
  }

  static long solve(String input, boolean partA) {
    Grid grid = Grid.parse(input);
    boolean[][] visited = new boolean[grid.height()][grid.width()];
    return grid.locations().mapToInt(start -> {
      if (visited[start.y()][start.x()]) return 0;
      int area = 0, perimeter = 0;
      char c = grid.get(start);
      Deque<Location> queue = new ArrayDeque<>(List.of(start));
      visited[start.y()][start.x()] = true;
      Location loc;
      while ((loc = queue.pollFirst()) != null) {
        area++;
        for (Direction direction : Direction.values()) {
          Location neighbor = loc.step(direction);
          if (grid.isInBounds(neighbor) && grid.get(neighbor) == c) { // neighbor is part of region
            if (!visited[neighbor.y()][neighbor.x()]) {
              queue.push(neighbor);
              visited[neighbor.y()][neighbor.x()] = true;
            }
          } else { // between loc and neighbor there is a side
            // for illustration, assume direction = N.
            // loc is at "c", and neighbor = "." (= anything except for "c"):
            // ?.?
            // ?c?
            // consider the side between "c" and ".". continuing along it to the right (clockwise),
            // the only way we are not taking a corner is if the side goes straight like this:
            // ?..
            // ?cc
            Direction right = direction.right();
            if (partA || grid.has(neighbor.step(right), c) || !grid.has(loc.step(right), c)) {
              perimeter++;
            }
          }
        }
      }
      return area * perimeter;
    }).sum();
  }
}
