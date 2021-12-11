package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_21_11;

public class Y21D11 extends Day {
	long sol1 = 0, sol2 = 0;
	int[][] grid = init();

	boolean isAnim = true;
	Animation anim = null;
	int animScale = 75;

	void drawAnim(int[][] grid) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(animScale * grid.length + 50, animScale * grid[0].length + 70, new DP_21_11());
		}
		((DP_21_11) anim.pane).drawGrid(grid);
	}

	@Override
	public Object part1() {
		solve();
		return sol1;
	}

	@Override
	public Object part2() {
		return sol2;
	}

	void solve() {
		drawAnim(grid);
		Util.readLine();
		long flashes = 0;
		boolean p1 = false, p2 = false;
		for (int steps = 1; steps < Long.MAX_VALUE; steps++) {
			drawAnim(grid);
			if (p1 && p2) {
				break;
			}
			if (steps == 101) {
				p1 = true;
				sol1 = flashes;
			}
			List<int[]> fp = new ArrayList<>();

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					grid[i][j]++;
					if (grid[i][j] > 9) { // flash
						grid[i][j] = 0;
						flashes++;
						fp.add(new int[] { i, j });
					}
				}
			}

			flashes += flash(grid, fp);
			if (!p2 && haveAllFlashed(grid)) {
				sol2 = steps;
				p2 = true;
			}
		}
	}

	long flash(int[][] grid, List<int[]> todos) {
		long flashes = 0;

		while (!todos.isEmpty()) {
			int i = todos.get(0)[0];
			int j = todos.get(0)[1];
			todos.remove(0);

			for (int h = -1; h < 2; h++) {
				int ii = h + i;
				if (ii < 0 || ii >= grid.length) {
					continue;
				}
				for (int v = -1; v < 2; v++) {
					int jj = v + j;
					if (jj < 0 || jj >= grid[0].length || (v == 0 && h == 0)) {
						continue;
					}
					if (grid[ii][jj] == 0) {
						continue;
					}
					grid[ii][jj]++;
					if (grid[ii][jj] > 9) {
						drawAnim(grid);
						grid[ii][jj] = 0;
						flashes++;
						todos.add(new int[] { ii, jj });
					}
				}
			}
		}
		return flashes;
	}

	boolean haveAllFlashed(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	int[][] init() {
		List<String> in = getInputList();
		int[][] ret = new int[in.size()][in.get(0).length()];
		for (int i = 0; i < ret.length; i++) {
			char[] car = in.get(i).toCharArray();
			for (int j = 0; j < ret[i].length; j++) {
				ret[i][j] = Character.getNumericValue(car[j]);
			}
		}
		return ret;
	}
}
