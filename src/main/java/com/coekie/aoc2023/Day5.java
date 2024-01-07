package com.coekie.aoc2023;

import static com.google.common.base.Preconditions.checkState;

import com.google.common.base.Splitter;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

// https://adventofcode.com/2023/day/5
class Day5 {
  static long solveA(String input) {
    List<String> lines = input.lines().toList();

    List<Long> gotList = Splitter.on(' ')
        .splitToStream(lines.getFirst().substring("seeds: ".length()))
        .map(Long::parseLong)
        .collect(Collectors.toCollection(ArrayList::new));

    int lineNumber = 1;
    while (lineNumber < lines.size()) {
      checkState(lines.get(lineNumber).isEmpty());
      lineNumber++;

      checkState(lines.get(lineNumber).endsWith("map:"));
      lineNumber++;

      List<Long> gotNextList = new ArrayList<>();

      while (lineNumber < lines.size() && !lines.get(lineNumber).isEmpty()) {
        List<String> split = Splitter.on(' ').splitToList(lines.get(lineNumber));
        long destStart = Long.parseLong(split.get(0));
        long srcStart = Long.parseLong(split.get(1));
        long len = Long.parseLong(split.get(2));

        for (Iterator<Long> it = gotList.iterator(); it.hasNext(); ) {
          Long got = it.next();
          if (got >= srcStart && got < srcStart + len) {
            it.remove();
            long gotNext = got - srcStart + destStart;
            gotNextList.add(gotNext);
          }
        }

        lineNumber++;
      }
      gotNextList.addAll(gotList);
      gotList = gotNextList;
    }

    return gotList.stream().mapToLong(Long::longValue).min().orElseThrow();
  }

  static long solveB(String input) {
    List<String> lines = input.lines().toList();

    List<Long> gotList = Splitter.on(' ')
        .splitToStream(lines.getFirst().substring("seeds: ".length()))
        .map(Long::parseLong)
        .toList();

    RangeSet<Long> gotRangeSet = TreeRangeSet.create();
    for (int i = 0; i < gotList.size(); i+=2) {
      gotRangeSet.add(Range.closedOpen(gotList.get(i), gotList.get(i) + gotList.get(i + 1)));
    }

    int lineNumber = 1;
    while (lineNumber < lines.size()) {
      checkState(lines.get(lineNumber).isEmpty());
      lineNumber++;

      checkState(lines.get(lineNumber).endsWith("map:"));
      lineNumber++;

      RangeSet<Long> gotNextRangeSet = TreeRangeSet.create();

      while (lineNumber < lines.size() && !lines.get(lineNumber).isEmpty()) {
        List<String> split = Splitter.on(' ').splitToList(lines.get(lineNumber));
        long destStart = Long.parseLong(split.get(0));
        long srcStart = Long.parseLong(split.get(1));
        long len = Long.parseLong(split.get(2));

        long diff = destStart - srcStart;
        Range<Long> srcRange = Range.closedOpen(srcStart, srcStart + len);

        if (gotRangeSet.intersects(srcRange)) {
          for (Range<Long> foundRange : gotRangeSet.subRangeSet(srcRange).asRanges()) {
            gotNextRangeSet.add(Range.range(
                foundRange.lowerEndpoint() + diff, foundRange.lowerBoundType(),
                foundRange.upperEndpoint() + diff, foundRange.upperBoundType()));
          }
          gotRangeSet.remove(srcRange);
        }

        lineNumber++;
      }

      gotNextRangeSet.addAll(gotRangeSet);
      gotRangeSet = gotNextRangeSet;
    }

    return gotRangeSet.span().lowerEndpoint();
  }
}
