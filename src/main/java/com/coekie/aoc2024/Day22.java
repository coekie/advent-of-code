package com.coekie.aoc2024;

import java.util.Arrays;
import java.util.stream.IntStream;

// https://adventofcode.com/2024/day/22
public class Day22 {
  static long solveA(String input) {
    return input.lines().mapToLong(Long::parseLong).map(n -> {
      for (int i = 0; i < 2000; i++) {
        n = next(n);
      }
      return n;
    }).sum();
  }

  static long next(long n) {
    n = (n ^ (n * 64)) % 16777216;
    n = (n ^ (n / 32)) % 16777216;
    return (n ^ (n * 2048)) % 16777216;
  }

  static long solveB(String input) {
    int[] bananas = new int[19 * 19 * 19 * 19];
    boolean[] hit = new boolean[19 * 19 * 19 * 19];
    input.lines().mapToLong(Long::parseLong).forEach(n -> {
      Arrays.fill(hit, false);
      int a, b = 0, c = 0, d = 0;
      for (int i = 0; i < 2000; i++) {
        long prev = n;
        n = next(n);
        a = b;
        b = c;
        c = d;
        d = (int) ((n % 10) - (prev % 10));
        int index = index(a, b, c, d);
        if (i >= 3 && !hit[index]) {
          hit[index] = true;
          bananas[index] += (int)(n % 10);
        }
      }
    });
    return IntStream.of(bananas).max().orElseThrow();
  }

  // squash four numbers in the range -9 to 9 into a single number in the range 0 to 19^4,
  // so we can use it as an index into an array
  static int index(int a, int b, int c, int d) {
    int i = (a + 9);
    i = i * 19 + (b + 9);
    i = i * 19 + (c + 9);
    return i * 19 + (d + 9);
  }
}
