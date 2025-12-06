package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {
  private static final String example = """
      L68
      L30
      R48
      L5
      R60
      L55
      L1
      L99
      R14
      L82""";

  @Test
  public void exampleA() {
    assertEquals(3, Day1.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(1100, Day1.solveA(InputReader.read("day1-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(6, Day1.solveB(example));
  }

  @Test
  public void manyCircles() {
    assertEquals(10, Day1.solveB("R1000"));
  }

  @Test
  public void zeroToZero() {
    assertEquals(2, Day1.solveB("""
        R50
        R100"""));
    assertEquals(2, Day1.solveB("""
        L50
        L100"""));
  }

  @Test
  public void toZero() {
    assertEquals(1, Day1.solveB("L50"));
    assertEquals(1, Day1.solveB("R50"));
  }

  @Test
  public void fromZero() {
    assertEquals(1, Day1.solveB("""
        R50
        R99"""));
    assertEquals(1, Day1.solveB("""
        L50
        L99"""));
  }

  @Test
  public void realB() {
    assertEquals(6358, Day1.solveB(InputReader.read("day1-input")));
  }
}
