package de.monx.aoc.year22;

import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y22D02 extends Day {

	List<IntPair> in = getInputList().stream() //
			.map(x -> x.split(" ")) //
			.map(x -> new IntPair( //
					(int) x[0].charAt(0) - (int) 'A', //
					(int) x[1].charAt(0) - (int) 'X'//
			)) //
			.toList();

	@Override
	public Object part1() {
		int ret = 0;
		for (var i : in) {
			ret += i.second + 1;
			if (i.first == i.second) {
				ret += 3;
			} else if (i.second - i.first == 1 || (i.second == 0 && i.first == 2)) {
				ret += 6;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return in.stream() //
				.map(i -> i.second * 3 + 1 + (i.first + ((i.second + 2) % 3)) % 3) //
				.reduce(0, (a, b) -> a + b);
	}

}
