package com.coekie.aoc2025;

import java.util.Arrays;

// https://adventofcode.com/2025/day/12
public class Day12 {
  static long solve(String input) {
    return input.lines().filter(line -> line.contains("x"))
        .map(line -> Arrays.stream(line.split("[x: ]+")).mapToInt(Integer::parseInt).toArray())
        .filter(inputs -> inputs[0] * inputs[1] > Arrays.stream(inputs).skip(2).sum() * 7)
        .count();
  }
}
