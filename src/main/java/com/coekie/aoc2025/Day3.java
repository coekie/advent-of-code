package com.coekie.aoc2025;

public class Day3 {
  static int solveA(String input) {
    return input.lines()
        .mapToInt(Day3::solveALine)
        .sum();
  }

  static int solveALine(String s) {
    int result = 0;
    int maxDigit = 0;
    for (char c : s.toCharArray()) {
      int digit = c - '0';
      result = Math.max(result, maxDigit * 10 + digit);
      maxDigit = Math.max(maxDigit, digit);
    }
    return result;
  }

  static long solveB(String input) {
    return input.lines()
        .mapToLong(Day3::solveBLine)
        .sum();
  }

  static long solveBLine(String s) {
    long[] best = new long[13]; // best so far with index digits
    for (char c : s.toCharArray()) {
      int digit = c - '0';
      for (int i = 12; i > 0; i--) {
        best[i] = Math.max(best[i], best[i - 1] * 10 + digit);
      }
    }
    return best[12];
  }
}
