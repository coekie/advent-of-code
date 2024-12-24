package com.coekie.aoc2024;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// https://adventofcode.com/2024/day/23
public class Day23 {
  private static SetMultimap<String, String> parse(String input) {
    SetMultimap<String, String> connections = HashMultimap.create();
    input.lines().forEach(line -> {
      String one = line.substring(0, 2);
      String two = line.substring(3, 5);
      String a = one.compareTo(two) < 0 ? one : two;
      String b = one.compareTo(two) < 0 ? two : one;
      connections.put(a, b);
    });
    return connections;
  }

  static long solveA(String input) {
    SetMultimap<String, String> connections = parse(input);
    int result = 0;
    for (String a : connections.keySet()) {
      for (String b : connections.get(a)) {
        for (String c : Sets.intersection(connections.get(b), connections.get(a))) {
          if (a.charAt(0) == 't' || b.charAt(0) == 't' || c.charAt(0) == 't') {
            result++;
          }
        }
      }
    }
    return result;
  }

  static String solveB(String input) {
    SetMultimap<String, String> connections = parse(input);
    List<String> best = new ArrayList<>();
    List<String> set = new ArrayList<>();
    for (String first : connections.keySet()) {
      solve(connections, set, best, first, connections.get(first));
    }
    return String.join(",", best);
  }

  static void solve(SetMultimap<String, String> connections, List<String> subset, List<String> best,
      String current, Set<String> candidates) {
    subset.add(current);
    if (subset.size() > best.size()) {
      best.clear();
      best.addAll(subset);
    }
    for (String candidate : candidates) {
      solve(connections, subset, best, candidate,
          Sets.intersection(connections.get(candidate), candidates));
    }
    subset.removeLast();
  }
}
