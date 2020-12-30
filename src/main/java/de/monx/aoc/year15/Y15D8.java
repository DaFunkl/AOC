package de.monx.aoc.year15;

import de.monx.aoc.util.Day;

public class Y15D8 extends Day {

	@Override
	public Object part1() {
		int count = 0;
		for (var s : getInputList()) {
			count += s.length();
			String m = s.replace("\\\\", "!") //
					.replace("\\\"", "?") //
					.replaceAll("\"", "") //
					.replaceAll("\\\\x[0-9a-f]{2}", "%"); //
			count -= m.length();
		}
		return count;
	}

	@Override
	public Object part2() {
		int count = 0;
		for (var s : getInputList()) {
			count -= s.length();
			count += adjustPart2(s).length();
		}
		return count;
	}

	static Character hexToChar(String s) {
		try {
			var t = (char) Integer.parseInt(s, 16);
			return '?';
		} catch (Exception e) {
			return null;
		}
	}

	static String adjustPart2(String s) {
		return "\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"";
	}
}
