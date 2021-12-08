package de.monx.aoc.year21;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D07 extends Day {
	List<Integer> in = Arrays.stream(getInputString().split(",")).map(Integer::valueOf).sorted().toList();

	@Override
	public Object part1() {
		final int m = in.get(in.size() / 2);
		return in.stream().reduce(0, (a, b) -> a + Math.abs(b - m));
	}

	@Override
	public Object part2() {
		final int m = in.stream().reduce(Integer::sum).get() / in.size();
		return in.stream().reduce(0, (a, b) -> a + triangleNumber(Math.abs(b - m)));
	}

	int triangleNumber(int n) {
		return (n * (n + 1)) / 2;
	}
}
