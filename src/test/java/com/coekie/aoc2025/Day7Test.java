package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Test {
  private static final String example = """
      .......S.......
      ...............
      .......^.......
      ...............
      ......^.^......
      ...............
      .....^.^.^.....
      ...............
      ....^.^...^....
      ...............
      ...^.^...^.^...
      ...............
      ..^...^.....^..
      ...............
      .^.^.^.^.^...^.
      ...............""";

  @Test
  public void exampleA() {
    assertEquals(21, Day7.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(1598, Day7.solveA(InputReader.read("day7-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(40, Day7.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(4509723641302L, Day7.solveB(InputReader.read("day7-input")));
  }
}
