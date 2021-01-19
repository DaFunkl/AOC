package de.monx.aoc.year16;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y16D9 extends Day {

	@Override
	public Object part1() {
		int count = 0;
		boolean isDP = false;
		int skip = -1;
		IntPair ip = null;
		boolean firstNum = false;
		for (var c : getInputString().toCharArray()) {
			if (skip > 0) {
				skip--;
			} else if (c == '(') {
				isDP = true;
				ip = new IntPair(0, 0);
				firstNum = true;
			} else if (c == ')') {
				isDP = false;
				skip = ip.first;
				count += ip.first * ip.second;
			} else if (isDP) {
				if (c == 'x') {
					firstNum = false;
				} else {
					if (firstNum) {
						ip.first *= 10;
						ip.first += Integer.valueOf("" + c);
					} else {
						ip.second *= 10;
						ip.second += Integer.valueOf("" + c);
					}
				}
			} else {
				count++;
			}
		}
		return count;
	}

	@Override
	public Object part2() {
		return solveP2(getInputString());
	}

	long solveP2(String s) {
		if (s.isBlank()) {
			return 0;
		}
		long count = 0l;
		boolean isB = false;
		IntPair ip = new IntPair(0, 0);
		boolean firstNum = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				isB = true;
				firstNum = true;
				ip.set(0, 0);
			} else if (c == ')') {
				isB = false;
				long recRet = solveP2(s.substring(i + 1, i + 1 + ip.first));
				count += recRet * ip.second;
				i += ip.first;
				isB = false;
			} else if (c == 'x') {
				firstNum = false;
			} else if (isB) {
				if (firstNum) {
					ip.first = ip.first * 10 + Integer.valueOf("" + c);
				} else {
					ip.second = ip.second * 10 + Integer.valueOf("" + c);
				}
			} else {
				count++;
			}
		}
		return count;
	}
}
