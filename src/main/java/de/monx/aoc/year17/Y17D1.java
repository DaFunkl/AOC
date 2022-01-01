package de.monx.aoc.year17;

import de.monx.aoc.util.Day;

public class Y17D1 extends Day {
	String in = getInputString();

	@Override
	public Object part1() {
		return solve(1);
	}

	@Override
	public Object part2() {
		return solve(in.length() / 2);
	}

	int solve(int distance) {
		int ret = 0;
		for (int i = 0; i < in.length(); i++) {
			int a = Integer.valueOf(Character.getNumericValue(in.charAt(i)));
			int b = Integer.valueOf(Character.getNumericValue(in.charAt((i + distance) % in.length())));
			if (a == b) {
				ret += a;
			}
		}
		return ret;
	}

}
