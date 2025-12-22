package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Test {
  private static final String example = """
      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}""";

  @Test
  public void exampleA() {
    assertEquals(7, Day10.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(428, Day10.solveA(InputReader.read("day10-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(33, Day10.solveB(example));
  }

  @Test
  public void exampleB1() {
    assertEquals(10, Day10.solveB("[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"));
  }

  @Test
  public void exampleB2() {
    assertEquals(12, Day10.solveB("[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}"));
  }

  @Test
  public void exampleB3() {
    assertEquals(11, Day10.solveB("[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}"));
  }

  @Test
  public void realB() {
    assertEquals(16613, Day10.solveB(InputReader.read("day10-input")));
  }
}
