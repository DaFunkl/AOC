package de.monx.aoc.year18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y18D6 extends Day {
	List<IntPair> in = getInputList().stream() //
			.map(x -> x.split(", ")) //
			.map(x -> new IntPair(x[0], x[1]) //
			).toList();
	IntPair borders = in.stream().reduce(new IntPair(0, 0), (s, e) -> s.maxVals(e));
	int[][] grid = new int[borders.first + 1][borders.second + 1];
	Set<Integer> infinites = new HashSet<>();
	int[] counts = new int[in.size()];

	@Override
	public Object part1() {
		IntPair ip = new IntPair(0, 0);
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				int[] t = { Integer.MAX_VALUE, -1, 0 };
				int count = 0;
				for (var dots : in) {
					int md = ip.manhattenDistance(dots);
					if (md < t[0]) {
						t[0] = md;
						t[1] = count;
						t[2] = count;
					} else if (t[0] == md) {
						t[2] = -1;
					}
					count++;
				}
				if (ip.first == 0 || ip.second == 0 || //
						ip.first == (borders.first) || ip.second == (borders.second)) {
					infinites.add(t[2]);
				}
				grid[ip.first][ip.second] = t[2];
				if (t[2] >= 0) {
					counts[t[2]]++;
				}
				ip.second++;
			}
			ip.first++;
			ip.second = 0;
		}
		int max = 0;
		for (int i = 0; i < counts.length; i++) {
			if (!infinites.contains(i)) {
				max = Math.max(max, counts[i]);
			}
		}
		return max;
	}

	@Override
	public Object part2() {

		return null;
	}

}
