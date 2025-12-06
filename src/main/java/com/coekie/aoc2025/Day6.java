package com.coekie.aoc2025;

import com.coekie.aocutil.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Day6 {
  static long solveA(String input) {
    List<String[]> lines = input.lines().map(line -> line.trim().split(" +")).toList();
    String[] operations = lines.getLast();
    return IntStream.range(0, operations.length)
        .mapToLong(i -> lines.subList(0, lines.size() - 1).stream()
            .mapToLong(line -> Long.parseLong(line[i]))
            .reduce(operations[i].equals("+") ? Long::sum : (a,b) -> a*b)
            .orElseThrow())
        .sum();
  }

  static long solveB(String input) {
    Grid grid = Grid.parse(input);
    long result = 0;
    List<Long> numbers = new ArrayList<>();
    for (int x = grid.width() - 1; x >= 0; x--) {
      long number = 0;
      for (int y = 0; y < grid.height() - 1; y++) {
        char c = grid.get(x, y);
        if ('0' <= c && c <= '9') {
          number = number * 10 + (c - '0');
        }
      }
      numbers.add(number);

      char operation = grid.get(x, grid.height() - 1);
      if (operation != ' ') {
        result += operation == '+'
            ? numbers.stream().reduce(0L, Long::sum)
            : numbers.stream().reduce(1L, (a, b) -> a * b);
        numbers.clear();
        x--;
      }
    }
    return result;
  }

}
