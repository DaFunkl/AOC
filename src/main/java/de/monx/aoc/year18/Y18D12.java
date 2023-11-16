package de.monx.aoc.year18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y18D12 extends Day {
	List<String> in = getInputList();
	String initialState = in.get(0).split(" ")[2];
	Map<String, Character> dict = new HashMap<>();

	@Override
	public Object part1() {
		init();
		return solve(20);
	}

	@Override
	public Object part2() {
		return solve(50_000_000_000l);
	}

	long solve(long amt) {
		var tt = fillUp(new Pair<>(initialState, 0l));
		String prev = "";
		for (long i = 0; i < amt; i++) {
			var ns = tt.first.toCharArray();
			for (int j = 2; j < ns.length - 2; j++) {
				var ss = tt.first.substring(j - 2, j + 3);
				ns[j] = dict.getOrDefault(ss, '.');
			}
			tt.first = new String(ns);
			tt = fillUp(tt);
			String cur = tt.first.replace(".", " ").trim();
			if (prev.equals(cur)) {
				tt.second += (amt - i) - 1;
				break;
			}
			prev = cur;
		}
		long ret = 0;
		for (int i = 0; i < tt.first.length(); i++) {
			if (tt.first.charAt(i) == '#') {
				ret += i + tt.second;
			}
		}
		return ret;
	}

	Pair<String, Long> fillUp(Pair<String, Long> tt) {
		while (!tt.first.startsWith(".....")) {
			tt.first = "." + tt.first;
			tt.second--;
		}
		while (!tt.first.endsWith(".....")) {
			tt.first = tt.first + ".";
		}
		return tt;
	}

	void init() {
		for (int i = 2; i < in.size(); i++) {
			var str = in.get(i).split(" => ");
			dict.put(str[0], str[1].charAt(0));
		}
	}
}
