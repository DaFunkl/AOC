package de.monx.aoc.year16;

import de.monx.aoc.util.Day;

public class Y16D18 extends Day {

	String start = getInputString();
	static final boolean _SAFE = true;
	static final boolean _TRAP = false;

	@Override
	public Object part1() {
		return solve(40);
	}

	@Override
	public Object part2() {
		return solve(400000);
	}

	int solve(int amt) {
		String[] rows = new String[amt];
		rows[0] = start;
		for (int i = 1; i < rows.length; i++) {
			rows[i] = fetchNext(rows[i - 1]);
		}
		return countSafe(rows);
		
	}
	
	String fetchNext(String row) {
		String ret = "";
		String temp = "." + row + ".";
		for (int i = 0; i < row.length(); i++) {
			ret += isTrap(temp, i + 1) ? "^" : ".";
		}
		return ret;
	}

	boolean isTrap(String s, int i) {
		int state = 0;
		if (s.charAt(i + 1) == '^') {
			state += 1;
		}
		if (s.charAt(i) == '^') {
			state += 2;
		}
		if (s.charAt(i - 1) == '^') {
			state += 4;
		}
		return state == 3 || state == 6 || state == 1 || state == 4;
	}

	int countSafe(String[] rows) { //@formatter:off
		int ret = 0;
		for(var r : rows) for(var c : r.toCharArray()) if(c == '.') ret++;
		return ret; //@formatter:on
	}
}
