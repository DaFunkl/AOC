package de.monx.aoc.year23;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.Data;

public class Y23D20 extends Day {
	static final int _BC = 0;
	static final int _FF = 1;
	static final int _CO = 2;

	List<String> in = getInputList();
	Map<String, Module> mods = new HashMap<>();
	String rxGiver;

	@Override
	public Object part1() {
		init();
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	long solve(boolean p2) {
		Map<String, Boolean> pulse = new HashMap<>();
		long high = 0;
		long low = 0;
		for (var k : mods.keySet()) {
			pulse.put(k, false);
		}
		Map<String, Long> p2tracker = new HashMap<>();
		for (long i = 0; i < 1000 || p2; i++) {

			ArrayDeque<Pair<String, Boolean>> stack = new ArrayDeque<>();
			pulse.put("broadcaster", false);
			for (var bco : mods.get("broadcaster").out) {
				stack.push(new Pair<>(bco, false));
			}
			low++;
			while (!stack.isEmpty()) {
				var cur = stack.pollLast();
				if (cur.first.equals("rx") && !cur.second) {
					return i;
				}
				if (cur.second) {
					high++;
				} else {
					low++;
				}
				if (!mods.containsKey(cur.first)) {
					continue;
				}
				if (mods.get(cur.first).type == _FF) {
					if (!cur.second) {
						pulse.put(cur.first, !pulse.get(cur.first));
						for (var n : mods.get(cur.first).out) {
							stack.push(new Pair<>(n, pulse.get(cur.first)));
						}
					}
				} else {
					boolean con = false;
					for (var p : mods.get(cur.first).inp) {
						if (!pulse.get(p)) {
							con = true;
							if (!p2) {
								break;
							}
						} else if (p2 && cur.first.equals(rxGiver)) {
							if (!p2tracker.containsKey(p)) {
								p2tracker.put(p, i + 1);
								if (p2tracker.size() >= mods.get(rxGiver).inp.size()) {
									return lcm(p2tracker);
								}
							}

						}
					}
					pulse.put(cur.first, con);
					for (var n : mods.get(cur.first).out) {
						stack.push(new Pair<>(n, con));
					}
				}
			}
		}
		if (p2) {
			for (var k : p2tracker.keySet()) {
				System.out.println(k + " -> " + p2tracker.get(k));
			}
		}
		return high * low;
	}

	long lcm(Map<String, Long> givers) {
		BigInteger ret = new BigInteger("1");
		for (var k : givers.values()) {
			BigInteger bi = new BigInteger("" + k);
			ret = ret.multiply(bi).divide(ret.gcd(bi));
		}
		return Long.valueOf(ret.toString());
	}

	@Data
	class Module {
		String name;
		int type = 0;
		List<String> inp = new ArrayList<>();
		List<String> out = new ArrayList<>();

		Module(String s) {
			var spl = s.split(" -> ");
			name = spl[0];
			out = Arrays.stream(spl[1].split(", ")).toList();
			if (spl[0].startsWith("%")) {
				type = _FF;
				name = name.substring(1);
			} else if (spl[0].startsWith("&")) {
				type = _CO;
				name = name.substring(1);
			}
		}
	}

	void init() {
		for (var str : in) {
			Module m = new Module(str);
			mods.put(m.name, m);
		}
		for (var k : mods.keySet()) {
			var mk = mods.get(k);
			if (mk.type == _CO) {
				if (mk.out.contains("rx")) {
					rxGiver = k;
				}
				for (var kk : mods.keySet()) {
					var mkk = mods.get(kk);
					if (mkk.out.contains(mk.name)) {
						mk.inp.add(kk);
					}
				}
			}
		}
	}
}
