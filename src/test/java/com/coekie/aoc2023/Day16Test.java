package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2023.Day16.Beam;
import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day16Test {
  private static final String example = """
      .|...\\....
      |.-.\\.....
      .....|-...
      ........|.
      ..........
      .........\\
      ..../.\\\\..
      .-.-/..|..
      .|....-|.\\
      ..//.|....
      """;

  @Test
  public void nextDot() {
    Grid grid = Grid.parse("""
        ...
        ...
        ...
        """);
    Beam beam = new Beam(new Location(1, 1), Direction.E);
    assertEquals(List.of(new Beam(new Location(2, 1), Direction.E)), Day16.next(grid, beam));
  }

  @Test
  public void nextMirror() {
    Grid grid = Grid.parse("""
        ...
        ./.
        ...
        """);
    Beam beam = new Beam(new Location(1, 1), Direction.E);
    assertEquals(List.of(new Beam(new Location(1, 0), Direction.N)), Day16.next(grid, beam));
  }

  @Test
  public void nextSplit() {
    Grid grid = Grid.parse("""
        ...
        .|.
        ...
        """);
    Beam beam = new Beam(new Location(1, 1), Direction.E);
    assertEquals(
        List.of(
            new Beam(new Location(1, 0), Direction.N),
            new Beam(new Location(1, 2), Direction.S)),
        Day16.next(grid, beam));
  }

  @Test
  public void exampleA() {
    assertEquals(46, Day16.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(7498, Day16.solveA(InputReader.read("day16-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(51, Day16.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(7846, Day16.solveB(InputReader.read("day16-input")));
  }
}