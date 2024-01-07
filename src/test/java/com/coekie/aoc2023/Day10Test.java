package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day10Test {

  private static final String square1 = """
      .....
      .S-7.
      .|.|.
      .L-J.
      .....""";
  private static final String square2 = """
      -L|F7
      7S-7|
      L|7||
      -L-J|
      L|-JF""";
  private static final String complex = """
      ..F7.
      .FJ|.
      SJ.L7
      |F--J
      LJ...""";

  @Test
  public void exampleSquare1A() {
    assertEquals(4, Day10.solveA(square1));
  }

  @Test
  public void exampleSquare2A() {
    assertEquals(4, Day10.solveA(square2));
  }

  @Test
  public void exampleComplexA() {
    assertEquals(8, Day10.solveA(complex));
  }

  @Test
  public void realA() {
    assertEquals(6927, Day10.solveA(InputReader.read("day10-input")));
  }

  @Test
  public void exampleSquare1B() {
    assertEquals(1, Day10.solveB(square1));
  }

  @Test
  public void exampleSquare2B() {
    assertEquals(1, Day10.solveB(square2));
  }

  @Test
  public void exampleComplexB() {
    assertEquals(1, Day10.solveB(complex));
  }

  @Test
  public void exampleOpeningB() {
    assertEquals(4, Day10.solveB("""
        ...........
        .S-------7.
        .|F-----7|.
        .||.....||.
        .||.....||.
        .|L-7.F-J|.
        .|..|.|..|.
        .L--J.L--J.
        ..........."""));
  }

  @Test
  public void exampleLargerB() {
    assertEquals(8, Day10.solveB("""
        .F----7F7F7F7F-7....
        .|F--7||||||||FJ....
        .||.FJ||||||||L7....
        FJL7L7LJLJ||LJ.L-7..
        L--J.L7...LJS7F-7L7.
        ....F-J..F7FJ|L7L7L7
        ....L7.F7||L7|.L7L7|
        .....|FJLJ|FJ|F7|.LJ
        ....FJL-7.||.||||...
        ....L---J.LJ.LJLJ..."""));
  }
  @Test
  public void exampleLarger2B() {
    assertEquals(10, Day10.solveB("""
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L"""));
  }

  @Test
  public void realB() {
    assertEquals(467, Day10.solveB(InputReader.read("day10-input")));
  }
}