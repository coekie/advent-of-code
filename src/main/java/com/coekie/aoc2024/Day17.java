package com.coekie.aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// https://adventofcode.com/2024/day/17
public class Day17 {
  static class Program {
    long a, b, c;
    int pc;
    final int[] code;

    Program(long a, long b, long c, int... code) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.code = code;
    }

    Program(String input) {
      String[] lines = input.split("\n");
      a = Long.parseLong(lines[0].substring(12));
      b = Long.parseLong(lines[1].substring(12));
      c = Long.parseLong(lines[2].substring(12));
      code = Stream.of(lines[4].substring(9).split(",")).mapToInt(Integer::parseInt).toArray();
    }

    String run() {
      List<String> out = new ArrayList<>();
      while (pc < code.length) {
        int o = step();
        if (o != -1) {
          out.add(Integer.toString(o));
        }
      }
      return String.join(",", out);
    }

    int runUntilOutput() {
      while (true) {
        int o = step();
        if (o != -1) {
          return o;
        }
      }
    }

    int step() {
      int opcode = code[pc];
      int op = code[pc + 1];
      pc += 2;
      int result = -1;
      switch (opcode) {
        case 0 -> a = a >> combo(op); // adv
        case 1 -> b = b ^ op; // bxl
        case 2 -> b = combo(op) % 8; // bst
        case 3 -> {if (a != 0) {pc = op;}} // jnz
        case 4 -> b = b ^ c; // bxc
        case 5 -> result = (int) (combo(op) % 8); // out
        case 6 -> b = a >> combo(op); // bdv
        case 7 -> c = a >> combo(op); // cdv
      }
      return result;
    }

    long combo(int op) {
      return switch (op) {
        case 0, 1, 2, 3 -> op;
        case 4 -> a;
        case 5 -> b;
        case 6 -> c;
        default -> throw new RuntimeException();
      };
    }
  }

  static String solveA(String input) {
    return new Program(input).run();
  }

  static long solveB(String input) {
    Program program = new Program(input);
    long a = 0;
    // solve it backwards, from last output to first
    for (int i = 1; i <= program.code.length; i++) {
      a = a << 3; // step back one loop iteration in the program
      // brute force one more output
      while (true) {
        // reset the program
        program.b = program.c = program.pc = 0;
        program.a = a;
        if (outputMatchesCodeSuffix(program, i)) break;
        a++;
      }
    }
    return a;
  }

  static boolean outputMatchesCodeSuffix(Program program, int length) {
    for (int i = program.code.length - length; i < program.code.length; i++) {
      if (program.runUntilOutput() != program.code[i]) {
        return false;
      }
    }
    return true;
  }

  // not used, but helps understand the puzzle input shape
  static String decompile(int... code) {
    StringBuilder sb = new StringBuilder();
    for (int pc = 0; pc < code.length; pc+=2) {
      int op = code[pc + 1];
      sb.append(switch (code[pc]) {
        case 0 -> "a = a >> " + decombo(code[pc + 1]);
        case 1 -> "b = b ^ " + op;
        case 2 -> "b = " + decombo(op) + " % 8";
        case 3 -> "if (a != 0) goto " + op;
        case 4 -> "b = b ^ c";
        case 5 -> "out(" + decombo(op) + " % 8)";
        case 6 -> "b = a >> " + decombo(code[pc + 1]);
        case 7 -> "c = a >> " + decombo(code[pc + 1]);
        default -> throw new RuntimeException();
      }).append('\n');
    }
    return sb.toString();
  }

  static String decombo(int op) {
    return switch (op) {
      case 0, 1, 2, 3 -> Integer.toString(op);
      case 4 -> "a";
      case 5 -> "b";
      case 6 -> "c";
      default -> throw new RuntimeException();
    };
  }
}
