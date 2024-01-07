package com.coekie.aoc2023;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2023.Day19.Rule;
import com.coekie.aoc2023.Day19.Workflow;
import com.google.common.collect.Range;
import java.util.List;
import org.junit.jupiter.api.Test;

class Day19Test {

  public static final String example = """
      px{a<2006:qkq,m>2090:A,rfg}
      pv{a>1716:R,A}
      lnx{m>1548:A,A}
      rfg{s<537:gd,x>2440:R,A}
      qs{s>3448:A,lnx}
      qkq{x<1416:A,crn}
      crn{x>2662:A,R}
      in{s<1351:px,qqz}
      qqz{s>2770:qs,m<1801:hdj,R}
      gd{a>3333:R,R}
      hdj{m>838:A,pv}
              
      {x=787,m=2655,a=1222,s=2876}
      {x=1679,m=44,a=2067,s=496}
      {x=2036,m=264,a=79,s=2244}
      {x=2461,m=1339,a=466,s=291}
      {x=2127,m=1623,a=2188,s=1013}""";

  @Test
  public void parseWorkflow() {
    assertEquals(
        new Workflow("px", List.of(
            new Rule("a", Range.closedOpen(1, 2006), Range.closed(2006, 4000), "qkq"),
            new Rule("m", Range.openClosed(2090, 4000), Range.closed(1, 2090), "A"),
            new Rule(null, null, null, "rfg"))),
        Workflow.parse("px{a<2006:qkq,m>2090:A,rfg}"));
  }

  @Test
  public void exampleA() {
    assertEquals(19114, Day19.solveA(example));
  }

  @Test
  public void realA() {
    assertEquals(399284, Day19.solveA(InputReader.read("day19-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(167409079868000L, Day19.solveB(example));
  }

  @Test
  public void realB() {
    assertEquals(121964982771486L, Day19.solveB(InputReader.read("day19-input")));
  }
}