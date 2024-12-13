package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day12Test {
  @Test
  public void exampleA() {
    assertEquals(1930, Day12.solveA("""
        RRRRIICCFF
        RRRRIICCCF
        VVRRRCCFFF
        VVRCCCJFFF
        VVVVCJJCFE
        VVIVCCJJEE
        VVIIICJJEE
        MIIIIIJJEE
        MIIISIJEEE
        MMMISSJEEE"""));
  }

  @Test
  public void realA() {
    assertEquals(1363484, Day12.solveA(InputReader.read("day12-input").trim()));
  }

  @Test
  public void exampleB() {
    assertEquals(80, Day12.solveB("""
        AAAA
        BBCD
        BBCC
        EEEC"""));
  }

  @Test
  public void exampleB_E() {
    assertEquals(236, Day12.solveB("""
        EEEEE
        EXXXX
        EEEEE
        EXXXX
        EEEEE"""));
  }

  @Test
  public void exampleB_diagonal() {
    assertEquals(368, Day12.solveB("""
        AAAAAA
        AAABBA
        AAABBA
        ABBAAA
        ABBAAA
        AAAAAA"""));
  }

  @Test
  public void realB() {
    assertEquals(838988, Day12.solveB(InputReader.read("day12-input").trim()));
  }
}