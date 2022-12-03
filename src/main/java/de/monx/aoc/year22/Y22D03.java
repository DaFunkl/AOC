package de.monx.aoc.year22;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y22D03 extends Day {

	List<String> in = getInputList();
	Map<Character, Integer> vals = new HashMap<>();

	// Lowercase item types a through z have priorities 1 through 26.
	// Uppercase item types A through Z have priorities 27 through 52.
	void initVals() {
		for (int i = 1; i <= 26; i++) {
			vals.put((char) ((int) 'a' + i - 1), i);
			vals.put((char) ((int) 'A' + i - 1), i + 26);
		}
	}

	@Override
	public Object part1() {
		initVals();
		int ret = 0;
		for (var l : in) {
			int[] set = new int[53];
			for (var c : l.substring(0, l.length() / 2).toCharArray()) {
				set[vals.get(c)] = 1;
			}

			for (var c : l.substring(l.length() / 2).toCharArray()) {
				if (set[vals.get(c)] == 1) {
					set[vals.get(c)] = 2;
					ret += vals.get(c);
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 0; i < in.size();) {
			int[] set = new int[53];
			for (var c : in.get(i++).toCharArray()) {
				set[vals.get(c)] = 1;
			}
			for (var c : in.get(i++).toCharArray()) {
				if (set[vals.get(c)] == 1) {
					set[vals.get(c)] = 2;
				}
			}
			for (var c : in.get(i++).toCharArray()) {
				if (set[vals.get(c)] == 2) {
					set[vals.get(c)] = 3;
					ret += vals.get(c);
				}
			}
		}
		return ret;
	}
}
