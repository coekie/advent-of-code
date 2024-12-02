package com.coekie.aoc2024;

import java.util.Arrays;

// https://adventofcode.com/2024/day/2
public class Day2 {
  static long solveA(String input) {
    return input.lines().filter(Day2::isSafeA).count();
  }

  static boolean isSafeA(String line) {
    int[] levels = Arrays.stream(line.split(" +")).mapToInt(Integer::parseInt).toArray();
    boolean increase = levels[1] - levels[0] > 0;
    for (int i = 0; i < levels.length - 1; i++) {
      if (!safeDifference(levels[i], levels[i + 1], increase)) {
        return false;
      }
    }
    return true;
  }

  static long solveB(String input) {
    return input.lines().filter(Day2::isSafeB).count();
  }

  static boolean isSafeB(String line) {
    return isSafeB(line, true) || isSafeB(line, false);
  }

  private static boolean isSafeB(String line, boolean increase) {
    int[] levels = Arrays.stream(line.split(" +")).mapToInt(Integer::parseInt).toArray();
    int skipped = 0;
    for (int i = 0; i < levels.length - 1; i++) {
      if (!safeDifference(levels[i], levels[i + 1], increase)) {
        if (i == levels.length - 2 || safeDifference(levels[i], levels[i + 2], increase)) {
          // skip i+1
          skipped++;
          i++;
        } else if (i == 0 || safeDifference(levels[i - 1], levels[i + 1], increase)) {
          // skip i
          skipped++;
        } else {
          return false;
        }
      }
    }
    return skipped <= 1;
  }

  private static boolean safeDifference(int a, int b, boolean increase) {
    // "The levels are either all increasing or all decreasing."
    // "Any two adjacent levels differ by at least one and at most three."
    return a != b && increase == (b - a > 0) && Math.abs(b - a) <= 3;
  }
}
