package de.monx.aoc.year21;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D07 extends Day {
	List<Integer> in = Arrays.stream(getInputString().split(",")).map(Integer::valueOf).sorted().toList();

	@Override
	public Object part1() {
		int m = in.get(in.size() / 2);
		return in.stream().reduce(0, (a, b) -> a + Math.abs(b - m));
	}

	@Override
	public Object part2() {
		int m = in.stream().reduce(Integer::sum).get() / in.size();
		return in.stream().reduce(0, (a, b) -> a + dreieckszahl(Math.abs(b - m)));
	}

	int dreieckszahl(int n) {
		return (n * (n + 1)) / 2;
	}
}
