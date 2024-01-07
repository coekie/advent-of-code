package com.coekie.aoc2023;

import com.google.common.collect.Range;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.geometry.euclidean.threed.Plane;
import org.apache.commons.geometry.euclidean.threed.Planes;
import org.apache.commons.geometry.euclidean.threed.Vector3D;
import org.apache.commons.geometry.euclidean.threed.line.Lines3D;
import org.apache.commons.math3.util.Combinations;
import org.apache.commons.numbers.core.Precision;
import org.apache.commons.numbers.core.Precision.DoubleEquivalence;

// https://adventofcode.com/2023/day/24
class Day24 {
  static long solveA(String input, double min, double max) {
    List<Stone> stones = parse(input);

    Range<Double> range = Range.closed(min, max);

    return Streams.stream(new Combinations(stones.size(), 2))
        .filter(c -> {
          Stone a = stones.get(c[0]);
          Stone b = stones.get(c[1]);
          if (a.m() == b.b()) {
            return false; // parallel
          }

          double d = b.m() - a.m();
          double x = (a.b() -b.b()) / d;
          double y = (a.b() * b.m() - b.b() * a.m()) / d;

          boolean inRange = range.contains(x) && range.contains(y);
          boolean inFuture = (Math.signum(x - a.p.getX()) == Math.signum(a.v.getX()))
              && (Math.signum(x - b.p.getX()) == Math.signum(b.v.getX()));
          return inRange && inFuture;
        })
        .count();
  }

  static long solveB(String input) {
    List<Stone> stones = parse(input);

    DoubleEquivalence precision = Precision.doubleEquivalenceOfEpsilon(0.000000001);

    Vector3D p1 = stones.get(0).p;
    Vector3D v1 = stones.get(0).v;

    // gogo relativity.
    // shift to reference frame where stone 1 is stationary at (0,0,0). ("_" = shifted ref frame)
    Vector3D p2_ = stones.get(1).p.subtract(p1);
    Vector3D v2_ = stones.get(1).v.subtract(v1);
    Vector3D p3_ = stones.get(2).p.subtract(p1);
    Vector3D v3_ = stones.get(2).v.subtract(v1);
    Vector3D p4_ = stones.get(3).p.subtract(p1);
    Vector3D v4_ = stones.get(3).v.subtract(v1);

    // line of the rock must go through the plane that contains stone 1 (fixed at (0, 0, 0)) and
    // the line that stone 2 follows
    Plane plane = Planes.fromPoints(Vector3D.of(0, 0, 0), p2_, p2_.add(v2_), precision);

    // the intersection of that plane and the line of stone 3 is where we hit stone 3
    Vector3D h3_ = plane.intersection(Lines3D.fromPointAndDirection(p3_, v3_, precision));
    double t3 = (h3_.getY() - p3_.getY()) / v3_.getY();

    // same for stone 4. (we don't even really need stone 4, but this makes it easier)
    Vector3D h4_ = plane.intersection(Lines3D.fromPointAndDirection(p4_, v4_, precision));
    double t4 = (h4_.getY() - p4_.getY()) / v4_.getY();

    // based on those two hits, derive velocity and starting position of the rock
    Vector3D rockV_ = h4_.subtract(h3_).multiply(1 / (t4 - t3));
    Vector3D rockP = h3_.subtract(t3, rockV_).add(p1);
    return Math.round(rockP.getX() + rockP.getY() + rockP.getZ());
  }

  private static List<Stone> parse(String input) {
    return input.lines()
        .map(line -> {
          List<Long> split = Stream.of(line.split("(, +| +@ +)")).map(Long::parseLong).toList();
          return new Stone(Vector3D.of(split.get(0), split.get(1), split.get(2)),
              Vector3D.of(split.get(3), split.get(4), split.get(5)));
        })
        .toList();
  }

  record Stone(Vector3D p, Vector3D v) {
    // equation for the line: y = mx + b
    double m() {
      return v.getY() / v.getX();
    }

    double b() {
      return p.getY() - m() * p.getX();
    }
  }
}
