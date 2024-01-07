package com.coekie.aoc2023;

import static com.google.common.base.Preconditions.checkState;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// https://adventofcode.com/2023/day/20
class Day20 {
  static long solveA(String input) {
    Execution execution = parse(input);
    for (int i = 0; i < 1000; i++) {
      execution.button();
    }

    return execution.lowPulseCount * execution.highPulseCount;
  }

  static long solveB(String input) {
    Execution execution = parse(input);
    for (int i = 0; i < 50000; i++) {
      execution.button();
    }

    var kh = (ConjunctionModule) execution.modules.get("kh");
    // the pattern for each input to kh is a switch to HIGH and in same button-press back to LOW,
    // after a consistent (different for each input) number of button presses
    kh.earlyHistory.values()
        .forEach(h -> IntStream.range(0, h.size()).forEach(i ->
            checkState(h.get(i) == (i / 2 + 1) * h.getFirst())));

    // it will be at all HIGH values when all the cycles align, that is the least common multiple
    // for all its inputs
    return kh.earlyHistory.values().stream()
        .map(v -> BigInteger.valueOf(v.getFirst()))
        .reduce((a, b) -> a.multiply(b).divide(a.gcd(b)))
        .orElseThrow()
        .longValue();
  }

  private static Execution parse(String input) {
    Map<String, Module> modules = input.lines()
        .map(line -> line.substring(0, line.indexOf(' ')))
        .map(fullName -> {
          String name = stripName(fullName);
          if (fullName.startsWith("%")) {
            return new FlipFlopModule(name);
          } else if (fullName.startsWith("&")) {
            return new ConjunctionModule(name);
          } else if (fullName.equals("broadcaster") || fullName.equals("output")) {
            return new BroadcasterModule("broadcaster");
          } else {
            throw new UnsupportedOperationException("Module named " + fullName);
          }
        })
        .collect(Collectors.toMap(m -> m.name, m -> m));

    for (String line : input.lines().toList()) {
      String[] split = line.split(" -> ");
      String name = stripName(split[0]);
      Module module = modules.get(name);
      for (String destName : split[1].split(", ")) {
        module.destinations.add(destName);
        if (modules.containsKey(destName)) {
          modules.get(destName).registerInput(module);
        }
      }
    }

    return new Execution(modules);
  }

  private static String stripName(String name) {
    if (name.equals("broadcaster") || name.equals("output")) {
      return name;
    } else {
      return name.substring(1);
    }
  }

  enum PulseType {
    LOW, HIGH
  }

  record Pulse(String destName, Module source, PulseType type) {

    @Override
    public String toString() {
      return source + " -" + type.name().toLowerCase() + "-> " + destName;
    }
  }

  static final class Execution {
    private final Module button = new ButtonModule("button");
    private final Map<String, Module> modules;
    private final ArrayDeque<Pulse> queue = new ArrayDeque<>();
    private long lowPulseCount;
    private long highPulseCount;
    private long buttonPresses;

    Execution(Map<String, Module> modules) {
      this.modules = modules;
    }

    void button() {
      buttonPresses++;
      Module broadcaster = modules.get("broadcaster");
      queue.add(new Pulse(broadcaster.name, button, PulseType.LOW));
      while (!queue.isEmpty()) {
        Pulse pulse = queue.pollFirst();
        if (pulse.type == PulseType.LOW) {
          lowPulseCount++;
        } else {
          highPulseCount++;
        }
        Module dest = modules.get(pulse.destName);
        if (dest != null) {
          dest.doPulse(pulse, this);
        }
      }
    }
  }

  static abstract class Module {
    final String name;
    private final List<String> destinations = new ArrayList<>();

    Module(String name) {
      this.name = name;
    }

    abstract PulseType pulse(PulseType type, Module input, Execution execution);

    void doPulse(Pulse pulse, Execution execution) {
      PulseType outType = pulse(pulse.type, pulse.source, execution);
      if (outType != null) {
        for (String destinationName : destinations) {
          execution.queue.add(new Pulse(destinationName, this, outType));
        }
      }
    }

    void registerInput(Module input) {
    }

    @Override
    public String toString() {
      return name;
    }
  }

  // %
  static class FlipFlopModule extends Module {
    private boolean on;

    FlipFlopModule(String name) {
      super(name);
    }

    @Override
    PulseType pulse(PulseType type, Module input, Execution execution) {
      if (type == PulseType.LOW) {
        on = !on;
        return on ? PulseType.HIGH : PulseType.LOW;
      }
      return null;
    }
  }

  // &
  static class ConjunctionModule extends Module {
    Map<Module, PulseType> memory = new HashMap<>();
    Map<Module, List<Long>> earlyHistory = new HashMap<>();

    ConjunctionModule(String name) {
      super(name);
    }

    @Override
    void registerInput(Module input) {
      memory.put(input, PulseType.LOW);
    }

    @Override
    PulseType pulse(PulseType type, Module input, Execution execution) {
      if (type != memory.get(input)) {
        List<Long> h = earlyHistory.computeIfAbsent(input, _ -> new ArrayList<>());
        if (h.size() < 100) {
          h.add(execution.buttonPresses);
        }
      }
      memory.put(input, type);
      if (memory.values().stream().allMatch(t -> t == PulseType.HIGH)) {
        return PulseType.LOW;
      } else {
        return PulseType.HIGH;
      }
    }
  }

  static class BroadcasterModule extends Module {
    BroadcasterModule(String name) {
      super(name);
    }

    @Override
    PulseType pulse(PulseType type, Module input, Execution execution) {
      return type;
    }
  }

  static class ButtonModule extends Module {
    ButtonModule(String name) {
      super(name);
    }

    @Override
    PulseType pulse(PulseType type, Module input, Execution execution) {
      return type;
    }
  }
}
