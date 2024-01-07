package com.coekie.aoc2023;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://adventofcode.com/2023/day/14
class Day14 {
  static long solveA(String input) {
    long load = 0;
    List<String> lines = input.lines().toList();
    for (int i = 0; i < lines.getFirst().length(); i++) {
      long nextLoad = lines.size();
      for (int j = 0; j < lines.size(); j++) {
        char c = lines.get(j).charAt(i);
        switch (c) {
          case '#' -> nextLoad = lines.size() - j - 1;
          case 'O' -> {
            load += nextLoad;
            nextLoad--;
          }
        }
      }
    }
    return load;
  }

  static long solveB(String input) {
    Grid grid = Grid.parse(input);

    Map<String, Integer> grid2Cycle = new HashMap<>();

    int totalCycles = 1_000_000_000;
    for (int cycle = 0; cycle < totalCycles; cycle++) {
      cycle(grid);
      String str = grid.toString();
      if (grid2Cycle.containsKey(str)) {
        // loop detected, fast-forward
        int loopStart = grid2Cycle.get(str);
        int loopLength = cycle - loopStart;
        int cyclesLeft = totalCycles - cycle;
        cycle += cyclesLeft / loopLength * loopLength;
      } else {
        grid2Cycle.put(str, cycle);
      }
    }

    return load(grid);
  }

  static int getX(Grid grid, int line, int pos, Direction direction) {
    return switch (direction) {
      case N,S -> line;
      case W -> pos;
      case E -> grid.width() - pos - 1;
    };
  }

  static int getY(Grid grid, int line, int pos, Direction direction) {
    return switch (direction) {
      case W,E -> line;
      case N -> pos;
      case S -> grid.height() - pos - 1;
    };
  }

  static int lines(Grid grid, Direction direction) {
    return switch (direction) {
      case N,S -> grid.width();
      case W,E -> grid.height();
    };
  }

  static int lineLength(Grid grid, Direction direction) {
    return switch (direction) {
      case N,S -> grid.height();
      case W,E -> grid.width();
    };
  }

  static char get(Grid grid, int line, int pos, Direction direction) {
    return grid.get(getX(grid, line, pos, direction), getY(grid, line, pos, direction));
  }

  static void set(Grid grid, int line, int pos, Direction direction, char c) {
    grid.set(getX(grid, line, pos, direction), getY(grid, line, pos, direction), c);
  }

  static void tilt(Grid grid, Direction direction) {
    for (int line = 0; line < lines(grid, direction); line++) {
      int nextPos = 0;
      for (int pos = 0; pos < lineLength(grid, direction); pos++) {
        char c = get(grid, line, pos, direction);
        switch (c) {
          case '#' -> nextPos = pos + 1;
          case 'O' -> {
            set(grid, line, pos, direction, '.');
            set(grid, line, nextPos, direction, 'O');
            nextPos++;
          }
        }
      }
    }
  }

  static void cycle(Grid grid) {
    for (Direction direction : Direction.values()) {
      tilt(grid, direction);
    }
  }

  static int load(Grid grid) {
    int load = 0;
    for (int i = 0; i < grid.height(); i++) {
      for (char c : grid.grid()[i]) {
        if (c == 'O') {
          load += grid.height() - i;
        }
      }
    }
    return load;
  }
}
