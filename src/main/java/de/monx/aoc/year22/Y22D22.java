package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D22 extends Day {
	List<String> in = getInputList();
	List<char[]> grid = new ArrayList<>();
	List<int[]> wrapX = new ArrayList<>();
	List<int[]> wrapY = new ArrayList<>();

	List<int[]> moves = new ArrayList<>();
	int[] startPos = new int[2];
	int maxLenght = 0;

	static final int[][] _DIRS = { //
			{ 00, 01 }, // R
			{ 01, 00 }, // D
			{ 00, -1 }, // L
			{ -1, 00 } // U
	};

	static final int _R = 0;
	static final int _D = 1;
	static final int _L = 2;
	static final int _U = 3;

	char[] dc = { '>', 'V', '<', '^' };

	void printGrid() {
		System.out.println("---------------------");
		for (var g : grid) {
			System.out.println(new String(g));
		}
	}

	@Override
	public Object part1() {
		init();
		return solve(true);
	}

	@Override
	public Object part2() {
		init();
		return solve(false);
	}

	int solve(boolean p1) {
		int[] pos = { 0, wrapX.get(0)[0] };
		int cd = 0;

		for (var m : moves) {
			grid.get(pos[0])[pos[1]] = dc[cd];
			for (int i = 0; i < m[0]; i++) {
				int[] np = { pos[0] + _DIRS[cd][0], pos[1] + _DIRS[cd][1] };

//				if (np[1] == -1 && !p1) {
//					System.out.println("");
//				}

				int[] wiu = p1 ? wrapItUp1(np, pos, cd) : wrapItUp2(np, pos, cd);
				np[0] = wiu[0];
				np[1] = wiu[1];

				if (grid.get(np[0])[np[1]] == '#') {
					break;
				}
				pos = np;
				if (!p1 && grid.get(pos[0])[pos[1]] == ' ') {
					printGrid();
					System.err.println("error");
				}
				grid.get(np[0])[np[1]] = dc[cd];
				cd = wiu[2];
//				if (!p1) {
//					printGrid();
//					Util.readLine();
//				}
			}
			cd = (cd + m[1]) % 4;
		}
		return (1000 * (pos[0] + 1)) + (4 * (pos[1] + 1)) + cd;
	}

	// Hardcoded for personal input
	int[] wrapItUp2(int[] np, int[] pos, int cd) {
		int pr = pos[0] / 50;
		int nr = np[0] < 0 ? -1 : np[0] / 50;
		int pc = pos[1] / 50;
		int nc = np[1] < 0 ? -1 : np[1] / 50;
		if (np[1] != pos[1] && pc != nc) {
			if (nr == 0) {
				if (pc == 1 && nc == 2) { // 1 -> 2 continue
				} else if (pc == 2 && nc == 1) { // 2 -> 1 continue
				} else if (pc == 1 && nc == 0) { // 1 -> 4
					cd = _R;
					np[1] = 0;
					np[0] = 149 - np[0];
				} else if (pc == 2 && nc == 3) { // 2 -> 5
					cd = _L;
					np[1] = 99;
					np[0] = 149 - np[0];
				}
			} else if (nr == 1) {
				if (nc == 0) { // 3 -> 4
					cd = _D;
					np[1] = np[0] - 50;
					np[0] = 100;
				} else if (nc == 2) { // 3 -> 2
					cd = _U;
					np[1] = np[0] + 50;
					np[0] = 49;
				}
			} else if (nr == 2) {
				if (pc == 0 && nc == -1) { // 4 -> 1
					cd = _R;
					np[1] = 50;
					np[0] = 149 - np[0];
				} else if (pc == 0 && nc == 1) { // continue
				} else if (nc == 0 && pc == 1) { // continue
				} else if (pc == 1 && nc == 2) { // 5 -> 2
					cd = _L;
					np[1] = 149;
					np[0] = 149 - np[0];
				}
			} else if (nr == 3) {
				if (nc == -1) { // 6 -> 1
					cd = _D;
					np[1] = np[0] - 100;
					np[0] = 0;
				} else if (nc == 1) { // 6 -> 5
					cd = _U;
					np[1] = np[0] - 100;
					np[0] = 149;
				}
			}
		} else if (np[0] != pos[0] && pr != nr) {
			if (nc == 0) {
				if (pr == 2 && nr == 1) { // 4 -> 3
					cd = _R;
					np[0] = np[1] + 50;
					np[1] = 50;
				} else if (pr == 2 && nr == 3) { // continue
				} else if (nr == 2 && pr == 3) { // continue
				} else if (pr == 3 && nr == 4) { // 6 -> 2
					np[0] = 0;
					np[1] += 100;
				}
			} else if (nc == 1) {
				if (pr == 0 && nr == -1) { // 1 -> 6
					cd = _R;
					np[0] = np[1] + 100;
					np[1] = 0;
				} else if (pr == 0 && nr == 1) { // continue
				} else if (pr == 1 && nr == 0) { // continue
				} else if (pr == 1 && nr == 2) { // continue
				} else if (pr == 2 && nr == 1) { // continue
				} else if (pr == 2 && nr == 3) { // 5 -> 6
					cd = _L;
					np[0] = np[1] + 100;
					np[1] = 49;
				}
			} else if (nc == 2) { // 2 -> 6
				if (nr == -1) {
					np[0] = 199;
					np[1] -= 100;
				} else if (nr == 1) { // 2 -> 3
					cd = _L;
					np[0] = np[1] - 50;
					np[1] = 99;
				}
			}
		}
		return new int[] { np[0], np[1], cd };
	}

	int[] wrapItUp1(int[] np, int[] pos, int cd) {
		if (np[0] != pos[0]) {
			int[] wy = wrapY.get(np[1]);
			np[0] = np[0] > wy[1] ? wy[0] : np[0] < wy[0] ? wy[1] : np[0];
		} else {
			int[] wx = wrapX.get(np[0]);
			np[1] = np[1] > wx[1] ? wx[0] : np[1] < wx[0] ? wx[1] : np[1];
		}
		return new int[] { np[0], np[1], cd };
	}

	void init() {
		grid = new ArrayList<>();
		for (int i = 0; i < in.size() - 2; i++) {
			grid.add(in.get(i).toCharArray());
		}

		for (int i = 0; i < grid.size(); i++) {
			int start = -1;
			for (int j = 0; j < grid.get(i).length; j++) {
				if (grid.get(i)[j] != ' ') {
					start = j;
					break;
				}
			}
			maxLenght = Math.max(maxLenght, grid.get(i).length);
			wrapX.add(new int[] { start, grid.get(i).length - 1 });
		}

		for (int i = 0; i < maxLenght; i++) {
			int start = -1;
			int end = -1;
			for (int j = 0; j < grid.size(); j++) {
				if (i >= grid.get(j).length) {
					continue;
				}
				if (start == -1 && grid.get(j)[i] != ' ') {
					start = j;
				}
				if (start != -1 && grid.get(j)[i] != ' ') {
					end = j;
				}
				if (end != -1 && grid.get(j)[i] == ' ') {
					break;
				}
			}
			wrapY.add(new int[] { start, end });
		}

		char[] instr = in.get(in.size() - 1).toCharArray();
		int num = 0;
		for (char c : instr) {
			if (c == 'L') {
				moves.add(new int[] { num, 3 });
				num = 0;
			} else if (c == 'R') {
				moves.add(new int[] { num, 1 });
				num = 0;
			} else {
				num = (num * 10) + Character.getNumericValue(c);
			}
		}
		moves.add(new int[] { num, 0 });
	}
}
