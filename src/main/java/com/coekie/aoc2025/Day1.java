package com.coekie.aoc2025;

public class Day1 {
  static int solveA(String input) {
    int pos = 50;
    int result = 0;
    for (int move : parseMoves(input)) {
      pos = mod100(pos + move);
      if (pos == 0)
        result++;
    }
    return result;
  }

  static int solveB(String input) {
    int pos = 50;
    int result = 0;
    for (int move : parseMoves(input)) {
      int newPos = Math.floorMod(pos + move, 100);
      if (move > 0) {
        // going right, crossing between 99 and 0 counts = counting changes in multiples of 100
        result += Math.abs((pos + move - newPos) / 100);
      } else {
        // going left, crossing between 1 and 0 counts, adjust by -1 so it becomes same as right
        result += Math.abs((mod100(pos - 1) + move - mod100(newPos - 1)) / 100);
      }
      pos = newPos;
    }
    return result;
  }

  static int mod100(int pos) {
    return Math.floorMod(pos, 100);
  }

  private static int[] parseMoves(String input) {
    return input.lines()
        .mapToInt(s -> Integer.parseInt(s.substring(1)) * (s.startsWith("R") ? 1 : -1))
        .toArray();
  }
}
