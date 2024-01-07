package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day20Test {
  @Test
  public void example1A() {
    assertEquals(32000000, Day20.solveA("""
        broadcaster -> a, b, c
        %a -> b
        %b -> c
        %c -> inv
        &inv -> a"""));
  }

  @Test
  public void example2A() {
    assertEquals(11687500, Day20.solveA("""
        broadcaster -> a
        %a -> inv, con
        &inv -> b
        %b -> con
        &con -> output"""));
  }

  @Test
  public void realA() {
    assertEquals(731517480, Day20.solveA(InputReader.read("day20-input")));
  }

    @Test
  public void realB() {
    assertEquals(244178746156661L, Day20.solveB(InputReader.read("day20-input")));
  }

}