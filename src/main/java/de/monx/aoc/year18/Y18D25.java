package de.monx.aoc.year18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y18D25 extends Day {
	List<int[]> in = getInputList().stream().map(x -> x.split(",")).map(x -> new int[] { //
			Integer.valueOf(x[0]), //
			Integer.valueOf(x[1]), //
			Integer.valueOf(x[2]), //
			Integer.valueOf(x[3]) //
	}).toList();

	@Override
	public Object part1() {
		int groups = 0;
		Set<Integer> seen = new HashSet<>();
		for (int i = 0; i < in.size(); i++) {
			if (seen.contains(i)) {
				continue;
			}
			Set<Integer> group = new HashSet<>();
			group.add(i);
			seen.add(i);
			groups++;
			boolean added = true;
			while (added) {
				added = false;
				for (int j = i + 1; j < in.size(); j++) {
					if (seen.contains(j)) {
						continue;
					}
					for (var g : group) {
						if (mhd(g, j) < 4) {
							group.add(j);
							seen.add(j);
							added = true;
							break;
						}
					}
				}
			}
		}
		return groups;
	}

	@Override
	public Object part2() {
		return "Yip Yip Ahoi!";
	}

	int mhd(int i, int j) {
		int[] a = in.get(i);
		int[] b = in.get(j);
		return Math.abs(a[0] - b[0]) //
				+ Math.abs(a[1] - b[1]) //
				+ Math.abs(a[2] - b[2]) //
				+ Math.abs(a[3] - b[3]);
	}
}
