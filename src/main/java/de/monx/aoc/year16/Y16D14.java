package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y16D14 extends Day {
	String salt = "abc";// getInputString();

	@Override
	public Object part1() {
		int idx = 0;
		var isRunning = true;
		Map<Character, List<Integer>> m3 = new HashMap<>();
		List<Integer> ids = new ArrayList<>();
		while (isRunning) {
			String hash = Util.md5Hash(salt + idx);
//			System.out.println(idx + "\t" + hash);
			String[] keys = fetchKeys(hash);
//			System.out.println(Arrays.toString(keys));
			for (var s3 : keys[0].toCharArray()) {
				if (!m3.containsKey(s3)) {
					m3.put(s3, new ArrayList<>());
				}
				m3.get(s3).add(idx);
			}
			final int fIdx = idx - 1000;
			for (var s5 : keys[1].toCharArray()) {
				if (m3.containsKey(s5)) {
					for (int i = 0; i < m3.get(s5).size(); i++) {
						var id = m3.get(s5).get(i);
						if (fIdx <= id) {
							ids.add(id);
							if (ids.size() >= 64) {
								return ids.stream().sorted(Comparator.reverseOrder()).findFirst().get();
							}
							m3.get(s5).remove(i);
						}
					}
				}
			}
			idx++;
		}
		return "Error";
	}

	@Override
	public Object part2() {

		return null;
	}

	String[] fetchKeys(String s) {
		String r3 = "";
		String r5 = "";
		var p = s.charAt(0);
		int count = 1;
		for (int i = 1; i < s.length(); i++) {
			var c = s.charAt(i);
//			if (r3.isBlank() && count == 3 && p != c) {
			if (count == 3) {
				r3 += p;
//				return new String[] { r3, "" };
			}
			if (count == 5) {
//			if (count == 5 && p != c) {
				r5 += p;
			}
			if (p == c) {
				count++;
			} else {
				count = 1;
			}
			p = c;
		}

//		if (count == 3) {
		if (r3.isBlank() && count == 3) {
			r3 += p;
		}
		if (count == 5) {
			r5 += p;
		}

		return new String[] { r3, r5 };
	}
}
