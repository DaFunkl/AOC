package de.monx.aoc.year18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y18D2 extends Day {
	List<String> in = getInputList();

	@Override
	public Object part1() {
		int[] cs = { 0, 0 };
		for (var i : in) {
			var c = count23(i);
			cs[0] += c[0];
			cs[1] += c[1];
		}
		return cs[0] * cs[1];
	}

	@Override
	public Object part2() {
		for (int i = 0; i < in.size(); i++) {
			char[] arr1 = in.get(i).toCharArray();
			for (int j = i + 1; j < in.size(); j++) {
				int idx = -1;
				StringBuilder sb = new StringBuilder();
				char[] arr2 = in.get(j).toCharArray();
				for (int k = 0; k < arr1.length; k++) {
					if (arr1[k] != arr2[k]) {
						if (idx >= 0) {
							idx = -1;
							break;
						}
						idx = k;
					} else {
						sb.append(arr1[k]);
					}
				}
				if (idx >= 0) {
					return sb.toString();
				}
			}
		}
		return null;
	}

	int[] count23(String s) {
		int[] ret = { 0, 0 };
		Map<Character, Integer> cc = new HashMap<>();
		for (var c : s.toCharArray()) {
			int amt = cc.getOrDefault(c, 0) + 1;
			cc.put(c, amt);
		} // @formatter:off
		for (var cv : cc.values()) {
			if (cv == 2) ret[0] = 1;
			if (cv == 3) ret[1] = 1;
		} // @formatter:on
		return ret;
	}
}
