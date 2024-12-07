package com.coekie.aocutil;

public enum Direction {
  N(0, -1), W(-1, 0), S(0, 1), E(1, 0);

  public final int dx;
  public final int dy;

  Direction(int dx, int dy) {
    this.dx = dx;
    this.dy = dy;
  }

  public Direction opposite() {
    return switch(this) {
      case N -> S;
      case S -> N;
      case W -> E;
      case E -> W;
    };
  }

  public Direction right() {
    return switch(this) {
      case N -> E;
      case E -> S;
      case S -> W;
      case W -> N;
    };
  }
}
