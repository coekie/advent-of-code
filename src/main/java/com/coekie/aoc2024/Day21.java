package com.coekie.aoc2024;

import static java.lang.Math.min;

import com.coekie.aocutil.Grid;
import com.coekie.aocutil.Location;

// https://adventofcode.com/2024/day/21
public class Day21 {
  static final Grid numGrid = Grid.parse("789\n456\n123\n 0A");

  static class KeyPad {
    // number of presses for going into each of the 8 directions
    long nw, wn, n, ne, en, w, e, sw, ws, s, se, es;

    // calculate the number of presses in a KeyPad that is controlled by this keypad
    KeyPad dirpad() {
      KeyPad result = new KeyPad();
      result.n = w + e; // ^A
      result.w = sw + 1 + en + 1; // <A
      result.e = s + n; // >A
      result.s = min(sw, ws) + min(ne, en); // vA
      result.nw = w + sw + en + 1; // ^<A
      result.wn = sw + 1 + en + e; // <^A
      result.ne = w + min(se, es) + n; // ^>A
      result.en = s + min(nw, wn) + e; // >^A
      result.sw = min(sw, ws) + w + en + 1; // v<A
      result.ws = sw + 1 + e + min(ne, en); // <vA
      result.se = min(sw, ws) + e + n; // v>A
      result.es = s + w + min(ne, en); // >vA
      return result;
    }
  }

  // the pad that we are holding
  static KeyPad keypad0() {
    KeyPad r = new KeyPad();
    r.n = r.e = r.s = r.w = 2;
    r.ne = r.en = r.se = r.es = r.sw = r.ws = r.nw = r.wn = 3;
    return r;
  }

  // number of keypresses to go from fromC to toC on the given keypad
  static long length(char fromC, char toC, KeyPad kp) {
    Location from = numGrid.findFirst(fromC);
    Location to = numGrid.findFirst(toC);
    int dx = to.x() - from.x();
    int dy = to.y() - from.y();

    // if we're taking more than one step in the same direction, the number of extra steps
    int extra = Math.max(0, Math.abs(dx) - 1) + Math.max(0, Math.abs(dy) - 1);

    if (dx == 0) {
      return (dy < 0 ? kp.n : kp.s) + extra;
    } else if (dy == 0) {
      return (dx < 0 ? kp.w : kp.e) + extra;
    } else if (dx < 0) {
      if (dy < 0) {
        return ((to.x() == 0 && from.y() == 3) ? kp.nw : min(kp.nw, kp.wn)) + extra;
      } else { //
        return min(kp.sw, kp.ws) + extra;
      }
    } else {
      if (dy < 0) {
        return min(kp.ne, kp.en) + extra;
      } else {
        return ((from.x() == 0 && to.y() == 3) ? kp.es : min(kp.se, kp.es)) + extra;
      }
    }
  }

  static long length(String code, KeyPad kp) {
    long cost = 0;
    char prev = 'A';
    for (char c : code.toCharArray()) {
      cost += length(prev, c, kp);
      prev = c;
    }
    return cost;
  }

  static long solve(String input, KeyPad keypad) {
    return input.lines()
        .mapToLong(s -> length(s, keypad) * Long.parseLong(s.substring(0, s.length() - 1)))
        .sum();
  }

  static KeyPad keypad(int keypads) {
    KeyPad kp = keypad0();
    for (int i = 0; i < keypads; i++) {
      kp = kp.dirpad();
    }
    return kp;
  }

  static long solveA(String input) {
    return solve(input, keypad(2));
  }

  static long solveB(String input) {
    return solve(input, keypad(25));
  }
}
