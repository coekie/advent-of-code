package com.coekie.aoc2024;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.HashSet;
import java.util.Set;

// https://adventofcode.com/2024/day/6
public class Day6 {
  static long solveA(String input) {
    Grid grid = Grid.parse(input);
    Location start = grid.findFirst('^');
    State state = new State(start, Direction.N);
    Set<Location> visited = new HashSet<>();
    while (state != null) {
      visited.add(state.location);
      state = step(grid, state);
    }
    return visited.size();
  }

  static State step(Grid grid, State state) {
    Location next = state.location.step(state.direction);
    if (!grid.isInBounds(next)) {
      return null;
    } else if (grid.get(next) == '#') {
      return new State(state.location, state.direction.right());
    } else {
      return new State(next, state.direction);
    }
  }

  record State(Location location, Direction direction) {}

  static long solveB(String input) {
    Grid grid = Grid.parse(input);
    Location start = grid.findFirst('^');
    State state = new State(start, Direction.N);
    Set<Location> visited = new HashSet<>();
    Set<Location> solutions = new HashSet<>();
    while (true) {
      visited.add(state.location);
      State next = step(grid, state);
      if (next == null) break;
      // if we could put an obstacle in the next position
      if (!visited.contains(next.location) && grid.get(next.location) == '.' ) {
        grid.set(next.location, '#');
        if (loops(grid, state)) {
          solutions.add(next.location);
        }
        grid.set(next.location, '.');
      }
      state = next;
    }
    return solutions.size();
  }

  static boolean loops(Grid grid, State state) {
    State slow = state;
    State fast = state;
    for (int i = 0; ; i++) {
      if (i % 4 == 1) {
        slow = step(grid, slow);
      }
      fast = step(grid, fast);
      if (fast == null) return false;
      if (fast.equals(slow)) return true;
    }
  }
}
