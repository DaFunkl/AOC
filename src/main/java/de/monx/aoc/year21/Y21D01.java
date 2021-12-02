package de.monx.aoc.year21;

import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;

public class Y21D01 extends Day {
	int sol1 = 0;
	int sol2 = 0;

	@Override
	public Object part1() {
		solve();
		return sol1;
	}

	@Override
	public Object part2() {
		return sol2;
	}

	void solve() {
		List<Integer> in = getInputList().stream().map(x -> Integer.valueOf(x)).collect(Collectors.toList());
		for (int i = 0; i < in.size() - 1; i++) {
			if (in.get(i) < in.get(i + 1)) {
				sol1++;
			}
			if (i < in.size() - 3 && in.get(i) < in.get(i + 3)) {
				sol2++;
			}
		}
	}
}
