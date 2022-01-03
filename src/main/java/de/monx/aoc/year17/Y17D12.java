package de.monx.aoc.year17;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y17D12 extends Day {

	Map<Integer, Set<Integer>> nodes = init();

	@Override
	public Object part1() {
		return fetchGroup(0).size();
	}

	@Override
	public Object part2() {
		int ret = 0;
		Set<Integer> seen = new HashSet<>();
		for (var gid : nodes.keySet()) {
			if (seen.contains(gid)) {
				continue;
			}
			seen.addAll(fetchGroup(gid));
			ret++;
		}
		return ret;
	}

	Set<Integer> fetchGroup(int gid) {
		Set<Integer> seen = new HashSet<>();
		Deque<Integer> stack = new ArrayDeque<>();
		stack.push(gid);
		while (!stack.isEmpty()) {
			int cur = stack.pop();
			if (seen.contains(cur)) {
				continue;
			}
			seen.add(cur);
			for (var x : nodes.get(cur)) {
				if (!seen.contains(x)) {
					stack.push(x);
				}
			}
		}
		return seen;
	}

	Map<Integer, Set<Integer>> init() {
		Map<Integer, Set<Integer>> ret = new HashMap<>();
		for (String s : getInputList()) {
			var sar = s.split(" <-> ");
			int i = Integer.valueOf(sar[0]);
			ret.put(i, new HashSet<>());
			for (var l : sar[1].split(", ")) {
				ret.get(i).add(Integer.valueOf(l));
			}
		}
		return ret;
	}
}
