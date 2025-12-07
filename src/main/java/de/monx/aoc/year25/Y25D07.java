package de.monx.aoc.year25;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y25D07 extends Day {
	List<char[]> in = getInputList().stream().map(String::toCharArray).toList();
	IntPair ip = new IntPair(0, 0);

	@Override
	public Object part1() {
		for (int i = 0; i < in.get(0).length; i++) {
			if (in.get(0)[i] == 'S') {
				this.ip.second = i;
				break;
			}
		}
		ArrayDeque<IntPair> stack = new ArrayDeque<>();
		stack.add(ip.clone());
		int ret = 0;
		Set<IntPair> seen = new HashSet<>();
		while (!stack.isEmpty()) {
			Set<IntPair> nps = new HashSet<>();
			while (!stack.isEmpty()) {
				var ip = stack.pop();
				seen.add(ip);
				if (ip.first + 1 >= in.size()) {
					continue;
				}
				var c = in.get(ip.first + 1)[ip.second];
				if (c == '.') {
					nps.add(ip.add(1, 0));
				} else if (c == '^') {
					nps.add(ip.add(1, -1));
					nps.add(ip.add(1, 1));
					ret++;
				} else {
					System.err.println(ip + " -> " + c);
				}
			}
			nps.forEach(stack::add);
		}
		return ret;
	}

	@Override
	public Object part2() {
		for (int i = 0; i < in.get(0).length; i++) {
			if (in.get(0)[i] == 'S') {
				this.ip.second = i;
				break;
			}
		}
		ArrayDeque<Pair<IntPair, Long>> stack = new ArrayDeque<>();
		stack.add(new Pair<>(ip, 1l));
		long ret = 1;
		while (!stack.isEmpty()) {
			Map<IntPair, Long> nps = new HashMap<>();
			while (!stack.isEmpty()) {
				var ipp = stack.pop();
				var ip = ipp.first;
				if (ip.first + 1 >= in.size()) {
					continue;
				}
				var c = in.get(ip.first + 1)[ip.second];
				if (c == '.') {
					var nip = ip.add(1, 0);
					if (nps.containsKey(nip)) {
						nps.put(nip, nps.get(nip) + ipp.second);
					} else {
						nps.put(nip, ipp.second);
					}
				} else if (c == '^') {
					var nipl = ip.add(1, -1);
					var nipr = ip.add(1, 1);
					if (nps.containsKey(nipl)) {
						nps.put(nipl, nps.get(nipl) + ipp.second);
					} else {
						nps.put(nipl, ipp.second);
					}
					if (nps.containsKey(nipr)) {
						nps.put(nipr, nps.get(nipr) + ipp.second);
					} else {
						nps.put(nipr, ipp.second);
					}
					ret += ipp.second;
				} else {
					System.err.println(ip + " -> " + ipp.second);
				}
			}
			nps.entrySet().forEach(x -> stack.add(new Pair<>(x.getKey(), x.getValue())));
		}
		return ret;
	}

	void print(Set<IntPair> seen) {
		IntPair ip = new IntPair(0, 0);
		StringBuilder sb = new StringBuilder();
		for (ip.first = 0; ip.first < in.size(); ip.first++) {
			for (ip.second = 0; ip.second < in.get(0).length; ip.second++) {
				var c = in.get(ip.first)[ip.second];
				sb.append(c == '.' && seen.contains(ip) ? '|' : c);
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		System.out.println();
	}
}
