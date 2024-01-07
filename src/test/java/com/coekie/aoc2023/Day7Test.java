package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day7Test {
  @Test void countsA() {
    assertEquals("5", Day7.counts("AAAAA", 'J')); // Five of a kind
    assertEquals("41", Day7.counts("AA8AA", 'J')); // Four of a kind
    assertEquals("32", Day7.counts("23332", 'J')); // Full house
    assertEquals("311", Day7.counts("TTT98", 'J')); // Three of a kind
    assertEquals("221", Day7.counts("23432", 'J')); // Two pair
    assertEquals("2111", Day7.counts("A23A4", 'J')); // One pair
    assertEquals("11111", Day7.counts("23456", 'J')); // High card

    assertEquals("41", Day7.counts("QJJQ2", 'J')); // wildcard
    assertEquals("5", Day7.counts("JJJJJ", 'J')); // only wildcards
  }

  @Test void exampleA() {
    assertEquals(6440, Day7.solveA("""
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483"""));
  }

  @Test void realA() {
    assertEquals(249204891, Day7.solveA(InputReader.read("day7-input")));
  }

  @Test void exampleB() {
    assertEquals(5905, Day7.solveB("""
        32T3K 765
        T55J5 684
        KK677 28
        KTJJT 220
        QQQJA 483"""));
  }

  @Test void realB() {
    assertEquals(249666369, Day7.solveB(InputReader.read("day7-input")));
  }
}