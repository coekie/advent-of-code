package com.coekie.aoc2023;

import com.coekie.aocutil.Direction;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/10
class Day10 {
  record Pipe(char c, Direction dir1, Direction dir2) {
    boolean has(Direction dir) {
      return dir1 == dir || dir2 == dir;
    }

    Direction other(Direction dir) {
      if (dir1 == dir) {
        return dir2;
      } else if (dir2 == dir) {
        return dir1;
      } else {
        throw new RuntimeException("Going the wrong way: " + dir + " on " + this);
      }
    }
  }

  static final Map<Character, Pipe> charToPipe = Stream.of(
      new Pipe('|', Direction.N, Direction.S),
      new Pipe('-', Direction.E, Direction.W),
      new Pipe('L', Direction.N, Direction.E),
      new Pipe('J', Direction.N, Direction.W),
      new Pipe('7', Direction.S, Direction.W),
      new Pipe('F', Direction.S, Direction.E))
      .collect(Collectors.toMap(Pipe::c, p -> p));

  record Board(Pipe[][] grid, int startX, int startY) {
    Pipe get(int y, int x) {
      if (y < 0 || x < 0 || y >= grid.length || y >= grid[0].length) {
        return null;
      } else {
        return grid[y][x];
      }
    }

    int sizeY() {
      return grid.length;
    }

    int sizeX() {
      return grid[0].length;
    }
  }

  static Board parse(String input) {
    List<String> lines = input.lines().toList();
    int startX = -1;
    int startY = -1;

    Pipe[][] grid = new Pipe[lines.size()][lines.getFirst().length()];

    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.toCharArray().length; x++) {
        char c = line.charAt(x);
        if (c == 'S') {
          startX = x;
          startY = y;
        } else {
          grid[y][x] = charToPipe.get(c);
        }
      }
    }
    return new Board(grid, startX, startY);
  }

  static long solveA(String input) {
    Board board = parse(input);

    Direction startDirection = Arrays.stream(Direction.values()).filter(dir -> {
      Pipe pipe = board.get(board.startY + dir.dy, board.startX + dir.dx);
      return pipe != null && pipe.has(dir.opposite());
    }).findFirst().orElseThrow();

    int x = board.startX + startDirection.dx;
    int y = board.startY + startDirection.dy;
    Direction prevDir = startDirection;
    int steps = 1;
    do {
      steps++;
      Direction dir = board.get(y, x).other(prevDir.opposite());
      x = x + dir.dx;
      y = y + dir.dy;
      prevDir = dir;
    } while ((x != board.startX || y != board.startY));
    return steps / 2;
  }

  static long solveB(String input) {
    List<String> lines = input.lines().toList();
    int startX = -1;
    int startY = -1;

    Pipe[][] grid = new Pipe[lines.size()][lines.getFirst().length()];

    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.toCharArray().length; x++) {
        char c = line.charAt(x);
        if (c == 'S') {
          startX = x;
          startY = y;
        } else {
          grid[y][x] = charToPipe.get(c);
        }
      }
    }
    Board board = new Board(grid, startX, startY);

    Direction startDirection = Arrays.stream(Direction.values()).filter(dir -> {
      Pipe pipe = board.get(board.startY + dir.dy, board.startX + dir.dx);
      return pipe != null && pipe.has(dir.opposite());
    }).findFirst().orElseThrow();

    boolean[][] partOfLoop = new boolean[board.sizeY()][board.sizeX()];

    int x = board.startX + startDirection.dx;
    int y = board.startY + startDirection.dy;
    partOfLoop[board.startY][board.startX] = true;
    Direction prevDir = startDirection;
    do {
      partOfLoop[y][x] = true;
      Direction dir = board.get(y, x).other(prevDir.opposite());
      x = x + dir.dx;
      y = y + dir.dy;
      prevDir = dir;
    } while ((x != board.startX || y != board.startY));

    // fix up the start tile
    Direction startOpposite = prevDir.opposite();
    board.grid[startY][startX] = charToPipe.values().stream()
        .filter(p -> p.has(startDirection) && p.has(startOpposite))
        .findFirst()
        .orElseThrow();

    int countIn = 0;

    boolean out = true;
    for (y = 0; y < board.sizeY(); y++) {
      for (x = 0; x < board.sizeX(); x++) {
        if (partOfLoop[y][x]) {
          Pipe pipe = board.grid[y][x];
          if (pipe.has(Direction.N)) {
            out = !out;
          }
        } else if (!out) {
          countIn++;
        }
      }
    }

    return countIn;
  }
}
