package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aocutil.Grid;
import com.google.common.base.Strings;
import org.junit.jupiter.api.Test;

class Day21Test {
  public static final String example = """
      ...........
      .....###.#.
      .###.##..#.
      ..#.#...#..
      ....#.#....
      .##..S####.
      .##..#...#.
      .......##..
      .##.#.####.
      .##..##.##.
      ...........""";

  @Test
  public void exampleA() {
    assertEquals(16, Day21.solve(example, 6));
  }

  @Test
  public void realA() {
    assertEquals(3649, Day21.solve(InputReader.read("day21-input"), 64));
  }

  @Test
  public void simpleB() {
    com.coekie.aocutil.Grid baseGrid = com.coekie.aocutil.Grid.parse("""
        .....
        .....
        .....
        .....
        .....""");

    Grid grid = expand(baseGrid, 6);

    for (int i = 0; i < 33; i++) {
      if (i % 2 == 1 && i > baseGrid.width() / 2) {
        long result = Day21.doB(baseGrid, i);
        int correct = Day21.countO(grid);
        if (result != correct) {
          print(grid, baseGrid.width());
          throw new AssertionError("After " + i + " expected " + correct + " got " + result);
        }
      }
      grid = Day21.step(grid);
    }
  }

  static void print(Grid grid, int markers) {
    for (int y = 0; y < grid.height(); y++) {
      if (y % markers == 0) {
        System.out.println(Strings.repeat("-", grid.width() + (grid.width() / markers) + 1));
      }
      for (int x = 0; x < grid.width(); x++) {
        if (x % markers == 0) System.out.print("|");
        System.out.print(grid.grid()[y][x]);
      }
      System.out.println("|");
    }
    System.out.println();
  }

  static Grid expand(Grid grid, int times) {
    int factor = 1 + (times * 2);
    Grid result = new Grid(new char[grid.height() * factor][grid.width() * factor]);
    for (int y = 0; y < result.height(); y++) {
      for (int x = 0; x < result.width(); x++) {
        result.set(x, y, grid.get(x % grid.width(), y % grid.height()));
      }
    }
    result.set(result.width() / 2, result.height() / 2, 'S');
    return result;
  }

  @Test
  public void realB() {
    assertEquals(612941134797232L,
        Day21.solveB(InputReader.read("day21-input"), 26501365));
  }
}