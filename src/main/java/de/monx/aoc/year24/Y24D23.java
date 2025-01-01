package de.monx.aoc.year24;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.BronKerbosch;
import de.monx.aoc.util.Day;

public class Y24D23 extends Day {

	List<String[]> in = getInputList().stream().map(x -> x.split("-")).toList();
	Map<String, Set<String>> connections = new HashMap<>();

	@Override
	public Object part1() {
		for (var sar : in) {
			connections.putIfAbsent(sar[0], new HashSet<>());
			connections.get(sar[0]).add(sar[1]);
			connections.putIfAbsent(sar[1], new HashSet<>());
			connections.get(sar[1]).add(sar[0]);
		}
		var keys = connections.keySet().stream().toList();
		int ret = 0;
		for (int i1 = 0; i1 < keys.size(); i1++) {
			var k1 = keys.get(i1);
			for (int i2 = i1 + 1; i2 < keys.size(); i2++) {
				var k2 = keys.get(i2);
				if (!connections.get(k1).contains(k2)) {
					continue;
				}
				for (int i3 = i2 + 1; i3 < keys.size(); i3++) {
					var k3 = keys.get(i3);
					if (!connections.get(k1).contains(k3)) {
						continue;
					}
					if (!connections.get(k2).contains(k3)) {
						continue;
					}
					if (!k1.startsWith("t") && !k2.startsWith("t") && !k3.startsWith("t")) {
						continue;
					}
					ret++;
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		BronKerbosch<String> bk = new BronKerbosch<>(connections);
		var password = bk.largestClique().toArray();
		Arrays.sort(password);
		return Arrays.toString(password).replace(" ", "").replace("[", "").replace("]", "");
	}

}
