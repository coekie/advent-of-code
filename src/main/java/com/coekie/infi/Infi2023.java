package com.coekie.infi;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Streams;
import java.util.List;
import java.util.stream.Stream;
import org.apache.commons.geometry.euclidean.twod.Line;
import org.apache.commons.geometry.euclidean.twod.Lines;
import org.apache.commons.geometry.euclidean.twod.Vector2D;
import org.apache.commons.geometry.euclidean.twod.shape.Circle;
import org.apache.commons.math3.util.Combinations;
import org.apache.commons.numbers.core.Precision;
import org.apache.commons.numbers.core.Precision.DoubleEquivalence;

/**
 * Solution for <a href="https://aoc.infi.nl/2023">Infi Advent of code 2023</a>.
 *
 * @author Wouter Coekaerts
 */
public class Infi2023 {
  static final DoubleEquivalence precision = Precision.doubleEquivalenceOfEpsilon(0.0000001);

  /** Parse one line of input */
  static List<Vector2D> parseLine(String line) {
    return Splitter.on("), (").splitToStream(line)
        .map(pointStr -> Vector2D.of(Splitter.on(", ")
            .splitToStream(CharMatcher.anyOf("()").removeFrom(pointStr))
            .mapToDouble(Double::parseDouble)
            .toArray()))
        .toList();
  }

  /**
   * Construct a minimal circle containing two points, so with the center at the middle between the
   * points
   */
  static Circle circleThrough2Points(Vector2D p1, Vector2D p2) {
    Vector2D mid = Vector2D.centroid(p1, p2);
    return Circle.from(mid, mid.distance(p1), precision);
  }

  /** Construct a circle going through three points */
  static Circle circleThrough3Points(Vector2D p1, Vector2D p2, Vector2D p3) {
    // line with points that have equal distance between p1 and p2
    Vector2D mid1 = Vector2D.centroid(p1, p2);
    Line line1 = Lines.fromPointAndDirection(mid1, p1.subtract(p2).orthogonal(), precision);

    // line with points that have equal distance between p2 and p3
    Vector2D mid2 = Vector2D.centroid(p1, p3);
    Line line2 = Lines.fromPointAndDirection(mid2, p1.subtract(p3).orthogonal(), precision);

    // circle center has equal distance from p1, p2 and p3 = the intersection between those lines
    Vector2D center = line1.intersection(line2);

    return Circle.from(center, center.distance(p1), precision);
  }

  /**
   * Solve it for one set of points.
   *
   * Input is small, so no need for fancy algorithms.
   * The circle must be the minimal circle containing 2 or 3 of the points, so bruteforce all such
   * circles.
   */
  static double solvePoints(List<Vector2D> points) {
    // consider circles from 2 points
    Stream<Circle> circles2 = Streams.stream(new Combinations(points.size(), 2))
        .map(combo ->
            circleThrough2Points(points.get(combo[0]), points.get(combo[1])));
    // consider circles from 3 points
    Stream<Circle> circles3 = Streams.stream(new Combinations(points.size(), 3))
        .map(combo ->
            circleThrough3Points(points.get(combo[0]), points.get(combo[1]), points.get(combo[2])));
    // of all such circles that contain all points, take minimum radius
    return Stream.concat(circles2, circles3)
        .filter(circle -> points.stream().allMatch(circle::contains))
        .mapToDouble(Circle::getRadius)
        .min().orElseThrow();
  }

  static double solve(String input) {
    return Math.floor(input.lines()
        .map(Infi2023::parseLine)
        .mapToDouble(Infi2023::solvePoints)
        .sum());
  }
}
