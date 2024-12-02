package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day2Test {
  @Test
  public void exampleA() {
    assertEquals(2, Day2.solveA("""
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9"""));
  }

  @Test
  public void realA() {
    assertEquals(383, Day2.solveA(InputReader.read("day2-input")));
  }

  @Test
  public void otherExamplesB() {
    // trivial
    assertTrue(Day2.isSafeB("0 1 2"));
    assertTrue(Day2.isSafeB("0 2 5 8"));

    // remove first
    assertTrue(Day2.isSafeB("9 0 2 3"));
    assertTrue(Day2.isSafeB("1 0 2 3")); // (remove either first or second)
    // remove last
    assertTrue(Day2.isSafeB("0 1 2 3 0"));

    // remove second
    assertTrue(Day2.isSafeB("0 9 1 2 3"));
    // remove second last
    assertTrue(Day2.isSafeB("0 1 2 9 3"));

    assertTrue(Day2.isSafeB("0 1 5"));

    // remove first and last
    assertFalse(Day2.isSafeB("9 1 2 3 0"));

    assertTrue(Day2.isSafeB("0 3 2 3 5"));
  }

  @Test
  public void exampleB() {
    assertEquals(4, Day2.solveB("""
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9"""));
  }

  @Test
  public void realB() {
    assertEquals(436, Day2.solveB(InputReader.read("day2-input")));
  }
}