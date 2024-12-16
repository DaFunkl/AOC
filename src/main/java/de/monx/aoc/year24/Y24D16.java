package de.monx.aoc.year24;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D16 extends Day {
	List<String> in = getInputList();
	int[] start = { in.size() - 2, 1 };
	int[] end = { 1, in.get(0).length() - 2 };

	int[][] _Dirs = { //
			{ -1, 0 }, // u
			{ 0, 1 }, // r
			{ 1, 0 }, // d
			{ 0, -1 } // l
	};

	Set<IntPair> paths = new HashSet<>();

	@Override
	public Object part1() {
		Map<String, Integer> scores = new HashMap<>();
		int best = Integer.MAX_VALUE;

		ArrayDeque<Pair<int[], Set<IntPair>>> stack = new ArrayDeque<>();

		stack.push(new Pair<>(new int[] { start[0], start[1], 1, 0 },
				addNewSet(new HashSet<IntPair>(), new IntPair(start[0], start[1]))));
		while (!stack.isEmpty()) {
			var pols = stack.pollLast();
			var cur = pols.first;
			var ips = pols.second;

			String key = cur[0] + "," + cur[1] + "," + cur[2];
			if (cur[3] <= scores.getOrDefault(key, Integer.MAX_VALUE)) {
				scores.put(key, cur[3]);
				if (cur[0] == end[0] && cur[1] == end[1]) {
					if (cur[3] < best) {
						best = cur[3];
						paths = new HashSet<>();
						ips.forEach(x -> paths.add(x));
					} else if (cur[3] == best) {
						ips.forEach(x -> paths.add(x));
					}
					continue;
				}
				int[][] next = { //
						{ cur[0] + _Dirs[cur[2]][0], cur[1] + _Dirs[cur[2]][1], cur[2], cur[3] + 1 }, //
						{ cur[0], cur[1], (cur[2] + 3) % 4, cur[3] + 1000 }, //
						{ cur[0], cur[1], (cur[2] + 1) % 4, cur[3] + 1000 }, //
				};
				if (in.get(next[0][0]).charAt(next[0][1]) != '#') {
					stack.push(new Pair<>(next[0], addNewSet(ips, new IntPair(next[0][0], next[0][1]))));
				}
				for (int i = 1; i < 3; i++) {
					if (in.get(next[i][0] + _Dirs[next[i][2]][0]).charAt(next[i][1] + _Dirs[next[i][2]][1]) != '#') {
						stack.push(new Pair<>(next[i], addNewSet(ips, new IntPair(next[i][0], next[i][1]))));
					}
				}
			} else {
				continue;
			}
		}
		return best;
	}

	Set<IntPair> addNewSet(Set<IntPair> ips, IntPair add) {
		Set<IntPair> ret = new HashSet<>();
		ret.add(add);
		ips.forEach(x -> ret.add(x));
		return ret;
	}

	@Override
	public Object part2() {
		return paths.size();
	}
}
