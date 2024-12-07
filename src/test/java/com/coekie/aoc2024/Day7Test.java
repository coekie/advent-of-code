package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day7Test {
  private static final String example = """
      190: 10 19
      3267: 81 40 27
      83: 17 5
      156: 15 6
      7290: 6 8 6 15
      161011: 16 10 13
      192: 17 8 14
      21037: 9 7 18 13
      292: 11 6 16 20""";

  @Test
  public void exampleA() {
    assertEquals(3749, Day7.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(6231007345478L, Day7.solveA(InputReader.read("day7-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(11387, Day7.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(333027885676693L, Day7.solveB(InputReader.read("day7-input")));
  }
}