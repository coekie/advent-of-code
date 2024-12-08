package com.coekie.aoc2024;

import static com.google.common.collect.ImmutableListMultimap.toImmutableListMultimap;

import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.math3.util.Combinations;

// https://adventofcode.com/2024/day/8
public class Day8 {
  static long solveA(String input) {
    return solve(input, false);
  }

  static long solveB(String input) {
    return solve(input, true);
  }

  static long solve(String input, boolean all) {
    Grid grid = Grid.parse(input);

    ListMultimap<Character, Location> antennas = grid.locations()
        .filter(loc -> grid.get(loc) != '.' && grid.get(loc) != '#')
        .collect(toImmutableListMultimap(grid::get, loc -> loc));

    return antennas.keySet().stream()
        .flatMap(c -> {
          List<Location> locations = antennas.get(c);
          return Streams.stream(new Combinations(locations.size(), 2))
              .flatMap(combo -> anti(grid, locations.get(combo[0]), locations.get(combo[1]), all));
        })
        .distinct()
        .count();
  }

  static Stream<Location> anti(Grid grid, Location a, Location b, boolean all) {
    Location diff = b.minus(a);
    return all
        ? Stream.concat(
        Stream.iterate(a, grid::isInBounds, diff::plus),
        Stream.iterate(a, grid::isInBounds, p -> p.minus(diff)))
        : Stream.of(b.plus(diff), a.minus(diff)).filter(grid::isInBounds);
  }
}
