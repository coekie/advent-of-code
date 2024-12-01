package com.coekie.aoc2024;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Streams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// https://adventofcode.com/2024/day/1
public class Day1 {
  static long solveA(String input) {
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();

    input.lines().forEach(line -> {
      String[] split = line.split(" +");
      list1.add(Integer.parseInt(split[0]));
      list2.add(Integer.parseInt(split[1]));
    });
    Collections.sort(list1);
    Collections.sort(list2);

    return Streams.zip(list1.stream(), list2.stream(), (a, b) -> Math.abs(a - b))
        .mapToInt(d -> d)
        .sum();
  }

  static long solveB(String input) {
    List<Integer> list1 = new ArrayList<>();
    Multiset<Integer> list2 = HashMultiset.create();

    input.lines().forEach(line -> {
      String[] split = line.split(" +");
      list1.add(Integer.parseInt(split[0]));
      list2.add(Integer.parseInt(split[1]));
    });

    return list1.stream()
        .mapToInt(a -> a * list2.count(a))
        .sum();
  }
}
