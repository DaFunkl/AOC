package de.monx.aoc.year22;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D01 extends Day {
	List<Integer> sums = null;

	@Override
	public Object part1() {
		sums = Arrays.stream(getFileString().split("\n\r\n")) //
				.map(x -> Arrays.stream(x.split("\n")) //
						.mapToInt(y -> Integer.valueOf(y.trim())).sum() //
				).sorted(Comparator.reverseOrder()).limit(3).toList();
		return sums.get(0);
	}

	@Override
	public Object part2() {
		return sums.stream().mapToInt(Integer::intValue).sum();
	}

}
