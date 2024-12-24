package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day23Test {

  private static final String example = """
      kh-tc
      qp-kh
      de-cg
      ka-co
      yn-aq
      qp-ub
      cg-tb
      vc-aq
      tb-ka
      wh-tc
      yn-cg
      kh-ub
      ta-co
      de-co
      tc-td
      tb-wq
      wh-td
      ta-ka
      td-qp
      aq-cg
      wq-ub
      ub-vc
      de-ta
      wq-aq
      wq-vc
      wh-yn
      ka-de
      kh-ta
      co-tc
      wh-qp
      tb-vc
      td-yn""";

  @Test
  public void exampleA() {
    assertEquals(7, Day23.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(1046, Day23.solveA(InputReader.read("day23-input")));
  }

  @Test
  public void exampleB() {
    assertEquals("co,de,ka,ta", Day23.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals("de,id,ke,ls,po,sn,tf,tl,tm,uj,un,xw,yz",
        Day23.solveB(InputReader.read("day23-input")));
  }
}