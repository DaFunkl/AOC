package de.monx.aoc.year23;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D01 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		return calc(false);
	}

	@Override
	public Object part2() {
		return calc(true);
	}

	int calc(boolean p2) {
		int sum = 0;
		for (var s : in) {
			char[] car = s.toCharArray();
			int n1 = -1, n2 = -1;
			for (int i = 0; i < car.length && (n1 == -1 || n2 == -1); i++) {
				int v1 = num(s, i, p2);
				if (n1 == -1 && 0 <= v1 && v1 < 10) {
					n1 = v1;
				}
				int v2 = num(s, car.length - 1 - i, p2);
				if (n2 == -1 && 0 <= v2 && v2 < 10) {
					n2 = v2;
				}
			}
			sum += n2 + (10 * n1);
		}
		return sum;
	}

	int num(String s, int i, boolean p2) {
		String[] nums = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
		int v = Character.getNumericValue(s.charAt(i));
		if (!p2 || (0 <= v && v < 10)) {
			return v;
		}
		for (int j = 0; j < nums.length; j++) {
			if (s.substring(i).startsWith(nums[j])) {
				return j + 1;
			}
		}
		return -1;
	}
}
