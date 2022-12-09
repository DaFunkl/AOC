package de.monx.aoc.year22;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y22D09 extends Day {

	List<int[]> in = getInputList().stream().map(x -> x.split(" ")).map(x -> new int[] { //
			x[0].equals("U") ? -1 : x[0].equals("D") ? 1 : 0, //
			x[0].equals("L") ? -1 : x[0].equals("R") ? 1 : 0, //
			Integer.valueOf(x[1]) }).toList();

	int md(int[] p1, int[] p2) {
		return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
	}

	@Override
	public Object part1() {
		return solve(2);
	}

	@Override
	public Object part2() {
		return solve(10);
	}

	int solve(int amtKnots) {
		Set<String> seen = new HashSet<>();
		int[][] knots = new int[amtKnots][2];
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
				}
				seen.add(knots[amtKnots - 1][0] + "," + knots[amtKnots - 1][1]);
			}
		}
		return seen.size();
	}
}
