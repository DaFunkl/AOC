package de.monx.aoc.year22;

import java.util.ArrayDeque;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_22_12;

public class Y22D12 extends Day {
	List<char[]> in = getInputList().stream().map(String::toCharArray).toList();
	int[][] POIs = findPOIs();
	int[][] _DIRS = { //
			{ 01, 00 }, // D
			{ -1, 00 }, // U
			{ 00, 01 }, // R
			{ 00, -1 }, // L
	};
	char[][] switcher = { //
			{ 'S', 'a' }, //
			{ 'E', 'z' } //
	};

	char[][][] condChars = { //
			{ //
					{ 'E', 'E' }, //
					{ 'z', 'y' } //
			}, { //
					{ 'a', 'S' }, //
					{ 'b', 'a' } //
			} //
	};

	int[][] findPOIs() {
		int[][] pois = new int[2][2];
		boolean f1 = false, f2 = false;
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(0).length; j++) {
				if ('S' == in.get(i)[j]) {
					pois[0][0] = i;
					pois[0][1] = j;
					f1 = true;
				}
				if ('E' == in.get(i)[j]) {
					pois[1][0] = i;
					pois[1][1] = j;
					f2 = true;
				}
				if (f1 && f2) {
					return pois;
				}
			}
		}
		return pois;
	}

//	Year: 22, Day: 12
//	Part1: 481 	 9.9025 ms
//	Part2: 480 	 4.3 ms

	boolean isAnim = true;
	Animation anim = null;
	long sleep = 1;

	void drawAnim(long sleep, int[][][] weights, int cIdx, int[] iPos) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(1400, 500, new DP_22_12());
			((DP_22_12) anim.pane).update(sleep, weights, cIdx, in, iPos);
			Util.readLine();
		}
		if (cIdx >= 0) {
			((DP_22_12) anim.pane).update(sleep, weights, cIdx, in, iPos);
		}
	}

	@Override
	public Object part1() {
		drawAnim(sleep, new int[0][0][1], -1, null);
		return solve(0);
	}

	@Override
	public Object part2() {
		return solve(1);
	}

	int md(int[] a, int[] b) {
		return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
	}

	int solve(int startPoi) {
		int[][][] weights = new int[in.size()][in.get(0).length][3];
		int min = Integer.MAX_VALUE;
		int cp1 = 0 + startPoi;
		int cp2 = (1 + startPoi) % 2;
		ArrayDeque<int[]> stack = new ArrayDeque<>();
		stack.push(new int[] { POIs[startPoi][0], POIs[startPoi][1], 0 });
		char[][] condChars = this.condChars[startPoi];
		while (!stack.isEmpty()) {
			int[] cur = stack.pollLast();
			int[] cw = weights[cur[0]][cur[1]];
			if (cur[2] != 0 && cw[2] != 0 && cw[2] <= cur[2]) {
				continue;
			}
			weights[cur[0]][cur[1]] = cur;
			drawAnim(sleep, weights, startPoi, null);
			char[] comp = { in.get(cur[0])[cur[1]], ' ' };
			if (comp[0] == switcher[startPoi][0]) {
				comp[0] = switcher[startPoi][1];
			}
			for (var d : _DIRS) {
				int[] next = { d[0] + cur[0], d[1] + cur[1], 0, cur[0], cur[1] };
				if (next[0] < 0 || next[1] < 0 || next[0] >= in.size() || next[1] >= in.get(0).length) {
					continue;
				}
				comp[1] = in.get(next[0])[next[1]];
				next[2] = cur[2] + 1;
				if (comp[1] == condChars[0][0] || comp[1] == condChars[0][1]) {
					if (comp[0] == condChars[1][0] || comp[0] == condChars[1][1]) {
						if (next[2] < min) {
							min = next[2];
							weights[next[0]][next[1]] = next;
							POIs[cp2][0] = next[0];
							POIs[cp2][1] = next[1];
						}
						break;
					}
					continue;
				}
				if ((comp[cp2] - comp[cp1]) > 1) {
					continue;
				}
				stack.push(next);
			}
		}
		int[] cp = new int[] { POIs[cp2][0], POIs[cp2][1] };
		while (!(cp[0] == POIs[cp1][0] && cp[1] == POIs[cp1][1])) {
			drawAnim(sleep * 5, weights, 0, cp);
			var np = weights[cp[0]][cp[1]];
			cp[0] = np[3];
			cp[1] = np[4];
		}
		drawAnim(sleep * 1000, weights, 0, cp);
		return min;
	}
}
