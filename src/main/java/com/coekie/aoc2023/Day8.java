package com.coekie.aoc2023;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toMap;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

// https://adventofcode.com/2023/day/8
class Day8 {
  record Node(String name, String left, String right) {
  }

  static long solveA(String input) {
    List<String> lines = input.lines().toList();

    char[] directions = lines.getFirst().toCharArray();

    Map<String, Node> nodes = lines.stream()
        .skip(2)
        .map(line -> new Node(line.substring(0, 3), line.substring(7, 10), line.substring(12, 15)))
        .collect(toMap(Node::name, n -> n));

    Node node = nodes.get("AAA");
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      node = switch (directions[i % directions.length]) {
        case 'L' -> nodes.get(node.left);
        case 'R' -> nodes.get(node.right);
        default -> throw new AssertionError();
      };
      if (node.name.equals("ZZZ")) {
        return i + 1;
      }
    }
    throw new AssertionError();
  }

  static long solveB(String input) {
    Network network = Network.parse(input);

    Set<Node> startNodesSet = network.nodesMap.values().stream().filter(n -> n.name.endsWith("A"))
        .collect(Collectors.toSet());

    Series series = startNodesSet.stream()
        .map(startNode -> toSeries(network, startNode))
        .reduce(Day8::merge)
        .orElseThrow();

    return new Position(series, 0).nextZSteps();
  }

  record Network(char[] directions, Map<String, Node> nodesMap) {
    Node step(Node node, int i) {
      return switch (directions[i % directions.length]) {
        case 'L' -> nodesMap.get(node.left);
        case 'R' -> nodesMap.get(node.right);
        default -> throw new AssertionError();
      };
    }

    static Network parse(String input) {
      List<String> lines = input.lines().toList();
      char[] directions = lines.getFirst().toCharArray();

      Map<String, Node> nodesMap = lines.stream()
          .skip(2)
          .map(line -> new Node(line.substring(0, 3), line.substring(7, 10), line.substring(12, 15)))
          .collect(toMap(Node::name, n -> n));
      return new Network(directions, nodesMap);
    }
  }

  static Series toSeries(Network network, Node startNode) {
    TreeSet<Long> zs = new TreeSet<>();
    Map<String, Integer> cyclicPath = new HashMap<>();

    Node node = startNode;
    for (int i = 0; i < Integer.MAX_VALUE; i++) {
      if (node.name.endsWith("Z")) {
        zs.add((long) i);
      }
      node = network.step(node, i);

      // if we've gone through a whole cycle of directions
      if ((i + 1) % network.directions.length == 0) {
        // if we're back where we've already been, then we've found a cycle, the series is complete
        if (cyclicPath.containsKey(node.name)) {
          return new Series(cyclicPath.get(node.name) + 1, i + 1, zs);
        }

        cyclicPath.put(node.name, i);
      }
    }
    throw new AssertionError();
  }

  record Series(long prefixLength, long length, TreeSet<Long> zs) {
    Series {
      checkArgument(prefixLength < length);
      checkArgument(!zs.isEmpty());
      checkArgument(zs.ceiling(prefixLength) != null); // there must be a Z in every cycle
    }

    long period() {
      return length - prefixLength;
    }
  }

  record Position(Series series, long pos) {
    Position advance(long steps) {
      return new Position(series, pos + steps);
    }

    long seriesPos() {
      if (pos < series.length) {
        return pos;
      }
      return series.prefixLength + ((pos - series.prefixLength) % series.period());
    }

    long nextZSteps() {
      long seriesPos = seriesPos();
      Long nextZPos = series.zs.ceiling(seriesPos + 1);
      if (nextZPos != null) {
        return nextZPos - seriesPos;
      } else {
        // steps until end + steps to first Z
        return (series.length - seriesPos)
            + (checkNotNull(series.zs.ceiling(series.prefixLength)) - series.prefixLength);
      }
    }

    boolean isZ() {
      return series.zs().contains(seriesPos());
    }
  }

  static Series merge(Series s1, Series s2) {
    long prefixLength = Math.max(s1.prefixLength, s2.prefixLength);

    BigInteger s1Period = BigInteger.valueOf(s1.period());
    BigInteger s2Period = BigInteger.valueOf(s2.period());
    long period = s1Period.multiply(s2Period).divide(s1Period.gcd(s2Period)).longValue();
    long len = prefixLength + period;

    Position pos1 = new Position(s1, 0);
    Position pos2 = new Position(s2, 0);

    TreeSet<Long> zs = new TreeSet<>();
    long pos = 0;
    while (true) {
      long nextSteps = Math.max(pos1.nextZSteps(), pos2.nextZSteps());
      pos += nextSteps;
      if (pos >= len) break;
      pos1 = pos1.advance(nextSteps);
      pos2 = pos2.advance(nextSteps);
      if (pos1.isZ() && pos2.isZ()) {
        zs.add(pos);
      }
    }
    return new Series(prefixLength, len, zs);
  }
}
