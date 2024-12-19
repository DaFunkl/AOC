package de.monx.aoc.year24;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y24D19 extends Day {
	List<String> in = getInputList();
	String[] patterns = in.get(0).split(", ");
	List<String> designs = in.stream().skip(2).toList();

	@Override
	public Object part1() {
		int ret = 0;
		for (var str : designs) {
			if (isPossible(str) > 0) {
				ret++;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		long ret = 0;
		for (var str : designs) {
			ret += isPossible(str);
		}
		return ret;
	}

	Map<String, Long> memo = new HashMap<>();

	long isPossible(String design) {
		if (memo.containsKey(design)) {
			return memo.get(design);
		}

		if (design.isBlank()) {
			return 1;
		}
		long found = 0;
		for (var p : patterns) {
			if (design.startsWith(p)) {
				var nd = design.substring(p.length());
				found += isPossible(nd);
			}
		}
		memo.put(design, found);
		return found;
	}
}
