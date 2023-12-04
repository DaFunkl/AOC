package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D04 extends Day {

	List<Integer> wins = new ArrayList<>();

	@Override
	public Object part1() {
		long[] score = { 0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 };
		long points = 0;
		for (var line : getInputList()) {
			boolean nextVals = false;
			var spl = line.split(": ")[1].trim().replace("  ", " ").split(" ");
			boolean[] winNums = new boolean[100];
			int matches = 0;
			for (var p : spl) {
				if (p.equals("|")) {
					nextVals = true;
					continue;
				}
				if (nextVals) {
					matches += winNums[Integer.valueOf(p)] ? 1 : 0;
				} else {
					winNums[Integer.valueOf(p)] = true;
				}
			}
			points += score[matches];
			wins.add(matches);
		}
		return points;
	}

	@Override
	public Object part2() {
		long sum = 0;
		int[] mults = new int[wins.size()];
		for (int i = 0; i < mults.length; i++) {
			mults[i] += 1;
			sum += mults[i];
			for (int j = 0; j < wins.get(i); j++) {
				mults[i + j + 1] += mults[i];
			}
		}
		return sum;
	}
}
