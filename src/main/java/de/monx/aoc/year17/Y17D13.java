package de.monx.aoc.year17;

import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y17D13 extends Day {
	List<IntPair> in = getInputList().stream() //
			.map(x -> x.split(": ")) //
			.map(x -> new IntPair(Integer.valueOf(x[0]), Integer.valueOf(x[1]))) //
			.toList();

	@Override
	public Object part1() {
		return severity(0).first;
	}

	@Override
	public Object part2() {
		int delay = 1;
		var sev = severity(delay);
		while (sev.second) {
			delay++;
			sev = severity(delay);
		}
		return delay;
	}

	Pair<Integer, Boolean> severity(int delay) {
		int ret = 0;
		boolean hit = false;
		for (var ip : in) {
			if ((delay + ip.first) % (ip.second * 2 - 2) == 0) {
				ret += ip.first * ip.second;
				hit = true;
			}
		}
		return new Pair<Integer, Boolean>(ret, hit);
	}

}
