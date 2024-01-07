package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day13Test {
  private static final String example1 = """
      #.##..##.
      ..#.##.#.
      ##......#
      ##......#
      ..#.##.#.
      ..##..##.
      #.#.##.#.""";
  private static final String example2 = """
      #...##..#
      #....#..#
      ..##..###
      #####.##.
      #####.##.
      ..##..###
      #....#..#""";
  private static final String examples = example1 + "\n\n" + example2;

  @Test
  public void exampleA() {
    assertEquals(5, Day13.solveOne(example1, 0));
    assertEquals(400, Day13.solveOne(example2, 0));
    assertEquals(405, Day13.solve(examples, 0));
  }

  @Test
  public void realA() {
    assertEquals(29165, Day13.solve(InputReader.read("day13-input"), 0));
  }

  @Test
  public void exampleB() {
    assertEquals(300, Day13.solveOne(example1, 1));
    assertEquals(100, Day13.solveOne(example2, 1));
    assertEquals(400, Day13.solve(examples, 1));
  }

  @Test
  public void realB() {
    assertEquals(32192, Day13.solve(InputReader.read("day13-input"), 1));
  }

  @Test
  public void isColumnMirror() {
    List<String> example1Lines = example1.lines().toList();
    assertFalse(Day13.isColumnMirror(example1Lines, 3, 0));
    assertTrue(Day13.isColumnMirror(example1Lines, 4, 0));
    assertFalse(Day13.isColumnMirror(example1Lines, 5, 0));
  }

  @Test
  public void isRowMirror() {
    List<String> example2Lines = example2.lines().toList();
    assertFalse(Day13.isRowMirror(example2Lines, 2, 0));
    assertTrue(Day13.isRowMirror(example2Lines, 3, 0));
    assertFalse(Day13.isRowMirror(example2Lines, 4, 0));
  }
}