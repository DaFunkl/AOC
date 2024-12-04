package de.monx.aoc.year24;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D04 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		int sum = 0;
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(i).length(); j++) {
				char[][] car = new char[8][4];
				for (int x = 0; x < 4; x++) {
					car[0][x] = getChar(i, j + x); // r
					car[1][x] = getChar(i, j - x); // l
					car[2][x] = getChar(i + x, j); // d
					car[3][x] = getChar(i - x, j); // u
					car[4][x] = getChar(i + x, j + x); // dr
					car[5][x] = getChar(i - x, j + x); // ur
					car[6][x] = getChar(i + x, j - x); // dl
					car[7][x] = getChar(i - x, j - x); // ul
				}
				for (var ca : car) {
					if (new String(ca).equals("XMAS")) {
						sum++;
					}
				}
			}
		}

		return sum;
	}

	@Override
	public Object part2() {
		int sum = 0;
		for (int i = 1; i < in.size(); i++) {
			for (int j = 1; j < in.get(i).length(); j++) {
				char[][] car = new char[4][3];
				for (int x = -1; x < 2; x++) {
					car[0][x + 1] = getChar(i + x, j + x); // dr
					car[1][x + 1] = getChar(i - x, j + x); // ur
					car[2][x + 1] = getChar(i + x, j - x); // dl
					car[3][x + 1] = getChar(i - x, j - x); // ul
				}
				int amt = 0;
				for (var ca : car) {
					if (new String(ca).equals("MAS")) {
						amt++;
					}
				}
				if (amt > 1) {
					sum++;
				}
			}
		}
		return sum;
	}

	char getChar(int y, int x) {
		if (x < 0 || y < 0 || y >= in.size() || x >= in.get(y).length()) {
			return '.';
		}
		return in.get(y).charAt(x);
	}
}
