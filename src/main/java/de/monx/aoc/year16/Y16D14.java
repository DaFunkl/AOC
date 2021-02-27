package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Y16D14 extends Day {

	String salt = //
//			"abc"; //
			getInputString();

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	Object solve(boolean p2) {
		int idx = 0;
		List<Integer> keys = new ArrayList<>();

		Map<Character, List<Integer>> m3 = new HashMap<>();
		while (true) {
			var hash = de.monx.aoc.util.Util.md5Hash(salt + idx).toLowerCase();
			if (p2) {
				for (int i = 0; i < 2016; i++) {
					hash = de.monx.aoc.util.Util.md5Hash(hash).toLowerCase();
				}
			}
			var vals = fetchHashVals(hash);
			if (vals.first != null) {
				if (!m3.containsKey(vals.first)) {
					m3.put(vals.first, new ArrayList<>());
				}
				m3.get(vals.first).add(idx);
			}
			int fidx = idx - 1000;
			for (var c5 : vals.second.toCharArray()) {
				if (m3.containsKey(c5)) {
					for (int i = 0; i < m3.get(c5).size(); i++) {
						var idx3 = m3.get(c5).get(i);
						if (idx3 != idx && idx3 > fidx) {
							keys.add(idx3);
							keys.sort(Comparator.naturalOrder());
							if (keys.size() >= 64 && keys.get(63) + 1000 < idx) {
								return keys.get(63);
							}
						}
					}
				}
			}
			idx++;
		}
	}

	Pair<Character, String> fetchHashVals(String hash) {
		Character c3 = null;
		String c5 = "";
		char prev = hash.charAt(0);
		int count = 1;
		for (int i = 1; i < hash.length(); i++) {
			char curr = hash.charAt(i);
			if (prev == curr) {
				count++;
			} else {
				count = 1;
				prev = curr;
			}
			if (c3 == null && count == 3) {
				c3 = prev;
			}
//			if (c5.isBlank() &&  count == 5) {
			if (count == 5) {
				c5 += prev;
			}
		}
		return new Pair(c3, c5);
	}
}
