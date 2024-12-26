package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day25Test {

  private static final String example = """
      #####
      .####
      .####
      .####
      .#.#.
      .#...
      .....
      
      #####
      ##.##
      .#.##
      ...##
      ...#.
      ...#.
      .....
      
      .....
      #....
      #....
      #...#
      #.#.#
      #.###
      #####
      
      .....
      .....
      #.#..
      ###..
      ###.#
      ###.#
      #####
      
      .....
      .....
      .....
      #....
      #.#..
      #.#.#
      #####""";

  @Test
  public void example() {
    assertEquals(3, Day25.solveA(example));
  }

  @Test
  public void real() {
    assertEquals(3395, Day25.solveA(InputReader.read("day25-input")));
  }
}