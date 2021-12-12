package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class Y21D12 extends Day {

	Map<String, List<String>> graph = init();

	@Data
	@EqualsAndHashCode(of = "path")
	static class GV {
		public String current;
		public Set<String> seen = new HashSet<>();
		public String path;
		public String specialSmall = null;

		public GV(String c) {
			current = c;
			seen.add(c);
			path = c;
		}

		public GV next(String s) {
			return next(s, specialSmall);
		}

		public GV next(String s, String specialSmall) {
			GV ret = new GV(s);
			ret.seen.addAll(seen);
			ret.path = path + s;
			ret.specialSmall = specialSmall;
			return ret;
		}
	}

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	int solve(boolean p2) {
		List<GV> todos = new ArrayList<>();
		todos.add(new GV("start"));
		Set<String> solutions = new HashSet<>();

		while (!todos.isEmpty()) {
			var p = todos.get(0);
			todos.remove(0);
			for (var n : graph.get(p.current)) { // end
				if (n.equals("end")) {
					solutions.add(p.path + "end");
				} else if (n.equals("start")) {
					continue;
				} else if (Util.isLoweCase(n)) { // small cave
					if (!p.seen.contains(n)) {
						todos.add(p.next(n));
					} else if (p2 && p.specialSmall == null) {
						todos.add(p.next(n, n));
					}
				} else { // big cave
					todos.add(p.next(n));
				}
			}
		}
		return solutions.size();

	}

	Map<String, List<String>> init() {
		Map<String, List<String>> graph = new HashMap<>();

		for (var s : getInputList()) {
			var spl = s.split("-");
			if (!graph.containsKey(spl[0])) {
				graph.put(spl[0], new ArrayList<>());
			}
			graph.get(spl[0]).add(spl[1]);

			if (!graph.containsKey(spl[1])) {
				graph.put(spl[1], new ArrayList<>());
			}
			graph.get(spl[1]).add(spl[0]);
		}

		return graph;
	}
}
