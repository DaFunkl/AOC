package de.monx.aoc.year22;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_22_14;

public class Y22D14 extends Day {
	int[][] mm = { //
			{ Integer.MAX_VALUE, Integer.MIN_VALUE }, //
			{ Integer.MAX_VALUE, Integer.MIN_VALUE }, //
	};
	static final int _AIR = 0;
	static final int _STONE = 1;
	static final int _SAND = 2;
	static final int _STABLE = 3;
	static final int[] _SAND_HOLE = { 0, 500 };
	int[][] grid = init();
	static final int[][] _DIR_DLR = { //
			{ 1, 0 }, //
			{ 1, -1 }, //
			{ 1, 1 },//
	};

	@Override
	public Object part1() {
//		return solve(true);
		return null;
	}

	@Override
	public Object part2() {
		return solve(false);
	}

	boolean isAnim = true;
	Animation anim = null;
	long sleep = 1;

	void drawAnim(long sleep) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(1500, 700, new DP_22_14());
			((DP_22_14) anim.pane).update(sleep, grid);
			Util.readLine();
		}
		((DP_22_14) anim.pane).update(sleep, grid);
	}

	int solve(boolean p1) {
		grid = init();
		int stables = 0;
		while (true) {
			int[] sand = { _SAND_HOLE[0], _SAND_HOLE[1] };
			boolean stable = false;
			while (!stable) {
				grid[sand[0]][sand[1]] = _SAND;
				drawAnim(0);
				stable = true;
				for (int i = 0; i < 3; i++) {
					drawAnim(0);
					if (grid[sand[0] + _DIR_DLR[i][0]][sand[1] + _DIR_DLR[i][1]] == _AIR) {
						grid[sand[0]][sand[1]] = _AIR;
						sand[0] += _DIR_DLR[i][0];
						sand[1] += _DIR_DLR[i][1];
						stable = false;
						break;
					}
				}
				if ((p1 && sand[0] > mm[0][1]) || (!p1 && grid[_SAND_HOLE[0]][_SAND_HOLE[1]] == _STABLE)) {
					break;
				}
				if (stable) {
					stables++;
					grid[sand[0]][sand[1]] = _STABLE;
					drawAnim(0);
				}
			}
			if (stables % 8 == 0) {
				drawAnim(1);
			} else {
				drawAnim(0);
			}
			if ((p1 && sand[0] > mm[0][1]) || (!p1 && grid[_SAND_HOLE[0]][_SAND_HOLE[1]] == _STABLE)) {
				break;
			}
		}
		return stables;
	}

	int[][] init() {
		int[][] ret = new int[200][700];
		for (var l : getInputList()) {
			var arr = l.split(" -> ");
			var spl = arr[0].split(",");
			int yp = Integer.valueOf(spl[1]);
			int xp = Integer.valueOf(spl[0]);
			mm[0][0] = Math.min(yp, mm[0][0]);
			mm[0][1] = Math.max(yp, mm[0][1]);
			mm[1][0] = Math.min(xp, mm[1][0]);
			mm[1][1] = Math.max(xp, mm[1][1]);
			for (int i = 1; i < arr.length; i++) {
				spl = arr[i].split(",");
				int y = Integer.valueOf(spl[1]);
				int x = Integer.valueOf(spl[0]);
				for (int yy = Math.min(y, yp); yy <= Math.max(y, yp); yy++) {
					for (int xx = Math.min(x, xp); xx <= Math.max(x, xp); xx++) {
						ret[yy][xx] = _STONE;
					}
				}
				mm[0][0] = Math.min(y, mm[0][0]);
				mm[0][1] = Math.max(y, mm[0][1]);
				mm[1][0] = Math.min(x, mm[1][0]);
				mm[1][1] = Math.max(x, mm[1][1]);
				yp = y;
				xp = x;
			}
		}
		for (int i = 0; i < ret[0].length; i++) {
			ret[mm[0][1] + 2][i] = _STONE;
		}
		return ret;
	}
}
