package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.coekie.aoc2023.Day12.Row;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day12Test {
  @Test
  public void exampleA() {
    assertEquals(21, Day12.solveA("""
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1"""));
  }

  @Test
  public void realA() {
    assertEquals(7460, Day12.solveA(InputReader.read("day12-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(525152, Day12.solveB("""
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1"""));
  }

  @Test
  public void realB() {
    assertEquals(6720660274964L, Day12.solveB(InputReader.read("day12-input")));
  }

  @Test
  public void parse() {
    assertEquals(new Row("#.#.###", List.of(1,1,3)), Row.parse("#.#.### 1,1,3"));
  }

  @Test
  public void canStartWithDamaged() {
    assertTrue(Row.parse("#.#.### 1,1,3").canStartWithDamaged());
    assertTrue(Row.parse("?.#.### 1,1,3").canStartWithDamaged());
    assertFalse(Row.parse("..#.### 1,1,3").canStartWithDamaged());

    assertFalse(Row.parse("?.#.### 2,1,3").canStartWithDamaged());
    assertTrue(Row.parse("??.#.### 2,1,3").canStartWithDamaged());
  }

  @Test
  public void remainderIfFirstOperational() {
    assertEquals(Row.parse("# 1,99"), Row.parse(".# 1,99").remainderIfFirstOperational());
  }

  @Test
  public void remainderIfFirstDamaged() {
    assertEquals(Row.parse("? 99"), Row.parse("#.? 1,99").remainderIfFirstDamaged());
    assertEquals(Row.parse("? 99"), Row.parse("##.? 2,99").remainderIfFirstDamaged());
  }

  @Test
  public void arrangements() {
    assertEquals(1, new Day12().arrangements(Row.parse("???.### 1,1,3")));
    assertEquals(4, new Day12().arrangements(Row.parse(".??..??...?##. 1,1,3")));
    assertEquals(10, new Day12().arrangements(Row.parse("?###???????? 3,2,1")));
  }

  @Test
  public void unfold() {
    assertEquals(Row.parse(".#?.#?.#?.#?.# 1,1,1,1,1"), Row.parse(".# 1").unfold());
    assertEquals(Row.parse("???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3"),
        Row.parse("???.### 1,1,3").unfold());
  }
}