package de.monx.aoc.year24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D10 extends Day {
	List<int[]> in = getInputList().stream().map(x -> x.chars().map(y -> y - '0').toArray()).toList();
	List<IntPair> starts = new ArrayList<>();
	static final IntPair[] _DIRS = { //
			new IntPair(-1, 00), // u
			new IntPair(00, 01), // r
			new IntPair(01, 00), // d
			new IntPair(00, -1), // l
	};
	int ret2 = 0;

	@Override
	public Object part1() {
		int ret = 0;
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(0).length; j++) {
				if (in.get(i)[j] == 0) {
					var ip = new IntPair(i, j);
					starts.add(ip);
					ret += getTrails(ip, true);
					ret2 += getTrails(ip, false);
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	int getTrails(IntPair start, boolean useSeen) {
		int ret = 0;
		ArrayDeque<IntPair> stack = new ArrayDeque<IntPair>();
		Set<IntPair> seen = new HashSet<>();
		stack.push(start);
		while (!stack.isEmpty()) {
			var c = stack.pop();
			if (useSeen && seen.contains(c)) {
				continue;
			}
			seen.add(c);
			var cv = in.get(c.first)[c.second];
			if (cv == 9) {
				ret++;
				continue;
			}
			for (var d : _DIRS) {
				var np = c.add(d);
				if (np.first < 0 || np.second < 0 || np.first >= in.size() || np.second >= in.get(0).length) {
					continue;
				}
				int nv = in.get(np.first)[np.second];
				if (nv - cv == 1) {
					stack.push(np);
				}
			}
		}
		return ret;
	}
}
