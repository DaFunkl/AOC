package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y22D15 extends Day {

	List<int[]> in = getInputList().stream().map(x -> x.split("=")) //
			.map(x -> new int[] { //
					Integer.valueOf(x[2].split(":")[0]), //
					Integer.valueOf(x[1].split(",")[0]), //
					Integer.valueOf(x[4]), //
					Integer.valueOf(x[3].split(",")[0]) //
			}).toList();

	int md(int[] arr) {
		return Math.abs(arr[0] - arr[2]) + Math.abs(arr[1] - arr[3]);
	}

	@Override
	public Object part1() {
		int row = 2000000;
		Set<Integer> counter = new HashSet<>();
		Set<Integer> beacons = new HashSet<>();
		for (var sb : in) {
			int md = md(sb);
			int del = Math.abs(row - sb[0]);
			int dd = md - del;
			if (dd < 0) {
				continue;
			}
			if (sb[2] == row) {
				beacons.add(sb[3]);
			}
			for (int i = (sb[1] - dd); i <= (sb[1] + dd); i++) {
				if (!beacons.contains(i)) {
					counter.add(i);
				}
			}
		}
		return counter.size();
	}

	@Override
	public Object part2() {
		long max = 4000000;
		for (int cr = 0; cr < max; cr++) {
			List<int[]> ranges = new ArrayList<>();
			for (var sb : in) {
				int dd = md(sb) - Math.abs(cr - sb[0]);
				if (dd < 0) {
					continue;
				}
				ranges.add(new int[] { sb[1] - dd, sb[1] + dd });
				Collections.sort(ranges, new Comparator<int[]>() {
					@Override
					public int compare(int[] o1, int[] o2) {
						return o1[0] - o2[0];
					}
				});
				for (int i = 0; i < ranges.size(); i++) {
					int[] r1 = ranges.get(i);
					for (int j = i + 1; j < ranges.size(); j++) {
						int[] r2 = ranges.get(j);
						if ((r2[0] - 1) <= r1[1]) {
							ranges.get(i)[0] = Math.min(r1[0], r2[0]);
							ranges.get(i)[1] = Math.max(r1[1], r2[1]);
							ranges.remove(j);
							i--;
							break;
						}
					}
				}

			}
			if (ranges.size() > 1) {
				return ((long) ranges.get(1)[0] - 1) * 4000000l + cr;
			}
		}
		return -1;
	}
}
