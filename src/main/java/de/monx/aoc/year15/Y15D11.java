package de.monx.aoc.year15;

import de.monx.aoc.util.Day;

public class Y15D11 extends Day {
	char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' };
	int[] skip = { 8, 11, 14 };
	String input = getInputString();

	@Override
	public Object part1() {
		return fetchNewPw(input);
	}

	@Override
	public Object part2() {
		return fetchNewPw(incrementPW((String) part1()));
	}

	String fetchNewPw(String input) {
		String pw = input;
		while (!isPwVald(pw)) {
			pw = incrementPW(pw);
		}
		return pw;
	}

	String incrementPW(String pw) {
		char[] npw = pw.toCharArray();
		for (int i = npw.length - 1; i >= 0; i--) {
			int ci = normalizeChar(npw[i]);
			if (ci == 25) {
				npw[i] = letters[0];
			} else {
				npw[i] = letters[ci + 1];
				break;
			}
		}
		return new String(npw);
	}

	int normalizeChar(char c) {
		return (int) c - (int) 'a';
	}

	boolean isPwVald(String pw) {
		if (pw.contains("i") || pw.contains("l") || pw.contains("o")) {
			return false;
		}
		boolean[] valid = { false, false };
		char[] arr = pw.toCharArray();
		char[] prev = { arr[0], arr[1] };
		int overlap = -1;
		if (prev[0] == prev[1]) {
			overlap = 1;
		}
		for (int i = 2; i < arr.length; i++) {
			char c = arr[i];
			if (prev[1] == c) {
				if (overlap > 0 && i - overlap > 1) {
					valid[0] = true;
				}
				if (overlap < 0) {
					overlap = i;
				}
			}
			if (isSequence(prev[0], prev[1], c)) {
				valid[1] = true;
			}
			if (valid[0] && valid[1]) {
				return true;
			}
			prev[0] = prev[1];
			prev[1] = c;
		}
		return false;
	}

	boolean isSequence(char c1, char c2, char c3) {
		int i1 = c1;
		int i2 = c2;
		int i3 = c3;
		return (i3 - i2 == 1) && (i2 - i1 == 1);
	}
}
