package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day22Test {
  public static final String example = """
      1,0,1~1,2,1
      0,0,2~2,0,2
      0,2,3~2,2,3
      0,0,4~0,2,4
      2,0,5~2,2,5
      0,1,6~2,1,6
      1,1,8~1,1,9""";

  @Test
  public void exampleA() {
    assertEquals(5, Day22.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(501, Day22.solveA(InputReader.read("day22-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(7, Day22.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(80948, Day22.solveB(InputReader.read("day22-input")));
  }
}