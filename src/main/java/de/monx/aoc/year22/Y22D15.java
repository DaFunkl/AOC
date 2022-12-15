package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

	int md(int[] arr, int[] arr2) {
		return Math.abs(arr[0] - arr2[0]) + Math.abs(arr[1] - arr2[1]);
	}

	@Override
	public Object part1() {
		int row = 2000000;
		Set<Integer> beacons = new HashSet<>();
		List<int[]> ranges = new ArrayList<>();
		for (var sb : in) {
			int dd = md(sb) - Math.abs(row - sb[0]);
			if (dd < 0) {
				continue;
			}
			if (sb[2] == row) {
				beacons.add(sb[3]);
			}
			ranges.add(new int[] { sb[1] - dd, sb[1] + dd });
		}
		compact(ranges);
		return ranges.stream().map(x -> 1 + x[1] - x[0]).reduce(0, (a, b) -> a + b) - beacons.size();
	}

	@Override
	public Object part2() {
		int[][] pairs = new int[2][];
		int found = 0;
		for (int i = 0; i < in.size() && found < 2; i++) {
			var sb1 = in.get(i);
			int md1 = md(sb1);
			for (int j = i + 1; j < in.size() && found < 2; j++) {
				var sb2 = in.get(j);
				int md2 = md(in.get(j));
				int mdij = md(sb1, sb2);
				if (md1 + md2 + 2 == mdij) {
					int[] mm = new int[2];
					mm[0] = 1;
					if (sb1[0] < sb2[0] && sb1[1] < sb2[1]) {
						mm[0] = -1;
						mm[1] = sb1[0] + md1 + 1 + sb1[1];
					} else if (sb1[0] > sb2[0] && sb1[1] > sb2[1]) {
						mm[0] = -1;
						mm[1] = sb2[0] + md2 + 1 + sb2[1];
					} else if (sb1[0] < sb2[0] && sb1[1] > sb2[1]) {
						mm[1] = sb2[0] - md2 - 1 - sb2[1];
					} else {
						mm[1] = sb1[0] - md1 - 1 - sb1[1];
					}
					pairs[found++] = mm;
				}
			}
		}
		long x = (pairs[1][1] - pairs[0][1]) / (pairs[0][0] - pairs[1][0]);
		long y = pairs[0][0] * x + pairs[0][1];
		return x * 4000000l + y;
	}

	public void sort(List<int[]> ranges) {
		Collections.sort(ranges, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0] - o2[0];
			}
		});
	}

	public void compact(List<int[]> ranges) {
		sort(ranges);
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
}
