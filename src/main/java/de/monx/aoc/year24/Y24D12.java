package de.monx.aoc.year24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D12 extends Day {

	List<String> in = getInputList();
	Map<Character, List<IntPair>> coords = new HashMap<>();

	static final IntPair[] _DIRS = { //
			new IntPair(-1, 0), // u
			new IntPair(0, 1), // r
			new IntPair(1, 0), // d
			new IntPair(0, -1), // l
	};

	int p2 = 0;

	@Override
	public Object part1() {
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(i).length(); j++) {
				coords.putIfAbsent(in.get(i).charAt(j), new ArrayList<>());
				coords.get(in.get(i).charAt(j)).add(new IntPair(i, j));
			}
		}
		int ret = 0;

		for (var ch : coords.keySet()) {
			Set<IntPair> seen = new HashSet<>();
			for (var co : coords.get(ch)) {
				if (seen.contains(co)) {
					continue;
				}
				int area = 0;
				int fence = 0;
				ArrayDeque<IntPair> stack = new ArrayDeque<>();
				stack.push(co);
				List<IntPair> fences = new ArrayList<>();
				while (!stack.isEmpty()) {
					var cur = stack.pop();
					if (seen.contains(cur)) {
						continue;
					}
					seen.add(cur);
					area++;
					for (var d : _DIRS) {
						var nd = cur.add(d);
						if (nd.first < 0 || nd.second < 0 || nd.first >= in.size() || nd.second >= in.get(0).length()) {
							fence++;
							fences.add(cur.mul(3).add(d));
							continue;
						} else if (in.get(nd.first).charAt(nd.second) != ch) {
							fence++;
							fences.add(cur.mul(3).add(d));
						} else {
							stack.push(nd);
						}
					}
				}
				ret += area * fence;
				p2 += area * fetchSides(fences);
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return p2;
	}

	int fetchSides(List<IntPair> fences) {
		int ret = 0;

		Map<Integer, List<Integer>> rows = new HashMap<>();
		Map<Integer, List<Integer>> cols = new HashMap<>();

		for (var ip : fences) {
			if (ip.first % 3 == 0) {
				rows.putIfAbsent(ip.second, new ArrayList<>());
				rows.get(ip.second).add(ip.first);
			} else {
				cols.putIfAbsent(ip.first, new ArrayList<>());
				cols.get(ip.first).add(ip.second);
			}
		}

		for (var xx : cols.keySet()) {
			var xl = cols.get(xx);
			Collections.sort(xl);
			ret++;
			for (int i = 1; i < xl.size(); i++) {
				if (xl.get(i) - xl.get(i - 1) != 3) {
					ret++;
				}
			}
		}

		for (var xx : rows.keySet()) {
			var xl = rows.get(xx);
			Collections.sort(xl);
			ret++;
			for (int i = 1; i < xl.size(); i++) {
				if (xl.get(i) - xl.get(i - 1) != 3) {
					ret++;
				}
			}
		}

		return ret;
	}
}
