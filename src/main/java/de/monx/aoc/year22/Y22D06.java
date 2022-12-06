package de.monx.aoc.year22;

import java.util.HashSet;
import java.util.Set;

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
		Set<Character> s = new HashSet<>();
		for (var c : arr) {
			s.add(c);
		}
		return s.size() == arr.length;
	}

}
