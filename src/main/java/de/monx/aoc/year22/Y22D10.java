package de.monx.aoc.year22;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D10 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		int[] iCycles = { 20, 60, 100, 140, 180, 220 };
		int ct = 0;
		int x = 1;
		int cycle = 0;
		int ret = 0;
		int idx = 0;
		while (ct < iCycles.length) {
			var str = in.get(idx++);
			int ticks = 1;
			int add = 0;
			if (!str.equals("noop")) {
				add = Integer.valueOf(str.split(" ")[1]);
				ticks++;
			}
			for (int t = 0; t < ticks; t++) {
				if (++cycle == iCycles[ct]) {
					ret += cycle * x;
					ct++;
				}
			}
			x += add;
		}
		return ret;
	}

	@Override
	public Object part2() {
		int x = 0;
		StringBuilder ret = new StringBuilder("\n");
		int j = 0;
		for (var str : in) {
			int val = 0;
			int ticks = 1;
			if (!str.equals("noop")) {
				ticks++;
				val = Integer.valueOf(str.split(" ")[1]);
			}
			for (int t = 0; t < ticks; t++) {
				if (j == x || j == x + 1 || j == x + 2) {
					ret.append("█");
				} else {
					ret.append(" ");
				}
				if (++j == 40) {
					j = 0;
					ret.append("\n");
				}
			}
			x += val;
		}
		return ret.toString();
	}

}
