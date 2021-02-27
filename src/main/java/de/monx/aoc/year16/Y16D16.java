package de.monx.aoc.year16;

import de.monx.aoc.util.Day;

public class Y16D16 extends Day {

	@Override
	public Object part1() {
		return solve(getInputString(), 272);
	}

	@Override
	public Object part2() {
		return solve(getInputString(), 35651584);
	}

	Object solve(String hash, int length) {
		while (hash.length() < length) {
			hash = newHash(hash);
		}
		return checkSum(hash, length);
	}
	
	String newHash(String hash) {
		StringBuilder a = new StringBuilder(hash);
		String b = a.reverse().toString();
		a = new StringBuilder(hash);
		a.append("0");
		for (var c : b.toCharArray()) {
			a.append(c == '1' ? "0" : "1");
		}
		return a.toString();
	}

	String checkSum(String hash, int size) {
		String s = hash.substring(0, size);
		while (s.length() % 2 == 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i += 2) {
				sb.append(s.charAt(i) == s.charAt(i + 1) ? "1" : "0");
			}
			s = sb.toString();
		}
		return s;
	}
}
