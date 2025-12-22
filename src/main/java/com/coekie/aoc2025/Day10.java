package com.coekie.aoc2025;

import org.apache.commons.math3.util.Combinations;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

// https://adventofcode.com/2025/day/10
public class Day10 {

  // ============================== Part A ==============================

  static int solveA(String input) {
    return input.lines().mapToInt(Day10::solveALine).sum();
  }

  static int solveALine(String line) {
    List<String> split = List.of(line.split(" "));

    BitSet target = new BitSet();
    String targetString = split.getFirst().substring(1, split.getFirst().length() - 1);
    for (int i = 0; i < targetString.length(); i++) {
      target.set(i, targetString.charAt(i) == '#');
    }

    List<BitSet> buttons = split.subList(1, split.size() - 1).stream()
        .map(s -> {
          BitSet button = new BitSet();
          for (String effect : s.substring(1, s.length() - 1).split(",")) {
            button.set(parseInt(effect));
          }
          return button;
        }).toList();

    // bruteforce all combinations, from lower to higher number of buttons
    BitSet current = new BitSet();
    for (int toPress = 0; toPress <= buttons.size(); toPress++) {
      for (int[] combo : new Combinations(buttons.size(), toPress)) {
        current.clear();
        for (int b : combo) {
          current.xor(buttons.get(b));
        }
        if (current.equals(target)) {
          return toPress;
        }
      }
    }
    throw new RuntimeException();
  }

  record Button(int[] effects) {
    Button(String s) {
      this(Stream.of(s.substring(1, s.length() - 1).split(",")).mapToInt(Integer::parseInt).toArray());
    }
  }

  // ============================== Part B ==============================

  static int solveB(String input) {
    return input.lines().mapToInt(Day10::solveBLine).sum();
  }

  static int solveBLine(String line) {
    // parse input
    List<String> split = List.of(line.split(" "));
    List<Button> buttons = split.subList(1, split.size() - 1).stream().map(Button::new).toList();
    int[] target = Stream.of(split.getLast().substring(1, split.getLast().length() - 1).split(","))
        .mapToInt(Integer::parseInt)
        .toArray();
    
    // transform input into a set of equations
    int[][] equations = new int[target.length][buttons.size() + 1];
    for (int b = 0; b < buttons.size(); b++) {
      Button button = buttons.get(b);
      for (int e = 0; e < button.effects.length; e++) {
        equations[button.effects[e]][b] = 1;
      }
    }
    for (int j = 0; j < target.length; j++) {
      equations[j][buttons.size()] = target[j];
    }

    // upper bound on the maximum number of presses for each button
    int[] max = IntStream.range(0, buttons.size()).map(b ->
        Stream.of(equations)
            .filter(eq -> eq[b] == 1)
            .mapToInt(eq -> eq[buttons.size()])
            .min().orElseThrow())
        .toArray();

    List<Integer> freeButtons = IntStream.range(0, buttons.size()).boxed().toList();
    List<Solution> solutions = new ArrayList<>();

    int buttonCount = equations[0].length - 1;
    return solve(buttonCount, freeButtons, solutions, equations, max);
  }

