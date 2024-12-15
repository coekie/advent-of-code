package com.coekie.aoc2024;

import static com.coekie.aocutil.Direction.E;
import static com.coekie.aocutil.Direction.N;
import static com.coekie.aocutil.Direction.S;
import static com.coekie.aocutil.Direction.W;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;

// https://adventofcode.com/2024/day/15
public class Day15 {
  static class Warehouse {
    final Grid grid;
    Location robot;

    Warehouse(String layout) {
      grid = Grid.parse(layout);
      robot = grid.findFirst('@');
      grid.set(robot, '.');
    }

    void move(String moves) {
      for (char move : moves.toCharArray()) {
        switch (move) {
          case '^' -> move(N);
          case '<' -> move(W);
          case 'v' -> move(S);
          case '>' -> move(E);
        }
      }
    }

    void move(Direction dir) {
      Location target = robot.step(dir);
      if (canPush(target, dir)) {
        doPush(target, dir);
        robot = target;
      }
    }

    boolean canPush(Location loc, Direction dir) {
      if (!grid.isInBounds(loc)) {
        return false;
      }
      char c = grid.get(loc);
      return switch (c) {
        case '#' -> false;
        case '.' -> true;
        case 'O' -> canPush(loc.step(dir), dir);
        case '[', ']' -> canPush(loc.step(dir), dir)
                && (dir.dy == 0 || canPush(loc.step(c == '[' ? E : W).step(dir), dir));
        default -> throw new RuntimeException();
      };
    }

    void doPush(Location loc, Direction dir) {
      char c = grid.get(loc);
      if (c == 'O' || c == '[' || c == ']') {
        Location next = loc.step(dir);
        doPush(next, dir);
        grid.set(loc, '.');
        grid.set(next, c);
        if (c != 'O' && dir.dx == 0) {
          // push the other half of this box
          Location other = loc.step(c == '[' ? E : W);
          Location otherNext = other.step(dir);
          doPush(otherNext, dir);
          grid.set(other, '.');
          grid.set(otherNext, c == '[' ? ']' : '[');
        }
      }
    }

    int score(char c) {
      return grid.locations()
          .mapToInt(loc -> grid.get(loc) == c ? loc.x() + loc.y() * 100 : 0)
          .sum();
    }
  }

  static long solveA(String input) {
    String[] split = input.split("\n\n");
    Warehouse wh = new Warehouse(split[0]);
    wh.move(split[1]);
    return wh.score('O');
  }

  static long solveB(String input) {
    String[] split = input.split("\n\n");
    StringBuilder sb = new StringBuilder();
    split[0].chars().forEach(c -> sb.append(switch (c) {
      case '#' -> "##"; case 'O' -> "[]"; case '.' -> ".."; case '@' -> "@.";
      case '\n' -> "\n"; default -> throw new RuntimeException();
    }));
    Warehouse wh = new Warehouse(sb.toString());
    wh.move(split[1]);
    return wh.score('[');
  }
}
