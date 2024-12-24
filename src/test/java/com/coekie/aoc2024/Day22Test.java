package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day22Test {
  @Test
  public void exampleA() {
    assertEquals(37327623, Day22.solveA("""
        1
        10
        100
        2024"""));
  }

  @Test
  public void realA() {
    assertEquals(13429191512L, Day22.solveA(InputReader.read("day22-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(23, Day22.solveB("""
        1
        2
        3
        2024"""));
  }

  @Test
  public void realB() {
    assertEquals(1582, Day22.solveB(InputReader.read("day22-input")));
  }
}