package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {
  private static final String example = """
      162,817,812
      57,618,57
      906,360,560
      592,479,940
      352,342,300
      466,668,158
      542,29,236
      431,825,988
      739,650,466
      52,470,668
      216,146,977
      819,987,18
      117,168,530
      805,96,715
      346,949,466
      970,615,88
      941,993,340
      862,61,35
      984,92,344
      425,690,689""";

  @Test
  public void exampleA() {
    assertEquals(40, Day8.solveA(example, 10));
  }

  @Test
  public void realA() {
    assertEquals(80446, Day8.solveA(InputReader.read("day8-input"), 1000));
  }

  @Test
  public void exampleB() {
    assertEquals(25272, Day8.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(51294528, Day8.solveB(InputReader.read("day8-input")));
  }
}
