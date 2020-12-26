package de.monx.aoc.year15;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;

public class Y15D2 extends Day {
	List<int[]> data = getInputList().stream().map(s -> s.split("x"))
			.map(s -> new int[] { Integer.valueOf(s[0]), Integer.valueOf(s[1]), Integer.valueOf(s[2]) })
			.collect(Collectors.toList());
	// L = 0, W = 1, H = 2

	@Override
	public Object part1() {
		return data.stream() //
				.map(e -> 2 * e[0] * e[1] + 2 * e[1] * e[2] + 2 * e[2] * e[0] + ( // @formatter:off
				e[0] <= e[2] && e[1] <= e[2] ? e[0] * e[1] : //
				e[1] <= e[0] && e[2] <= e[0] ? e[1] * e[2] : //
				e[2] <= e[1] && e[0] <= e[1] ? e[2] * e[0] : 0)) // @formatter:on
				.reduce(0, (a, b) -> a + b);
	}

	@Override
	public Object part2() {
		return data.stream() //
				.map(e -> e[0] * e[1] * e[2] + ( // @formatter:off
				e[0] <= e[2] && e[1] <= e[2] ? 2 * e[0] + 2 * e[1] : //
				e[1] <= e[0] && e[2] <= e[0] ? 2 * e[1] + 2 * e[2] : //
				e[2] <= e[1] && e[0] <= e[1] ? 2 * e[2] + 2 * e[0] : 0)) // @formatter:on
				.reduce(0, (a, b) -> a + b);
	}

}
