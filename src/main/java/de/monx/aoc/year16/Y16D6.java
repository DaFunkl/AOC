package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y16D6 extends Day {
	List<String> input = getInputList();

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	public String solve(boolean p2) {
		List<Map<Character, Integer>> counter = new ArrayList<>();
		for (var s : input) {
			var car = s.toCharArray();
			for (int i = 0; i < car.length; i++) {
				var c = car[i];
				while (counter.size() <= i) {
					counter.add(new HashMap<>());
				}
				if (!counter.get(i).containsKey(c)) {
					counter.get(i).put(c, 1);
				} else {
					counter.get(i).put(c, counter.get(i).get(c) + 1);
				}
			}
		}
		String ret = "";
		for (var c : counter) {
			int m = -1;
			if (p2) {
				m = Integer.MAX_VALUE;
			}
			char a = '@';
			for (var key : c.keySet()) {
				if (!p2 && c.get(key) > m) {
					m = c.get(key);
					a = key;
				} else if (p2 && c.get(key) < m) {
					m = c.get(key);
					a = key;
				}
			}
			ret += a;
		}
		return ret;
	}
}
