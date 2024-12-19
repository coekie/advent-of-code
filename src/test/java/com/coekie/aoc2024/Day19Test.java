package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day19Test {

  public static final String example = """
      r, wr, b, g, bwu, rb, gb, br
      
      brwrr
      bggr
      gbbr
      rrbgbr
      ubwu
      bwurrg
      brgr
      bbrgwb""";

  @Test
  public void exampleA() {
    assertEquals(6, Day19.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(308, Day19.solveA(InputReader.read("day19-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(16, Day19.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(662726441391898L, Day19.solveB(InputReader.read("day19-input")));
  }
}