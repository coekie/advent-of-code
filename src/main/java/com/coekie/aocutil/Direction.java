package com.coekie.aocutil;

public enum Direction {
  N(0, -1), W(-1, 0), S(0, 1), E(1, 0);

  private static final Direction[] values = values(); // cache

  public final int dx;
  public final int dy;

  Direction(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  public Direction left() {
    return values[(ordinal() + 1) % 4];
  }

  public Direction opposite() {
    return values[(ordinal() + 2) % 4];
  }

  public Direction right() {
    return values[(ordinal() + 3) % 4];
  }
}
