package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y21D08 extends Day {

	@Override
	public Object part1() {
		return getInputList().stream().map(x -> solveP1(x)).reduce(0, (a, b) -> a + b);
	}

	static int solveP1(String l) {
		int ret = 0;
		for (var s : l.split("\\| ")[1].split(" ")) {
			int b = s.length();
			if (b == 7 || (1 < b && b < 5)) {
				ret++;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return getInputList().stream().map(x -> solveP2(x)).reduce(0, (a, b) -> a + b);
	}

	int solveP2(String s) {
		String[] spl = s.split(" \\| ");
		var map = fetchMapping(spl[0]);
		int ret = 0;
		for (var str : spl[1].split(" ")) {
			ret = ret * 10 + map.get(Util.sortString(str));
		}
		System.out.println(ret);
		return ret;
	}

	Map<String, Integer> fetchMapping(String line) {
		Map<String, Integer> mapping = new HashMap<>();
		String[] dm = new String[10];
		List<String> todosL5 = new ArrayList<>();
		List<String> todosL6 = new ArrayList<>();

		for (var str : line.split(" ")) { // 7, 8, 1, 4
			String s = Util.sortString(str);
			switch (s.length()) { // @formatter:off
				case 2: mapping.put(s, 1); dm[1] = s; break;
				case 3: mapping.put(s, 7); dm[7] = s; break;
				case 4: mapping.put(s, 4); dm[4] = s; break;
				case 7: mapping.put(s, 8); dm[8] = s; break;
				
				case 5: todosL5.add(s); break;	// 2, 3, 5
				case 6: todosL6.add(s); break;	// 0, 6, 9
				default: System.err.println("Unknown digit: " + s);
			}
		}

		for (var s : todosL5) { // 2, 3, 5
			if (matches(s, dm[4]) == 2) {
				mapping.put(s, 2); dm[2] = s;
			} else  if (matches(s, dm[7]) == 3) {
				mapping.put(s, 3); dm[3] = s;
			} else {
				mapping.put(s, 5); dm[5] = s;
			}
		}

		for (var s : todosL6) { // 0, 6, 9
			if (matches(s, dm[5]) == 5 && matches(s, dm[1]) == 1) {
				mapping.put(s, 6); dm[6] = s;
			} else  if (matches(s, dm[4]) == 4 && matches(s, dm[1]) == 2) {
				mapping.put(s, 9); dm[9] = s;
			} else {
				mapping.put(s, 0); dm[0] = s;
			}// @formatter:on
		}
		return mapping;
	}

	int matches(String s1, String s2) {
		char[] cArr1 = s1.toCharArray(), cArr2 = s2.toCharArray();
		int ret = 0;
		for (var c1 : cArr1) {
			for (var c2 : cArr2) {
				if (c1 == c2) {
					ret++;
					break;
				}
			}
		}
		return ret;
	}
}
