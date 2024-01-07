package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day15Test {
  @Test
  public void hash() {
    assertEquals(52, Day15.hash("HASH"));
  }

  @Test
  public void exampleA() {
    assertEquals(1320, Day15.solveA("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"));
  }

  @Test
  public void realA() {
    assertEquals(513172, Day15.solveA(InputReader.read("day15-input").strip()));
  }

  @Test
  public void exampleB() {
    assertEquals(145, Day15.solveB("rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7"));
  }

  @Test
  public void realB() {
    assertEquals(237806, Day15.solveB(InputReader.read("day15-input").strip()));
  }
}