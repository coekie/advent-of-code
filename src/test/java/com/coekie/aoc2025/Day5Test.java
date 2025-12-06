package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {
  private static final String example = """
      3-5
      10-14
      16-20
      12-18
      
      1
      5
      8
      11
      17
      32""";

  @Test
  public void exampleA() {
    assertEquals(3, Day5.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(509, Day5.solveA(InputReader.read("day5-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(14, Day5.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(336790092076620L, Day5.solveB(InputReader.read("day5-input")));
  }
}
