package com.coekie.aoc2023;

import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/2
class Day2 {
  static final Map<String, Integer> maxCubes = Map.of("red", 12, "green", 13, "blue", 14);

  record CubeSet(long count, String color) {
    static CubeSet parse(String str) {
      String[] split = str.split(" ");
      return new CubeSet(Long.parseLong(split[0]), split[1]);
    }
  }

  record Round(List<CubeSet> cubeSets) {
    static Round parse(String str) {
      return new Round(Splitter.on(", ").splitToStream(str).map(CubeSet::parse).toList());
    }
  }

  record Game(long id, List<Round> rounds) {
    static Game parse(String line) {
      String[] split = line.substring("Game ".length()).split(": ");
      return new Game(Long.parseLong(split[0]),
          Stream.of(split[1].split("; ")).map(Round::parse).toList());
    }
  }

  static long solveA(String input) {
    return input.lines()
        .mapToLong(Day2::solveLineA)
        .sum();
  }

  static long solveLineA(String line) {
    Game game = Game.parse(line);
    for (Round round : game.rounds) {
      for (CubeSet cubeSet : round.cubeSets) {
        if (cubeSet.count > maxCubes.getOrDefault(cubeSet.color, 0)) {
          return 0;
        }
      }
    }
    return game.id;
  }

  static long solveB(String input) {
    return input.lines()
        .mapToLong(Day2::solveLineB)
        .sum();
  }

  static long solveLineB(String line) {
    Game game = Game.parse(line);
    Map<String, Long> maxCubes = new HashMap<>();
    for (Round round : game.rounds) {
      for (CubeSet cubeSet : round.cubeSets) {
        maxCubes.merge(cubeSet.color, cubeSet.count, Long::max);
      }
    }

    return maxCubes.values().stream()
        .reduce((a, b) -> a * b)
        .orElseThrow();
  }
}
