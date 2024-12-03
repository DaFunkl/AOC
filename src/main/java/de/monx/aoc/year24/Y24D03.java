package de.monx.aoc.year24;

import de.monx.aoc.util.Day;

public class Y24D03 extends Day {

	String in = getInputList().stream().reduce("", (a, b) -> a + b);

	@Override
	public Object part1() {
		return solve(in);
	}

	@Override
	public Object part2() {
		var ars = in.split("don't()");
		int sum = solve(ars[0]);
		for (int i = 1; i < ars.length; i++) {
			var arr = ars[i].split("do()");
			for (int j = 1; j < arr.length; j++) {
				sum += solve(arr[j]);
			}
		}
		return sum;
	}

	int solve(String in) {
		int sum = 0;
		String[] ars = in.split("mul\\(");
		for (var a : ars) {
			if (!a.contains(")")) {
				continue;
			}
			var str = a.split("\\)")[0];
			int[] ns = { 0, 0 };
			int nr = 0;
			for (var c : str.toCharArray()) {
				if (c == ',') {
					nr++;
				} else if (Character.getNumericValue(c) < 0) {
					nr = -1;
					break;
				} else {
					ns[nr] = (ns[nr] * 10) + Character.getNumericValue(c);
				}
			}
			if (nr != -1) {
				sum += ns[0] * ns[1];
			}
		}
		return sum;
	}

}
