package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day1Test {
  @Test
  public void exampleA() {
    assertEquals(11, Day1.solveA("""
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3"""));
  }

  @Test
  public void realA() {
    assertEquals(3574690, Day1.solveA(InputReader.read("day1-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(31, Day1.solveB("""
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3"""));
  }

  @Test
  public void realB() {
    assertEquals(22565391, Day1.solveB(InputReader.read("day1-input")));
  }
}