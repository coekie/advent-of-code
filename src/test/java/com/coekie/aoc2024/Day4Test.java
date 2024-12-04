package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day4Test {
  @Test
  public void exampleA() {
    assertEquals(18, Day4.solveA("""
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX"""));
  }

  @Test
  public void realA() {
    assertEquals(2496, Day4.solveA(InputReader.read("day4-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(9, Day4.solveB("""
            MMMSXXMASM
            MSAMXMSMSA
            AMXSXMAAMM
            MSAMASMSMX
            XMASAMXAMM
            XXAMMXXAMA
            SMSMSASXSS
            SAXAMASAAA
            MAMMMXMMMM
            MXMXAXMASX"""));
  }

  @Test
  public void realB() {
    assertEquals(1967, Day4.solveB(InputReader.read("day4-input")));
  }
}