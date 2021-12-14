package de.monx.aoc.year21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y21D14 extends Day {

	String startString = "";
	Map<String, Character> mapChar = new HashMap<>();
	Map<String, Integer> mapIdx = new HashMap<>();
	Map<Integer, String> mapKey = new HashMap<>();

	@Override
	public Object part1() {
		init();
		return solve(10);
	}

	@Override
	public Object part2() {
		return solve(40);
	}

	long solve(int steps) {
		Map<Character, Long> count = new HashMap<>();
		long[] mapCount = new long[mapIdx.size()];
		char[] car = startString.toCharArray();
		for (int i = 1; i < car.length; i++) {
			String k = "" + car[i - 1] + car[i];
			mapCount[mapIdx.get(k)]++;
			count.putIfAbsent(car[i - 1], 0l);
			count.put(car[i - 1], count.get(car[i - 1]) + 1);
		}
		count.putIfAbsent(car[car.length - 1], 0l);
		count.put(car[car.length - 1], count.get(car[car.length - 1]) + 1);
		for (int step = 0; step < steps; step++) {
			long[] newMapCount = new long[mapIdx.size()];
			for (int i = 0; i < mapCount.length; i++) {
				if (mapCount[i] <= 0) {
					continue;
				}
				String key = mapKey.get(i);
				char nc = mapChar.get(key);

				count.putIfAbsent(nc, 0l);
				count.put(nc, count.get(nc) + mapCount[i]);

				String nk1 = "" + key.charAt(0) + nc;
				newMapCount[mapIdx.get(nk1)] += mapCount[i];
				String nk2 = "" + nc + key.charAt(1);
				newMapCount[mapIdx.get(nk2)] += mapCount[i];
			}
			mapCount = newMapCount;
		}
		return solveCount(count);
	}

	long solveCount(Map<Character, Long> counter) {
		long[] mm = { Long.MAX_VALUE, -1 };
		for (var k : counter.keySet()) {
			mm[0] = Math.min(mm[0], counter.get(k));
			mm[1] = Math.max(mm[1], counter.get(k));
		}
		return mm[1] - mm[0];
	}

	void init() {
		List<String> in = getInputList();
		startString = in.get(0);
		int count = 0;
		for (int i = 2; i < in.size(); i++) {
			var sar = in.get(i).split(" -> ");
			mapChar.put(sar[0], sar[1].charAt(0));
			mapKey.put(count, sar[0]);
			mapIdx.put(sar[0], count++);
		}
	}
}
