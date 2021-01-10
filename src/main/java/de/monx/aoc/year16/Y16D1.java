package de.monx.aoc.year16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y16D1 extends Day {

	List<Pair<Boolean, Integer>> data = Arrays.stream(getInputString().split(", "))//
			.map(x -> new Pair(x.charAt(0) == 'L', Integer.valueOf(x.charAt(1) + ""))) //
			.collect(Collectors.toList());

	@Override
	public Object part1() {
		IntPair dir = new IntPair(0, -1);
		IntPair pos = new IntPair(0, 0);
		System.out.println(pos + " <-> " + dir);
		for (var d : data) {
			dir = turn(dir, d.first);
			pos.addi(dir, d.second);
		}
		return pos.manhattenDistance();
	}

	@Override
	public Object part2() {
		return null;
	}

	IntPair turn(IntPair dir, boolean l) {
		if (l) {
			return new IntPair(dir.second, dir.first * -1);
		} else {
			return new IntPair(dir.second * -1, dir.first);
		}
	}
}
