package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {
  // the example is not helpful for this puzzle

  @Test
  public void real() {
    assertEquals(474, Day12.solve(InputReader.read("day12-input")));
  }
}
