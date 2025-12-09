package com.coekie.aoc2025;

import com.google.common.collect.*;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.util.Combinations;

import java.util.*;
import java.util.function.Function;

public class Day8 {
  // brute force (runs in ~30ms)
  static int solveA(String input, int connectionCount) {
    List<Vector3D> boxes = input.lines().map(Day8::parseBox).toList();

    Iterable<Pair> allPairs = Iterables.transform(new Combinations(boxes.size(), 2),
        combo -> new Pair(boxes.get(combo[0]), boxes.get(combo[1])));
    List<Pair> closestPairs =
        Ordering.from(Comparator.comparing(Pair::distance)).leastOf(allPairs.iterator(), connectionCount);

    SetMultimap<Vector3D, Vector3D> connectionMap = HashMultimap.create();
    for (Pair p : closestPairs) {
      connectionMap.put(p.a, p.b);
      connectionMap.put(p.b, p.a);
    }

    List<Integer> circuitSizes = new ArrayList<>();
    Set<Vector3D> todo = new HashSet<>(boxes);
    while (!todo.isEmpty()) {
      Vector3D circuitStart = todo.iterator().next();
      Set<Vector3D> circuit = connectedSet(circuitStart, connectionMap::get);
      todo.removeAll(circuit);
      circuitSizes.add(circuit.size());
    }
    circuitSizes.sort(Comparator.reverseOrder());
    return circuitSizes.get(0) * circuitSizes.get(1) * circuitSizes.get(2);
  }

  static <T> Set<T> connectedSet(T start, Function<T, Iterable<T>> function) {
    Set<T> result = new HashSet<>(List.of(start));
    Deque<T> q = new ArrayDeque<>(List.of(start));
    T v;
    while ((v = q.poll()) != null) {
      for (T connectedV : function.apply(v)) {
        if (result.add(connectedV)) {
          q.add(connectedV);
        }
      }
    }
    return result;
  }

  static Vector3D parseBox(String box) {
    String[] split = box.split(",");
    return new Vector3D(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
  }

  record Pair(Vector3D a, Vector3D b, double distance) {
    public Pair(Vector3D a, Vector3D b) {
      this(a, b, a.distanceSq(b));
    }
  }

  // eagerly grow the circuit adding the next closest candidate.
  // keep track for each candidate how far they are from the closest box already in the circuit.
  // the furthest jump we have to make is the connection we were looking for.
  static int solveB(String input) {
    List<Box> boxes = input.lines().map(Box::new).toList();
    // boxes we haven't added to the circuit yet, ordered by distance
    TreeSet<Box> candidates = new TreeSet<>(Comparator.<Box, Long>comparing(b -> b.closestDistance)
        .thenComparing(b -> b.x).thenComparing(b -> b.y).thenComparing(b -> b.z));
    candidates.addAll(boxes);
    long maxDistance = 0; // longest connection so far
    int result = 0; // result if maxDistance stays the longest
    candidates.first().closestDistance = 0;
    while (!candidates.isEmpty()) {
      Box current = candidates.removeFirst();
      if (current.closestDistance > maxDistance) {
        maxDistance = current.closestDistance;
        result = current.x * current.closestBox.x;
      }
      for (Box other : List.copyOf(candidates)) {
        long distance = current.distance(other);
        if (distance < other.closestDistance) {
          candidates.remove(other); // remove from old position (distance)
          other.closestBox = current;
          other.closestDistance = distance;
          candidates.add(other); // add back in new position
        }
      }
    }
    return result;
  }

  static class Box {
    final int x;
    final int y;
    final int z;

    // closest box that is already in the circuit
    Box closestBox;
    long closestDistance = Long.MAX_VALUE;

    Box(String box) {
      String[] split = box.split(",");
      this.x = Integer.parseInt(split[0]);
      this.y = Integer.parseInt(split[1]);
      this.z = Integer.parseInt(split[2]);
    }

    long distance(Box other) {
      long dx = x - other.x;
      long dy = y - other.y;
      long dz = z - other.z;
      return dx * dx + dy * dy + dz * dz;
    }
  }
}
