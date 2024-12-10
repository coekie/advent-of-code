package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day10Test {
  private static final String example = """
      89010123
      78121874
      87430965
      96549874
      45678903
      32019012
      01329801
      10456732
      """;

  @Test
  public void exampleA() {
    assertEquals(36, Day10.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(496, Day10.solveA(InputReader.read("day10-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(81, Day10.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(1120, Day10.solveB(InputReader.read("day10-input")));
  }
}