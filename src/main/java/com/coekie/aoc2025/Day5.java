package com.coekie.aoc2025;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Day5 {
  static long solveA(String input) {
    String[] split = input.split("\n\n");
    TreeMap<Long, Long> ranges = parseRanges(split[0]);
    return split[1].lines().mapToLong(Long::parseLong).filter(ingredient -> contains(ranges, ingredient)).count();
  }

  static boolean contains(TreeMap<Long, Long> ranges, long ingredient) {
    var entry = ranges.floorEntry(ingredient);
    return entry != null && ingredient <= entry.getValue();
  }

  /// Parse ranges into map where key is starting of a range and value is the end of that range
  /// Overlapping or adjacent ranges are merged together.
  static TreeMap<Long, Long> parseRanges(String rangesInput) {
    var result = new TreeMap<Long, Long>();
    // sort to make merging easy
    Stream<Range> ranges = rangesInput.lines().map(Range::new)
        .sorted(Comparator.comparing(Range::from).thenComparing(Range::to));
    PeekingIterator<Range> it = Iterators.peekingIterator(ranges.iterator());
    while (it.hasNext()) {
      if (it.next() instanceof Range(long from, long to)) {
        while (it.hasNext() && it.peek().from() <= to + 1) {
          to = Math.max(to, it.next().to());
        }
        result.put(from, to);
      }
    }
    return result;
  }

  static long solveB(String input) {
    return parseRanges(input.split("\n\n")[0]).entrySet().stream()
        .mapToLong(e -> e.getValue() - e.getKey() + 1)
        .sum();
  }

  record Range(long from, long to) {
    Range(String line) {
      String[] split = line.split("-");
      this(Long.parseLong(split[0]), Long.parseLong(split[1]));
    }
  }
}
