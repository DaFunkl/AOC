package de.monx.aoc.year18;

import de.monx.aoc.util.Day;

public class Y18D5 extends Day {

	String in = getInputString();
	static final int _DELTA = Math.abs((int) 'a' - (int) 'A');

	@Override
	public Object part1() {
		return solve(getInputString()).length();
	}

	@Override
	public Object part2() {
		String str = getInputString();
		int minVal = Integer.MAX_VALUE;
		for (int i = (int) 'a'; i < (int) 'z'; i++) {
			if (!str.contains(((char) i) + "")) {
				continue;
			}
			String newStr = str.replace(((char) i) + "", "");
			newStr = newStr.replace(((char) (i - _DELTA)) + "", "");
			int replacedLength = solve(newStr).length();
			minVal = Math.min(minVal, replacedLength);
		}
		return minVal;
	}

	String solve(String str) {
		boolean running = true;
		while (running) {
			running = false;
			int prevVal = (int) str.charAt(0);
			for (int i = 1; i < str.length(); i++) {
				int curVal = (int) str.charAt(i);
				if (Math.abs(prevVal - curVal) == _DELTA) {
					String newStr = str.substring(0, i - 1);
					newStr += str.substring(i + 1);
					str = newStr;
					running = true;
					break;
				}
				prevVal = curVal;
			}
		}
		return str.trim();
	}
}
