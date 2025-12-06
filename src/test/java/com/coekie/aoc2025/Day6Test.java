package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {
  private static final String example = """
      123 328  51 64\s
       45 64  387 23\s
        6 98  215 314
      *   +   *   + \s""";

  @Test
  public void exampleA() {
    assertEquals(4277556, Day6.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(4771265398012L, Day6.solveA(InputReader.read("day6-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(3263827, Day6.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(10695785245101L, Day6.solveB(InputReader.read("day6-input")));
  }
}
