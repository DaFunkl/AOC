package de.monx.aoc.year16;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y16D22 extends Day {

	List<List<int[]>> data = parse();
	final int _SIZE = 0;
	final int _USED = 1;
	final int _AVAIL = 2;
	final int _USE = 3;

	@Override
	public Object part1() {
		int count = 0;
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).size(); j++) {
				var cData = data.get(i).get(j);
				if (cData[1] == 0) {
					continue;
				}
				for (int ig = 0; ig < data.size(); ig++) {
					for (int jg = 0; jg < data.get(ig).size(); jg++) {
						var gData = data.get(ig).get(jg);
						if (i == ig && j == jg) {
							continue;
						}
						if (cData[_USED] < gData[_AVAIL]) {
							count++;
						}
					}
				}
			}
		}

		return count;
	}

	@Override
	public Object part2() {
		int x_size = data.size();
		int y_size = data.get(0).size();
		IntPair wStart = null, hole = null;
		for (int x = 0; x < x_size; x++) {
			for (int y = 0; y < y_size; y++) {
				var ip = new IntPair(x, y);
				var n = data.get(x).get(y);
				if (x == 0 && y == 0)
					System.out.print("S");
				else if (x == x_size - 1 && y == 0)
					System.out.print("G");
				else if (n[_USED] == 0) {
					hole = ip;
					System.out.print("_");
				} else if (n[_SIZE] > 250) {
					if (wStart == null) {
						wStart = new IntPair(x - 1, y);
						System.out.print("Q");
					} else {
						System.out.print("#");
					}
				} else
					System.out.print(".");
			}
			System.out.println();
		}
//		int result = hole.manhattenDistance() - wStart.manhattenDistance();
		int result = Math.abs(hole.first - wStart.first) + Math.abs(hole.second - wStart.second);
		result += x_size - wStart.first + wStart.second;
		return result + 5 * (x_size - 1);
	}

	List<List<int[]>> parse() {
		List<List<int[]>> ret = new ArrayList<>();
		List<int[]> current = null;
		var il = getInputList();
		for (int i = 2; i < il.size(); i++) {
			var pl = parseLine(il.get(i));
			if (pl.first == 0) {
				if (current != null) {
					ret.add(current);
				}
				current = new ArrayList<>();
			}
			current.add(pl.second);
		}
		ret.add(current);
		return ret;
	}

	Pair<Integer, int[]> parseLine(String s) {
		s = s//
				.replace("/dev/grid/node-", "") //
				.replace("x", "") //
				.replace("y", "") //
				.replace("T", "") //
				.replace("%", "") //
				.replace("-", " ");
		while (s.contains("  ")) {
			s = s.replace("  ", " ");
		}
		var spl = s.split(" ");
		return new Pair<Integer, int[]>(//
				Integer.valueOf(spl[1]), //
				new int[] { //
						Integer.valueOf(spl[2]), //
						Integer.valueOf(spl[3]), //
						Integer.valueOf(spl[4]), //
						Integer.valueOf(spl[5]) //
				});
	}
}
