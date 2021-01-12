package de.monx.aoc.year15;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y15D4 extends Day {

	@Override
	public Object part1() {
		StringBuilder str = new StringBuilder(getInputString());
		int iter = -1;
		String hash = "0";
		while (!hash.startsWith("00000")) {
			StringBuilder sb = new StringBuilder(str);
			sb.append(++iter);
			hash = Util.md5Hash(sb.toString());
		}
		return iter;
	}

	@Override
	public Object part2() {
		StringBuilder str = new StringBuilder(getInputString());
		int iter = -1;
		String hash = "0";
		while (!hash.startsWith("000000")) {
			StringBuilder sb = new StringBuilder(str);
			sb.append(++iter);
			hash = Util.md5Hash(sb.toString());
		}
		return iter;
	}
}
