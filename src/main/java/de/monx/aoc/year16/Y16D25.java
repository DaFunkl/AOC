package de.monx.aoc.year16;

import de.monx.aoc.util.Day;

public class Y16D25 extends Day {

	int[] nums = init();

	@Override
	public Object part1() {
		int adder = nums[0] * nums[1];
		int n = 0;
		while (!finished(adder, n)) {
			n++;
		}
		return n;
	}

	boolean finished(int adder, int n) {
		String str = Integer.toBinaryString(adder + n);
		boolean zero = true;
		for (int i = str.length() - 1; i >= 0; i--) {
			if (zero && str.charAt(i) != '0') {
				return false;
			} else if (!zero && str.charAt(i) != '1') {
				return false;
			}
			zero = !zero;
		}
		return true;
	}

	@Override
	public Object part2() {
		return "it's done ´°`-´°`";
	}

	int[] init() {
		var in = getInputList();
		return new int[] { //
				Integer.valueOf(in.get(1).split(" ")[1]), //
				Integer.valueOf(in.get(2).split(" ")[1]), //
		};
	}
}
