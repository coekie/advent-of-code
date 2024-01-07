package com.coekie.aoc2023;

import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.math3.util.Combinations;

// https://adventofcode.com/2023/day/11
class Day11 {
  record Point(int x, int y) {
  }

  static long solve(String input, int expansion) {
    List<String> lines = input.lines().toList();
    List<Point> points = new ArrayList<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          points.add(new Point(x, y));
        }
      }
    }

    Set<Integer> xWithGalaxy = points.stream().map(Point::x).collect(Collectors.toSet());
    Set<Integer> yWithGalaxy = points.stream().map(Point::y).collect(Collectors.toSet());
    long[] xMapping = toMapping(xWithGalaxy, lines.getFirst().length(), expansion);
    long[] yMapping = toMapping(yWithGalaxy, lines.size(), expansion);

    return Streams.stream(new Combinations(points.size(), 2))
        .mapToLong(combo -> {
          Point p0 = points.get(combo[0]);
          Point p1 = points.get(combo[1]);
          return Math.abs(yMapping[p1.y] - yMapping[p0.y])
              + Math.abs(xMapping[p1.x] - xMapping[p0.x]);
        })
        .sum();
  }

  static long[] toMapping(Set<Integer> withGalaxy, int size, int expansion) {
    long[] mapping = new long[size];
    long destY = 0;
    for (int y = 0; y < size; y++) {
      mapping[y] = destY;
      destY += withGalaxy.contains(y) ? 1 : expansion;
    }
    return mapping;
  }
}
