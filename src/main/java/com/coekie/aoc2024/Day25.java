package com.coekie.aoc2024;

import com.coekie.aocutil.Grid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

// https://adventofcode.com/2024/day/25
public class Day25 {

  static long solveA(String input) {
    List<int[]> locks = new ArrayList<>();
    List<int[]> keys = new ArrayList<>();
    for (String in : input.split("\n\n")) {
      Grid grid = Grid.parse(in);
      if (grid.get(0, 0) == '.') {
        keys.add(IntStream.range(0, grid.width())
            .map(x -> IntStream.range(0, 6).filter(y -> grid.get(x, 5 - y) == '.').findFirst()
                .orElseThrow()).toArray());
      } else {
        locks.add(IntStream.range(0, grid.width())
            .map(x -> IntStream.range(0, 6).filter(y -> grid.get(x, 1 + y) == '.').findFirst()
                .orElseThrow()).toArray());
      }
    }
    return locks.stream().flatMap(lock -> keys.stream()
            .filter(key -> IntStream.range(0, 5).allMatch(i -> lock[i] + key[i] <= 5)))
        .count();
  }
}
