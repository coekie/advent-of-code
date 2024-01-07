package com.coekie.aoc2023;

import com.google.common.base.CharMatcher;
import java.util.List;

// https://adventofcode.com/2023/day/1
class Day1 {
  static long solveA(String input) {
    return input.lines()
        .mapToInt(Day1::solveLineA)
        .sum();
  }

  static int solveLineA(String line) {
    String digits = CharMatcher.inRange('0', '9').retainFrom(line);
    return Integer.parseInt("" + digits.charAt(0) + digits.charAt(digits.length() - 1));
  }

  record Digit(String name, int value) {
  }

  static final List<Digit> digitlist = List.of(
      new Digit("one", 1),
      new Digit("two", 2),
      new Digit("three", 3),
      new Digit("four", 4),
      new Digit("five", 5),
      new Digit("six", 6),
      new Digit("seven", 7),
      new Digit("eight", 8),
      new Digit("nine", 9),
      new Digit("1", 1),
      new Digit("2", 2),
      new Digit("3", 3),
      new Digit("4", 4),
      new Digit("5", 5),
      new Digit("6", 6),
      new Digit("7", 7),
      new Digit("8", 8),
      new Digit("9", 9)
  );

  static long solveB(String input) {
    return input.lines()
        .mapToInt(Day1::solveLineB)
        .sum();
  }

  static int solveLineB(String line) {
    int minIndex = Integer.MAX_VALUE;
    int minValue = -1;
    int maxIndex = -1;
    int maxValue = -1;
    for (Digit digit : digitlist) {
      int index = line.indexOf(digit.name);
      if (index != -1 && index < minIndex) {
        minIndex = index;
        minValue = digit.value;
      }

      int lastIndex = line.lastIndexOf(digit.name);
      if (lastIndex != -1 && lastIndex > maxIndex) {
        maxIndex = lastIndex;
        maxValue = digit.value;
      }
    }

    return minValue * 10 + maxValue;
  }
}
