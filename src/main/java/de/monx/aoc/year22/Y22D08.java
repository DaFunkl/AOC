package de.monx.aoc.year22;

import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y22D08 extends Day {

	int max = 0;
	int[][] grid = init();
	static final IntPair[] _DIRS = Util._DIRS4;

	@Override
	public Object part1() {
		int ret = grid.length * 2 + grid[0].length * 2 - 4;
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[0].length - 1; j++) {
				int val = grid[i][j];
				boolean[] canBeSeen = { true, true, true, true };
				int closedByDirs = 0;
				for (int d = 1; d < max && closedByDirs < 4; d++) {
					int dd = 0;
					for (var dir : _DIRS) {
						int ii = i + dir.first * d;
						int jj = j + dir.second * d;
						if (canBeSeen[dd] && ii < grid.length && ii > -1 && jj < grid[0].length && jj > -1) {
							if (val <= grid[ii][jj]) {
								canBeSeen[dd] = false;
								closedByDirs++;
							} else if (ii == grid.length - 1 || ii == 0 || jj == grid[0].length - 1 || jj == 0) {
								break;
							}
						}
						dd++;
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
					int dd = 0;
					for (var dir : _DIRS) {
						int ii = i + dir.first * d;
						int jj = j + dir.second * d;

						if (canBeSeen[dd] && ii < grid.length && ii > -1 && jj < grid[0].length && jj > -1) {
							seen[dd]++;
							if (val <= grid[ii][jj]) {
								canBeSeen[dd] = false;
								closedByDirs++;
							}
						} else {
							canBeSeen[dd] = false;
						}
						dd++;
					}
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
