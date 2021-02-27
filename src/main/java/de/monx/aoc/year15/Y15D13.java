package de.monx.aoc.year15;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.PermutationGenerator;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y15D13 extends Day {
	static final int AMT = 8;
	Map<String, Integer> map = new HashMap<>();
	int[][] vals = new int[AMT][AMT];

	@Override
	public Object part1() {
		parse();
		return solve(AMT);
	}

	@Override
	public Object part2() {
		return solve(AMT + 1);
	}

	int solve(int amt) {
		int max = Integer.MIN_VALUE;
		var pg = new PermutationGenerator(amt, 0);
		while (pg.hasMore()) {
			int[] next = pg.getNext();
			var score = fetchScore(next);
			if (score > max) {
				max = score;
			}
		}
		return max;
	}

	int fetchScore(int[] order) {
		int ret = fetchSmiles(order[order.length - 1], order[0]);
		for (int i = 0; i < order.length - 1; i++) {
			ret += fetchSmiles(order[i], order[i + 1]);
		}
		return ret;
	}

	int fetchSmiles(int i, int j) {
		if (i >= AMT || j >= AMT) {
			return 0;
		}
		return vals[i][j] + vals[j][i];
	}

	void parse() {
		int id = 0;
		for (String s : getInputList()) {
			String[] spl = s.split(" ");
			String a = spl[0];
			String b = spl[10].substring(0, spl[10].length() - 1);
			int val = Integer.valueOf(spl[3]);
			if (spl[2].equals("lose")) {
				val *= -1;
			}
			var ids = fetchIds(a, b, id);
			id = ids.first;
			vals[ids.second.first][ids.second.second] = val;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	Pair<Integer, IntPair> fetchIds(String a, String b, int id) {
		if (!map.containsKey(a)) {
			map.put(a, id++);
		}
		if (!map.containsKey(b)) {
			map.put(b, id++);
		}
		return new Pair(id, new IntPair(map.get(a), map.get(b)));
	}
}
