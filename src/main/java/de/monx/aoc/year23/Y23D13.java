package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D13 extends Day {

	List<List<String>> in = init();
	List<Integer> reflections = new ArrayList<>();

	@Override
	public Object part1() {
		int ret = 0;
		for (var g : in) {
			int sol = solve(g, -1, -1, -1);
			ret += sol;
			reflections.add(sol);
		}
		return ret;
	}

	@Override
	public Object part2() {
		int ret = 0;
		for (int i = 0; i < in.size(); i++) {
			var g = in.get(i);
			int line = -1;
			for (int y = 0; y < g.size(); y++) {
				for (int x = 0; x < g.get(0).length(); x++) {
					int sol = solve(g, y, x, i);
					if (sol == -1) {
						continue;
					}
					line = sol;
					if (line != -1) {
						break;
					}
				}
				if (line != -1) {
					break;
				}
			}
			if (line != -1) {
				ret += line;
			}
		}
		return ret;
	}

	int solve(List<String> g, int sy, int sx, int idx) {

		for (int i = 1; i < g.get(0).length(); i++) {
			boolean found = true;
			for (int d = 0; d < i && d + i < g.get(0).length(); d++) {
				for (int j = 0; j < g.size(); j++) {
					char c1 = g.get(j).charAt(i - d - 1);
					if (sy == j && sx == (i - d - 1)) {
						c1 = c1 == '.' ? '#' : '.';
					}
					char c2 = g.get(j).charAt(i + d);
					if (sy == j && sx == (i + d)) {
						c2 = c2 == '.' ? '#' : '.';
					}
					if (c1 != c2) {
						found = false;
						break;
					}
				}
				if (!found) {
					break;
				}
			}
			if (found && (idx < 0 || reflections.get(idx) != i)) {
				return i;
			}
		}
		for (int i = 1; i < g.size(); i++) {
			boolean found = true;
			for (int d = 0; d < i && d + i < g.size(); d++) {
				for (int j = 0; j < g.get(i).length(); j++) {

					char c1 = g.get(i - d - 1).charAt(j);
					if (sy == (i - d - 1) && sx == j) {
						c1 = c1 == '.' ? '#' : '.';
					}
					char c2 = g.get((i + d)).charAt(j);
					if (sy == (i + d) && sx == j) {
						c2 = c2 == '.' ? '#' : '.';
					}
					if (c1 != c2) {
						found = false;
						break;
					}
				}
				if (!found) {
					break;
				}
			}
			if (found && (idx < 0 || reflections.get(idx) != (i * 100))) {
				return i * 100;
			}
		}
		return -1;
	}

	List<List<String>> init() {
		List<List<String>> in = new ArrayList<>();
		List<String> t = new ArrayList<>();
		for (var i : getInputList()) {
			if (i.isBlank()) {
				in.add(t);
				t = new ArrayList<>();
				continue;
			}
			t.add(i);
		}
		in.add(t);
		return in;
	}
}
