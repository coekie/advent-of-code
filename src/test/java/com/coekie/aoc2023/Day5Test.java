package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day5Test {
  static final String example = """
      seeds: 79 14 55 13
            
      seed-to-soil map:
      50 98 2
      52 50 48
            
      soil-to-fertilizer map:
      0 15 37
      37 52 2
      39 0 15
            
      fertilizer-to-water map:
      49 53 8
      0 11 42
      42 0 7
      57 7 4
            
      water-to-light map:
      88 18 7
      18 25 70
            
      light-to-temperature map:
      45 77 23
      81 45 19
      68 64 13
            
      temperature-to-humidity map:
      0 69 1
      1 0 69
            
      humidity-to-location map:
      60 56 37
      56 93 4""";

  @Test
  public void exampleA() {
    assertEquals(35, Day5.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(806029445, Day5.solveA(InputReader.read("day5-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(46, Day5.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(59370572, Day5.solveB(InputReader.read("day5-input")));
  }
}