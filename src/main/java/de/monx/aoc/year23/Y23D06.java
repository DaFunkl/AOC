package de.monx.aoc.year23;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D06 extends Day {

	List<String> l = getInputList();

	@Override
	public Object part1() {
		var s1 = l.get(0).replaceAll(" +", " ").split(" ");
		var s2 = l.get(1).replaceAll(" +", " ").split(" ");
		int[][] in = new int[s1.length - 1][2];
		for (int i = 0; i < in.length; i++) {
			in[i][0] = Integer.valueOf(s1[i + 1]);
			in[i][1] = Integer.valueOf(s2[i + 1]);
		}
		int ret = 1;
		for (int i = 0; i < in.length; i++) {
			int opts = 0;
			for (int j = 1; j < in[i][0]; j++) {
				if (in[i][1] < j * (in[i][0] - j)) {
					opts++;
				}
			}
			ret *= opts;
		}
		return ret;
	}

	@Override
	public Object part2() {
		var v1 = Long.valueOf(l.get(0).replaceAll(" +", "").split(":")[1]);
		var v2 = Long.valueOf(l.get(1).replaceAll(" +", "").split(":")[1]);
		int ret = 0;
		for (int j = 1; j < v1; j++) {
			if (v2 < j * (v1 - j)) {
				ret++;
			}
		}
		return ret;
	}

}
