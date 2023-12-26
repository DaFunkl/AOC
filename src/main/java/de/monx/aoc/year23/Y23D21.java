package de.monx.aoc.year23;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.spec.GCMParameterSpec;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y23D21 extends Day {

	List<char[]> in = getInputList().stream().map(String::toCharArray).toList();
	IntPair[] _DIRS = { //
			new IntPair(-1, 0), //
			new IntPair(0, -1), //
			new IntPair(1, 0), //
			new IntPair(0, 1)//
	};
	IntPair start = new IntPair(in.size() / 2, in.size() / 2);

	@Override
	public Object part1() {
		in.get(start.first)[start.second] = '.';
		return solve(start, false, 64, 1);
	}

	@Override
	public Object part2() {
		long evenPoints = solve(start, false, in.size() * 3, 1);
		long oddPoints = solve(start, false, in.size() * 3, 0);
		System.out.println(evenPoints);
		System.out.println(oddPoints);
		return 0;
	}

	long p2() {
		int steps = 26501365;
		long base = steps % steps;
		List<Long> refs = new ArrayList<>();

		List<IntPair> q = new ArrayList<>();
		q.add(start);
		int step = 0;
		Map<IntPair, Integer> seen = new HashMap<>();
		long[] dp = { 1, 0 };

		while (step <= base + 3 * in.size()) {
			List<IntPair> next = new ArrayList<>();
			for (var p : q) {
				for (var d : _DIRS) {
					var nip = d.add(p);
					if (seen.containsKey(nip) || getChar(nip, true) == '#') {
						continue;
					}
					seen.put(nip, step);
					next.add(nip);
				}
			}
			step++;
			dp[step%2] += q.size();
			long ps = step - 1;
			if(ps == base || ()) {
				
			}
		}

		long ret = 0;
		return ret;
	}

	long solve(IntPair start, boolean p2, int steps, int odd) {
		Map<IntPair, Integer> seen = new HashMap<>();
		Set<IntPair> plots = new HashSet<>();
		ArrayDeque<Pair<IntPair, Integer>> stack = new ArrayDeque<>();
		stack.push(new Pair<>(start, 0));
		while (!stack.isEmpty()) {
			var cur = stack.pollLast();
			if (seen.containsKey(cur.first) && seen.get(cur.first) <= cur.second) {
				continue;
			}
			seen.put(cur.first, cur.second);
			for (var d : _DIRS) {
				var ip = d.add(cur.first);
				var c = getChar(ip, p2);
				if (c == '.') {
					if (cur.second % 2 == odd) {
						plots.add(ip);
					}
					if (cur.second < (steps - 1)) {
						stack.push(new Pair<>(ip, cur.second + 1));
					}
				}
			}
		}
		return plots.size();
	}

	char getChar(IntPair p, boolean p2) {
		var ip = p.clone();
		if (ip.first < 0 || ip.second < 0 || ip.first >= in.size() || ip.second >= in.get(0).length) {
			if (!p2) {
				return 'X';
			}
			while (ip.first < 0) {
				ip.first += in.size();
			}
			while (ip.second < 0) {
				ip.second += in.size();
			}
			ip.first %= in.size();
			ip.second %= in.size();
		}
		return in.get(ip.first)[ip.second];
	}

}
