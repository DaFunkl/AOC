package de.monx.aoc.year23;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y23D02 extends Day {

	int p2 = 0;

	@Override
	public Object part1() {
		int sum = 0;
		for (var str : getInputList()) {
			int id = Integer.valueOf(str.split(":")[0].split(" ")[1]);
			Map<String, Integer> map = new HashMap<>();
			map.put("red", 0);
			map.put("green", 0);
			map.put("blue", 0);
			boolean add = true;
			for (var spl : str.split(": ")[1].split("; ")) {
				for (var s : spl.split(", ")) {
					var p = s.split(" ");
					int val = Integer.valueOf(p[0]);
					if (val > map.get(p[1])) {
						map.put(p[1], val);
					}
					int comp = switch (p[1]) {
					case "red" -> 12;
					case "green" -> 13;
					case "blue" -> 14;
					default -> 100;
					};
					if (val > comp) {
						add = false;
					}
				}
			}
			if (add) {
				sum += id;
			}
			p2 += map.get("red") * map.get("green") * map.get("blue");
		}
		return sum;
	}

	@Override
	public Object part2() {
		return p2;
	}

}
