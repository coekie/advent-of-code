package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day18Test {

  public static final String example = """
      5,4
      4,2
      4,5
      3,0
      2,1
      6,3
      2,4
      1,5
      0,6
      3,3
      2,6
      5,1
      1,2
      5,5
      2,5
      6,5
      1,4
      0,4
      6,4
      1,1
      6,1
      1,0
      0,5
      1,6
      2,0""";

  @Test
  public void exampleA() {
    assertEquals(22, Day18.solveA(example, 7, 12));
  }

  @Test
  public void realA() {
    assertEquals(438, Day18.solveA(InputReader.read("day18-input"), 71, 1024));
  }

  @Test
  public void exampleB() {
    assertEquals("6,1", Day18.solveB(example, 7));
  }

  @Test
  public void realB() {
    assertEquals("26,22", Day18.solveB(InputReader.read("day18-input"), 71));
  }
}