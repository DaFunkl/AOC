package de.monx.aoc.year15;

import de.monx.aoc.util.Day;

public class Y15D10 extends Day {
	String input = getInputString();

	@Override
	public Object part1() {
		return fetchLength(40);
	}
	
	@Override
	public Object part2() {
		return fetchLength(50);
	}

	int fetchLength(int amt) {
		String s = input;
		for (int i = 1; i <= amt; i++) {
			s = las(s);
		}
		return s.length();
	}
	
	String las(String s) {
		StringBuilder ret = new StringBuilder();
		char[] car = s.toCharArray();
		int count = 1;
		char prev = car[0];
		for (int i = 1; i < car.length; i++) {
			char c = car[i];
			if (prev != c) {
				ret.append(count);
				ret.append(prev);
				count = 0;
			}
			count++;
			prev = c;
		}
		ret.append(count);
		ret.append(prev);
		count = 0;
		return ret.toString();
	}
}
