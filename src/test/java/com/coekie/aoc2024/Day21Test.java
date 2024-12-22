package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2024.Day21.KeyPad;
import org.junit.jupiter.api.Test;

class Day21Test {
  @Test
  public void exampleAparts() {
    KeyPad dirpad2 = Day21.keypad0().dirpad().dirpad();
    assertEquals(68, Day21.length("029A", dirpad2));
    assertEquals(60, Day21.length("980A", dirpad2));
    assertEquals(68, Day21.length("179A", dirpad2));
    assertEquals(64, Day21.length("456A", dirpad2));
    assertEquals(64, Day21.length("379A", dirpad2));
  }

  @Test
  public void exampleA() {
    assertEquals(126384, Day21.solveA("""
        029A
        980A
        179A
        456A
        379A"""));
  }

  @Test
  public void realA() {
    assertEquals(184180, Day21.solveA(InputReader.read("day21-input")));
  }

  @Test
  public void realB() {
    assertEquals(231309103124520L, Day21.solveB(InputReader.read("day21-input")));
  }
}