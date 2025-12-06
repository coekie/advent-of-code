package com.coekie.aoc2025;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {
  private static final String example = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224," +
      "1698522-1698528,446443-446449,38593856-38593862,565653-565659," +
      "824824821-824824827,2121212118-2121212124";

  @Test
  public void exampleA() {
    assertEquals(1227775554, Day2.solveA(example));
  }

  @Test
  public void examplesA() {
    assertEquals(33, Day2.solveA(11, 22));
    assertEquals(99, Day2.solveA(95, 115));
    assertEquals(1010, Day2.solveA(998, 1012));
    assertEquals(1188511885, Day2.solveA(1188511880, 1188511890));
    assertEquals(222222, Day2.solveA(222220, 222224));
    assertEquals(0, Day2.solveA(1698522, 1698528));
    assertEquals(446446, Day2.solveA(446443, 446449));
    assertEquals(38593859, Day2.solveA(38593856, 38593862));
    assertEquals(0, Day2.solveA(565653, 565659));
    assertEquals(0, Day2.solveA(824824821, 824824827));
    assertEquals(0, Day2.solveA(2121212118, 2121212124));
  }

  @Test
  public void realA() {
    assertEquals(35367539282L, Day2.solveA(InputReader.read("day2-input")));
  }

  @Test
  public void next() {
    assertEquals(11, Day2.next(11, 10));
    assertEquals(22, Day2.next(12, 10));

    assertEquals(99, Day2.next(95, 10));
    assertEquals(111, Day2.next(100, 10));
    assertEquals(222, Day2.next(112, 10));

    assertEquals(999, Day2.next(998, 10));
    assertEquals(1010, Day2.next(998, 100));

    assertEquals(11, Day2.next(1, 10));
    assertEquals(11, Day2.next(2, 10));
  }

  @Test
  public void findNext() {
    assertEquals(11, Day2.findNext(11, 22));
    assertEquals(22, Day2.findNext(12, 22));
    assertEquals(-1, Day2.findNext(23, 23));

    assertEquals(99, Day2.findNext(95, 115));
    assertEquals(111, Day2.findNext(100, 115));
    assertEquals(-1, Day2.findNext(112, 115));

    assertEquals(999, Day2.findNext(998, 1012));
    assertEquals(1010, Day2.findNext(1000, 1012));
    assertEquals(-1, Day2.findNext(1011, 1012));
  }

  @Test
  public void exampleB() {
    assertEquals(4174379265L, Day2.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(45814076230L, Day2.solveB(InputReader.read("day2-input")));
  }
}
