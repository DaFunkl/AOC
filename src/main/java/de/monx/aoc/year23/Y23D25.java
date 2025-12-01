package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y23D25 extends Day {

	List<String> in = getInputList();
	Map<String, List<String>> cons = init();

	@Override
	public Object part1() {
		while (true) {
			Map<String, List<String>> cons = init();
			Map<String, Integer> counts = new HashMap<>();
			for (var k : cons.keySet()) {
				counts.put(k, 1);
			}

			while (cons.size() > 2) {
				var a = cons.keySet().stream().skip((int) (cons.size() * Math.random())).findAny().get();
				var b = cons.get(a).stream().skip((int) (cons.get(a).size() * Math.random())).findAny().get();
				var ab = a + "-" + b;
				counts.put(ab, counts.getOrDefault(a, 0) + counts.getOrDefault(b, 0));
				if (a.equals(b)) {
					continue;
				}

				List<String> abs = new ArrayList<>();
				for (var k : cons.get(a)) {
					if (k.equals(b)) {
						continue;
					}
					cons.get(k).remove(a);
					cons.get(k).add(ab);
					abs.add(k);
				}
				for (var k : cons.get(b)) {
					if (k.equals(a)) {
						continue;
					}
					cons.get(k).remove(b);
					cons.get(k).add(ab);
					abs.add(k);
				}
				cons.remove(a);
				cons.remove(b);
				cons.put(ab, abs);

			}
			if (cons.size() == 2 && cons.values().stream().findFirst().get().size() == 3) {
				int ret = 1;
				for (var k : cons.keySet()) {
					ret *= counts.get(k);
				}
				return ret;
			}

		}
	}

	// jqt: rhn xhk nvd
	Map<String, List<String>> init() {
		Map<String, List<String>> ret = new HashMap<>();
		for (var s : in) {
			var spl = s.split(": ");
			var k = spl[0];
			spl = spl[1].split(" ");
			ret.putIfAbsent(k, new ArrayList<>());
			for (var v : spl) {
				ret.putIfAbsent(v, new ArrayList<>());
				ret.get(k).add(v);
				ret.get(v).add(k);
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return "It's done!";
	}

}
