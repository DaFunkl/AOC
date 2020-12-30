package de.monx.aoc.year15;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.PermutationGenerator;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y15D9 extends Day {

	Map<String, Integer> map = new HashMap<>();
	static final int AMT = 8;
	int[][] distance = new int[AMT][AMT];

	@Override
	public Object part1() {
		parse();
		PermutationGenerator pg = new PermutationGenerator(8, 0);
		int ret = Integer.MAX_VALUE;
		while (pg.hasMore()) {
			int[] path = pg.getNext();
			int length = routeLength(path);
			if (length < ret) {
				ret = length;
			}
		}
		return ret;
	}

	int routeLength(int[] path) {
		int count = 0;
		int p = path[0];
		for (int i = 1; i < path.length; i++) {
			count += distance[p][path[i]];
			p = path[i];
		}
		return count;
	}

	@Override
	public Object part2() {
		PermutationGenerator pg = new PermutationGenerator(8, 0);
		int ret = Integer.MIN_VALUE;
		while (pg.hasMore()) {
			int[] path = pg.getNext();
			int length = routeLength(path);
			if (length > ret) {
				ret = length;
			}
		}
		return ret;
	}

	void parse() {
		int id = -1;
		for (var s : getInputList()) {
			String[] spl = s.split(" = ");
			int dis = Integer.valueOf(spl[1]);
			spl = spl[0].split(" to ");
			IntPair ids = fetchIds(spl, id);
			id = Math.max(id, Math.max(ids.first, ids.second));
			distance[ids.first][ids.second] = dis;
			distance[ids.second][ids.first] = dis;
		}
	}

	IntPair fetchIds(String[] spl, int id) {
		int id1 = 0;
		int id2 = 0;
		if (!map.containsKey(spl[0])) {
			map.put(spl[0], ++id);
		}
		if (!map.containsKey(spl[1])) {
			map.put(spl[1], ++id);
		}
		id1 = map.get(spl[0]);
		id2 = map.get(spl[1]);
		return new IntPair(id1, id2);
	}
}
