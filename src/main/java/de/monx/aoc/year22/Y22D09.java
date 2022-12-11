package de.monx.aoc.year22;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_22_09;

public class Y22D09 extends Day {

	boolean isAnim = true;
	Animation anim = null;
	long sleep = 10;

	void drawAnim(long sleep, int[][] knots, Set<String> seen) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(1000, 1000, new DP_22_09());
			((DP_22_09) anim.pane).update(sleep, new int[2][2], seen);
			Util.readLine();
		}
		((DP_22_09) anim.pane).update(sleep, knots, seen);
	}

	List<int[]> in = getInputList().stream().map(x -> x.split(" ")).map(x -> new int[] { //
			x[0].equals("U") ? -1 : x[0].equals("D") ? 1 : 0, //
			x[0].equals("L") ? -1 : x[0].equals("R") ? 1 : 0, //
			Integer.valueOf(x[1]) }).toList();

	int md(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
	}

	@Override
	public Object part1() {
		isAnim = false;
		return solve(2);
	}

	@Override
	public Object part2() {
		isAnim = true;
		return solve(10);
	}

	int solve(int amtKnots) {
		Set<String> seen = new HashSet<>();
		int[][] knots = new int[amtKnots][2];
		int[][] mm = new int[2][2];
		for (var x : in) {
			for (int i = 0; i < x[2]; i++) {
				knots[0][0] += x[0];
				knots[0][1] += x[1];
				for (int idx = 1; idx < knots.length; idx++) {
					int md = md(knots[idx], knots[idx - 1]);
					if (md == 2 && (knots[idx][0] == knots[idx - 1][0] || knots[idx][1] == knots[idx - 1][1])) {
						knots[idx][0] = (knots[idx - 1][0] + knots[idx][0]) / 2;
						knots[idx][1] = (knots[idx - 1][1] + knots[idx][1]) / 2;
					} else if (md >= 3) {
						int d1 = Math.abs(knots[idx - 1][0] - knots[idx][0]);
						int d2 = Math.abs(knots[idx - 1][1] - knots[idx][1]);

						knots[idx][0] = d1 == 1 ? knots[idx - 1][0]//
								: (knots[idx - 1][0] + knots[idx][0]) / 2;
						knots[idx][1] = d2 == 1 ? knots[idx - 1][1]//
								: (knots[idx - 1][1] + knots[idx][1]) / 2;
					}
//					for (var k : knots) {
//						mm[0][0] = Math.min(k[0], mm[0][0]);
//						mm[0][1] = Math.max(k[0], mm[0][1]);
//						mm[1][0] = Math.min(k[1], mm[1][0]);
//						mm[1][1] = Math.max(k[1], mm[1][1]);
//					}
//					drawAnim(1, knots, seen);
				}
				drawAnim(15, knots, seen);
				seen.add(knots[amtKnots - 1][0] + "," + knots[amtKnots - 1][1]);
			}
		}
		return seen.size();
	}
}
