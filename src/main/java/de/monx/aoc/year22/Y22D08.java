package de.monx.aoc.year22;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D08 extends Day {

	int max = 0;
	int[][] grid = init();

	@Override
	public Object part1() {
		int ret = grid.length * 2 + grid[0].length * 2 - 4;
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[0].length - 1; j++) {
				int val = grid[i][j];
				boolean[] canBeSeen = { true, true, true, true };
				int closedByDirs = 0;
				for (int d = 1; d < max && closedByDirs < 4; d++) {
					if (canBeSeen[0] && i + d < grid.length) {
						if (val <= grid[i + d][j]) {
							canBeSeen[0] = false;
							closedByDirs++;
						} else if (i + d == grid.length - 1) {
							break;
						}
					}

					if (canBeSeen[1] && i - d > -1) {
						if (val <= grid[i - d][j]) {
							canBeSeen[1] = false;
							closedByDirs++;
						} else if (i - d == 0) {
							break;
						}
					}

					if (canBeSeen[2] && j + d < grid[0].length) {
						if (val <= grid[i][j + d]) {
							canBeSeen[2] = false;
							closedByDirs++;
						} else if (j + d == grid[0].length - 1) {
							break;
						}
					}

					if (canBeSeen[3] && j - d > -1) {
						if (val <= grid[i][j - d]) {
							canBeSeen[3] = false;
							closedByDirs++;
						} else if (j - d == 0) {
							break;
						}
					}
				}
				if (closedByDirs < 4) {
					ret++;
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[0].length - 1; j++) {
				int val = grid[i][j];
				int[] seen = new int[4];
				int closedByDirs = 0;
				boolean[] canBeSeen = { true, true, true, true };
				for (int d = 1; d < max && closedByDirs < 4; d++) {
					int dir = 0;
					if (canBeSeen[dir] && i + d < grid.length) {
						seen[dir]++;
						if (val <= grid[i + d][j]) {
							canBeSeen[dir] = false;
							closedByDirs++;
						}
					} else {
						canBeSeen[dir] = false;
					}
					dir++;

					if (canBeSeen[dir] && i - d >= 0) {
						seen[dir]++;
						if (val <= grid[i - d][j]) {
							canBeSeen[dir] = false;
							closedByDirs++;
						}
					} else {
						canBeSeen[dir] = false;
					}
					dir++;

					if (canBeSeen[dir] && j + d < grid[0].length) {
						seen[dir]++;
						if (val <= grid[i][j + d]) {
							canBeSeen[dir] = false;
							closedByDirs++;
						}
					} else {
						canBeSeen[dir] = false;
					}
					dir++;

					if (canBeSeen[dir] && j - d >= 0) {
						seen[dir]++;
						if (val <= grid[i][j - d]) {
							canBeSeen[dir] = false;
							closedByDirs++;
						}
					} else {
						canBeSeen[dir] = false;
					}
					dir++;
				}
				int score = seen[0] * seen[1] * seen[2] * seen[3];
				ret = Math.max(score, ret);
			}
		}
		return ret;
	}

	int[][] init() {
		List<String> in = getInputList();
		int[][] ret = new int[in.size()][in.get(0).length()];
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(i).length(); j++) {
				ret[i][j] = Character.getNumericValue(in.get(i).charAt(j));
			}
		}
		max = Math.max(in.size(), in.get(0).length());
		return ret;
	}
}
