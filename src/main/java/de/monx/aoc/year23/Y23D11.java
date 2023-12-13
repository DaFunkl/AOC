package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D11 extends Day {

	List<String> in = getInputList();
	List<int[]> stars = new ArrayList<>();
	boolean[] col = new boolean[in.size()];
	boolean[] row = new boolean[in.get(0).length()];

	@Override
	public Object part1() {
		for (int i = 0; i < in.size(); i++) {
			String s = in.get(i);
			for (int j = 0; j < s.length(); j++) {
				if (s.charAt(j) == '#') {
					col[j] = true;
					row[i] = true;
					stars.add(new int[] { i, j });
				}
			}
		}
		return solve(1);
	}

	@Override
	public Object part2() {
		return solve(1_000_000 - 1);
	}

	long solve(int spaceSize) {
		long ret = 0;
		for (int i = 0; i < stars.size(); i++) {
			var s1 = stars.get(i);
			for (int j = i + 1; j < stars.size(); j++) {
				var s2 = stars.get(j);
				long adder = 0;
				adder = Math.abs(s2[0] - s1[0]) + Math.abs(s2[1] - s1[1]);
				int[] mm = { Math.min(s1[0], s2[0]), Math.max(s1[0], s2[0]), //
						Math.min(s1[1], s2[1]), Math.max(s1[1], s2[1]), };
				for (int y = mm[0]; y <= mm[1]; y++) {
					if (!row[y]) {
						adder += spaceSize;
					}
				}
				for (int x = mm[2]; x <= mm[3]; x++) {
					if (!col[x]) {
						adder += spaceSize;
					}
				}
//				System.out.println("[" + i + "|" + j + "] -> " + adder + " -> " + ret);
				ret += adder;
			}
		}
		return ret;
	}
}
