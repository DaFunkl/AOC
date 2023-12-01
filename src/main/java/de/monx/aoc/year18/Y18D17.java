package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y18D17 extends Day {

	int[] start = null;
	char[][] grid = init();

	@Override
	public Object part1() {
		print(start);
		return null;
	}

	@Override
	public Object part2() {
		return null;
	}

	void print(int[] coords) {
		for (int i = Math.max(0, coords[0] - 5); i < Math.min(grid.length, coords[0] + 20); i++) {
			StringBuilder sb = new StringBuilder();
//			for (int j = Math.max(0, coords[1] - 20); j < Math.min(grid[i].length, coords[1] + 21); j++) {
			for (int j = 0; j < grid[i].length; j++) {
				sb.append(grid[i][j]);
			}
			System.out.println(sb.toString());
		}
	}

	char[][] init() {
		int[] mm = { //
				Integer.MAX_VALUE, Integer.MIN_VALUE, //
				Integer.MAX_VALUE, Integer.MIN_VALUE //
		};
		List<int[]> li = new ArrayList<>();
		for (var i : getInputList()) {
			var spl = i.split(", ");
			int n1 = Integer.valueOf(spl[0].split("=")[1]);
			spl = spl[1].split("=")[1].split("\\.\\.");
			int n2 = Integer.valueOf(spl[0]);
			int n3 = Integer.valueOf(spl[1]);
			if (i.startsWith("y")) {
				li.add(new int[] { n1, n1, n2, n3 });
				mm[0] = Math.min(mm[0], n1);
				mm[1] = Math.max(mm[1], n1);
				mm[2] = Math.min(mm[2], n2);
				mm[3] = Math.max(mm[3], n3);
			} else {
				li.add(new int[] { n2, n3, n1, n1 });
				mm[0] = Math.min(mm[0], n2);
				mm[1] = Math.max(mm[1], n3);
				mm[2] = Math.min(mm[2], n1);
				mm[3] = Math.max(mm[3], n1);
			}
		}

		char[][] ret = new char[mm[1] + 2][mm[3] - mm[2] + 3];
		int del = mm[3] - (mm[3] - mm[2] + 1);
		start = new int[] { 0, 500 - del };

		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
				ret[i][j] = '.';
			}
		}

		for (var co : li) {
			for (int i = co[0]; i <= co[1]; i++) {
				for (int j = co[2]; j <= co[3]; j++) {
					ret[i][j - del] = '#';
				}
			}
		}

		ret[start[0]][start[1]] = '+';
		return ret;
	}
}
