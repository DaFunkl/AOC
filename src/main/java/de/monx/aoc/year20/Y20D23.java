package de.monx.aoc.year20;

import de.monx.aoc.util.Day;

public class Y20D23 extends Day {
	static final String input = "685974213";

	@Override
	public Object part1() {
		int[] data = inToData(-1);
		return solve(data, 100, false);
	}

	@Override
	public Object part2() {
		int[] data = inToData(1_000_000);
		return solve(data, 10_000_000, true);
	}

	static String solve(int[] data, int steps, boolean p2) {
		int current = data[0];
		int maxVal = data[data.length - 1];
		for (int q = 1; q <= steps; q++) {
			int[] narr = new int[3];
			int accu = current;
			for (int i = 0; i < 3; i++) {
				narr[i] = data[accu];
				accu = data[accu];
			}
			data[current] = data[accu];
			int dest = current - 1;
			while (dest <= 0 || contains(narr, dest)) {
				if (dest <= 0) {
					dest = maxVal;
				} else {
					dest--;
				}
			}
			data[narr[2]] = data[dest];
			data[dest] = narr[0];
			current = data[current];
		}
		if (p2) {
			long a = data[1];
			long b = data[data[1]];
			long res = a * b;
			return res + "";
		} else {
			String str = toStr(data, "");
			String[] spl = str.split("1");
			return spl[1] + spl[0];
		}
	}

	static boolean contains(int[] arr, int nr) {
		for (int n : arr) {
			if (n == nr) {
				return true;
			}
		}
		return false;
	}

	static String toStr(int[] arr, String delimiter) {
		int start = arr[0];
		int current = start;
		String ret = current + "";
		while (arr[current] != start) {
			ret += delimiter + arr[current];
			current = arr[current];
		}
		return ret;
	}

	static int[] inToData(int upTo) {
		int[] ret = new int[input.length() + 2];
		if (upTo > 0) {
			ret = new int[upTo + 2];
		}
		char[] car = input.toCharArray();
		ret[0] = Integer.valueOf(car[0] + "");
		int prev = ret[0];
		ret[ret.length - 1] = ret[0];
		for (int i = 1; i < car.length; i++) {
			int current = Integer.valueOf(car[i] + "");
			if (current > ret[ret.length - 1]) {
				ret[ret.length - 1] = current;
			}
			ret[prev] = current;
			prev = current;
		}
		if (upTo >= 0) {
			for (int current = 10; current <= upTo; current++) {
				if (current > ret[ret.length - 1]) {
					ret[ret.length - 1] = current;
				}
				ret[prev] = current;
				prev = current;
			}
		}
		ret[prev] = ret[0];
		return ret;
	}
}
