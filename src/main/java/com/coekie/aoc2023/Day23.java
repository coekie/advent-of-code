package com.coekie.aoc2023;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/23
class Day23 {
  enum Part {
    A, B
  }

  static long solve(String input, Part part) {
    Grid grid = Grid.parse(input);
    Node startNode = toGraph(grid, part);

    return maxLength(startNode, target(grid), new HashSet<>());
  }

  static Location target(Grid grid) {
    return new Location(grid.width() - 2, grid.height() -1);
  }

  static int maxLength(Node node, Location target, HashSet<Node> visited) {
    if (node.location.equals(target)) {
      return 0;
    }
    visited.add(node);
    int result = node.edges.stream()
        .filter(edge -> !visited.contains(edge.dest))
        .mapToInt(edge -> edge.length + maxLength(edge.dest, target, visited))
        .max()
        .orElse(Integer.MIN_VALUE);

    visited.remove(node);
    return result;
  }

  static Node toGraph(Grid grid, Part part) {
    Node startNode = new Node(new Location(1, 0), new ArrayList<>());
    Map<Location, Node> nodes = new HashMap<>();
    Deque<Node> q = new ArrayDeque<>(List.of(startNode));
    while (!q.isEmpty()) {
      Node node = q.pop();
      destinations(grid, node.location, part, (dest, length) -> {
        Node destNode = nodes.get(dest);
        if (destNode == null) {
          destNode = new Node(dest, new ArrayList<>());
          nodes.put(dest, destNode);
          q.add(destNode);
        }
        node.edges.add(new Edge(destNode, length));
      });
    }
    return startNode;
  }

  static void destinations(Grid grid, Location from, Part part, ObjIntConsumer<Location> consumer) {
    next(grid, from, part)
        .forEach(loc -> {
          Location prev = from;
          for (int length = 1; ; length++) {
            List<Location> nextList = next(grid, loc, part)
                .filter(Predicate.not(prev::equals))
                .toList();
            if (nextList.size() == 1) {
              prev = loc;
              loc = nextList.getFirst();
            } else if (nextList.size() > 1
                || (loc.x() == grid.width() - 2 && loc.y() == grid.height() - 1)) {
              consumer.accept(loc, length);
              return;
            } else {
              return;
            }
          }
        });
  }

  static Stream<Location> next(Grid grid, Location from, Part part) {
    Stream<Direction> directions = part == Part.B
        ? Stream.of(Direction.values())
        : switch (grid.get(from)) {
        case '.' -> Stream.of(Direction.values());
        case '^' -> Stream.of(Direction.N);
        case '>' -> Stream.of(Direction.E);
        case 'v' -> Stream.of(Direction.S);
        case '<' -> Stream.of(Direction.W);
        default -> throw new AssertionError();
      };
    return directions
        .map(from::step)
        .filter(grid::isInBounds)
        .filter(loc -> grid.get(loc) != '#');
  }

  record Node(Location location, List<Edge> edges) {

    @Override
    public int hashCode() {
      return location.hashCode();
    }

    @Override
    public String toString() {
      return location.toString();
    }
  }
  
  record Edge(Node dest, int length) {
  }
}
