package com.coekie.aoc2023;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimaps;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/22
class Day22 {
  static long solveA(String input) {
    List<Brick> bricks = settle(input);
    return bricks.size() -
        bricks.stream().filter(b -> b.on.size() == 1)
            .map(b -> Iterables.getOnlyElement(b.on))
            .distinct().count();
  }

  static long solveB(String input) {
    List<Brick> bricks = settle(input);

    long result = 0;
    for (Brick brick : bricks) {
      Map<Brick, Integer> supportCount = bricks.stream()
          .collect(Collectors.toMap(b -> b, b -> b.on.size()));

      var q = new ArrayDeque<>(List.of(brick));
      while (!q.isEmpty()) {
        Brick b = q.pop();
        for (Brick unsupported : b.supports) {
          if (supportCount.compute(unsupported, (_, o) -> o - 1) == 0) {
            q.push(unsupported);
            result++;
          }
        }
      }
    }

    return result;
  }

  private static List<Brick> settle(String input) {
    List<Brick> bricks = new ArrayList<>();

    input.lines().map(line -> {
          int[] n = Stream.of(line.split("[,~]")).mapToInt(Integer::parseInt).toArray();
          return new Brick(n[0], n[1], n[2], n[3], n[4], n[5], Set.of(), Set.of());
        })
        .sorted(Comparator.comparing(b -> b.z1))
        .forEach(fallingBrick -> {
          HashMultimap<Integer, Brick> landingByZ = bricks.stream()
              .filter(fallingBrick::xyOverlaps)
              .collect(Multimaps.toMultimap(b -> b.z2, b -> b, HashMultimap::create));
          Brick landed;
          if (landingByZ.isEmpty()) {
            landed = fallingBrick.dropOn(0, Set.of());
          } else {
            int restZ = landingByZ.keySet().stream().mapToInt(i -> i).max().orElseThrow();
            landed = fallingBrick.dropOn(restZ, landingByZ.get(restZ));
            landed.on.forEach(b -> b.supports.add(landed));
          }
          bricks.add(landed);
        });

    return bricks;
  }

  record Brick(int x1, int y1, int z1, int x2, int y2, int z2, Set<Brick> on, Set<Brick> supports) {
    boolean xyOverlaps(Brick o) {
      return x1 <= o.x2 && o.x1 <= x2 && y1 <= o.y2 && o.y1 <= y2;
    }

    Brick dropOn(int restZ, Set<Brick> on) {
      return new Brick(x1, y1, restZ + 1, x2, y2, restZ + 1 + z2 - z1, on, new HashSet<>());
    }

    @Override
    public int hashCode() {
      return Objects.hash(x1, y1, z1); // avoid recursion into `on` and `supports`
    }
  }
}
