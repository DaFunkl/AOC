package de.monx.aoc.year15;

import de.monx.aoc.util.Day;

public class Y15D1 extends Day {

	@Override
	public Object part1() {
		String in = getInputString();
		int initialSize = in.length();
		int ups = in.replace(")", "").length();
		int downs = initialSize - ups;
		return ups - downs;
	}

	@Override
	public Object part2() {
		int lvl = 0, step = 0;
		for (char c : getInputString().toCharArray()) {
			// @formatter:off
			if(lvl == -1) return step;
			step++;
			switch (c) {
				case '(': lvl++; break;
				case ')': lvl--; break;
				default: break;
			}
			// @formatter:on
		}
		return "not found :(";
	}

}
