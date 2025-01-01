package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y24D24 extends Day {
	List<String> in = getInputList();
	Map<String, Integer> regs = new HashMap<>();
	List<String[]> ops = new ArrayList<>();

	@Override
	public Object part1() {
		init();
		Set<Integer> todos = new HashSet<>();
		for (int i = 0; i < ops.size(); i++) {
			todos.add(i);
		}
		while (!todos.isEmpty()) {
			for (var xi : todos) {
				var x = ops.get(xi);
				if (regs.containsKey(x[0]) && regs.containsKey(x[2])) {
					regs.put(x[3], switch (x[1]) {
					case "AND" -> (regs.get(x[0]) & regs.get(x[2])) & 1;
					case "XOR" -> (regs.get(x[0]) ^ regs.get(x[2])) & 1;
					case "OR" -> (regs.get(x[0]) | regs.get(x[2])) & 1;
					default -> throw new IllegalArgumentException("Unexpected value: " + x[1]);
					});
					todos.remove(xi);
					break;
				}
			}
		}

		String bin = regs.keySet().stream().filter(x -> x.startsWith("z")) //
				.sorted((o1, o2) -> o2.compareTo(o1)).map(x -> "" + regs.get(x)) //
				.reduce("", (a, b) -> a + b);
		return Long.parseLong(bin, 2);
	}

	@Override
	public Object part2() {
		return "manually solved with Excel :)";
	}

	String swapTry(int[] idxs, long res) {
		return null;
	}

	void init() {
		boolean sec = false;
		for (var str : in) {
			if (str.isBlank()) {
				sec = true;
			} else if (!sec) {
				var spl = str.split(": ");
				regs.put(spl[0], Integer.valueOf(spl[1]));
			} else {
				ops.add(str.replace(" ->", "").split(" "));
			}
		}
	}
}
