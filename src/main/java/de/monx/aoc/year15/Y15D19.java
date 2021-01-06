package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y15D19 extends Day {
	String molecule = "";
	Map<String, List<String>> replacements = new HashMap<>();

	@Override
	public Object part1() {
		parse();
		char[] msg = molecule.toCharArray();
		Character pC = null;
		Set<String> set = new HashSet<>();
		String pStr = "";
		String ppStr = "";
		String key = "";
		String key2 = "";
		for (int i = 0; i < msg.length; i++) {
			char c = msg[i];
			key = (pC != null ? pC + "" : "") + c;
			key2 = c + "";
			if (replacements.containsKey(key)) {
				for (String s : replacements.get(key)) {
					String pre = key.length() == 2 ? ppStr : pStr;
					set.add(pre + s + molecule.substring(i + 1));
				}
			} else if (replacements.containsKey(key2)) {
				for (String s : replacements.get(key2)) {
					set.add(pStr + s + molecule.substring(i + 1));
				}
			} else {
				pC = c;
			}
			pStr += c;
			ppStr += i >= 1 ? msg[i - 1] : "";
		}
		return set.size();
	}

	@Override
	public Object part2() {
		String msg = molecule;
		String prev = "";
		int count = 0;
		while (!msg.equals("e") && !prev.equals(msg)) {
			prev = new String(msg);
			for (String k : replacements.keySet()) {
				for (String r : replacements.get(k)) {
					if (!msg.contains(r)) {
						continue;
					}
					msg = msg.replaceFirst(r, k);
					count++;
				}
			}
		}
		return count;
	}

	void parse() {
		boolean msg = false;
		for (String s : getInputList()) {
			if (s.isBlank()) {
				msg = true;
				continue;
			}
			if (msg) {
				molecule = s;
			} else {
				var spl = s.split(" => ");
				if (!replacements.containsKey(spl[0])) {
					List<String> t = new ArrayList<>();
					replacements.put(spl[0], t);
				}
				replacements.get(spl[0]).add(spl[1]);
			}
		}
	}
}
