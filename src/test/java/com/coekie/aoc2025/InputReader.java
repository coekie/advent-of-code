package com.coekie.aoc2025;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class InputReader {
  static String read(String filename) {
    try {
      return Files.readString(Path.of("../advent-of-code-private/aoc2025", filename));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
