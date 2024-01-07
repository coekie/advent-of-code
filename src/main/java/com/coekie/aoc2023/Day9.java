package com.coekie.aoc2023;

import com.google.common.base.Splitter;
import java.util.ArrayList;
import java.util.List;

// https://adventofcode.com/2023/day/9
abstract class Day9 {
  static long solveA(String input) {
    return new A().solve(input);
  }

  static long solveB(String input) {
    return new B().solve(input);
  }

  abstract long combine(List<Long> numbers, long n);

  static class A extends Day9 {
    @Override
    long combine(List<Long> numbers, long n) {
      return numbers.getLast() + n;
    }
  }

  static class B extends Day9 {
    @Override
    long combine(List<Long> numbers, long n) {
      return numbers.getFirst() - n;
    }
  }

  long solve(String input) {
    return input.lines()
        .mapToLong(s -> solveOne(Splitter.on(' ').splitToStream(s).map(Long::parseLong).toList()))
        .sum();
  }

  long solveOne(List<Long> numbers) {
    if (numbers.stream().allMatch(n -> n == 0)) {
      return 0;
    } else {
      List<Long> next = new ArrayList<>(numbers.size() - 1);
      for (int i = 1; i < numbers.size(); i++) {
        next.add(numbers.get(i) - numbers.get(i-1));
      }
      return combine(numbers, solveOne(next));
    }
  }
}
