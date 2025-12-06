package com.coekie.aoc2025;

import java.util.Arrays;

public class Day2 {
  static long solveA(String input) {
    return Arrays.stream(input.split(","))
        .mapToLong(s -> solveA(Long.parseLong(s.split("-")[0]), Long.parseLong(s.split("-")[1])))
        .sum();
  }

  static long solveA(long from, long to) {
    int digits = Long.toString(from).length();
    long factor = (long) Math.pow(10, (digits + 1) >> 1);
    long half = from / factor;
    long otherHalf = from % factor;
    if (half < otherHalf) { // e.g. if we are at 1367 then 13 < 67 => start at 1414
      half++;
    }
    half = Math.max(half, factor / 10); // no leading zeros
    long next = half * factor + half;
    return next > to ? 0 : next + solveA(next + 1, to);
  }

  static long solveB(String input) {
    return Arrays.stream(input.split(","))
        .mapToLong(s -> solveB(Long.parseLong(s.split("-")[0]), Long.parseLong(s.split("-")[1])))
        .sum();
  }

  static long solveB(long from, long to) {
    long result = 0;
    for (long n = findNext(from, to); n != -1; n = findNext(n + 1, to)) {
      result += n;
    }
    return result;
  }

  static long findNext(long from, long to) {
    long next = Long.MAX_VALUE;
    for (long factor = 10; factor * factor < to * 10; factor *= 10) {
      next = Math.min(next, next(from, factor));
    }
    return next > to ? -1 : next;
  }

  /// Next "invalid id" that is >= `from`, splitting by `factor` (10, 100,...)
  static long next(long from, long factor) {
    long part = 0; // one part. eg 23 in 2323 for factor 100
    long partCount = 0; // number of times the part repeats
    long remaining = from; // remaining part of number not processed yet
    do {
      partCount++;
      long newPart = remaining % factor;
      if (newPart < part) { // e.g. if we are at 1367 then 13 < 67 => start at 1414
        newPart++;
      }
      part = newPart;
      remaining = remaining / factor;
    } while (remaining != 0);

    // at least two parts
    if (partCount == 1) {
      partCount = 2;
      part = factor / 10;
    }

    part = Math.max(part, factor / 10); // no leading zeros

    // result = part repeated partCount times
    long result = 0;
    for (long i = 0; i < partCount; i++) {
      result = result * factor + part;
    }

    return result;
  }
}
