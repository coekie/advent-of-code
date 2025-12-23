package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Test {

  @Test
  public void exampleA() {
    assertEquals(5, Day11.solveA("""
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out"""));
  }

  @Test
  public void realA() {
    assertEquals(758, Day11.solveA(InputReader.read("day11-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(2, Day11.solveB("""
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out"""));
  }

  @Test
  public void realB() {
    assertEquals(490695961032000L, Day11.solveB(InputReader.read("day11-input")));
  }
}
