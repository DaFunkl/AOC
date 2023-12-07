package de.monx.aoc.year23;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y23D07 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		vals.put('J', 0);
		return solve(true);
	}

	int solve(boolean p2) {
		var parsed = getInputList().stream().map(x -> x.split(" ")) //
				.map(x -> new Pair<String, int[]>(x[0], new int[] { Integer.valueOf(x[1]), points(x[0], p2) })) //
				.sorted(comp).toList();
		int ret = 0;
		for (int i = 0; i < parsed.size(); i++) {
			ret += (i + 1) * parsed.get(i).second[0];
		}
		return ret;
	}

	int points(String s, boolean p2) {
		Map<Character, Integer> map = new HashMap<>();
		int max = 0;
		char cm = '.';
		for (var c : s.toCharArray()) {
			map.computeIfAbsent(c, k -> 0);
			int num = map.get(c) + 1;
			map.put(c, num);
			if (num > max && c != 'J') {
				max = num;
				cm = c;
			}
		}
		if (p2 && map.containsKey('J') && map.size() > 1) {
			map.put(cm, map.get(cm) + map.remove('J'));
		}
		int ret = 5 - map.size();
		if (map.size() == 3 && map.values().stream().anyMatch(x -> x == 3)) {
			ret += 1;
		}
		if (map.size() == 2 && map.values().stream().anyMatch(x -> x == 3)) {
			ret++;
		}
		if (map.size() == 1 || (map.size() == 2 && map.values().stream().anyMatch(x -> x == 4))) {
			ret += 2;
		}
		return ret;
	}

	static Map<Character, Integer> vals;
	static {
		Map<Character, Integer> o = new HashMap<>();
		o.put('2', 1);
		o.put('3', 2);
		o.put('4', 3);
		o.put('5', 4);
		o.put('6', 5);
		o.put('7', 6);
		o.put('8', 7);
		o.put('9', 8);
		o.put('T', 9);
		o.put('J', 10);
		o.put('Q', 11);
		o.put('K', 12);
		o.put('A', 13);
		vals = o;
	}

	int compCards(String o1, String o2) {
		for (int i = 0; i < o1.length(); i++) {
			if (o1.charAt(i) != o2.charAt(i)) {
				return vals.get(o1.charAt(i)) - vals.get(o2.charAt(i));
			}
		}
		return 0;
	}

	Comparator<Pair<String, int[]>> comp = new Comparator<>() {
		@Override
		public int compare(Pair<String, int[]> o1, Pair<String, int[]> o2) {
			if (o2.second[1] != o1.second[1]) {
				return o1.second[1] - o2.second[1];
			}
			return compCards(o1.first, o2.first);
		}

	};
}
