package com.coekie.aoc2023;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/17
class Day17 {
  static long solve(String input, Part part) {
    Grid grid = Grid.parse(input);

    PriorityQueue<ScoredState> queue = new PriorityQueue<>(
        Comparator.comparing(ScoredState::heatLoss)
            .thenComparing(ss -> ss.state.streak)
            .thenComparing(ss -> -ss.state.location.x())
            .thenComparing(ss -> -ss.state.location.y())
            .thenComparing(ss -> ss.state.direction));
    queue.add(new ScoredState(new State(new Location(0, 0), null, 0), 0, null));

    Map<State, Integer> heatLosses = new HashMap<>();

    Location destination = new Location(grid.width() - 1, grid.height() - 1);

    while (true) {
      ScoredState scoredState = queue.remove();
      if (scoredState.state.location.equals(destination)
          && scoredState.state.streak >= part.minMoves) {
        return scoredState.heatLoss;
      }
      for (ScoredState nextScoredState : next(grid, scoredState, part)) {
        Integer oldHeatLoss = heatLosses.get(nextScoredState.state);
        if (oldHeatLoss == null || nextScoredState.heatLoss < oldHeatLoss) {
          heatLosses.put(nextScoredState.state, nextScoredState.heatLoss);
          queue.add(nextScoredState);
        }
      }
    }
  }

  static List<ScoredState> next(Grid grid, ScoredState scoredState, Part part) {
    return next(grid, scoredState.state, part).stream()
        .map(state -> new ScoredState(state,
            scoredState.heatLoss + grid.get(state.location) - '0',
            scoredState))
        .toList();
  }

  static List<State> next(Grid grid, State oldState, Part part) {
    return Stream.of(Direction.values())
        .filter(d -> oldState.direction == null || d != oldState.direction.opposite())
        .map(d -> new State(
            oldState.location.step(d),
            d,
            d == oldState.direction ? oldState.streak + 1 : 1))
        .filter(s -> s.direction == oldState.direction || oldState.direction == null
            || oldState.streak >= part.minMoves)
        .filter(s -> s.streak <= part.maxMoves)
        .filter(s -> grid.isInBounds(s.location))
        .toList();
  }

  record State(Location location, Direction direction, int streak) {
  }

  record ScoredState(State state, int heatLoss, ScoredState prev) {
  }

  enum Part {
    A(0, 3),
    B(4, 10);

    final int minMoves;
    final int maxMoves;

    Part(int minMoves, int maxMoves) {
      this.minMoves = minMoves;
      this.maxMoves = maxMoves;
    }
  }
}
