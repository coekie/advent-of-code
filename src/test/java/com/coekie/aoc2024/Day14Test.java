package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day14Test {
  @Test
  public void exampleA() {
    assertEquals(12, Day14.solveA("""
        p=0,4 v=3,-3
        p=6,3 v=-1,-3
        p=10,3 v=-1,2
        p=2,0 v=2,-1
        p=0,0 v=1,3
        p=3,0 v=-2,-2
        p=7,6 v=-1,-3
        p=3,0 v=-1,-2
        p=9,3 v=2,3
        p=7,3 v=-1,2
        p=2,4 v=2,-3
        p=9,5 v=-3,-3""", 11, 7));
  }

  @Test
  public void realA() {
    assertEquals(224969976, Day14.solveA(InputReader.read("day14-input").trim(), 101, 103));
  }

  @Test
  public void realB() {
    assertEquals(7892, Day14.solveB(InputReader.read("day14-input")));
  }
}