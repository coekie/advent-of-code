package com.coekie.aoc2024;

import static java.lang.Integer.parseInt;

import java.util.BitSet;
import java.util.List;

// https://adventofcode.com/2024/day/14
public class Day14 {
  record Robot(int px, int py, int vx, int vy, int width, int height) {
    Robot(String line, int width, int height) {
      String[] split = line.split("p=|,| v=");
      this(parseInt(split[1]), parseInt(split[2]), parseInt(split[3]), parseInt(split[4]),
          width, height);
    }

    int xAfter(int steps) {
      return Math.floorMod(px + vx * steps, width);
    }

    int yAfter(int steps) {
      return Math.floorMod(py + vy * steps, height);
    }
  }

  static int solveA(String input, int width, int height) {
    int[][] quadrants = new int[2][2];
    input.lines().map(line -> new Robot(line, width, height)).forEach(robot -> {
      int x = robot.xAfter(100);
      int y = robot.yAfter(100);
      if (x != width / 2 && y != height / 2) {
        quadrants[y < height / 2 ? 0 : 1][x < width / 2 ? 0 : 1]++;
      }
    });
    return quadrants[0][0] * quadrants[0][1] * quadrants[1][0] * quadrants[1][1];
  }

  static int solveB(String input) {
    List<Robot> robots = input.lines().map(line -> new Robot(line, 101, 103)).toList();
    BitSet grid = new BitSet(103 * 101);
    for (int steps = 0; ; steps++) {
      if (isSolution(robots, steps, grid)) {
        return steps;
      }
    }
  }

  // it's guesswork/heuristics, but apparently the solution is one without collisions ¯\_(ツ)_/¯
  static boolean isSolution(List<Robot> robots, int steps, BitSet grid) {
    grid.clear();
    for (Robot robot : robots) {
      int x = robot.xAfter(steps);
      int y = robot.yAfter(steps);
      if (grid.get(y * 103 + x)) {
        return false; // collision
      }
      grid.set(y * 103 + x);
    }
    return true;
  }
}
