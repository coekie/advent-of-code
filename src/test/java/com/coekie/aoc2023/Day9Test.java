package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day9Test {
  @Test
  public void exampleA() {
    assertEquals(114, Day9.solveA("""
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45"""));
  }

  @Test
  public void realA() {
    assertEquals(1974913025, Day9.solveA(InputReader.read("day9-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(2, Day9.solveB("""
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45"""));
  }

  @Test
  public void realB() {
    assertEquals(884, Day9.solveB(InputReader.read("day9-input")));
  }
}