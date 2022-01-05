package de.monx.aoc.year17;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y17D22 extends Day {
	IntPair start = null;
	Set<IntPair> infected = init();

	static final IntPair[] _DIRS = { //
			new IntPair(-1, 0), // up
			new IntPair(0, 1), // right
			new IntPair(1, 0), // down
			new IntPair(0, -1) // left
	};

	@Override
	public Object part1() {
		Set<IntPair> infected = new HashSet<>();
		for (var ip : this.infected) {
			infected.add(ip.clone());
		}
		IntPair pos = start.clone();
		int dir = 0;
		int ret = 0;
		for (int i = 0; i < 10000; i++) {
			boolean isInfected = infected.contains(pos);
			dir = (dir + (isInfected ? 1 : 3)) % 4;
			if (isInfected) {
				infected.remove(pos.clone());
			} else {
				ret++;
				infected.add(pos.clone());
			}
			pos.addi(_DIRS[dir]);
		}
		return ret;
	}

	static final int _CLEAN = 0;
	static final int _WEAKENED = 1;
	static final int _INFECTED = 2;
	static final int _FLAGGED = 3;

	@Override
	public Object part2() {
		Map<IntPair, Integer> infected = new HashMap<>();
		for (var ip : this.infected) {
			infected.put(ip.clone(), _INFECTED);
		}
		IntPair pos = start.clone();
		int dir = 0;
		int ret = 0;
		for (int i = 0; i < 10000000; i++) {
			int state = infected.getOrDefault(pos, 0);
			dir = (dir + state + 3) % 4;
			infected.put(pos.clone(), (state + 1) % 4);
			if (state == _WEAKENED) {
				ret++;
			}
			pos.addi(_DIRS[dir]);
		}
		return ret;
	}

	Set<IntPair> init() {
		Set<IntPair> ret = new HashSet<>();
		List<String> in = getInputList();
		start = new IntPair(in.size() / 2, in.get(0).length() / 2);
		for (int i = 0; i < in.size(); i++) {
			var car = in.get(i).toCharArray();
			for (int j = 0; j < car.length; j++) {
				if (car[j] == '#') {
					ret.add(new IntPair(i, j));
				}
			}
		}
		return ret;
	}
}
