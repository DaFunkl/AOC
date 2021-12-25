package de.monx.aoc.year21;

import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_21_25;

public class Y21D25 extends Day {
	List<String> in = getInputList();
	char[][] grid = init();

	@Override
	public Object part2() {
		return "It's done! ´!`_´!`";
	}

	boolean isAnim = true;
	Animation anim = null;
	long sleep = 25;

	void drawAnim(long sleep, char[][] grid) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(600, 650, new DP_21_25());
		}
		((DP_21_25) anim.pane).update(sleep, grid);
	}

	@Override
	public Object part1() {
		long ret = 0;
		boolean changed = true;
		drawAnim(sleep, grid);
		Util.readLine();
		while (changed) {
			char[][] ng = new char[grid.length][grid[0].length];

			changed = false;

			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[i].length; j++) {
					int jj = (j + 1) % grid[i].length;
					if (grid[i][j] == '>' && grid[i][jj] == '.') {
						ng[i][j] = '.';
						ng[i][jj] = '>';
						changed = true;
					} else {
						if (jj == 0 && ng[i][jj] != 0) {
							ng[i][jj] = ng[i][jj];
						} else {
							ng[i][jj] = grid[i][jj];
						}
					}
				}
			}
			grid = ng;
			drawAnim(sleep, grid);
			ng = new char[grid.length][grid[0].length];

			for (int j = 0; j < grid[0].length; j++) {
				for (int i = 0; i < grid.length; i++) {
					int ii = (i + 1) % grid.length;
					if (grid[i][j] == 'v' && grid[ii][j] == '.') {
						ng[i][j] = '.';
						ng[ii][j] = 'v';
						changed = true;
					} else {
						if (ii == 0 && ng[ii][j] != 0) {
							ng[ii][j] = ng[ii][j];
						} else {
							ng[ii][j] = grid[ii][j];
						}
					}
				}
			}
			grid = ng;
			ret++;
			drawAnim(sleep, grid);
		}
		return ret;
	}

	void print() {
		for (var s : grid) {
			System.out.println(new String(s));
		}
		System.out.println();
	}

	char[][] init() {
		char[][] grid = new char[in.size()][];
		for (int i = 0; i < in.size(); i++) {
			grid[i] = in.get(i).toCharArray();
		}
		return grid;
	}
}
