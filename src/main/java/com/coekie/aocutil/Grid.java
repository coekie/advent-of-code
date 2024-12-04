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

  public void set(int x, int y, char c) {
    grid[y][x] = c;
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

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (char[] line : grid) {
      sb.append(line).append('\n');
    }
    return sb.toString();
  }
}