  static int solve(
      int buttonCount, List<Integer> buttonsToSolve, List<Solution> solutions, int[][] equations, int[] max) {
//    System.out.println("=== Solve ===");
//    System.out.println("buttonsToSolve: " + buttonsToSolve);
//    System.out.println("max: " + Arrays.toString(max));
//    for (int j = 0; j < equations.length; j++) {
//      for (int b = 0; b < equations[j].length; b++) {
//        System.out.printf("%3s", equations[j][b]);
//      }
//      System.out.println();
//    }
//    System.out.println();

    // if there are buttons and equations left, then try to solve one
    if (!buttonsToSolve.isEmpty() && equations.length != 0) {
      // solve first for button `b`
      Optional<Integer> maybeB = buttonsToSolve.stream()
          // a button for which we actually have an equation
          .filter(candidateB -> Arrays.stream(equations).anyMatch(eq -> eq[candidateB] != 0))
          // prefer the button that would take the most iterations if we would have to bruteforce it
          .max(Comparator.comparing(bb -> max[bb]));
      if (maybeB.isPresent()) {
        int b = maybeB.get(); // the button to solve for
        List<Integer> remainingButtonsToSolve = buttonsToSolve.stream().filter(bb -> bb != b).toList();
        int[][] newEquations = new int[equations.length - 1][equations[0].length];
        // choose an equation to eliminate (to use for solving b later)
        int eqi = IntStream.range(0, equations.length)
            .filter(eqii -> equations[eqii][b] != 0)
            .findFirst().orElseThrow();
//        System.out.println("Choosing button " + b + " in equation " + eqi + ": " + Arrays.toString(equations[eqi]));
        for (int i = 0; i < newEquations.length; i++) {
          int[] oldEquation = equations[i < eqi ? i : i + 1];
          if (oldEquation[b] == 0) { // if not related to b, keep it as-is
            newEquations[i] = oldEquation;
          } else {
            // eliminate b from oldEquation by replacing it with (f1 * oldEquation) - (f2 * equations[eqi])
            int f = oldEquation[b] * equations[eqi][b]
                / (BigInteger.valueOf(oldEquation[b]).gcd(BigInteger.valueOf(equations[eqi][b])).intValue());
            int f1 = f / oldEquation[b];
            int f2 = f / equations[eqi][b];
            for (int v = 0; v < oldEquation.length; v++) {
              newEquations[i][v] = f1 * oldEquation[v] - (f2 * equations[eqi][v]);
            }
          }
        }
        solutions.add(new Solution() {
          @Override
          public int button() {
            return b;
          }

          @Override
          public int solve(int[] known) {
            int c = 0; // how much we have from the other factors in equation eqi
            for (int bb = 0; bb < buttonCount; bb++) {
              if (bb != b) {
                c += equations[eqi][bb] * known[bb];
              }
            }
            int need = equations[eqi][buttonCount] - c;
            int factorForB = equations[eqi][b];
            if (need % factorForB != 0) { // can't have a non-whole number of button presses
              return -1;
            }
            return need / factorForB;
          }
        });
        // recursively solve the rest
        return solve(buttonCount, remainingButtonsToSolve, solutions, newEquations, max);
      }
    }

    // bruteforce the remaining buttonsToSolve
//    System.out.println("Bruteforcing " + buttonsToSolve.stream().map(bb -> (long) max[bb]).reduce(1L, (x,y) -> x*y));
    return bruteforce(buttonCount, buttonsToSolve, solutions, equations, max);
  }

  static boolean satisfies(int[] equation, int[] solved) {
    int jolt = 0;
    for (int i = 0; i < equation.length - 1; i++) {
      jolt += equation[i] * solved[i];
    }
    return jolt == equation[equation.length - 1];
  }

  static int bruteforce(
      int buttonCount, List<Integer> buttonsToSolve, List<Solution> solutions, int[][] equations, int[] max) {
    if (buttonsToSolve.isEmpty()) {
      int totalPresses = 0;
      int[] solved = new int[buttonCount];
      for (Solution solution : solutions.reversed()) {
        int b = solution.button();
        int bPresses = solution.solve(solved);
        if (bPresses < 0 || bPresses > max[b]) {
          return Integer.MAX_VALUE;
        }
        totalPresses += bPresses;
        solved[b] = bPresses;
      }
      for (int[] equation : equations) {
        if (!satisfies(equation, solved)) {
          return Integer.MAX_VALUE;
        }
      }

      return totalPresses;
    }
    int b = buttonsToSolve.getFirst();
    List<Integer> remainingFreeButtons = buttonsToSolve.subList(1, buttonsToSolve.size());
    int best = Integer.MAX_VALUE;
    for (int i = 0; i <= max[b]; i++) {
      solutions.add(new BruteforcedSolution(b, i));
      best = Math.min(best, bruteforce(buttonCount, remainingFreeButtons, solutions, equations, max));
      solutions.removeLast();
    }
    return best;
  }

  /// Solution for [#button()], given that all the buttons that we chose to solve of bruteforce afterward have known
  /// values
  interface Solution {
    int button();
    int solve(int[] known);
  }

  record BruteforcedSolution(int button, int presses) implements Solution {
    @Override
    public int solve(int[] known) {
      return presses;
    }
  }
}
