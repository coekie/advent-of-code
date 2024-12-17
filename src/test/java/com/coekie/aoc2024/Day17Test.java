package com.coekie.aoc2024;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.coekie.aoc2024.Day17.Program;
import org.junit.jupiter.api.Test;

class Day17Test {
  // If register C contains 9, the program 2,6 would set register B to 1
  @Test
  public void exampleA1() {
    Program p = new Program(0, 0, 9, 2, 6);
    p.run();
    assertEquals(1, p.b);
  }

  // If register A contains 10, the program 5,0,5,1,5,4 would output 0,1,2.
  @Test
  public void exampleA2() {
    assertEquals("0,1,2", new Program(10, 0, 0, 5,0,5,1,5,4).run());

  }

  // If register A contains 2024, the program 0,1,5,4,3,0 would output 4,2,5,6,7,7,7,7,3,1,0 and
  // leave 0 in register A.
  @Test
  public void exampleA3() {
    Program p = new Program(2024, 0, 0, 0,1,5,4,3,0);
    assertEquals("4,2,5,6,7,7,7,7,3,1,0", p.run());
    assertEquals(0, p.a);

  }

  // If register B contains 29, the program 1,7 would set register B to 26.
  @Test
  public void exampleA4() {
    Program p = new Program(0, 29, 0, 1, 7);
    p.run();
    assertEquals(26, p.b);

  }

  // If register B contains 2024 and register C contains 43690, the program 4,0 would set register B
  // to 44354.
  @Test
  public void exampleA5() {
    Program p = new Program(0, 2024, 43690, 4, 0);
    p.run();
    assertEquals(44354, p.b);
  }

  @Test
  public void realA() {
    assertEquals("6,1,6,4,2,4,7,3,5", Day17.solveA(InputReader.read("day17-input")));
  }

  @Test
  public void exampleB() {
    assertEquals(117440, Day17.solveB("""
        Register A: 2024
        Register B: 0
        Register C: 0
        
        Program: 0,3,5,4,3,0"""));
  }

  @Test
  public void decompileExampleB() {
    assertEquals("""
        a = a >> 3
        out(a % 8)
        if (a != 0) goto 0
        """, Day17.decompile(0,3,5,4,3,0));
  }

  @Test
  public void realBIncremental() {
    assertEquals(202975183645226L, Day17.solveB(InputReader.read("day17-input")));
  }
}