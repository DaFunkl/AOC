package de.monx.aoc.year23;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Y23D16 extends Day {

	List<String> in = getInputList();
	IntPair[] _DIRS = { new IntPair(-1, 0), new IntPair(0, -1), new IntPair(1, 0), new IntPair(0, 1), };
	int[] swd1 = { 1, 0, 3, 2 };
	int[] swd2 = { 3, 2, 1, 0 };
	int[][] spl = { { 1, 3 }, { 0, 2 }, { 1, 3 }, { 0, 2 } };

	@Override
	public Object part1() {
		return solve(new Pair(new IntPair(0, -1), 3));
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 0; i < in.size(); i++) {
			ret = Math.max(ret, solve(new Pair(new IntPair(i, -1), 3)));
			ret = Math.max(ret, solve(new Pair(new IntPair(i, in.get(0).length()), 1)));
		}
		for (int j = 0; j < in.get(0).length(); j++) {
			ret = Math.max(ret, solve(new Pair(new IntPair(-1, j), 0)));
			ret = Math.max(ret, solve(new Pair(new IntPair(in.size(), j), 2)));
		}

		return ret;
	}

	int solve(Pair<IntPair, Integer> start) {
		ArrayDeque<Pair<IntPair, Integer>> s = new ArrayDeque<>();
		Map<IntPair, boolean[]> bw = new HashMap<>();
		s.add(start);
		while (!s.isEmpty()) {
			var st = s.pop();
			var ip = st.first;
			var d = st.second;
			if (bw.containsKey(ip)) {
				if (bw.get(ip)[d]) {
					continue;
				}
			} else {
				bw.put(ip, new boolean[4]);
			}
			bw.get(ip)[d] = true;
			ip = ip.add(_DIRS[d]);
			char c = getChar(ip);
			if (c == 'X') {
				continue;
			}
			// pass through
			if (c == '.' || (c == '-' && _DIRS[d].first == 0) || (c == '|' && _DIRS[d].second == 0)) {
				s.push(new Pair(ip, d));
				// Mirror 1
			} else if (c == '\\') {
				d = swd1[d];
				s.push(new Pair(ip, d));
				// Mirror 2
			} else if (c == '/') {
				d = swd2[d];
				s.push(new Pair(ip, d));
				// Mirror split
			} else if (c == '-' || c == '|') {
				if (!bw.containsKey(ip)) {
					bw.put(ip, new boolean[4]);
				}
				bw.get(ip)[d] = true;
				var d1 = spl[d][0];
				var ip1 = ip.clone();
				if (getChar(ip1) != 'X' && (!bw.containsKey(ip1) || !bw.get(ip1)[d1])) {
					s.push(new Pair(ip1, d1));
				}
				var d2 = spl[d][1];
				var ip2 = ip.clone();
				if (getChar(ip2) != 'X' && (!bw.containsKey(ip2) || !bw.get(ip2)[d2])) {
					s.push(new Pair(ip2, d2));
				}
			} else {
				System.err.println("???");
			}
		}
		return bw.keySet().size() - 1;
	}

	char getChar(IntPair ip) {
		if (ip.first < 0 || ip.second < 0 || ip.first >= in.size() || ip.second >= in.get(0).length()) {
			return 'X';
		}
		return in.get(ip.first).charAt(ip.second);
	}
}
