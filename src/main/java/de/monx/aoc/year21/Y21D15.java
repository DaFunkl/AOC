package de.monx.aoc.year21;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y21D15 extends Day {

	List<String> in = getInputList();
	int[][] grid = null;
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
		long[][] seen = new long[grid.length][grid[0].length];
		Deque<int[]> stack = new ArrayDeque<>();
		stack.push(new int[] { start.first, start.second, start.first, start.second });
		while (!stack.isEmpty()) {
			var cur = stack.pollLast();
			long rl = grid[cur[0]][cur[1]] + seen[cur[2]][cur[3]];
			if (seen[cur[0]][cur[1]] > 0 && seen[cur[0]][cur[1]] <= rl) {
				continue;
			}
			seen[cur[0]][cur[1]] = rl;
			if (cur[0] == end.first && cur[1] == end.second) {
				continue;
			}
			for (var dir : directions) {
				int[] np = { //
						dir[0] + cur[0], // new Y
						dir[1] + cur[1], // new X
						cur[0], // prev Y
						cur[1] // .prev X
				};
				if (np[0] < 0 || np[1] < 0 || np[0] > end.first || np[1] > end.second) {
					continue;
				}
				if (seen[np[0]][np[1]] > 0 && seen[np[0]][np[1]] <= rl) {
					continue;
				}
				stack.push(np);
			}
		}
		return seen[end.first][end.second] - grid[0][0];
	}

	@Override
	public Object part2() {
		long[][] seen = new long[grid.length * 5][grid[0].length * 5];
		Deque<int[]> stack = new ArrayDeque<>();
		stack.push(new int[] { start.first, start.second, start.first, start.second });
		while (!stack.isEmpty()) {
			var cur = stack.pollLast();

			long rl = seen[cur[2]][cur[3]];
			long gridRisk = (grid[cur[0] % grid.length][cur[1] % grid[0].length] //
					+ (cur[0] / grid.length) + (cur[1] / grid[0].length));
			if (gridRisk > 9) {
				gridRisk -= 9;
			}
			rl += gridRisk;
			if (seen[cur[0]][cur[1]] > 0 && seen[cur[0]][cur[1]] <= rl) {
				continue;
			}
			seen[cur[0]][cur[1]] = rl;
			if (cur[0] == bigEnd.first && cur[1] == bigEnd.second) {
				continue;
			}
			for (var dir : directions) {
				int[] np = { //
						dir[0] + cur[0], // new Y
						dir[1] + cur[1], // new X
						cur[0], // prev Y
						cur[1] // .prev X
				};
				if (np[0] < 0 || np[1] < 0 || np[0] > bigEnd.first || np[1] > bigEnd.second) {
					continue;
				}
				if (seen[np[0]][np[1]] > 0 && seen[np[0]][np[1]] <= rl) {
					continue;
				}
				stack.push(np);
			}
		}
		return seen[bigEnd.first][bigEnd.second] - grid[0][0];
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
	}
}
