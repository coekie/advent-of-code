package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.coekie.aoc2024.Day3.Parser;
import org.junit.jupiter.api.Test;

class Day3Test {
  @Test
  public void read() {
    Parser parser = new Parser("abc");
    assertTrue(parser.read("a"));
    assertTrue(parser.read("b"));
    assertFalse(parser.read("x"));
    assertTrue(parser.read("c"));
    assertFalse(parser.read("c"));
  }

  @Test
  public void readNumber() {
    Parser parser = new Parser("a123456b");
    assertEquals(-1, parser.readNumber3());
    assertTrue(parser.read("a"));
    assertEquals(123, parser.readNumber3());
    assertEquals(456, parser.readNumber3());
    assertTrue(parser.read("b"));
  }

  @Test
  public void exampleA() {
    assertEquals(161, Day3.solveA(
        "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"));
  }

  @Test
  public void realA() {
    assertEquals(185797128, Day3.solveA(InputReader.read("day3-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(48, Day3.solveB(
        "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"));
  }

  @Test
  public void realB() {
    assertEquals(89798695, Day3.solveB(InputReader.read("day3-input")));
  }
}