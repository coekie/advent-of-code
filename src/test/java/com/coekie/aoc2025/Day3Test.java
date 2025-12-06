package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {
  private static final String example = """
      987654321111111
      811111111111119
      234234234234278
      818181911112111""";

  @Test
  public void exampleA() {
    assertEquals(357, Day3.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(17613, Day3.solveA(InputReader.read("day3-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(3121910778619L, Day3.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(175304218462560L, Day3.solveB(InputReader.read("day3-input")));
  }
}
