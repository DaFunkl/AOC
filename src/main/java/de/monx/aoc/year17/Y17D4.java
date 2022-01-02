package de.monx.aoc.year17;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y17D4 extends Day {
	List<String> pws = getInputList();
	int ret1, ret2;

	@Override
	public Object part1() {
		solve();
		return ret1;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	void solve() {
		ret1 = 0;
		ret2 = 0;
		for (var pw : pws) {
			Set<String> words = new HashSet<>();
			Set<String> anagr = new HashSet<>();
			boolean valid1 = true;
			boolean valid2 = true;
			for (var s : pw.split(" ")) {
				char[] sar = s.toCharArray();
				Arrays.sort(sar);
				String anaStr = new String(sar);
				if (anagr.contains(anaStr)) {
					valid2 = false;
				}
				anagr.add(anaStr);
				if (words.contains(s)) {
					valid1 = false;
					break;
				}
				words.add(s);
			}
			if (valid1) {
				ret1++;
			}
			if (valid2) {
				ret2++;
			}
		}
	}
}
