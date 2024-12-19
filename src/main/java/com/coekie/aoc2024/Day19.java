package com.coekie.aoc2024;

import java.util.Set;

// https://adventofcode.com/2024/day/19
public class Day19 {
  static class Towelset {
    final Set<String> towels;
    final int longest;

    Towelset(String line) {
      towels = Set.of(line.split(", "));
      longest = towels.stream().mapToInt(String::length).max().orElseThrow();
    }

    boolean canMake(String s, int start, Boolean[] cache) {
      if (start == s.length()) return true;
      Boolean b = cache[start];
      if (b != null) {
        return b;
      }
      for (int i = 1; i <= longest && i <= s.length() - start; i++) {
        if (towels.contains(s.substring(start, start + i)) && canMake(s, start + i, cache)) {
          return cache[start] = true;
        }
      }
      return cache[start] = false;
    }

    long count(String s, int start, Long[] cache) {
      if (start == s.length()) return 1;
      Long c = cache[start];
      if (c != null) {
        return c;
      }
      long r = 0;
      for (int i = 1; i <= longest && i <= s.length() - start; i++) {
        if (towels.contains(s.substring(start, start + i))) {
          r += count(s, start + i, cache);
        }
      }
      return cache[start] = r;
    }
  }


  static long solveA(String input) {
    String[] split = input.split("\n\n");
    Towelset towelset = new Towelset(split[0]);
    return split[1].lines().filter(s -> towelset.canMake(s, 0, new Boolean[s.length()])).count();
  }

  static long solveB(String input) {
    String[] split = input.split("\n\n");
    Towelset towelset = new Towelset(split[0]);
    return split[1].lines().mapToLong(p -> towelset.count(p, 0, new Long[p.length()])).sum();
  }
}
