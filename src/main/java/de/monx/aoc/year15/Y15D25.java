package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y15D25 extends Day {

	// first = row, second = column
	IntPair pos = fetchInput();

	@Override
	public Object part1() {
		int i = 1, j = 1, pm = 1;
		long prev = 20151125l;
		while (!(i == pos.first && j == pos.second)) {
			prev = (prev * 252533l) % 33554393l;
			if (i == 1) {
				i = ++pm;
				j = 1;
			} else {
				i--;
				j++;
			}
		}
		return prev;
	}

	@Override
	public Object part2() {
		return "AOC FTW!";
	}

	IntPair fetchInput() {
		var spl = getInputString().replace(".", "").replace(",", "").split(" ");
		return new IntPair(Integer.valueOf(spl[16]), Integer.valueOf(spl[18]));
	}
}
