package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y23D18 extends Day {

	List<String> inStrs = getInputList();
	List<String> in2 = new ArrayList<>();
	IntPair[] _DIRS = { //
			new IntPair(-1, 0), // U
			new IntPair(0, -1), // L
			new IntPair(1, 0), // D
			new IntPair(0, 1), // R
	};

	@Override
	public Object part1() {
		int a = 0;
		int b = 1;
		IntPair cur = new IntPair(0, 0);
		IntPair prev = new IntPair(0, 0);
//		U 2 (#caa171)
		for (var s : inStrs) {
			var spl = s.split(" ");
			var amt = Integer.valueOf(spl[1]);
			in2.add(spl[2]);
			cur = cur.add(switch (spl[0]) {
			case "U" -> _DIRS[0].mul(amt);
			case "L" -> _DIRS[1].mul(amt);
			case "D" -> _DIRS[2].mul(amt);
			case "R" -> _DIRS[3].mul(amt);
			default -> throw new IllegalArgumentException("Unexpected value: " + spl[0]);
			});
			a += cur.first * prev.second - prev.first * cur.second;
			a += Math.abs(cur.first - prev.first) + Math.abs(cur.second - prev.second);
			prev = cur;
		}
		cur = new IntPair(0, 0);
		a += cur.first * prev.second - prev.first * cur.second;
		return Math.abs(a / 2) + b;
	}

	@Override
	public Object part2() {
		long a = 0;
		long b = 1;
		long[] cur = new long[2];
		long[] prev = new long[2];
		for (var s : in2) {
			var amt = Long.parseLong(s.substring(2, s.length() - 2), 16);
			int didx = switch (s.charAt(7)) {
			case '0' -> 3;
			case '1' -> 2;
			case '2' -> 1;
			case '3' -> 0;
			default -> throw new IllegalArgumentException("Unexpected value: " + s.charAt(7));
			};
			cur[0] += _DIRS[didx].first * amt;
			cur[1] += _DIRS[didx].second * amt;
			a += cur[0] * prev[1] - prev[0] * cur[1];
			a += Math.abs(cur[0] - prev[0]) + Math.abs(cur[1] - prev[1]);
			prev[0] = cur[0];
			prev[1] = cur[1];
		}
		cur[0] = 0;
		cur[1] = 0;
		a += cur[0] * prev[1] - prev[0] * cur[1];
		return Math.abs(a / 2) + b;
	}

}
