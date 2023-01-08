package de.monx.aoc.year18;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y18D6 extends Day {
	List<int[]> in = getInputList().stream().map(x -> x.split(", ")) //
			.map(x -> new int[] { Integer.valueOf(x[0]), Integer.valueOf(x[1]) }).toList();
	int[] max = in.stream().reduce(new int[] { 0, 0 },
			(a, b) -> new int[] { Math.max(a[0], b[0]), Math.max(a[1], b[1]) });

	int md(int a1, int a2, int b1, int b2) {
		return Math.abs(a1 - b1) + Math.abs(a2 - b2);
	}

	@Override
	public Object part1() {
		Set<Integer> infinite = new HashSet<>();
		Map<Integer, Integer> count = new HashMap<>();
		int[][] grid = new int[max[0] + 1][max[1] + 1];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				boolean inf = i == 0 || j == 0 || i == max[0] || j == max[1];
				boolean same = false;
				int minDis = Integer.MAX_VALUE;
				int idx = -1;
				for (int k = 0; k < in.size(); k++) {
					int md = md(i, j, in.get(k)[0], in.get(k)[1]);
					if (md <= minDis) {
						same = md == minDis;
						minDis = md;
						idx = k;
					}
				}
				if (!same) {
					grid[i][j] = idx + 1;
					if (inf) {
						infinite.add(idx);
					}
					count.put(idx, count.getOrDefault(idx, 0) + 1);
				}
			}
		}
		return count.entrySet().stream().filter(x -> !infinite.contains(x.getKey())) //
				.mapToInt(x -> x.getValue()).max().getAsInt();
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 0; i < max[0]; i++) {
			for (int j = 0; j < max[1]; j++) {
				int md = 0;
				for (var x : in) {
					int add = md(i, j, x[0], x[1]);
					md += add;
				}
				if (md >= 10000) {
					continue;
				}
				ret++;
			}
		}
		return ret;
	}

}
