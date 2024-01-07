package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day3Test {
  @Test
  public void exampleA() {
    assertEquals(4361, Day3.solveA("""
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598.."""));
  }

  @Test
  public void realA() {
    assertEquals(539713, Day3.solveA(InputReader.read("day3-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(467835, Day3.solveB("""
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598.."""));
  }

  @Test
  public void realB() {
    assertEquals(84159075, Day3.solveB(InputReader.read("day3-input")));
  }
}