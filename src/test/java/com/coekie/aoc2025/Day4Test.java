package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {
  private static final String example = """
      ..@@.@@@@.
      @@@.@.@.@@
      @@@@@.@.@@
      @.@@@@..@.
      @@.@@@@.@@
      .@@@@@@@.@
      .@.@.@.@@@
      @.@@@.@@@@
      .@@@@@@@@.
      @.@.@@@.@.""";

  @Test
  public void exampleA() {
    assertEquals(13, Day4.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(1437, Day4.solveA(InputReader.read("day4-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(43, Day4.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(8765, Day4.solveB(InputReader.read("day4-input")));
  }
}
