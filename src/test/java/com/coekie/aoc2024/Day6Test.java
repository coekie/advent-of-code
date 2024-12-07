package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day6Test {
  private static final String example = """
      ....#.....
      .........#
      ..........
      ..#.......
      .......#..
      ..........
      .#..^.....
      ........#.
      #.........
      ......#...""";

  @Test
  public void exampleA() {
    assertEquals(41, Day6.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(4647, Day6.solveA(InputReader.read("day6-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(6, Day6.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(1723, Day6.solveB(InputReader.read("day6-input")));
  }
}