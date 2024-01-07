package com.coekie.aoc2023;

import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Multisets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// https://adventofcode.com/2023/day/7
class Day7 {
  record Hand(String str, String counts, long bid) {
  }

  static long solveA(String input) {
    return solve(input, '\0');
  }

  static long solveB(String input) {
    return solve(input, 'J');
  }

  static long solve(String input, char wildcard) {
    Comparator<Hand> comparator = Comparator.comparing(Hand::counts)
        .thenComparing
            (hand -> hand.str.chars().mapToObj((wildcard + "23456789TJQKA")::indexOf).toList(),
            Comparators.lexicographical(Integer::compareTo));


    List<Hand> hands = input.lines()
        .map(line -> {
          String str = line.substring(0, 5);
          long bid = Long.parseLong(line.substring(6));
          return new Hand(str, counts(str, wildcard), bid);
        })
        .sorted(comparator)
        .toList();

    return IntStream.range(0, hands.size())
        .mapToLong(i -> hands.get(i).bid * (i + 1))
        .sum();
  }

  // counts of each type of card, highest first. e.g. "AA8AA" -> "41".
  // J is wildcard
  static String counts(String str, char wildcard) {
    String withoutJ = str.replaceAll("" + wildcard, "");
    List<Integer> counts = Multisets.copyHighestCountFirst(
            withoutJ.chars()
                .boxed()
                .collect(ImmutableMultiset.toImmutableMultiset()))
        .entrySet()
        .stream()
        .map(Entry::getCount)
        .collect(Collectors.toCollection(ArrayList::new));

    if (counts.isEmpty()) { // all wildcards
      return "5";
    }

    // add wildcard to the highest count
    counts.set(0, counts.getFirst() + (str.length() - withoutJ.length()));
    return counts.stream().map(c -> Integer.toString(c)).collect(Collectors.joining());
  }
}
