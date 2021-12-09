package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y21D08 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		return in.stream().map(x -> solve1(x)).reduce(0, (a, b) -> a + b);
	}

	@Override
	public Object part2() {
		return in.stream().map(x -> solve2(x)).reduce(0, (a, b) -> a + b);
	}

	static int solve1(String l) {
		int ret = 0;
		for (var s : l.split("\\| ")[1].split(" ")) {
			int b = s.length();
			if (b == 7 || (1 < b && b < 5)) {
				ret++;
			}
		}
		return ret;
	}

	int solve2(String s) {
		String[] spl = s.split(" \\| ");
		var map = fetchMapping(spl[0]);
		int ret = 0;
		for (var str : spl[1].split(" ")) {
			ret = ret * 10 + map.get(Util.sortString(str));
		}
		return ret;
	}

	Map<String, Integer> fetchMapping(String line) {
		List<String> l5 = new ArrayList<>(), l6 = new ArrayList<>();
		Map<String, Integer> mapping = new HashMap<>();
		String s1 = "", s4 = "";

		for (var str : line.split(" ")) { // 7, 8, 1, 4
			String s = Util.sortString(str);
			switch (s.length()) { // @formatter:off 	´°`_´°` formatting to reduce rows? 
				case 2: mapping.put(s, 1); s1 = s; 	break;
				case 3: mapping.put(s, 7); 			break;
				case 4: mapping.put(s, 4); s4 = s; 	break;
				case 7: mapping.put(s, 8);  		break;
				
				case 5: l5.add(s); break;	// 2, 3, 5
				case 6: l6.add(s); break;	// 0, 6, 9
				default: System.err.println("Unknown digit: " + s);
			}
		}

		for (var s : l5) { // 2, 3, 5
			if 		(matches(s, s1) == 2) 	mapping.put(s, 3);
			else if (matches(s, s4) == 2)	mapping.put(s, 2);
			else  							mapping.put(s, 5);
		}

		for (var s : l6) { // 0, 6, 9
			if 		(matches(s, s4) == 4)	mapping.put(s, 9);
			else if (matches(s, s1) == 1)	mapping.put(s, 6); 
			else 							mapping.put(s, 0); 
		} // @formatter:on
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
