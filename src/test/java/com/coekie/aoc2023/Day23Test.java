package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2023.Day23.Part;
import org.junit.jupiter.api.Test;

class Day23Test {
  private static final String example = """
      #.#####################
      #.......#########...###
      #######.#########.#.###
      ###.....#.>.>.###.#.###
      ###v#####.#v#.###.#.###
      ###.>...#.#.#.....#...#
      ###v###.#.#.#########.#
      ###...#.#.#.......#...#
      #####.#.#.#######.#.###
      #.....#.#.#.......#...#
      #.#####.#.#.#########v#
      #.#...#...#...###...>.#
      #.#.#v#######v###.###v#
      #...#.>.#...>.>.#.###.#
      #####v#.#.###v#.#.###.#
      #.....#...#...#.#.#...#
      #.#########.###.#.#.###
      #...###...#...#...#.###
      ###.###.#.###v#####v###
      #...#...#.#.>.>.#.>.###
      #.###.###.#.###.#.#v###
      #.....###...###...#...#
      #####################.#""";

  @Test
  public void exampleA() {
    assertEquals(94, Day23.solve(example, Part.A));
  }

  @Test
  public void realA() {
    assertEquals(2034, Day23.solve(InputReader.read("day23-input"), Part.A));
  }

  @Test
  public void exampleB() {
    assertEquals(154, Day23.solve(example, Part.B));
  }

  @Test
  public void realB() {
    assertEquals(6302, Day23.solve(InputReader.read("day23-input"), Part.B));
  }
}