package com.coekie.aoc2023;

import static com.coekie.aocutil.Direction.E;
import static com.coekie.aocutil.Direction.N;
import static com.coekie.aocutil.Direction.S;
import static com.coekie.aocutil.Direction.W;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import com.google.common.collect.Streams;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

// https://adventofcode.com/2023/day/16
class Day16 {
  static long solveA(String input) {
    Grid grid = Grid.parse(input);
    return energized(grid, new Beam(new Location(0, 0), E));
  }

  static long solveB(String input) {
    Grid grid = Grid.parse(input);

    return Streams.concat(
            IntStream.range(0, grid.width())
                .mapToObj(x -> new Beam(new Location(x, 0), S)),
            IntStream.range(0, grid.width())
                .mapToObj(x -> new Beam(new Location(x, grid.height() - 1), N)),
            IntStream.range(0, grid.height())
                .mapToObj(y -> new Beam(new Location(0, y), E)),
            IntStream.range(0, grid.height())
                .mapToObj(y -> new Beam(new Location(grid.width() - 1, y), W)))
        .mapToLong(startBeam -> energized(grid, startBeam))
        .max().orElseThrow();
  }

  private static long energized(Grid grid, Beam startBeam) {
    Set<Beam> beams = new HashSet<>();
    shine(grid, beams, startBeam);

    return beams.stream()
        .map(Beam::location)
        .distinct()
        .count();
  }

  private static void shine(Grid grid, Set<Beam> beams, Beam beam) {
    if (!beams.add(beam)) {
      return;
    }
    for (Beam newBeam : next(grid, beam)) {
      shine(grid, beams, newBeam);
    }
  }

  static List<Beam> next(Grid grid, Beam beam) {
    char c = grid.get(beam.location);
    return newDirections(c, beam.direction).stream()
        .map(d -> new Beam(beam.location.step(d), d))
        .filter(b -> grid.isInBounds(b.location))
        .toList();
  }

  static EnumSet<Direction> newDirections(char c, Direction direction) {
    return switch (c) {
      case '.' -> EnumSet.of(direction);
      case '/' -> EnumSet.of(switch (direction) {
        case N -> E;
        case W -> S;
        case S -> W;
        case E -> N;
      });
      case '\\' -> EnumSet.of(switch (direction) {
        case N -> W;
        case W -> N;
        case S -> E;
        case E -> S;
      });
      case '-' -> switch (direction) {
        case W, E -> EnumSet.of(direction);
        case N, S -> EnumSet.of(W, E);
      };
      case '|' -> switch (direction) {
        case N, S -> EnumSet.of(direction);
        case W, E -> EnumSet.of(N, S);
      };
      default -> throw new UnsupportedOperationException();
    };
  }

  record Beam(Location location, Direction direction) {

  }
}
