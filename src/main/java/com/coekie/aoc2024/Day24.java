package com.coekie.aoc2024;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.math3.util.Combinations;

// https://adventofcode.com/2024/day/24
public class Day24 {

  static long solveA(String input) {
    String[] splitInput = input.split("\n\n");
    Map<String, Supplier<Boolean>> map = new HashMap<>();
    splitInput[0].lines().forEach(line ->
        map.put(line.substring(0, 3), () -> line.charAt(5) == '1'));
    splitInput[1].lines().forEach(line -> {
      String[] split = line.split(" ");
      map.put(split[4], () -> switch (split[1]) {
        case "AND" -> map.get(split[0]).get() && map.get(split[2]).get();
        case "OR" -> map.get(split[0]).get() || map.get(split[2]).get();
        case "XOR" -> map.get(split[0]).get() ^ map.get(split[2]).get();
        default -> throw new RuntimeException();
      });
    });
    return map.keySet().stream()
        .filter(k -> k.startsWith("z"))
        .sorted(Comparator.reverseOrder())
        .mapToLong(z -> map.get(z).get() ? 1L : 0L)
        .reduce((a, b) -> a * 2 + b)
        .orElseThrow();
  }

  static String solveB(String input) {
    Sys sys = parse(input);

    int origScore = score(sys, new Swap(null, null));

    // find swaps that improve the score
    List<Wire> swappable = sys.wires.values().stream()
        .filter(n -> n.name.charAt(0) != 'x' && n.name.charAt(0) != 'y').toList();
    Map<Swap, Integer> goodSwaps = new HashMap<>();
    for (int[] s : new Combinations(swappable.size(), 2)) {
      Swap swap = new Swap(swappable.get(s[0]), swappable.get(s[1]));
      int score = score(sys, swap);
      if (score > origScore) {
        goodSwaps.put(swap, score);
      }
    }

    // find the best 8 wires to swap around
    Set<Wire> bestSwaps = new HashSet<>();
    for (Swap swap : goodSwaps.entrySet().stream()
        .sorted(Comparator.comparing(e -> -e.getValue())).map(Entry::getKey).toList()) {
      if (!bestSwaps.contains(swap.a) && !bestSwaps.contains(swap.b)) {
        bestSwaps.add(swap.a);
        bestSwaps.add(swap.b);
        if (bestSwaps.size() == 8) {
          return bestSwaps.stream().map(w -> w.name).sorted().collect(Collectors.joining(","));
        }
      }
    }
    throw new RuntimeException("not found");
  }

  static String h(char c, int i) {
    return c + (i < 10 ? "0" : "") + i;
  }

  // shape of a system that works is like, for each layer:
  // x XOR y -> n
  // x AND y -> m
  // c XOR n -> z
  // c AND n -> d
  // d OR m -> c++ (the c of the next layer)
  //
  // we give a score based on how much the system fits that pattern.
  // this is a rough heuristic, but it's already better than what we need to solve it:
  // if we remove some parts of the logic, it still works.
  static int score(Sys sys, Swap swap) {
    int score = 0;
    // the c we got from the previous layer. we track 2 candidates (usually they are the same).
    Wire c0 = sys.wire("x00").andOut(swap);
    Wire c1 = c0;
    for (int i = 1; i < 45; i++) {
      Wire x = sys.wire(h('x', i));
      Wire n = x.xorOut(swap); // x XOR y -> n
      Wire m = x.andOut(swap); // x AND y -> m

      // c XOR n -> z
      if (n.xorPartner != null) {
        if (n.xorPartner == c0) score++;
        if (n.xorPartner == c1) score++;
        if (n.xorOut(swap).name.equals(h('z', i)))
          score++;
      }
      // c AND n -> d
      if (n.andPartner != null) {
        if (n.andPartner == c0) score++;
        if (n.andPartner == c1) score++;
        Wire d = n.andOut(swap);

        // d OR m -> c++
        if (d.orPartner != null) {
          score++;
          if (d.orPartner == m) {
            score++;
          }
          c0 = d.orOut(swap);
        } else {
          c0 = null;
        }
      } else {
        c0 = null;
      }
      c1 = m.orPartner != null ? m.orOut(swap) : null;
    }
    return score;
  }

  static Sys parse(String input) {
    String wiring = input.substring(input.indexOf("\n\n") + 2);
    Sys sys = new Sys();
    wiring.lines().forEach(line -> {
      String[] split = line.split(" ");
      Wire in0 = sys.wire(split[0]);
      Wire in1 = sys.wire(split[2]);
      Wire out = sys.wire(split[4]);
      switch (split[1]) {
        case "AND" -> {
          in0.andPartner = in1;
          in1.andPartner = in0;
          in0.origAndOut = in1.origAndOut = out;
        }
        case "OR" -> {
          in0.orPartner = in1;
          in1.orPartner = in0;
          in0.origOrOut = in1.origOrOut = out;
        }
        case "XOR" -> {
          in0.xorPartner = in1;
          in1.xorPartner = in0;
          in0.origXorOut = in1.origXorOut = out;
        }
      }
    });
    return sys;
  }

  static class Sys {
    Map<String, Wire> wires = new HashMap<>();

    Wire wire(String name) {
      return wires.computeIfAbsent(name, Wire::new);
    }
  }

  static class Wire {
    final String name;
    Wire andPartner, origAndOut, orPartner, origOrOut, xorPartner, origXorOut;

    Wire(String name) {
      this.name = name;
    }

    Wire andOut(Swap swap) {
      return swap.apply(origAndOut);
    }
    Wire orOut(Swap swap) {
      return swap.apply(origOrOut);
    }
    Wire xorOut(Swap swap) {
      return swap.apply(origXorOut);
    }
  }

  record Swap(Wire a, Wire b) {
    Wire apply(Wire n) {
      return n == a ? b : n == b ? a : n;
    }
  }
}
