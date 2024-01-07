package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2023.Day18.Part;
import org.junit.jupiter.api.Test;

class Day18Test {
  private static final String example = """
      R 6 (#70c710)
      D 5 (#0dc571)
      L 2 (#5713f0)
      D 2 (#d2c081)
      R 2 (#59c680)
      D 2 (#411b91)
      L 5 (#8ceee2)
      U 2 (#caa173)
      L 1 (#1b58a2)
      U 2 (#caa171)
      R 2 (#7807d2)
      U 3 (#a77fa3)
      L 2 (#015232)
      U 2 (#7a21e3)""";

  @Test
  public void exampleA() {
    assertEquals(62, Day18.solve(example, Part.A));
  }

  @Test
  public void simpleRect1() {
    assertEquals(6, Day18.solve("""
        R 2
        D 1
        L 2
        U 1""", Part.A));
  }

  @Test
  public void simpleRect2() {
    assertEquals(6, Day18.solve("""
        R 1
        D 2
        L 1
        U 2""", Part.A));
  }

  @Test
  public void simple1() {
    // 0##### 5
    // 1#   # 5
    // 2### # 5
    // 3  ### 3
    assertEquals(18, Day18.solve("""
        R 4
        D 3
        L 2
        U 1
        L 2
        U 2""", Part.A));
  }

  @Test
  public void realA() {
    assertEquals(61865, Day18.solve(InputReader.read("day18-input"), Part.A));
  }

  @Test
  public void exampleB() {
    assertEquals(952408144115L, Day18.solve(example, Part.B));
  }

  @Test
  public void realB() {
    assertEquals(40343619199142L, Day18.solve(InputReader.read("day18-input"), Part.B));
  }
}