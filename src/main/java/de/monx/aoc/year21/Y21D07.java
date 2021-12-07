package de.monx.aoc.year21;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D07 extends Day {
	List<Integer> in = Arrays.stream(getInputString().split(",")).map(Integer::valueOf).sorted().toList();

	@Override
	public Object part1() {
		return in.stream().reduce(0, (a, b) -> a + Math.abs(b - in.get(in.size() / 2)));
	}

	@Override
	public Object part2() {
		int minFuel = Integer.MAX_VALUE;
		for (int i = in.get(0); i <= in.get(in.size() - 1); i++) {
			int f = 0, x = i; // @formatter:off 
			f = in.stream().reduce(0, (a, b) -> a + dreieckszahl(Math.abs(b - x)));
			if (f < minFuel) minFuel = f;
			else break;
		}
		return minFuel;
	}

	int dreieckszahl(int n) { return (n * (n + 1)) / 2; }
} // @formatter:off
