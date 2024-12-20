package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day20Test {

  public static final String example = """
      ###############
      #...#...#.....#
      #.#.#.#.#.###.#
      #S#...#.#.#...#
      #######.#.#.###
      #######.#.#...#
      #######.#.###.#
      ###..E#...#...#
      ###.#######.###
      #...###...#...#
      #.#####.#.###.#
      #.#...#.#.#...#
      #.#.#.#.#.#.###
      #...#...#...###
      ###############""";

  @Test
  public void exampleA() {
    assertEquals(1, Day20.solveA(example, 64));
    assertEquals(2, Day20.solveA(example, 40));
    assertEquals(3, Day20.solveA(example, 38));
    assertEquals(4, Day20.solveA(example, 26));
    assertEquals(5, Day20.solveA(example, 20));
  }

  @Test
  public void realA() {
    assertEquals(1369, Day20.solveA(InputReader.read("day20-input"), 100));
  }

  @Test
  public void exampleB() {
    assertEquals(3, Day20.solveB(example, 20, 76));
    assertEquals(3 + 4, Day20.solveB(example, 20, 74));
    assertEquals(3 + 4 + 22, Day20.solveB(example, 20, 72));
    assertEquals(3 + 4 + 22 + 12, Day20.solveB(example, 20, 70));
    assertEquals(3 + 4 + 22 + 12 + 14, Day20.solveB(example, 20, 68));
    assertEquals(3 + 4 + 22 + 12 + 14 + 12, Day20.solveB(example, 20, 66));
    assertEquals(3 + 4 + 22 + 12 + 14 + 12 + 19, Day20.solveB(example, 20, 64));
    assertEquals(3 + 4 + 22 + 12 + 14 + 12 + 19 + 20, Day20.solveB(example, 20, 62));
    assertEquals(3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23, Day20.solveB(example, 20, 60));
    assertEquals(3 + 4 + 22 + 12 + 14 + 12 + 19 + 20 + 23 + 25, Day20.solveB(example, 20, 58));
  }

  @Test
  public void realB() {
    assertEquals(979012, Day20.solveB(InputReader.read("day20-input"), 20, 100));
  }
}