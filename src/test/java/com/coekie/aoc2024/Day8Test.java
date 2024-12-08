package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day8Test {
  private static final String example = """
      ............
      ........0...
      .....0......
      .......0....
      ....0.......
      ......A.....
      ............
      ............
      ........A...
      .........A..
      ............
      ............
      """;

  @Test
  public void exampleA() {
    assertEquals(14, Day8.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(289, Day8.solveA(InputReader.read("day8-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(34, Day8.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(1030, Day8.solveB(InputReader.read("day8-input")));
  }
}