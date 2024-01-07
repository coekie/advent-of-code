package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2023.Day17.Part;
import org.junit.jupiter.api.Test;

class Day17Test {
  @Test
  public void exampleA() {
    assertEquals(102, Day17.solve("""
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533""", Part.A));
  }

  @Test
  public void realA() {
    assertEquals(870, Day17.solve(InputReader.read("day17-input"), Part.A));
  }

  @Test
  public void exampleB() {
    assertEquals(94, Day17.solve("""
        2413432311323
        3215453535623
        3255245654254
        3446585845452
        4546657867536
        1438598798454
        4457876987766
        3637877979653
        4654967986887
        4564679986453
        1224686865563
        2546548887735
        4322674655533""", Part.B));
  }

  @Test
  public void exampleB2() {
    assertEquals(71, Day17.solve("""
        111111111111
        999999999991
        999999999991
        999999999991
        999999999991""", Part.B));
  }

  @Test
  public void realB() {
    assertEquals(1063, Day17.solve(InputReader.read("day17-input"), Part.B));
  }
}