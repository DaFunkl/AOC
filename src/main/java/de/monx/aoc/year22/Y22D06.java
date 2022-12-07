package de.monx.aoc.year22;

import de.monx.aoc.util.Day;

public class Y22D06 extends Day {

	String in = getInputString();

	@Override
	public Object part1() {
		return solve(4);
	}

	@Override
	public Object part2() {
		return solve(14);
	}

	int solve(int size) {
		int idx = size;
		boolean[] arr = new boolean[26];
		while (idx < in.length()) {
			arr = new boolean[26];
			int adder = 0;
			for (int i = 0; i < size; i++) {
				if (arr[(int) in.charAt(idx - i) - (int) 'a']) {
					adder = size - i;
					break;
				} else {
					arr[(int) in.charAt(idx - i) - (int) 'a'] = true;
				}
			}
			if (adder == 0) {
				return idx + 1;
			}
			idx += adder;
		}
		return idx;
	}
}
