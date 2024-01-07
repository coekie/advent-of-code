package com.coekie.aoc2023;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

// https://adventofcode.com/2023/day/3
class Day3 {
  record PartNumber(int x, int y, int number) {}
  record Cell(PartNumber partNumber, Character symbol) {}

  static class Schematic {
    final Cell[][] cells;

    Schematic(int width, int height) {
      this.cells = new Cell[height][width];
    }

    Cell get(int x, int y) {
      if (x < 0 || y < 0 || x > cells[0].length || y > cells.length) {
        return new Cell(null, null);
      } else {
        return cells[y][x];
      }
    }

    int width() {
      return cells[0].length;
    }

    int height() {
      return cells.length;
    }

    static Schematic parse(String input) {
      List<String> lines = input.lines().toList();
      Schematic schematic = new Schematic(lines.getFirst().length(), lines.size());
      for (int y = 0; y < lines.size(); y++) {
        String line = lines.get(y);
        for (int x = 0; x < line.length(); x++) {
          char c = line.charAt(x);
          if (Character.isDigit(c)) {
            int numberLen = 1;
            while (x + numberLen < line.length() && Character.isDigit(line.charAt(x + numberLen))) {
              numberLen++;
            }
            PartNumber partNumber = new PartNumber(x, y,
                Integer.parseInt(line.substring(x, x + numberLen)));
            for (int i = 0; i < numberLen; i++) {
              schematic.cells[y][x + i] = new Cell(partNumber, null);
            }
            x += numberLen - 1;
          } else if (c != '.') {
            schematic.cells[y][x] = new Cell(null, c);
          } else {
            schematic.cells[y][x] = new Cell(null, null);
          }
        }
      }
      return schematic;
    }
  }

  static long solveA(String input) {
    Schematic schematic = Schematic.parse(input);

    Set<PartNumber> adjacentPartNumbers = new HashSet<>();

    for (int y = 0; y < schematic.height(); y++) {
      for (int x = 0; x < schematic.width(); x++) {
        if (schematic.cells[y][x].symbol != null) {
          for (int dy = -1; dy <= 1 ; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
              if (dx == 0 && dy == 0) continue;
              PartNumber partNumber = schematic.get(x + dx, y + dy).partNumber;
              if (partNumber != null) {
                adjacentPartNumbers.add(partNumber);
              }
            }
          }
        }
      }
    }

    return adjacentPartNumbers.stream()
        .mapToInt(pn -> pn.number)
        .sum();
  }

  static long solveB(String input) {
    Schematic schematic = Schematic.parse(input);
    int total = 0;
    for (int y = 0; y < schematic.height(); y++) {
      for (int x = 0; x < schematic.width(); x++) {
        if (Objects.equals(schematic.cells[y][x].symbol, '*')) {
          Set<PartNumber> adjacentPartNumbers = new HashSet<>();
          for (int dy = -1; dy <= 1 ; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
              if (dx == 0 && dy == 0) continue;
              PartNumber partNumber = schematic.get(x + dx, y + dy).partNumber;
              if (partNumber != null) {
                adjacentPartNumbers.add(partNumber);
              }
            }
          }
          if (adjacentPartNumbers.size() == 2) {
            total += adjacentPartNumbers.stream()
                .mapToInt(pn -> pn.number)
                .reduce((a, b) -> a * b)
                .orElseThrow();
          }
        }
      }
    }
    return total;
  }
}
