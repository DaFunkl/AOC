package de.monx.aoc.year22;

import de.monx.aoc.util.Day;

public class Y22D06 extends Day {

	String in = getInputString();

	@Override
	public Object part1() {
		return solve(4);
	}

	@Override
	public Object part2() {
		return solve(14);
	}

	int solve(int size) {
		int idx = 0;
		char[] arr = new char[size];
		while (!(allUnique(arr) && idx >= size)) {
			arr[idx % size] = in.charAt(idx++);
		}
		return idx;
	}

	boolean allUnique(char[] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				if (arr[i] == arr[j]) {
					return false;
				}
			}
		}
		return true;
	}
}
