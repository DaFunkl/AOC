package de.monx.aoc.year22;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D04 extends Day {
//	2-4,6-8
	List<int[]> in = getInputList().stream() //
			.map(x -> x.replace(",", "-").split("-")) //
			.map(x -> new int[] { //
					Integer.valueOf(x[0]), Integer.valueOf(x[1]), Integer.valueOf(x[2]), Integer.valueOf(x[3]) //
			}).toList();

	@Override
	public Object part1() {
		return in.stream().map(x -> (x[0] <= x[2] && x[3] <= x[1]) || (x[2] <= x[0] && x[1] <= x[3]) ? 1 : 0).reduce(0,
				(a, b) -> a + b);
	}

	@Override
	public Object part2() {
		return in.stream().map(x -> !(x[1] < x[2] || x[3] < x[0]) ? 1 : 0).reduce(0, (a, b) -> a + b);
	}
}
