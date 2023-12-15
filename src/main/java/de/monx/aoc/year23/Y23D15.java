package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y23D15 extends Day {

	List<String> in = Arrays.stream(getInputString().split(",")).toList();

	@Override
	public Object part1() {
		return in.stream().mapToInt(this::hash).sum();
	}

	Map<String, Integer> hash = new HashMap<>();

	int getHash(String s) {
		if (!hash.containsKey(s)) {
			hash.put(s, hash(s));
		}
		return hash.get(s);
	}

	@Override
	public Object part2() {
		Map<Integer, List<Pair<String, Integer>>> boxes = new HashMap<>();
		for (var str : in) {
			if (str.endsWith("-")) {
				var s = str.substring(0, str.length() - 1);
				int h = getHash(s);
				if (!boxes.containsKey(h)) {
					continue;
				}
				for (int i = 0; i < boxes.get(h).size(); i++) {
					if (boxes.get(h).get(i).first.equals(s)) {
						boxes.get(h).remove(i);
						break;
					}
				}
			} else {
				var spl = str.split("=");
				int h = getHash(spl[0]);
				int v = Integer.valueOf(spl[1]);
				boxes.putIfAbsent(h, new ArrayList<>());
				boolean found = false;
				for (int i = 0; i < boxes.get(h).size(); i++) {
					if (boxes.get(h).get(i).first.equals(spl[0])) {
						boxes.get(h).get(i).second = v;
						found = true;
						break;
					}
				}
				if (!found) {
					boxes.get(h).add(new Pair<String, Integer>(spl[0], v));
				}
			}
		}
		long ret = 0;
		for (int k : boxes.keySet()) {
			var box = boxes.get(k);
			for (int n = 0; n < box.size(); n++) {
				ret += (k + 1) * (n + 1) * box.get(n).second;
			}
		}
		return ret;
	}

	int hash(String str) {
		int ret = 0;
		for (var c : str.toCharArray()) {
			ret += c;
			ret *= 17;
			ret %= 256;
		}
		return ret;
	}
}
