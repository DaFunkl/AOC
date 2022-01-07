package de.monx.aoc.year17;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;
import lombok.Data;

public class Y17D24 extends Day {
	List<IntPair> in = getInputList().stream().map(x -> x.split("/")) //
			.map(x -> new IntPair(Integer.valueOf(x[0]), Integer.valueOf(x[1]))) //
			.toList();
	Map<Integer, List<Integer>> connections = new HashMap<>();

	int r1 = 0;
	int[] r2 = { 0, 0 };

	@Override
	public Object part1() {
		solve();
		return r1;
	}

	void solve() {
		initConnections();
		Deque<Bridge> stack = new ArrayDeque<>();
		stack.push(new Bridge(0));
		Set<String> seen = new HashSet<>();
		while (!stack.isEmpty()) {
			var b = stack.pop();
			if (seen.contains(b.hash())) {
				continue;
			}
			seen.add(b.hash());
			r1 = Math.max(r1, b.strength);
			if (b.pathsTaken.size() > r2[0]) {
				r2[0] = b.pathsTaken.size();
				r2[1] = b.strength;
			} else if (r2[0] == b.pathsTaken.size()) {
				r2[1] = Math.max(r2[1], b.strength);
			}
			for (var n : connections.get(b.current)) {
				var bn = b.next(n);
				if (bn == null || seen.contains(bn.hash())) {
					continue;
				}
				stack.push(bn);
			}
		}
	}

	@Override
	public Object part2() {
		return r2[1];
	}

	void initConnections() {
		for (var ip : in) {
			connections.computeIfAbsent(ip.first, k -> new ArrayList<>()).add(ip.second);
			connections.computeIfAbsent(ip.second, k -> new ArrayList<>()).add(ip.first);
		}
	}

	@Data
	static class Bridge {
		int current = 0;
		int strength;
		Set<String> pathsTaken = new HashSet<>();

		Bridge(int c) {
			current = c;
		}

		Bridge next(int n) {
			Bridge nb = new Bridge(n);
			var ph = ph(n, current);
			if (pathsTaken.contains(ph)) {
				return null;
			}
			nb.pathsTaken.addAll(pathsTaken);
			nb.strength = strength + n + current;
			nb.pathsTaken.add(ph);
			return nb;
		}

		String ph(int a, int b) {
			return Math.min(a, b) + "/" + Math.max(a, b);
		}

		String hash() {
			var arr = pathsTaken.toArray();
			Arrays.sort(arr);
			return Arrays.toString(arr) + "|" + current;
		}
	}
}
