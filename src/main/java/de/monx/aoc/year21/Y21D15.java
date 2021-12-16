package de.monx.aoc.year21;

import java.util.List;
import java.util.PriorityQueue;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y21D15 extends Day {

	List<String> in = getInputList();
	int[][] grid = null;
	int[][] riskLvl = null;
	IntPair start, end, bigEnd;

	int[][] directions = { //
			{ 00, -1 }, // left
			{ -1, 00 }, // up
			{ 01, 00 }, // down
			{ 00, 01 }, // right
	};

	@Override
	public Object part1() {
		init();
		return solve(true);
	}

	@Override
	public Object part2() {
		return solve(false);
	}

	int solve(boolean p1) {
		int[][] seen = new int[grid.length * (p1 ? 1 : 5)][grid[0].length * (p1 ? 1 : 5)];

		PriorityQueue<int[]> stack = new PriorityQueue<int[]>((a, b) -> {
			return Integer.compare(seen[a[2]][a[3]], seen[b[2]][b[3]]);
		});
		stack.add(new int[] { start.first, start.second, start.first, start.second });
		while (!stack.isEmpty()) {
			var cur = stack.poll();

			int rl = seen[cur[2]][cur[3]] + riskLvl[cur[0]][cur[1]];
			if (seen[cur[0]][cur[1]] > 0 && seen[cur[0]][cur[1]] <= rl) {
				continue;
			}
			seen[cur[0]][cur[1]] = rl;
			if (cur[0] == end.first * (p1 ? 1 : 5) && cur[1] == end.second * (p1 ? 1 : 5)) {
				continue;
			}
			for (var dir : directions) {
				int[] np = { //
						dir[0] + cur[0], // new Y
						dir[1] + cur[1], // new X
						cur[0], // prev Y
						cur[1] // .prev X
				};
				if (np[0] < 0 || np[1] < 0 || np[0] > end.first * (p1 ? 1 : 5) || np[1] > end.second * (p1 ? 1 : 5)) {
					continue;
				}

				if (seen[np[0]][np[1]] > 0 && seen[np[0]][np[1]] <= rl + riskLvl[np[0]][np[1]]) {
					continue;
				}
				stack.add(np);
			}
		}
		return seen[end.first * (p1 ? 1 : 5)][end.second * (p1 ? 1 : 5)] - grid[0][0];
	}

	void init() {
		grid = new int[in.size()][in.get(0).length()];
		start = new IntPair(0, 0);
		end = new IntPair(grid.length - 1, grid[0].length - 1);
		bigEnd = new IntPair(grid.length * 5 - 1, grid[0].length * 5 - 1);
		for (int i = 0; i < grid.length; i++) {
			char[] arr = in.get(i).toCharArray();
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = Character.getNumericValue(arr[j]);
			}
		}
		riskLvl = new int[grid.length * 5][grid[0].length * 5];
		for (int i = 0; i < riskLvl.length; i++) {
			for (int j = 0; j < riskLvl[0].length; j++) {
				riskLvl[i][j] = (grid[i % grid.length][j % grid[0].length] //
						+ (i / grid.length) + (j / grid[0].length));
				if (riskLvl[i][j] > 9) {
					riskLvl[i][j] -= 9;
				}
			}
		}
	}
}
