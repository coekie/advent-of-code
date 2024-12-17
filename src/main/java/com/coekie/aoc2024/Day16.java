package com.coekie.aoc2024;

import static java.util.Comparator.comparing;

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
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

// https://adventofcode.com/2024/day/16
public class Day16 {
  record State(Location loc, Direction dir) {}

  record Path(State state, List<Path> prev, int score) {
    Path step(Direction d, int cost) {
      return new Path(new State(state.loc.step(d), d), new ArrayList<>(List.of(this)),
          score + cost);
    }
  }

  static long solveA(String input) {
    return findPath(input).score;
  }

  static long solveB(String input) {
    Path path = findPath(input);
    Deque<Path> queue = new ArrayDeque<>(List.of(path));
    Set<Location> locations = new HashSet<>();
    while (!queue.isEmpty()) {
      Path p = queue.remove();
      locations.add(p.state.loc);
      queue.addAll(p.prev);
    }
    return locations.size();
  }

  static Path findPath(String input) {
    Grid grid = Grid.parse(input);
    Path start = new Path(new State(grid.findFirst('S'), Direction.E), List.of(), 0);
    Map<State, Path> bestPaths = new HashMap<>();
    PriorityQueue<Path> queue = new PriorityQueue<>(comparing(Path::score));
    queue.add(start);
    while (!queue.isEmpty()) {
      Path current = queue.remove();
      Path bestPath = bestPaths.get(current.state);
      if (bestPath == null) {
        if (grid.get(current.state.loc) == 'E') {
          return current;
        }
        bestPaths.put(current.state, current);
        queue.addAll(getNext(grid, current));
      } else if (bestPath.score == current.score) {
        bestPath.prev().addAll(current.prev);
      }
    }
    throw new RuntimeException("No path found");
  }

  static List<Path> getNext(Grid grid, Path p) {
    return Stream.of(p.step(p.state.dir, 1),
            p.step(p.state.dir.left(), 1001),
            p.step(p.state.dir.right(), 1001))
        .filter(s -> grid.get(s.state().loc()) != '#')
        .toList();
  }
}
