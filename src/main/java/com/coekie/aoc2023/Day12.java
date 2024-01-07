package com.coekie.aoc2023;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/12
class Day12 {
  private final Map<Row, Long> cache = new HashMap<>();

  static long solveA(String input) {
    return input.lines().map(Row::parse).mapToLong(new Day12()::arrangements).sum();
  }

  static long solveB(String input) {
    return input.lines().map(Row::parse).map(Row::unfold).mapToLong(new Day12()::arrangements)
        .sum();
  }

  long arrangements(Row row) {
    if (!cache.containsKey(row)) {
      cache.put(row, doArrangements(row));
    }
    return cache.get(row);
  }

  private long doArrangements(Row row) {
    if (row.springs.isEmpty() && row.groups.isEmpty()) return 1;
    long result = 0;
    if (row.canStartWithOperational()) {
      result += arrangements(row.remainderIfFirstOperational());
    }
    if (row.canStartWithDamaged()) {
      result += arrangements(row.remainderIfFirstDamaged());
    }
    return result;
  }

  record Row(String springs, List<Integer> groups) {
    static Row parse(String line) {
      String[] split = line.split(" ");
      return new Row(split[0], Stream.of(split[1].split(",")).map(Integer::parseInt).toList());
    }

    boolean canStartWithOperational() {
      if (springs.isEmpty()) return false;
      return springs.charAt(0) != '#';
    }

    boolean canStartWithDamaged() {
      if (groups.isEmpty()) return false;
      int damagedCount = groups.getFirst();
      if (springs.length() < damagedCount) return false;
      for (int i = 0; i < damagedCount; i++) {
        if (springs.charAt(i) == '.') return false;
      }
      return springs.length() <= damagedCount || springs.charAt(damagedCount) != '#';
    }

    Row remainderIfFirstOperational() {
      return new Row(springs.substring(1), groups);
    }

    Row remainderIfFirstDamaged() {
      int damagedCount = groups.getFirst();
      String remainderSprings = (springs.length() == damagedCount)
          ? "" : springs.substring(damagedCount + 1);
      return new Row(remainderSprings, groups.subList(1, groups.size()));
    }

    Row unfold() {
      return new Row(String.join("?", Collections.nCopies(5, springs)),
          IntStream.range(0, 5).boxed().flatMap(_ -> groups.stream()).toList());
    }
  }
}
