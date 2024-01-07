package com.coekie.aoc2023;

import com.google.common.base.Splitter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.LongStream;

// https://adventofcode.com/2023/day/4
class Day4 {
  static long solveA(String input) {
    return input.lines().mapToLong(line -> {
          List<String> splitLine = Splitter.on('|').splitToList(line);
          String winning = splitLine.get(0);
          String mine = splitLine.get(1);
          HashSet<String> winningSet = new HashSet<>(
              Splitter.on(' ').omitEmptyStrings().splitToList(winning));
          long count = Splitter.on(' ').omitEmptyStrings().splitToList(mine).stream()
              .filter(winningSet::contains)
              .count();
          return count == 0 ? 0 : 1L << (count - 1);
        })
        .sum();
  }

  static long solveB(String input) {
    List<String> lines = input.lines().toList();

    long[] cards = new long[lines.size()];
    Arrays.fill(cards, 1);

    for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++) {
      String line = lines.get(lineNumber);
      List<String> splitLine = Splitter.on('|').splitToList(line);
      String winning = splitLine.get(0);
      String mine = splitLine.get(1);
      HashSet<String> winningSet = new HashSet<>(
          Splitter.on(' ').omitEmptyStrings().splitToList(winning));
      long count = Splitter.on(' ').omitEmptyStrings().splitToList(mine).stream()
          .filter(winningSet::contains)
          .count();
      if (count == 0) {
        continue;
      }
      for (int i = 0; i < count; i++) {
        cards[lineNumber + 1 + i] += cards[lineNumber];
      }
    }
    return LongStream.of(cards).sum();
  }
}
