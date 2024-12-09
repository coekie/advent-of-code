package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class Day9Test {
  private static String toString(int[] disk) {
    return IntStream.of(disk).mapToObj(id -> id == -1 ? "." : "" + id)
        .collect(Collectors.joining());
  }

  @Test
  public void parseA() {
    assertEquals("00...111...2...333.44.5555.6666.777.888899",
        toString(Day9.parseA("2333133121414131402")));
  }

  @Test
  public void exampleA() {
    assertEquals(1928, Day9.solveA("2333133121414131402"));
  }

  @Test
  public void realA() {
    assertEquals(6211348208140L, Day9.solveA(InputReader.read("day9-input").trim()));
  }

  @Test
  public void exampleB() {
    assertEquals(2858, Day9.solveB("2333133121414131402"));
  }

  @Test
  public void realB() {
    assertEquals(6239783302560L, Day9.solveB(InputReader.read("day9-input").trim()));
  }
}