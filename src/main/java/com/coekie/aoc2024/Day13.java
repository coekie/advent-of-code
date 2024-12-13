package com.coekie.aoc2024;

// https://adventofcode.com/2024/day/13
public class Day13 {
  static long solveA(String input) {
    return solve(input, 0);
  }

  static long solveB(String input) {
    return solve(input, 10000000000000L);
  }

  static long solve(String input, long extra) {
    String[] lines = input.split("\n");
    long tokens = 0;
    for (int i = 0; i < lines.length; i++) {
      long ax = x(lines[i]), ay = y(lines[i++]);
      long bx = x(lines[i]), by = y(lines[i++]);
      long px = x(lines[i]) + extra, py = y(lines[i++]) + extra;
      // two equations:
      // ax * a + bx * b == px
      // ay * a + by * b == py
      // first solve for b:
      long b = Math.round((px - (ax * py / (double) ay)) / ((bx - ax * by / (double) ay)));
      // then a:
      long a = Math.round((px - bx * b) / (double) ax);
      // it only counts if it works out with whole (rounded) numbers
      if (ax * a + bx * b == px && ay * a + by * b == py) {
        tokens += 3 * a + b;
      }
    }
    return tokens;
  }

  static int x(String line) {
    int start = line.indexOf('X') + 2;
    return Integer.parseInt(line.substring(start, line.indexOf(',', start)));
  }

  static int y(String line) {
    return Integer.parseInt(line.substring(line.indexOf('Y') + 2));
  }
}
