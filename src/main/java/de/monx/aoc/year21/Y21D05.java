package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D05 extends Day {
	List<int[]> dLines = new ArrayList<>();
	List<int[]> sLines = new ArrayList<>();
	boolean wasInit = init();

	final int girdSize = 1000;
	int[][] grid = new int[girdSize][girdSize];
	int sol1 = -1;

	@Override
	public Object part1() {
		int count = 0;
		for (var l : sLines) {
			int y1 = Math.min(l[1], l[3]);
			int y2 = Math.max(l[1], l[3]);
			int x1 = Math.min(l[0], l[2]);
			int x2 = Math.max(l[0], l[2]);
			for (int y = y1; y <= y2; y++) {
				for (int x = x1; x <= x2; x++) {
					grid[y][x]++;
					if (grid[y][x] == 2) {
						count++;
					}
				}
			}
		}
		sol1 = count;
		return count;
	}

	@Override
	public Object part2() {
		int count = 0;
		for (var l : dLines) {
			int xm = l[1] < l[3] ? 1 : -1;
			int ym = l[0] < l[2] ? 1 : -1;
			for (int i = 0; i <= Math.abs(l[0] - l[2]); i++) {
				grid[l[1] + (i * xm)][l[0] + (i * ym)]++;
				if (grid[l[1] + (i * xm)][l[0] + (i * ym)] == 2) {
					count++;
				}
			}
		}
		return count + sol1;
	}

	boolean init() {
		for (var s : getInputList()) {
			var sar = s.replace(" ", "").replace("->", ",").split(",");
			int[] arr = new int[4];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = Integer.valueOf(sar[i]);
			}
			if (arr[0] == arr[2] || arr[1] == arr[3]) {
				sLines.add(arr);
			} else {
				dLines.add(arr);
			}
		}
		return true;
	}

}
