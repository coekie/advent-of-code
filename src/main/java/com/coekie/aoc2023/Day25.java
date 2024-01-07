package com.coekie.aoc2023;

import java.util.Set;
import org.jgrapht.Graphs;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

// https://adventofcode.com/2023/day/25
class Day25 {
  static int solve(String input) {
    SimpleGraph<String, DefaultEdge> graph = parseGraph(input);
    Set<String> minCut = new StoerWagnerMinimumCut<>(graph).minCut();
    return minCut.size() * (graph.vertexSet().size() - minCut.size());
  }

  private static SimpleGraph<String, DefaultEdge> parseGraph(String input) {
    SimpleGraph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

    input.lines().forEach(line -> {
      String[] split = line.split(": ", 2);
      String from = split[0];
      for (String to : split[1].split(" ")) {
        Graphs.addEdgeWithVertices(graph, from, to);
      }
    });

    return graph;
  }
}
