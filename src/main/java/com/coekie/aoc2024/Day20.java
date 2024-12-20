package com.coekie.aoc2024;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;

// https://adventofcode.com/2024/day/20
public class Day20 {
  /// Build a grid where the values are the number of steps to get there,
  /// counting from 1 at start, up to end
  static Grid stepGrid(Grid grid) {
    Grid stepGrid = new Grid(new char[grid.height()][grid.width()]);
    Location pos = grid.findFirst('S');
    grid.set(grid.findFirst('E'), '.');
    char step = 1;
    loopityloop:
    while (true) {
      stepGrid.set(pos, step++);
      for (Direction dir : Direction.values()) {
        Location next = pos.step(dir);
        // found a step we can take, and not stepping backwards
        if (grid.has(next, '.') && stepGrid.get(next) == 0) {
          pos = next;
          continue loopityloop;
        }
      }
      return stepGrid;
    }
  }

  static long solveA(String input, int toSave) {
    return solveB(input, 2, toSave);
  }

  static long solveB(String input, int jumpDistance, int toSave) {
    Grid grid = Grid.parse(input);
    Grid stepGrid = stepGrid(grid);
    return grid.locations().filter(loc -> grid.get(loc) != '#').mapToLong(current -> {
      int cheats = 0;
      int minX = Math.max(0, current.x() - jumpDistance);
      int maxX = Math.min(stepGrid.width(), current.x() + jumpDistance + 1);
      for (int x = minX; x < maxX; x++) {
        int remainingJump = jumpDistance - Math.abs(x - current.x());
        int minY = Math.max(0, current.y() - remainingJump);
        int maxY = Math.min(stepGrid.height(), current.y() + remainingJump + 1);
        for (int y = minY; y < maxY; y++) {
          int distance = Math.abs(x - current.x()) + Math.abs(y - current.y());
          if (stepGrid.get(x, y) - stepGrid.get(current) - distance >= toSave) {
            cheats++;
          }
        }
      }
      return cheats;
    }).sum();
  }
}
