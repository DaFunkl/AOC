package de.monx.aoc.year22;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D02 extends Day {

	List<int[]> in = getInputList().stream() //
			.map(x -> new int[] { (int) x.charAt(0) - (int) 'A', (int) x.charAt(2) - (int) 'X' }) //
			.toList();

	@Override
	public Object part1() {
		int ret = 0;
		for (var i : in) {
			ret += i[1] + 1;
			if (i[0] == i[1]) {
				ret += 3;
			} else if (i[1] - i[0] == 1 || (i[1] == 0 && i[0] == 2)) {
				ret += 6;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return in.stream() //
				.map(i -> i[1] * 3 + 1 + (i[0] + ((i[1] + 2) % 3)) % 3) //
				.reduce(0, (a, b) -> a + b);
	}

}
