package de.monx.aoc.year24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D20 extends Day {
	List<String> in = getInputList();
	IntPair start = null;
	IntPair end = null;
	List<IntPair> route = new ArrayList<>();
	Map<Integer, List<Pair<IntPair, IntPair>>> saves = new HashMap<>();

	static final IntPair[] _DIRS = { //
			new IntPair(1, 0), // u
			new IntPair(0, 1), // r
			new IntPair(-1, 0), // d
			new IntPair(0, -1), // l
	};

	Map<IntPair, Integer> secs = new HashMap<>();

	@Override
	public Object part1() {
		fetchStartEnd();
		passOnce();
		return collectCheats(100, 2);
	}

	@Override
	public Object part2() {
		return collectCheats(100, 20);
	}

	int collectCheats(int filter, int dur) {
		Map<IntPair, Set<IntPair>> found = new HashMap<>();
		for (int j = 0; j < route.size(); j++) {
			var ip = route.get(j);
			ArrayDeque<Pair<IntPair, Integer>> stack = new ArrayDeque<>();
			stack.push(new Pair<>(ip, dur));
			Map<IntPair, Integer> seen = new HashMap<>();
			while (!stack.isEmpty()) {
				var curState = stack.pollLast();
				if (seen.getOrDefault(curState.first, 0) >= curState.second) {
					continue;
				}
				seen.put(curState.first, curState.second);
				for (var d : _DIRS) {
					var np = curState.first.add(d);
					if (np.first < 1 || np.second < 1 || np.first >= in.size() || np.second >= in.get(0).length()) {
						continue;
					}
					var ns = curState.second - 1;
					if (secs.containsKey(np)) {
						var sav = (secs.get(np) - secs.get(ip) - (dur - curState.second));
						if (sav >= filter) {
							found.putIfAbsent(ip, new HashSet<>());
							found.get(ip).add(np);
						}
					}
					if (ns > 0) {
						stack.push(new Pair<>(np, ns));
					}
				}
			}
		}
		return found.values().stream().mapToInt(Set::size).sum();
	}

	void passOnce() {
		var cp = start.clone();
		secs.put(cp, 0);
		route.add(cp);
		while (!cp.equals(end)) {
			for (var d : _DIRS) {
				var nd = cp.add(d);
				if (getChar(nd) == '#' || secs.containsKey(nd)) {
					continue;
				}
				secs.put(nd, secs.get(cp) + 1);
				cp = nd;
				route.add(cp);
			}
		}
	}

	char getChar(IntPair ip) {
		return in.get(ip.first).charAt(ip.second);
	}

	void fetchStartEnd() {
		for (int i = 0; i < in.size() && (start == null || end == null); i++) {
			for (int j = 0; j < in.get(0).length() && (start == null || end == null); j++) {
				if (in.get(i).charAt(j) == 'S') {
					start = new IntPair(i, j);
				}
				if (in.get(i).charAt(j) == 'E') {
					end = new IntPair(i, j);
				}
			}
		}
	}
}
