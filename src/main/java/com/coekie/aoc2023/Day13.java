package com.coekie.aoc2023;

import java.util.List;
import java.util.function.IntBinaryOperator;

// https://adventofcode.com/2023/day/13
class Day13 {
  static long solve(String input, int smudges) {
    long result = 0;
    for (String puzzle : input.split("\n\n")) {
      result += solveOne(puzzle, smudges);
    }
    return result;
  }

  static long solveOne(String puzzleS, int smudges) {
    List<String> lines = puzzleS.lines().toList();
    for (int i = 0; i < lines.getFirst().length() - 1; i++) {
      if (isColumnMirror(lines, i, smudges)) {
        return i + 1;
      }
    }
    for (int i = 0; i < lines.size() - 1; i++) {
      if (isRowMirror(lines, i, smudges)) {
        return (i + 1L) * 100;
      }
    }
    throw new IllegalStateException();
  }

  static boolean isColumnMirror(List<String> puzzle, int m, int smudges) {
    return isMirror(m, puzzle.getFirst().length(), puzzle.size(),
        (column, row) -> puzzle.get(row).charAt(column), smudges);
  }

  static boolean isRowMirror(List<String> puzzle, int m, int smudges) {
    return isMirror(m, puzzle.size(), puzzle.getFirst().length(),
        (row, column) -> puzzle.get(row).charAt(column), smudges);
  }

  static boolean isMirror(int m, int n, int n2, IntBinaryOperator get, int smudges) {
    int foundSmudges = 0;
    for (int i = 0; i <= m; i++) {
      for (int j = 0; j < n2; j++) {
        int mirror = m * 2 - i + 1;
        if (mirror >= 0 && mirror < n) {
          int one = get.applyAsInt(i, j);
          int two = get.applyAsInt(mirror, j);
          if (one != two) {
            foundSmudges++;
            if (foundSmudges > smudges) return false;
          }
        }
      }
    }
    return foundSmudges == smudges;
  }
}
