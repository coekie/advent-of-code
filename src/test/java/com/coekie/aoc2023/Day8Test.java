package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2023.Day8.Network;
import com.coekie.aoc2023.Day8.Position;
import com.coekie.aoc2023.Day8.Series;
import java.util.List;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class Day8Test {
  static final String exampleB = """
      LR
            
      11A = (11B, XXX)
      11B = (XXX, 11Z)
      11Z = (11B, XXX)
      22A = (22B, XXX)
      22B = (22C, 22C)
      22C = (22Z, 22Z)
      22Z = (22B, 22B)
      XXX = (XXX, XXX)""";

  @Test
  public void exampleA1() {
    assertEquals(2, Day8.solveA("""
        RL
              
        AAA = (BBB, CCC)
        BBB = (DDD, EEE)
        CCC = (ZZZ, GGG)
        DDD = (DDD, DDD)
        EEE = (EEE, EEE)
        GGG = (GGG, GGG)
        ZZZ = (ZZZ, ZZZ)"""));
  }

  @Test
  public void exampleA2() {
    assertEquals(6, Day8.solveA("""
        LLR
              
        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)"""));
  }

  @Test
  public void realA() {
    assertEquals(12599, Day8.solveA(InputReader.read("day8-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(6, Day8.solveB(exampleB));
  }

  @Test
  public void realB() {
    assertEquals(8245452805243L, Day8.solveB(InputReader.read("day8-input")));
  }

  @Test public void testExampleSeries1() {
    Network network = Network.parse(exampleB);
    Series series = Day8.toSeries(network, network.nodesMap().get("11A"));
    assertEquals(2, series.prefixLength());
    assertEquals(4, series.length());
    assertEquals(new TreeSet<>(List.of(2L)), series.zs());

    Position pos = new Position(series, 0);
    assertEquals(0, pos.seriesPos());
    assertEquals(2, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(1, pos.seriesPos());
    assertEquals(1, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(2, pos.seriesPos());
    assertEquals(2, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(3, pos.seriesPos());
    assertEquals(1, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(2, pos.seriesPos());
    assertEquals(2, pos.nextZSteps());
  }

  @Test public void testExampleSeries2() {
    Network network = Network.parse(exampleB);
    Series series = Day8.toSeries(network, network.nodesMap().get("22A"));
    assertEquals(2, series.prefixLength());
    assertEquals(8, series.length());
    assertEquals(new TreeSet<>(List.of(3L, 6L)), series.zs());

    Position pos = new Position(series, 0);
    assertEquals(0, pos.seriesPos());
    assertEquals(3, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(1, pos.seriesPos());
    assertEquals(2, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(2, pos.seriesPos());
    assertEquals(1, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(3, pos.seriesPos());
    assertEquals(3, pos.nextZSteps());
    pos = pos.advance(1);
    assertEquals(4, pos.seriesPos());
    assertEquals(2, pos.nextZSteps());
  }

  @Test public void testMerge1() {
    Network network = Network.parse("""
        LR
                
        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
        """);

    Series series1 = Day8.toSeries(network, network.nodesMap().get("11A"));
    Series series2 = Day8.toSeries(network, network.nodesMap().get("22A"));
    Series merged = Day8.merge(series1, series2);

    assertEquals(2, merged.prefixLength());
    assertEquals(8, merged.length());
    assertEquals(new TreeSet<>(List.of(6L)), merged.zs());
  }
}