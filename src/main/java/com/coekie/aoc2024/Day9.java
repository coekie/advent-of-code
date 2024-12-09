package com.coekie.aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.IntStream;

// https://adventofcode.com/2024/day/9
public class Day9 {

  /********** part A **********/

  static int[] parseA(String input) {
    int[] disk = new int[input.chars().map(i -> i - '0').sum()];
    Arrays.fill(disk, -1);
    int pos = 0;
    for (int id = 0; id <= input.length() / 2; id++) {
      int fileSize = input.charAt(id * 2) - '0';
      Arrays.fill(disk, pos, pos + fileSize, id);
      pos += fileSize;
      if (id * 2 + 1 < input.length()) {
        pos += input.charAt(id * 2 + 1) - '0';
      }
    }
    return disk;
  }

  static long solveA(String input) {
    int[] disk = parseA(input);
    long checksum = 0;
    int freePos = 0;
    for (int pos = disk.length - 1; pos >= 0; pos--) {
      if (disk[pos] != -1) {
        while (disk[freePos] != -1) freePos++;
        int newPos = freePos < pos ? freePos++ : pos;
        checksum += (long) newPos * disk[pos];
      }
    }
    return checksum;
  }

  /********** part B **********/

  record File(int id, int pos, int size) {}

  /// @param gaps Gaps indexed by their size. Key is the size, values are positions of the gaps.
  record Disk(List<File> files, TreeMap<Integer, TreeSet<Integer>> gaps) {
    TreeSet<Integer> gaps(int gapSize) {
      return gaps.computeIfAbsent(gapSize, _ -> new TreeSet<>());
    }
  }

  static Disk parseB(String input) {
    Disk disk = new Disk(new ArrayList<>(), new TreeMap<>());
    int pos = 0;
    for (int id = 0; id <= input.length() / 2; id++) {
      int fileSize = input.charAt(id * 2) - '0';
      disk.files.add(new File(id, pos, fileSize));
      pos += fileSize;
      if (id * 2 + 1 < input.length()) {
        int gapSize = input.charAt(id * 2 + 1) - '0';
        disk.gaps(gapSize).add(pos);
        pos += gapSize;
      }
    }
    return disk;
  }

  static long solveB(String input) {
    Disk disk = parseB(input);
    long checksum = 0;

    for (File file : disk.files.reversed()) {
      Optional<Integer> gapSizeOptional = disk.gaps.tailMap(file.size)
          .entrySet().stream()
          // find a gap before the current file position
          .filter(e -> !e.getValue().isEmpty() && e.getValue().first() < file.pos)
          // the first = minimum position
          .min(Comparator.comparing(e -> e.getValue().first()))
          .map(Entry::getKey);

      int newFilePos;
      if (gapSizeOptional.isPresent()) {
        int gapSize = gapSizeOptional.get();
        newFilePos = disk.gaps(gapSize).removeFirst();
        int remainingGap = gapSize - file.size;
        if (remainingGap > 0) {
          disk.gaps(remainingGap).add(newFilePos + file.size);
        }
      } else {
        newFilePos = file.pos;
      }

      checksum += IntStream.range(newFilePos, newFilePos + file.size)
          .mapToLong(pos -> (long) pos * file.id)
          .sum();
    }

    return checksum;
  }
}
