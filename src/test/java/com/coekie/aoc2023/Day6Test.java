package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day6Test {
  @Test void testSolveOne() {
    assertEquals(4, Day6.solveOne(7, 9));
    assertEquals(8, Day6.solveOne(15, 40));
    assertEquals(9, Day6.solveOne(30, 200));
  }

  @Test void testExample() {
    assertEquals(288, Day6.solve("""
        Time:      7  15   30
        Distance:  9  40  200"""));
  }

  @Test void testReal() {
    assertEquals(505494, Day6.solve(InputReader.read("day6-input")));
  }

  @Test void testBExample() {
    assertEquals(71503, Day6.solveOne(71530, 940200));
  }

  @Test void testBReal() {
    assertEquals(23632299, Day6.solveOne(40829166, 277133813491063L));
  }
}