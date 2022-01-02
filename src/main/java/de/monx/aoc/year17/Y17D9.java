package de.monx.aoc.year17;

import de.monx.aoc.util.Day;

public class Y17D9 extends Day {
	String in = getInputString();
	int ret1, ret2;

	@Override
	public Object part1() {
		solve();
		return ret1;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	void solve() {
		ret1 = 0;
		ret2 = 0;
		int glvl = 0;
		boolean garbage = false;
		boolean ignore = false;
		for (char c : in.toCharArray()) {
			if (ignore) {
				ignore = false;
				continue;
			}
			if (c == '!') {
				ignore = true;
				continue;
			}
			if (garbage) {
				if (c == '>') {
					garbage = false;
				} else {
					ret2++;
				}
			} else {
				switch (c) {
				case '{':
					glvl++;
					ret1 += glvl;
					break;
				case '}':
					glvl--;
					break;
				case '<':
					garbage = true;
					break;
				}
			}
		}
	}

}
