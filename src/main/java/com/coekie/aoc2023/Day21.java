package com.coekie.aoc2023;

import static com.google.common.base.Preconditions.checkArgument;

import com.coekie.aocutil.Direction;
import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;
import java.util.Arrays;

// https://adventofcode.com/2023/day/21
class Day21 {
  static long solve(String input, int steps) {
    Grid grid = Grid.parse(input);
    for (int i = 0; i < steps; i++) {
      grid = step(grid);
    }
    return countO(grid);
  }

  static Grid step(Grid oldGrid) {
    Grid newGrid = new Grid(new char[oldGrid.height()][oldGrid.width()]);
    for (int y = 0; y < newGrid.height(); y++) {
      for (int x = 0; x < newGrid.width(); x++) {
        Location location = new Location(x, y);
        newGrid.set(x, y, switch (oldGrid.get(x, y)) {
          case '#' -> '#';
          case '.', 'O', 'S' -> Arrays.stream(Direction.values()).map(location::step)
              .anyMatch(loc -> oldGrid.isInBounds(loc)
                  && (oldGrid.get(loc) == 'O' || oldGrid.get(loc) == 'S'))
              ? 'O' : '.';
          default -> throw new UnsupportedOperationException();
        });
      }
    }
    return newGrid;
  }

  static long solveB(String input, int n) {
    return doB(Grid.parse(input), n);
  }

  static long doB(Grid grid, int n) {
    checkArgument(grid.width() == grid.height());
    checkArgument(n % 2 == 1); // only implemented for odd number of steps
    int s = grid.width();
    // hs = half-size = time to get from center to neighbor map = (size - 1) / 2
    int hs = (s - 1) / 2;
    // straight steps, maps reached by going straight = (n + hs) / s
    int ss = (n + hs) / s;

    // situation after odd number of steps, each char represents one copy of the map:
    //     ^
    //    /E\
    //   /EOE\
    //  /EOEOE\
    // <EOEOEOE>
    //  \EOEOE/
    //   \OEO/
    //    \E/
    //     v

    // full maps, odd
    long centerOMaps = 1 + ((ss-1)/2) * 2; // full odd maps on the center line of maps
    long oMaps = centerOMaps * centerOMaps;
    int o = countAfterEnter(grid, hs, hs, s * 2 + 1);

    // full maps, even
    long centerEMaps = (ss/2) * 2; // full even maps on the center line of maps
    long eMaps = centerEMaps * centerEMaps;
    int e = countAfterEnter(grid, hs, hs, s * 2);

    int intoStraightStep = (n + hs) % s;
    int[] tips = new int[]{
        countAfterEnter(grid, hs, 0, intoStraightStep), // ^
        countAfterEnter(grid, 0, hs, intoStraightStep), // <
        countAfterEnter(grid, s - 1, hs, intoStraightStep), // >
        countAfterEnter(grid, hs, s - 1, intoStraightStep), // v
    };

    // two sets of diagonal maps on each side, we call them A and B

    // first reached at s+1, then each 2s add 2 more
    int diagAMaps = ((n + s - 1) / (s*2)) * 2 - 1;
    int intoDiagsA = (n + s - 1) % (s*2);
    // fix off-by-one-ish: diagonal crossing just one tile in a corner of a map already counts as a
    // full one, so don't count it as a diagonal one
    if (intoDiagsA >= s*2 - 3) diagAMaps=0;
    int[] diagsA = new int[]{
        countAfterEnter(grid, 0, 0, intoDiagsA),
        countAfterEnter(grid, 0, s-1, intoDiagsA),
        countAfterEnter(grid, s-1, 0, intoDiagsA),
        countAfterEnter(grid, s-1, s-1, intoDiagsA),
    };

    // first reached at 2s + 1, then each 2s add 2 more
    int diagBMaps = ((n - 1) / (s*2)) * 2;
    int intoDiagsB = (n - 1) % (s*2);
    // fix off-by-one-ish: diagonal crossing just one tile in a corner of a map already counts as a
    // full one, so don't count it as a diagonal one
    if (intoDiagsB >= s*2 - 3) diagBMaps=0;
    int[] diagsB = new int[]{
        countAfterEnter(grid, 0, 0, intoDiagsB),
        countAfterEnter(grid, 0, s-1, intoDiagsB),
        countAfterEnter(grid, s-1, 0, intoDiagsB),
        countAfterEnter(grid, s-1, s-1, intoDiagsB),
    };

    return oMaps * o
        + eMaps * e
        + tips[0] + tips[1] + tips[2] + tips[3]
        + (long) diagAMaps * (diagsA[0] + diagsA[1] + diagsA[2] + diagsA[3])
        + (long) diagBMaps * (diagsB[0] + diagsB[1] + diagsB[2] + diagsB[3]);
  }

  // number of tiles reachable after taking n steps in grid, start at pos x,y
  static int countAfterEnter(Grid grid, int x, int y, int n) {
    grid = grid.copy();
    grid.set(grid.width() / 2, grid.height() / 2, '.');
    grid.set(x, y, 'O');
    for (int i = 0; i < n; i++) {
      grid = step(grid);
    }
    return countO(grid);
  }

  static int countO(Grid grid) {
    int count = 0;
    for (int y = 0; y < grid.height(); y++) {
      for (int x = 0; x < grid.width(); x++) {
        if (grid.get(x, y) == 'O') count++;
      }
    }
    return count;
  }
}
