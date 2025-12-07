package com.coekie.aoc2025;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

public class Day7 {
  static int solveA(String input) {
    List<String> lines = input.lines().toList();
    Set<Integer> current = new HashSet<>();
    current.add(lines.getFirst().indexOf('S'));
    int result = 0;
    for (String line : lines.subList(1, lines.size())) {
      Set<Integer> next = new HashSet<>();
      for (Integer x : current) {
        if (line.charAt(x) == '.') {
          next.add(x);
        } else {
          next.add(x - 1);
          next.add(x + 1);
          result++;
        }
      }
      current = next;
    }
    return result;
  }

  static long solveB(String input) {
    List<String> lines = input.lines().toList();
    long[] current = new long[lines.getFirst().length()];
    current[lines.getFirst().indexOf('S')] = 1;
    for (String line : lines.subList(1, lines.size())) {
      long[] next = new long[current.length];
      for (int x = 0; x < current.length; x++) {
        if (line.charAt(x) == '.') {
          next[x] += current[x];
        } else {
          next[x - 1] += current[x];
          next[x + 1] += current[x];
        }
      }
      current = next;
    }
    return LongStream.of(current).sum();
  }

}
