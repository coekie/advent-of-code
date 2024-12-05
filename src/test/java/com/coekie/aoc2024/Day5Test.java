package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day5Test {
  private static final String example = """
      47|53
      97|13
      97|61
      97|47
      75|29
      61|13
      75|53
      29|13
      97|29
      53|29
      61|53
      97|53
      61|29
      47|13
      75|47
      97|75
      47|61
      75|61
      47|29
      75|13
      53|13
      
      75,47,61,53,29
      97,61,53,29,13
      75,29,13
      75,97,47,61,53
      61,13,29
      97,13,75,29,47""";

  @Test
  public void exampleA() {
    assertEquals(143, Day5.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(6267, Day5.solveA(InputReader.read("day5-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(123, Day5.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(5184, Day5.solveB(InputReader.read("day5-input")));
  }
}