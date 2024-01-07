package com.coekie.aoc2023;

import static com.google.common.base.Preconditions.checkState;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Location;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeSet;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

// https://adventofcode.com/2023/day/18
class Day18 {
  static long solve(String input, Part part) {
    // map from y location to x location, of where we move up or down
    HashMultimap<Integer, Integer> corners = HashMultimap.create();

    Location location = new Location(0, 0);

    for (String line : input.lines().toList()) {
      Instruction instruction = part.parse(line);
      location = location.step(instruction.direction, instruction.length);
      corners.put(location.y(), location.x());
    }
    checkState(location.equals(new Location(0, 0)), "Should end where we started");

    TreeSet<Integer> scanLine = new TreeSet<>();
    List<Integer> edgeYs = corners.keySet().stream().sorted().toList();

    long volume = 0;
    for (int i = 0; i < edgeYs.size(); i++) {
      TreeRangeSet<Integer> ranges = ranges(scanLine);
      int y = edgeYs.get(i);
      for (int x : corners.get(y)) {
        flip(scanLine, x);
      }

      // count volume of region below
      TreeRangeSet<Integer> newRanges = ranges(scanLine);
      if (!scanLine.isEmpty()) {
        int height = edgeYs.get(i + 1) - y - 1;
        volume += size(newRanges) * height;
      }

      // count volume of this row (union of ranges before and after)
      ranges.addAll(newRanges);
      volume += size(ranges);
    }

    return volume;
  }

  static void flip(Set<Integer> scanLine, int x) {
    if (!scanLine.remove(x)) {
      scanLine.add(x);
    }
  }

  static TreeRangeSet<Integer> ranges(TreeSet<Integer> scanLine) {
    checkState(scanLine.size() % 2 == 0);
    TreeRangeSet<Integer> result = TreeRangeSet.create();
    Iterator<Integer> it = scanLine.iterator();
    while (it.hasNext()) {
      result.add(Range.closed(it.next(), it.next()));
    }
    return result;
  }

  static long size(TreeRangeSet<Integer> ranges) {
    return ranges.asRanges().stream()
        .mapToInt(r -> r.upperEndpoint() - r.lowerEndpoint() + 1)
        .sum();
  }

  record Instruction(Direction direction, int length) {
  }

  static final Map<String, Direction> directions = Map.of(
      "R", Direction.E,
      "D", Direction.S,
      "L", Direction.W,
      "U", Direction.N);

  enum Part {
    A {
      @Override
      Instruction parse(String line) {
        String[] split = line.split(" ");
        return new Instruction(directions.get(split[0]), Integer.parseInt(split[1]));
      }
    },
    B {
      @Override
      Instruction parse(String line) {
        Direction direction = List.of(Direction.E, Direction.S, Direction.W, Direction.N)
            .get(line.charAt(line.indexOf(')') - 1) - '0');
        int length = HexFormat.fromHexDigits(line, line.indexOf('#') + 1, line.indexOf(')') - 1);
        return new Instruction(direction, length);
      }
    };

    abstract Instruction parse(String line);
  }
}
