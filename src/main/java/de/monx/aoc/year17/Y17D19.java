package de.monx.aoc.year17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y17D19 extends Day {
	Map<Character, IntPair> pos = new HashMap<>();
	IntPair start = null;
	char[][] grid = init();
	String ret1 = "";
	int ret2 = 1;

	@Override
	public Object part1() {
		solve();
		return ret1;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	public void solve() {
		IntPair dir = new IntPair(1, 0);
		IntPair pos = start.clone();
		while (true) {
			pos.addi(dir);
			ret2++;
			char c = grid[pos.first][pos.second];
			while (c != '+') {
				if (c != '-' && c != '|') {
					ret1 += c;
					if (ret1.length() == this.pos.size()) {
						return;
					}
				}
				pos.addi(dir);
				ret2++;
				c = grid[pos.first][pos.second];
			}
			if (next(pos.add(dir)) != ' ') {
				continue;
			}
			dir.revi();
			if (next(pos.add(dir)) != ' ') {
				continue;
			}
			dir.muli(-1);
			if (next(pos.add(dir)) == ' ') {
				System.err.println("Error");
				break;
			}
		}
	}

	char next(IntPair ip) {
		try {
			return grid[ip.first][ip.second];
		} catch (Exception e) {
			return ' ';
		}
	}

	char[][] init() {
		List<String> in = getInputList();
		char[][] grid = new char[in.size()][in.get(0).length()];
		for (int i = 0; i < in.size(); i++) {
			char[] car = in.get(i).toCharArray();
			for (int j = 0; j < car.length; j++) {
				char c = car[j];
				grid[i][j] = c;
				switch (c) {
				case ' ':
					break;
				case '|':
					if (i == 0) {
						start = new IntPair(i, j);
					}
					break;
				case '-':
					break;
				case '+':
					break;
				default:
					pos.put(c, new IntPair(i, j));
					break;
				}
			}
		}
		return grid;
	}
}
