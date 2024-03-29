package de.monx.aoc.year22;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D18 extends Day {
	List<int[]> dots = getInputList().stream().map(x -> x.split(",")) //
			.map(x -> new int[] { //
					Integer.valueOf(x[0]) + 1, //
					Integer.valueOf(x[1]) + 1, //
					Integer.valueOf(x[2]) + 1 //
			}).toList();

	static final int[][] _DIRS = { //
			{ 01, 00, 00 }, //
			{ -1, 00, 00 }, //
			{ 00, 01, 00 }, //
			{ 00, -1, 00 }, //
			{ 00, 00, 01 }, //
			{ 00, 00, -1 }, //
	};

	int size = 25;
	boolean[][][] grid = new boolean[size][size][size];
	static final int _L = 1;
	int max = 0;

	@Override
	public Object part1() {
		int ret = 0;
		for (var dot : dots) {
			grid[dot[0]][dot[1]][dot[2]] = true;
			max = Math.max(max, dot[0]);
			max = Math.max(max, dot[1]);
			max = Math.max(max, dot[2]);
		}
		for (var dot : dots) {
			for (var dir : _DIRS) {
				if (!grid[dir[0] + dot[0]][dir[1] + dot[1]][dir[2] + dot[2]]) {
					ret++;
				}
			}
		}
		max += 2;
		return ret;
	}

	@Override
	public Object part2() {
		boolean[][][] seen = new boolean[max][max][max];
		ArrayDeque<int[]> stack = new ArrayDeque<>();
		stack.add(new int[] { 0, 0, 0 });
		List<int[]> exposed = new ArrayList<>();
		while (!stack.isEmpty()) {
			var dot = stack.pollLast();
			if (dot[0] < 0 || max <= dot[0] //
					|| dot[1] < 0 || max <= dot[1] //
					|| dot[2] < 0 || max <= dot[2] //
			) {
				continue;
			}
			if (seen[dot[0]][dot[1]][dot[2]]) {
				continue;
			}
			seen[dot[0]][dot[1]][dot[2]] = true;
			if (grid[dot[0]][dot[1]][dot[2]]) {
				exposed.add(dot);
				continue;
			}
			for (var dir : _DIRS) {
				stack.push(new int[] { dir[0] + dot[0], dir[1] + dot[1], dir[2] + dot[2] });
			}
		}
		int ret = 0;
		for (var dot : exposed) {
			for (var dir : _DIRS) {
				if (!grid[dir[0] + dot[0]][dir[1] + dot[1]][dir[2] + dot[2]]
						&& seen[dir[0] + dot[0]][dir[1] + dot[1]][dir[2] + dot[2]]) {
					ret++;
				}
			}
		}
		return ret;
	}

}
