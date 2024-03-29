package de.monx.aoc.year23;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_23_14;

public class Y23D14 extends Day {

//	Year: 23, Day: 14
//	Part1: 109833 	 1.4587 ms
//	Part2: 99875 	 55.9938 ms

	List<char[]> in = getInputList().stream().map(String::toCharArray).toList();

	int[][] _DIRS = { //
			{ -1, 0 }, // north
			{ 0, -1 }, // west
			{ 1, 0 }, // south
			{ 0, 1 },// east
	};
	Map<String, Integer> map = new HashMap<>();

	boolean isAnim = false;
	Animation anim = null;
	long sleep = 1;

	void drawAnim(long sleep) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(1000, 1000, new DP_23_14());
			((DP_23_14) anim.pane).update(sleep, in);
			Util.readLine();
		}
		((DP_23_14) anim.pane).update(sleep, in);
	}

	@Override
	public Object part1() {

		drawAnim(1);
		int ret = solve(_DIRS[0]);
		return ret;
	}

	@Override
	public Object part2() {
		String curStr = "";
		int ret = 0;

		ret = solve(_DIRS[1]);
		ret = solve(_DIRS[2]);
		ret = solve(_DIRS[3]);
		curStr = toStr();
		int interval = -1;
		map.put(curStr, 1);
		for (int c = 2; c <= 1000000000; c++) {
			ret = solve(_DIRS[0]);
			ret = solve(_DIRS[1]);
			ret = solve(_DIRS[2]);
			ret = solve(_DIRS[3]);
			curStr = toStr();
			if (interval == -1 && map.containsKey(curStr)) {
				int prev = map.get(curStr);
				interval = c - prev;
				c += ((1000000000 - c) / interval) * interval;
			}
			map.put(curStr, c);
		}
		return ret;
	}

	int solve(int[] d) {
		int ret = 0;
		int i = d[0] == 1 ? in.size() - 1 : 0;
		int ii = d[0] == 1 ? -1 : 1;
		for (; i < in.size() && i >= 0; i += ii) {
			int j = d[1] == 1 ? in.get(0).length - 1 : 0;
			int jj = d[1] == 1 ? -1 : 1;
			for (; j < in.get(i).length && j >= 0; j += jj) {
				if (in.get(i)[j] != 'O') {
					continue;
				}
				int k = i + d[0];
				for (; k >= 0 && k <= in.size() - 1 && in.get(k)[j] == '.'; k += d[0]) {
				}
				int n = j + d[1];
				for (; n >= 0 && n <= in.get(i).length - 1 && in.get(i)[n] == '.'; n += d[1]) {
				}
				k = Math.max(0, Math.min(in.size() - 1, k));
				n = Math.max(0, Math.min(in.get(0).length - 1, n));
				if (in.get(k)[n] != '.') {
					if (k != i) {
						k -= d[0];
					}
					if (n != j) {
						n -= d[1];
					}
				}
				if (in.get(k)[n] == '.') {
					in.get(k)[n] = 'O';
					in.get(i)[j] = '.';
					drawAnim(0);
					ret += in.size() - k;
				} else {
					ret += in.size() - i;
				}
//				drawAnim(1);
			}
			drawAnim(1);
		}
		drawAnim(50);
		return ret;
	}

	String toStr() {
		StringBuilder ret = new StringBuilder();
		for (var i : in) {
			ret.append(i);
			ret.append("\n");
		}
		return ret.toString();
	}

	void print() {
		for (var i : in) {
			System.out.println(new String(i));
		}
	}
}
