package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day11Test {
  @Test
  public void example() {
    String example = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....""";
    assertEquals(374, Day11.solve(example, 2));
    assertEquals(1030, Day11.solve(example, 10));
    assertEquals(8410, Day11.solve(example, 100));
  }

  @Test
  public void real() {
    String input = InputReader.read("day11-input");
    assertEquals(9521776, Day11.solve(input, 2));
    assertEquals(553224415344L, Day11.solve(input, 1000000));
  }
}