package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_21_09;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y21D09 extends Day {

	int[][] in = init();
	int[][] animGrid = null;
	List<IntPair> lowPoints = new ArrayList<>();

	boolean isAnim = true;
	Animation anim = null;
	int animScale = 6;

	void drawAnim() {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(animScale * in.length + 50, animScale * in[0].length + 70, new DP_21_09());
		}
		((DP_21_09) anim.pane).drawGrid(animGrid);
	}

	@Override
	public Object part1() {
		long ret = 0;
		for (int i = 0; i < in.length; i++) {
			for (int j = 0; j < in[i].length; j++) {
				int c = in[i][j];
				int[] nums = { //
						i > 0 ? in[i - 1][j] : -1, // up
						i < in.length - 1 ? in[i + 1][j] : -1, // down
						j > 0 ? in[i][j - 1] : -1, // left
						j < in[i].length - 1 ? in[i][j + 1] : -1 // right
				};
				boolean smaller = true;
				for (int x : nums) {
					if (x < 0) {
						continue;
					}
					if (x <= c) {
						smaller = false;
						break;
					}
				}
				if (smaller) {
					ret += c + 1;
					lowPoints.add(new IntPair(i, j));
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		drawAnim();
		Util.readLine();
		animGrid = new int[in.length][in[0].length];
		return lowPoints.stream() //
				.map(this::fetchBasin) // get size of all basins
				.sorted(Collections.reverseOrder()) // sort reversed
				.limit(3) // limit to three biggest
				.reduce(1, (a, b) -> a * b); // get the product of those three
	}

	int fetchBasin(IntPair lp) {
		int ret = 0;
		List<IntPair> todos = new ArrayList<>();
		Set<IntPair> seen = new HashSet<>();
		todos.add(lp);
		while (!todos.isEmpty()) {
			var p = todos.get(0);
			todos.remove(0);
			if (seen.contains(p)) {
				continue;
			}
			animGrid[p.first][p.second] = in[p.first][p.second] + 1;
			drawAnim();
			seen.add(p);
			ret++;
			IntPair[] ips = { p.add(-1, 0), p.add(1, 0), p.add(0, -1), p.add(0, 1) };
			for (var ip : ips) {
				if (!seen.contains(ip) && ip.first >= 0 && ip.first < in.length && ip.second >= 0
						&& ip.second < in[0].length) {
					if (in[ip.first][ip.second] >= 9) {
						animGrid[ip.first][ip.second] = in[ip.first][ip.second] + 1;
						seen.add(ip);
						continue;
					}
					todos.add(ip);
				}
			}
		}
		return ret;
	}

	int[][] init() {
		var in = getInputList();
		int[][] ret = new int[in.size()][in.get(0).length()];
		for (int i = 0; i < ret.length; i++) {
			for (int j = 0; j < ret[i].length; j++) {
				ret[i][j] = Character.getNumericValue(in.get(i).charAt(j));
			}
		}
		return ret;
	}
}
