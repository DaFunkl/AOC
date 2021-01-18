package de.monx.aoc.year16;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y16D8 extends Day {
	List<String> input = getInputList();
	int[][] p1Solution;

	@Override
	public Object part1() {
		p1Solution = solve(6, 50);
		return Arrays.stream(p1Solution).map(x -> Arrays.stream(x).sum()).reduce(0, (q, w) -> q + w);

	}

	@Override
	public Object part2() {
		for (var g : p1Solution) { //@formatter:off
			StringBuilder sb = new StringBuilder();
			for(var i : g) if(i == 1) sb.append("█"); else sb.append("-");
			System.out.println(sb.toString());
		}//@formatter:on
		return "Read Console";
	}

	int[][] solve(int height, int width) {
		int[][] grid = new int[height][width];
		for (var s : input) {
			var spl = s.split(" ");
			switch (spl[0]) { //@formatter:off
			case "rect": 	doRect(grid, spl); 	break;
			case "rotate": 	doRotate(grid, spl); break;
			default: System.err.println("Error: " + Arrays.toString(spl)); return null;
			} //@formatter:on
		}
		return grid;
	}

	void doRotate(int[][] grid, String[] spl) {
		var isColumn = spl[1].equals("column");
		var p = new IntPair(spl[2].split("=")[1], spl[4]);
		apply(grid, fetch(grid, p, isColumn), p, isColumn);
	}

	int[] fetch(int[][] grid, IntPair p, boolean isColumn) {
		int[] ret = isColumn ? new int[grid.length] : new int[grid[p.first].length];
		for (int i = 0; i < ret.length; i++) {
			int idx = i - p.second;
			while (idx < 0) {
				idx += ret.length;
			}
			idx = idx % ret.length;

			if (isColumn) {
				ret[i] = grid[idx][p.first];
			} else {
				ret[i] = grid[p.first][idx];
			}
		}
		return ret;
	}

	void apply(int[][] grid, int[] fetched, IntPair p, boolean isColumn) {
		for (int i = 0; i < fetched.length; i++) {
			if (isColumn) {
				grid[i][p.first] = fetched[i];
			} else {
				grid[p.first][i] = fetched[i];
			}
		}
	}

	void doRect(int[][] grid, String[] spl) {
		spl = spl[1].split("x");
		IntPair p = new IntPair(spl[0], spl[1]);
		for (int i = 0; i < p.second; i++) {
			for (int j = 0; j < p.first; j++) {
				grid[i][j] = 1;
			}
		}
	}

}
