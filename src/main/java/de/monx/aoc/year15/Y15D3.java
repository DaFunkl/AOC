package de.monx.aoc.year15;

import java.util.HashSet;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Directions;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y15D3 extends Day {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	Directions<Character, IntPair> dirs = new Directions<>( //
			new Pair('^', new IntPair(-1, 0)), //
			new Pair('v', new IntPair(1, 0)), //
			new Pair('>', new IntPair(0, 1)), //
			new Pair('<', new IntPair(0, -1)) //
	);

	@Override
	public Object part1() {
		Set<IntPair> grid = new HashSet<>();
		IntPair pos = new IntPair(0, 0);
		grid.add(pos);
		for (char c : getInputString().toCharArray()) {
			pos = pos.add(dirs.getDir(c));
			grid.add(pos);
		}
		return grid.size();
	}

	@Override
	public Object part2() {
		Set<IntPair> grid = new HashSet<>();
		IntPair p1 = new IntPair(0, 0);
		IntPair p2 = new IntPair(0, 0);
		boolean turn = false;
		grid.add(p1);
		for (char c : getInputString().toCharArray()) {
			if (turn = !turn) {
				p1 = p1.add(dirs.getDir(c));
				grid.add(p1);
			} else {
				p2 = p2.add(dirs.getDir(c));
				grid.add(p2);
			}
		}
		return grid.size();
	}

}
