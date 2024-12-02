package de.monx.aoc.year24;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D02 extends Day {

	List<int[]> in = getInputList().stream().map(x -> x.split(" "))
			.map(x -> Arrays.stream(x).mapToInt(Integer::valueOf).toArray()).toList();

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	int solve(boolean p2) {
		int ret = 0;
		for (var x : in) {
			if (isSafe(x)) {
				ret++;
			} else if (p2) {
				for (int i = 0; i < x.length; i++) {
					int[] ta = new int[x.length - 1];
					int p = 0;
					for (int j = 0; j < x.length; j++) {
						if (j == i) {
							continue;
						}
						ta[p++] = x[j];
					}
					if (isSafe(ta)) {
						ret++;
						break;
					}
				}
			}
		}
		return ret;
	}

	boolean isSafe(int[] x) {
		Boolean inc = x[0] < x[1];
		int del = Math.abs(x[0] - x[1]);
		if (del < 1 || del > 3) {
			return false;
		}
		for (int i = 1; i < x.length; i++) {
			del = Math.abs(x[i - 1] - x[i]);
			if (x[i - 1] < x[i] != inc || del < 1 || del > 3) {
				return false;
			}
		}
		return true;
	}
}
