package com.coekie.aoc2023;

import com.google.common.base.Splitter;
import java.util.List;
import java.util.stream.IntStream;

// https://adventofcode.com/2023/day/6
class Day6 {
  static long solve(String input) {
    List<String> lines = input.lines().toList();

    List<String> timesSplit = Splitter.on(' ').omitEmptyStrings().splitToList(lines.get(0));
    List<String> recordSplit = Splitter.on(' ').omitEmptyStrings().splitToList(lines.get(1));

    return IntStream.range(1, timesSplit.size())
        .mapToLong(i ->
            solveOne(Long.parseLong(timesSplit.get(i)), Long.parseLong(recordSplit.get(i))))
        .reduce((a, b) -> a * b)
        .orElseThrow();
  }

  // t: time
  // w: wait
  // d: distance
  // r: record distance
  // d = (t - w) * w = tw - w^2
  // to win we need: d > r
  //  <=> tw - w^2 - r > 0
  //  <=> w^2 - tw + r < 0
  // bounds: (t +/- sqrt(t^2 - 4r)) / 2
  static long solveOne(long time, long record) {
    double det = Math.sqrt(time * time - 4 * record);

    double lowerD = (time - det) / 2;
    double upperD = (time + det) / 2;

    // round up and down, excluding when we'd be equal to the record
    long lowerL = (long) Math.ceil(Math.nextUp(lowerD));
    long upperL = (long) Math.nextDown(upperD);

    return upperL - lowerL + 1;
  }
}
