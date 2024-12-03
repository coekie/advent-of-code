package com.coekie.aoc2024;

// https://adventofcode.com/2024/day/3
public class Day3 {
  static class Parser {
    final String input;
    int index;

    Parser(String input) {
      this.input = input;
    }

    boolean read(String expected) {
      if (input.startsWith(expected, index)) {
        index += expected.length();
        return true;
      }
      return false;
    }

    /** Read a number, max length 3. Returns -1 if not at a number */
    int readNumber3() {
      int numberLength = 0;
      while (index + numberLength < input.length()
          && Character.isDigit(input.charAt(index + numberLength)) && numberLength < 3) {
        numberLength++;
      }
      if (numberLength == 0) {
        return -1;
      }
      int result = Integer.parseInt(input, index, index + numberLength, 10);
      index += numberLength;
      return result;
    }
  }

  static long solveA(String input) {
    Parser parser = new Parser(input);
    int result = 0;
    while (parser.index < input.length()) {
      int a, b;
      if (parser.read("mul(") && (a = parser.readNumber3()) != -1 && parser.read(",")
          && (b = parser.readNumber3()) != -1 && parser.read(")")) {
        result += a * b;
      } else {
        parser.index++;
      }
    }
    return result;
  }

  static long solveB(String input) {
    Parser parser = new Parser(input);
    int result = 0;
    boolean enabled = true;
    while (parser.index < input.length()) {
      int a, b;
      if (enabled && parser.read("mul(") && (a = parser.readNumber3()) != -1 && parser.read(",")
          && (b = parser.readNumber3()) != -1 && parser.read(")")) {
        result += a * b;
      } else if (parser.read("do()")) {
        enabled = true;
      } else if (parser.read("don't()")) {
        enabled = false;
      } else {
        parser.index++;
      }
    }
    return result;
  }
}
