package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day14Test {
  static final String example = """
      O....#....
      O.OO#....#
      .....##...
      OO.#O....O
      .O.....O#.
      O.#..O.#.#
      ..O..#O..O
      .......O..
      #....###..
      #OO..#....""";

  @Test
  public void exampleA() {
    assertEquals(136, Day14.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(108144, Day14.solveA(InputReader.read("day14-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(64, Day14.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(108404, Day14.solveB(InputReader.read("day14-input")));
  }
}