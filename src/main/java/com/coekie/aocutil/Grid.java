package com.coekie.aocutil;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Grid(char[][] grid) {
  public static Grid parse(String input) {
    return new Grid(input.lines()
        .map(String::toCharArray)
        .toArray(char[][]::new));
  }

  public char get(Location location) {
    return grid[location.y()][location.x()];
  }

  public char get(int x, int y) {
    return grid[y][x];
  }

  public boolean has(Location location, char c) {
    return isInBounds(location) && get(location) == c;
  }

  public void set(int x, int y, char c) {
    grid[y][x] = c;
  }

  public void set(Location location, char c) {
    set(location.x(), location.y(), c);
  }

  public boolean isInBounds(Location location) {
    return location.x() >= 0 && location.x() < width()
        && location.y() >= 0 && location.y() < height();
  }

  public int width() {
    return grid[0].length;
  }

  public int height() {
    return grid.length;
  }

  public Grid copy() {
    return new Grid(Arrays.stream(grid).map(char[]::clone).toArray(char[][]::new));
  }

  public Stream<Location> locations() {
    return IntStream.range(0, width())
        .boxed()
        .flatMap(x -> IntStream.range(0, height()).mapToObj(y -> new Location(x, y)));
  }

  public Location findFirst(char c) {
    for (int y = 0; y < grid.length; y++) {
      for (int x = 0; x < grid[y].length; x++) {
        if (grid[y][x] == c) {
          return new Location(x, y);
        }
      }
    }
    throw new IllegalStateException("Cannot find '" + c + "' in:\n" + this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (char[] line : grid) {
      sb.append(line).append('\n');
    }
    return sb.toString();
  }
}
