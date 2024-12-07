package com.coekie.aoc2024;

import java.util.Arrays;
import java.util.List;

// https://adventofcode.com/2024/day/7
public class Day7 {

  static long solveA(String input) {
    return solve(input, false);
  }

  static long solveB(String input) {
    return solve(input, true);
  }

  static long solve(String input, boolean allowConcat) {
    return input.lines().mapToLong(line -> {
      String[] split = line.split(": ");
      long target = Long.parseLong(split[0]);
      List<Long> numbers = Arrays.stream(split[1].split(" ")).map(Long::parseLong).toList();
      return canSolve(target, numbers, allowConcat) ? target : 0;
    }).sum();
  }

  static boolean canSolve(long target, List<Long> numbers, boolean allowConcat) {
    if (numbers.size() == 1) {
      return target == numbers.getFirst();
    } else if (target <= 0) {
      return false;
    } else {
      long last = numbers.getLast();
      var remaining = numbers.subList(0, numbers.size() - 1);
      String targetStr = Long.toString(target);
      String lastStr = Long.toString(last);
      return
          // use +
          canSolve(target - last, remaining, allowConcat)
              // use *
              || (target % last == 0 && canSolve(target / last, remaining, allowConcat))
              // use ||
              || (allowConcat && targetStr.endsWith(lastStr)
              && targetStr.length() > lastStr.length()
              && canSolve(
              Long.parseLong(targetStr.substring(0, targetStr.length() - lastStr.length())),
              remaining, true));
    }
  }
}
