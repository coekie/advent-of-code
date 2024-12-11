package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day11Test {

  @Test
  public void split() {
    assertEquals(-1, Day11.splitIfEven(1));
    assertEquals(-1, Day11.splitIfEven(9));
    assertEquals(10, Day11.splitIfEven(10));
    assertEquals(10, Day11.splitIfEven(99));
    assertEquals(-1, Day11.splitIfEven(100));
    assertEquals(-1, Day11.splitIfEven(999));
    assertEquals(100, Day11.splitIfEven(1000));
    assertEquals(100, Day11.splitIfEven(9999));
  }

  @Test
  public void exampleA() {
    assertEquals(55312, Day11.solveA("125 17"));
  }

  @Test
  public void realA() {
    assertEquals(198075, Day11.solveA(InputReader.read("day11-input").trim()));
  }

  @Test
  public void realB() {
    assertEquals(235571309320764L, Day11.solveB(InputReader.read("day11-input").trim()));
  }
}