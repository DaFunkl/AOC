package de.monx.aoc.year17;

import de.monx.aoc.util.Day;

public class Y17D2 extends Day {
	int[][] in = init();

	@Override
	public Object part1() {
		int ret = 0;
		for (var g : in) {
			int[] mm = { Integer.MAX_VALUE, Integer.MIN_VALUE };
			for (var n : g) {
				mm[0] = Math.min(mm[0], n);
				mm[1] = Math.max(mm[1], n);
			}
			ret += mm[1] - mm[0];
		}
		return ret;
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (var g : in) {
			for (int i = 1; i < g.length; i++) {
				boolean found = false;
				int a = g[i];
				for (int j = 0; j < i; j++) {
					int b = g[j];
					if (a % b == 0) {
						ret += a / b;
						found = true;
						break;
					}
					if (b % a == 0) {
						ret += b / a;
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
			}
		}
		return ret;
	}

	int[][] init() {
		var in = getInputList();
		int[][] ret = new int[in.size()][];
		for (int i = 0; i < in.size(); i++) {
			var sar = in.get(i).split("\t");
			int[] arr = new int[sar.length];
			for (int j = 0; j < sar.length; j++) {
				arr[j] = Integer.valueOf(sar[j]);
			}
			ret[i] = arr;
		}
		return ret;
	}
}
