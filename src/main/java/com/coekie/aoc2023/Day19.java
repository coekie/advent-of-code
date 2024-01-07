package com.coekie.aoc2023;

import com.google.common.base.Splitter;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// https://adventofcode.com/2023/day/19
class Day19 {
  // reformulated first part in terms of the second part
  static long solveA(String input) {
    String[] split = input.split("\n\n");
    WorkflowSet ws = WorkflowSet.parse(split[0]);

    return split[1].lines()
        .map(Day19::parsePart)
        .filter(p -> ws.countAccepted("in", p) == 1)
        .mapToInt(p -> p.ranges.values().stream().mapToInt(Range::lowerEndpoint).sum())
        .sum();
  }

  static long solveB(String input) {
    String[] split = input.split("\n\n");
    WorkflowSet ws = WorkflowSet.parse(split[0]);
    return ws.countAccepted("in", Partset.ALL);
  }

  static Partset parsePart(String part) {
    List<Range<Integer>> values = Splitter.on(Pattern.compile("[^0-9]")).omitEmptyStrings()
        .splitToStream(part)
        .map(Integer::parseInt)
        .map(i -> Range.closedOpen(i, i + 1))
        .toList();
    return new Partset(Map.of("x", values.get(0), "m", values.get(1), "a", values.get(2), "s", values.get(3)));
  }

  record WorkflowSet(Map<String, Workflow> workflows) {
    static WorkflowSet parse(String workflows) {
      return new WorkflowSet(workflows.lines()
          .map(Workflow::parse)
          .collect(Collectors.toMap(Workflow::name, w -> w)));
    }

    long countAccepted(String w, Partset partset) {
      if (w.equals("A")) {
        return partset.size();
      } else if (w.equals("R")) {
        return 0;
      }
      Workflow workflow = workflows.get(w);
      Partset remainingPartset = partset;
      long result = 0;
      for (Rule rule : workflow.rules) {
        Partset matching = rule.matching(remainingPartset, true);

        if (matching != null) {
          result += countAccepted(rule.target, matching);
        }

        remainingPartset = rule.matching(remainingPartset, false);
        if (remainingPartset == null) {
          break;
        }
      }
      return result;
    }
  }

  record Workflow(String name, List<Rule> rules) {
    static Workflow parse(String line) {
      String[] split = line.split("[{}]");
      return new Workflow(split[0], Stream.of(split[1].split(",")).map(Rule::parse).toList());
    }
  }

  record Rule(String category, Range<Integer> matchingRange, Range<Integer> notMatchingRange, String target) {
    static Rule parse(String rule) {
      if (rule.contains(":")) {
        String[] split = rule.split("\\b");
        int value = Integer.parseInt(split[2]);
        Range<Integer> matchingRange = switch (split[1]) {
          case ">" -> Range.openClosed(value, 4000);
          case "<" -> Range.closedOpen(1, value);
          default -> throw new IllegalStateException();
        };
        Range<Integer> notMatchingRange = switch (split[1]) {
          case ">" -> Range.closed(1, value);
          case "<" -> Range.closed(value, 4000);
          default -> throw new IllegalStateException();
        };

        return new Rule(split[0], matchingRange, notMatchingRange, split[4]);
      } else {
        return new Rule(null, null, null, rule);
      }
    }

    Partset matching(Partset partset, boolean shouldMatch) {
      if (category == null) {
        return shouldMatch ? partset : null;
      } else {
        Range<Integer> v = partset.ranges.get(category);
        Range<Integer> relevantRange = shouldMatch ? matchingRange : notMatchingRange;
        if (!v.isConnected(relevantRange)) {
          return null;
        }
        Range<Integer> intersection = v.intersection(relevantRange)
            .canonical(DiscreteDomain.integers());
        if (intersection.isEmpty()) {
          return null;
        }
        Map<String, Range<Integer>> newRanges = new HashMap<>(partset.ranges);
        newRanges.put(category, intersection);
        return new Partset(newRanges);
      }
    }
  }

  record Partset(Map<String, Range<Integer>> ranges) {
    static final Partset ALL = new Partset(Map.of(
        "x", Range.closedOpen(1, 4001),
        "m", Range.closedOpen(1, 4001),
        "a", Range.closedOpen(1, 4001),
        "s", Range.closedOpen(1, 4001)));

    long size() {
      return ranges.values().stream().mapToLong(r -> r.upperEndpoint() - r.lowerEndpoint())
          .reduce(1, (a, b) -> a * b);
    }
  }
}
