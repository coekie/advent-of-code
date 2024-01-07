package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day1Test {
  @Test
  public void exampleA() {
    assertEquals(142, Day1.solveA("""
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet"""));
  }

  @Test
  public void realA() {
    assertEquals(54634, Day1.solveA(InputReader.read("day1-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(281, Day1.solveB("""
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen"""));
  }

  @Test
  public void realB() {
    assertEquals(53855, Day1.solveB(InputReader.read("day1-input")));
  }
}