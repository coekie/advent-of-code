package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day24Test {

  public static final String example = """
      19, 13, 30 @ -2,  1, -2
      18, 19, 22 @ -1, -1, -2
      20, 25, 34 @ -2, -2, -4
      12, 31, 28 @ -1, -2, -1
      20, 19, 15 @  1, -5, -3""";

  @Test
  public void exampleA() {
    assertEquals(2, Day24.solveA(example, 7, 27));
  }

  @Test
  public void realA() {
    assertEquals(15318, Day24.solveA(InputReader.read("day24-input"),
        200000000000000., 400000000000000.));
  }

  @Test
  public void exampleB() {
    assertEquals(47, Day24.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(870379016024859L, Day24.solveB(InputReader.read("day24-input")));
  }
}