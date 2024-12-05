package com.coekie.aoc2024;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://adventofcode.com/2024/day/5
public class Day5 {
  private record Rule(String before, String after) {
  }

  private record Update(List<String> pages) {
    Update(String line) {
      var pages = line.split(",");
      this(List.of(pages));
    }

    boolean satisfies(Rule rule) {
      int beforeIndex = pages.indexOf(rule.before);
      int afterIndex = pages.indexOf(rule.after);
      return beforeIndex == -1 || afterIndex == -1 || beforeIndex < afterIndex;
    }
  }

  private static List<Rule> parseRules(String rules) {
    return rules.lines()
        .map(line -> line.split("\\|"))
        .map(s -> new Rule(s[0], s[1]))
        .toList();
  }

  static long solveA(String input) {
    String[] split = input.split("\n\n");
    List<Rule> rules = parseRules(split[0]);
    return split[1].lines().map(Update::new)
        .filter(update -> rules.stream().allMatch(update::satisfies))
        .mapToInt(update -> Integer.parseInt(update.pages.get(update.pages.size() / 2)))
        .sum();
  }

  static long solveB(String input) {
    String[] split = input.split("\n\n");
    List<Rule> rules = parseRules(split[0]);
    return split[1].lines().map(Update::new)
        .filter(update -> !rules.stream().allMatch(update::satisfies))
        .mapToInt(update -> findMiddle(rules, update))
        .sum();
  }

  private static int findMiddle(List<Rule> rules, Update update) {
    Set<String> remaining = new HashSet<>(update.pages);
    while (true) {
      // find page where there is no rule that it has to be after another one in remaining
      String next = remaining.stream()
          .filter(page -> rules.stream()
              .noneMatch(rule -> rule.after.equals(page) && remaining.contains(rule.before)))
          .findAny().orElseThrow();
      remaining.remove(next);
      if (remaining.size() == update.pages.size() / 2) {
        return Integer.parseInt(next);
      }
    }
  }
}
