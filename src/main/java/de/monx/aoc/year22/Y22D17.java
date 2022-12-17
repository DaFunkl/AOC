package de.monx.aoc.year22;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y22D17 extends Day {
	char[] dir = getInputString().toCharArray();
	int[][][] shapes = { //
			{ { 1, 1, 1, 1 } }, //
			{ { 0, 1, 0 }, { 1, 1, 1 }, { 0, 1, 0 } }, //
			{ { 0, 0, 1 }, { 0, 0, 1 }, { 1, 1, 1 } }, //
			{ { 1 }, { 1 }, { 1 }, { 1 } }, //
			{ { 1, 1 }, { 1, 1 } } //
	};
	int _W = 7;
	int _DX = 2;
	int _DY = 3;

	void printGrid(int[][] grid, int height, int[][] shape, int cy, int cx) {
		for (int i = height; i >= height - 20 && i >= 0; i--) {
			StringBuilder sb = new StringBuilder().append("|");
			int sy = cy - i;
			for (int j = 0; j < _W; j++) {
				int gv = grid[i][j];
				int sx = j - cx;
				if (sy >= 0 && sy < shape.length && sx >= 0 && sx < shape[0].length) {
					gv += shape[sy][sx];
				}
				if (gv == 0) {
					sb.append(" ");
				} else if (gv == 1) {
					sb.append("#");
				} else if (gv == 2) {
					sb.append("X");
				} else {
					sb.append("-");
				}
			}
			sb.append("|");
			System.out.println(sb.toString());
//			System.out.println(Arrays.toString(grid[i])//
//					.replace("1", "#").replace("0", " ").replace(", ", ""));
		}
	}

	@Override
	public Object part1() {
		int height = 0;
		int sIdx = 0;
		int dIdx = 0;
		int[][] grid = new int[5000][_W];
		for (int i = 0; i < _W; i++) {
			grid[0][i] = 3;
		}
		for (int spawnCounter = 0; spawnCounter < 2022; spawnCounter++) {
			var shape = shapes[sIdx];
			int cx = _DX;
			int cy = height + 3 + shape.length;
			boolean landed = false;

			while (!landed) {
				int lr = dir[dIdx] == '>' ? 1 : -1;
				dIdx = (dIdx + 1) % dir.length;
				if ((cx + lr) >= 0 && (cx + lr + shape[0].length) <= 7 && canBeMoved(grid, shape, cy, cx + lr)) {
					cx += lr;
				}
				if (!canBeMoved(grid, shape, cy - 1, cx)) {
					height = Math.max(cy, height);
					landed = true;
					for (int i = 0; i < shape.length; i++) {
						for (int j = 0; j < shape[0].length; j++) {
							grid[cy - i][cx + j] = Math.max(shape[i][j], grid[cy - i][cx + j]);
						}
					}
				}
				cy--;
			}

			sIdx = (sIdx + 1) % shapes.length;
		}
		return height;
	}

	class State {
		int sIdx = 0;
		int dIdx = 0;
		int height = 0;
		int count = 0;
		int[][] state = new int[0][0];

		@Override
		public boolean equals(Object obj) {
			State o2 = (State) obj;
			int[][] oState = ((State) obj).state;
			if (o2.sIdx != sIdx || o2.dIdx != dIdx || state.length != oState.length) {
				return false;
			}
			for (int j = 0; j < state.length; j++) {
				for (int i = 0; i < 7; i++) {
					if (state[j][i] != oState[j][i]) {
						return false;
					}
				}
			}
			return true;
		}

		@Override
		public int hashCode() {
			StringBuilder sb = new StringBuilder();
			sb.append(sIdx).append(dIdx);
			for (var ss : state) {
				sb.append(Arrays.toString(ss));
			}
			return Arrays.hashCode(sb.toString().toCharArray());
		}
	}

	@Override
	public Object part2() {
		int height = 0;
		int sIdx = 0;
		int dIdx = 0;
		int[][] grid = new int[5000][_W];
		for (int i = 0; i < _W; i++) {
			grid[0][i] = 3;
		}
		Map<Integer, State> cd = new HashMap<>();
		State cState = new State();
		State pState = new State();
		long adder = 0;
		int max = Integer.MAX_VALUE;
		for (int spawnCounter = 0; spawnCounter < max; spawnCounter++) {
			var shape = shapes[sIdx];
			int cx = _DX;
			int cy = height + 3 + shape.length;
			boolean landed = false;

			cState = new State();
			cState.dIdx = dIdx;
			cState.sIdx = sIdx;
			cState.state = new int[20][7];
			cState.height = height;
			cState.count = spawnCounter;
			for (int i = 0; i < cState.state.length; i++) {
				int ii = cy - cState.state.length + i;
				for (int j = 0; j < 7; j++) {
					cState.state[i][j] = ii < 0 ? 0 : grid[ii][j];
				}
			}

			if (max == Integer.MAX_VALUE && cd.containsKey(cState.hashCode())) {
				pState = cd.get(cState.hashCode());
				long deltaClock = cState.count - pState.count;
				long deltaHeight = cState.height - pState.height;
				long remaining = 1000000000000l - spawnCounter;
				long div = remaining / deltaClock;
				adder = deltaHeight * div;

				spawnCounter = 0;
				max = (int) (remaining - (div * deltaClock));
			}
			cd.put(cState.hashCode(), cState);
			while (!landed) {
				int lr = dir[dIdx] == '>' ? 1 : -1;
				dIdx = (dIdx + 1) % dir.length;
				if ((cx + lr) >= 0 && (cx + lr + shape[0].length) <= 7 && canBeMoved(grid, shape, cy, cx + lr)) {
					cx += lr;
				}
				if (!canBeMoved(grid, shape, cy - 1, cx)) {
					height = Math.max(cy, height);
					landed = true;
					for (int i = 0; i < shape.length; i++) {
						for (int j = 0; j < shape[0].length; j++) {
							grid[cy - i][cx + j] = Math.max(shape[i][j], grid[cy - i][cx + j]);
						}
					}
				}
				cy--;
			}
			sIdx = (sIdx + 1) % shapes.length;
		}
		return ((long) height) + adder;
	}

	boolean canBeMoved(int[][] grid, int[][] shape, int cy, int cx) {
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				int gv = grid[cy - i][cx + j];
				int sv = shape[i][j];
				if ((gv + sv) > 1) {
					return false;
				}
			}
		}
		return true;
	}

}
