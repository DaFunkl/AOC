package de.monx.aoc.year18;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y18D17 extends Day {

	int waterX = 500;
	char[][] grid = init();
	int p1 = 0;
	int p2 = 0;

	@Override
	public Object part1() {
		grid[0][waterX] = '+';

		ArrayDeque<int[]> stack = new ArrayDeque<>();
		stack.addLast(new int[] { 0 + 1, waterX, });
		print(0);
		while (!stack.isEmpty()) {
			var cur = stack.pollFirst();
			var cc = grid[cur[0]][cur[1]];
			if (cc == '~') {
				continue;
			}
			cur = down(cur);
			if (cur[0] >= grid.length) {
				continue;
			}
			if (grid[cur[0]][cur[1]] == '#') {
				cur[0]--;
			}
			var flood = flood(cur);
			print(cur[0]);
			if (flood[0] == -1 && flood[1] == -1) {
				var fill = fill(cur);
				if (fill[1] != -1) {
					stack.addLast(fill);
				}
			} else {
				if (flood[0] != -1) {
					stack.addLast(new int[] { cur[0] + 1, flood[0] });
				}
				if (flood[1] != -1) {
					stack.addLast(new int[] { cur[0] + 1, flood[1] });
				}
			}
			print(cur[0]);
//			Util.readLine();
		}
		return p1;
	}

	int[] down(int[] cur) {
		while (cur[0] < grid.length && grid[cur[0]][cur[1]] == '.') {
			grid[cur[0]][cur[1]] = '|';
			p1++;
			cur[0]++;
		}
		return cur;
	}

	int[] fill(int[] cur) {
		int[] ret = { -1, -1 };
		ret[0] = cur[0] - 1;
		if (grid[ret[0]][cur[1]] == '|') {
			ret[1] = cur[1];
		} else {
			System.out.println("here");
		}
		grid[cur[0]][cur[1]] = '~';
		p2++;
		// left
		int xl = cur[1] - 1;
		while (grid[cur[0]][xl] == '|') {
			if (ret[1] == -1 && grid[ret[0]][xl] == '|') {
				ret[1] = xl;
			}
			grid[cur[0]][xl] = '~';
			p2++;
			xl--;
		}
		// right
		int xr = cur[1] + 1;
		while (grid[cur[0]][xr] == '|') {
			if (ret[1] == -1 && grid[ret[0]][xr] == '|') {
				ret[1] = xr;
			}
			grid[cur[0]][xr] = '~';
			p2++;
			xr++;
		}
		if (ret[1] == -1) {
			while (grid[cur[0]][xl] == '~' && ret[1] == -1) {
				if (ret[1] != -1 && grid[ret[0]][xl] == '|') {
					ret[1] = xl;
				}
				xl--;
			}
			while (grid[cur[0]][xr] == '~' && ret[1] == -1) {
				if (ret[1] != -1 && grid[ret[0]][xr] == '|') {
					ret[1] = xr;
				}
				xr++;
			}
		}
		return ret;
	}

	int[] flood(int[] cur) {
		int[] ret = { -1, -1 };
		// check left
		int xl = cur[1] - 1;
		while ((grid[cur[0]][xl] == '.' || grid[cur[0]][xl] == '|')
				&& (grid[cur[0] + 1][xl] == '#' || grid[cur[0] + 1][xl] == '~')) {
			if (grid[cur[0]][xl] != '|') {
				grid[cur[0]][xl] = '|';
				p1++;
			}
			xl--;
		}
		if (grid[cur[0]][xl] == '.' && grid[cur[0] + 1][xl] == '.') {
			grid[cur[0]][xl] = '|';
			p1++;
			ret[0] = xl;
		}
		// check right
		int xr = cur[1] + 1;
		while ((grid[cur[0]][xr] == '.' || grid[cur[0]][xr] == '|')
				&& (grid[cur[0] + 1][xr] == '#' || grid[cur[0] + 1][xr] == '~')) {
			grid[cur[0]][xr] = '|';
			p1++;
			xr++;
		}
		if (grid[cur[0]][xr] == '.' && grid[cur[0] + 1][xr] == '.') {
			if (grid[cur[0]][xr] != '|') {
				grid[cur[0]][xr] = '|';
				p1++;
			}
			ret[1] = xr;
		}
		return ret;
	}

	@Override
	public Object part2() {
		return p2;
	}

	void print(int cy) {
		System.out.println();
		for (int y = Math.max(0, cy - 15); y < Math.min(cy + 10, grid.length); y++) {
			System.out.println(new String(grid[y]));
		}
	}

	char[][] init() {
		int mm[] = { Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE };
		List<int[]> lines = new ArrayList<>();
		for (var l : getInputList()) {
			var spl = l.split(", ");
			int v1 = Integer.valueOf(spl[0].split("=")[1]);
			spl = spl[1].split("\\.\\.");
			int v2 = Integer.valueOf(spl[0].split("=")[1]);
			int v3 = Integer.valueOf(spl[1]);
			int[] arr = null;
			if (l.startsWith("x")) {
				arr = new int[] { v1, v1, v2, v3 };
			} else {
				arr = new int[] { v2, v3, v1, v1 };
			}

			mm[0] = Math.min(mm[0], arr[1]);
			mm[1] = Math.max(mm[1], arr[0]);
			mm[2] = Math.min(mm[2], arr[3]);
			mm[3] = Math.max(mm[3], arr[2]);

			lines.add(arr);
		}

		int off = mm[0] - 2;
		char[][] grid = new char[mm[3] + 1][mm[1] - off + 2];
		this.waterX -= off;
		System.out.println(this.waterX);
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = '.';
			}
		}
		for (var l : lines) {
			for (int x = l[0] - off; x <= l[1] - off; x++) {
				for (int y = l[2]; y <= l[3]; y++) {
					grid[y][x] = '#';
				}
			}
		}
		return grid;
	}
}
