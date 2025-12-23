package com.coekie.aoc2025;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimaps;
import org.apache.commons.math3.util.Combinations;
import org.jspecify.annotations.NonNull;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

// https://adventofcode.com/2025/day/11
public class Day11 {

  static long solveA(String input) {
    HashMultimap<String, String> connections = parseConnections(input);
    return solveA(connections, new HashMap<>(Map.of("out", 1L)), "you");
  }

  private static HashMultimap<String, String> parseConnections(String input) {
    return input.lines().collect(Multimaps.flatteningToMultimap(
        line -> line.substring(0, 3),
        line -> Stream.of(line.substring(5).split(" ")),
        HashMultimap::create));
  }

  static long solveA(HashMultimap<String, String> connections, Map<String, Long> paths, String start) {
    Long result = paths.get(start);
    if (result == null) {
      result = connections.get(start).stream().mapToLong(m -> solveA(connections, paths, m)).sum();
      paths.put(start, result);
    }
    return result;
  }

  static long solveB(String input) {
    HashMultimap<String, String> connections = parseConnections(input);
    return solveB(connections, new HashMap<>(Map.of(new State("out", true, true), 1L)), new State("svr", false, false));
  }

  static long solveB(HashMultimap<String, String> connections, Map<State, Long> paths, State start) {
    Long result = paths.get(start);
    if (result == null) {
      result = connections.get(start.machine).stream()
          .mapToLong(m -> solveB(connections, paths,
              new State(m, start.dac || m.equals("dac"), start.fft || m.equals("fft"))))
          .sum();
      paths.put(start, result);
    }
    return result;
  }

  record State(String machine, boolean dac, boolean fft) {}
}
