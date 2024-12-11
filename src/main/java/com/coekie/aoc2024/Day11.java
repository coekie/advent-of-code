package com.coekie.aoc2024;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

// https://adventofcode.com/2024/day/11
public class Day11 {

  static long solveA(String input) {
    return solve(input, 25);
  }

  static long solveB(String input) {
    return solve(input, 75);
  }

  static long solve(String input, int steps) {
    Solver solver = new Solver();
    return Stream.of(input.split(" ")).mapToLong(s -> solver.solve(Long.parseLong(s), steps)).sum();
  }

  record Query(long number, int steps) {
  }
  static class Solver {
    final Map<Query, Long> cache = new HashMap<>();

    long solve(long number, int steps) {
      Query query = new Query(number, steps);
      Long result = cache.get(query);
      if (result == null) {
        result = doSolve(number, steps);
        cache.put(query, result);
      }
      return result;
    }

    long doSolve(long number, int steps) {
      if (steps == 0) {
        return 1;
      } else if (number == 0) {
        return solve(1, steps - 1);
      } else {
        long split = splitIfEven(number);
        if (split != -1) {
          return solve(number / split, steps -1) + solve(number % split, steps - 1);
        } else {
          return solve(number * 2024, steps -1);
        }
      }
    }
  }

  /** Power of 10 used to split if number of digits is even, -1 if number of digits is odd */
  static long splitIfEven(long n) {
    if (n < 10) {
      return -1;
    }
    long size = 10;
    while (true) {
      if (n < size * size) {
        return size;
      }
      if (n < size * size * 10) {
        return -1;
      }
      size *= 10;
    }
  }
}
