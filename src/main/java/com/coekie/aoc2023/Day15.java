package com.coekie.aoc2023;

import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// https://adventofcode.com/2023/day/15
class Day15 {
  static long solveA(String input) {
    return Arrays.stream(input.split(",")).mapToLong(Day15::hash).sum();
  }

  static long solveB(String input) {
    Map<Integer, LinkedHashMap<String, Integer>> boxes = new HashMap<>();

    for (String step : input.split(",")) {
      String[] split = step.split("[=-]");
      String label = split[0];
      var box = boxes.computeIfAbsent(hash(label), _ -> new LinkedHashMap<>());
      if (step.endsWith("-")) {
        box.remove(label);
      } else {
        box.put(label, Integer.parseInt(split[1]));
      }
    }

    return boxes.entrySet().stream().flatMapToLong(box ->
        Streams.mapWithIndex(box.getValue().values().stream(),
                (focusingPower, slot) -> (box.getKey() + 1) * (slot + 1) * focusingPower)
            .mapToLong(Long::longValue)).sum();
  }

  static int hash(String input) {
    return input.chars().reduce(0, (h, c) -> (h + c) * 17 % 256);
  }
}
