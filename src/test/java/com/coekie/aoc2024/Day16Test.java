package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day16Test {

  public static final String example = """
      ###############
      #.......#....E#
      #.#.###.#.###.#
      #.....#.#...#.#
      #.###.#####.#.#
      #.#.#.......#.#
      #.#.#####.###.#
      #...........#.#
      ###.#.#####.#.#
      #...#.....#.#.#
      #.#.#.###.#.#.#
      #.....#...#.#.#
      #.###.#.#.#.#.#
      #S..#.....#...#
      ###############""";

  @Test
  public void exampleA() {
    assertEquals(7036, Day16.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(101492, Day16.solveA(InputReader.read("day16-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(45, Day16.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(543, Day16.solveB(InputReader.read("day16-input")));
  }
}