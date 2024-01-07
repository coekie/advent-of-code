package com.coekie.aocutil;

public record Location(int x, int y) {
  @Override
  public String toString() {
    return "[" + x + ", " + y + "]";
  }

  public Location step(Direction direction) {
    return new Location(x + direction.dx, y + direction.dy);
  }

  public Location step(Direction direction, int steps) {
    return new Location(x + steps * direction.dx, y + steps * direction.dy);
  }
}
