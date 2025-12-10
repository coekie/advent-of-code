package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {
  private static final String example = """
      7,1
      11,1
      11,7
      9,7
      9,5
      2,5
      2,3
      7,3""";

  @Test
  public void exampleA() {
    assertEquals(50, Day9.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(4763509452L, Day9.solveA(InputReader.read("day9-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(24, Day9.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(1516897893, Day9.solveB(InputReader.read("day9-input")));
  }
}
