package de.monx.aoc.year18;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y18D3 extends Day {
//	#1 @ 565,109: 14x24
//	#2 @ 413,723: 16x28
//	#3 @ 136,229: 27x11
//	#4 @ 640,187: 10x17
	List<int[]> in = getInputList().stream() //
//	Object in = getInputList().stream() //
			.map(x -> x.replace("#", "").replace(" @ ", ",").replace(": ", ",").replace("x", ",").split(",")) //
			.map(x -> new int[] { //
					Integer.valueOf(x[0]), //
					Integer.valueOf(x[1]), Integer.valueOf(x[2]), //
					Integer.valueOf(x[3]), Integer.valueOf(x[4]), //
			}).toList();

	@Override
	public Object part1() {
		int[][] grid = new int[1000][1000];
		int ret = 0;
		for (var i : in) {
			for (int x = i[1]; x < i[1] + i[3]; x++) {
				for (int y = i[2]; y < i[2] + i[4]; y++) {
					grid[y][x]++;
					if (grid[y][x] == 2) {
						ret++;
					}
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		for (var i : in) {
			boolean overlap = false;
			for (var j : in) {
				if (i[0] == j[0]) {
					continue;
				}
				if (!(i[1] > j[1] + j[3] || j[1] > i[1] + i[3] || //
						i[2] > j[2] + j[4] || j[2] > i[2] + i[4])) {
					overlap = true;
					break;
				}
			}
			if (!overlap) {
				return i[0];
			}
		}
		return -1;
	}

}
