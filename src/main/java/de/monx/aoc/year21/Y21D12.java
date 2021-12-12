package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y21D12 extends Day {

	Map<String, List<String>> graph = null;

	@Override
	public Object part1() {
		graph = init();
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	int solve(boolean isPart2) {
		List<String[]> todos = new ArrayList<>();
		todos.add(new String[] { "start", "", null });
		int ret = 0;
		while (!todos.isEmpty()) {
			var currentPath = todos.get(0);
			todos.remove(0);
			for (var nextNode : graph.get(currentPath[0])) { // end
				if (nextNode.equals("end")) {
					ret++;
				} else if (nextNode.equals("start")) {
					continue;
				} else if (Util.isLoweCase(nextNode)) { // small cave
					if (!currentPath[1].contains(nextNode)) {
						todos.add(new String[] { nextNode, currentPath[1] + "|" + nextNode, currentPath[2] });
					} else if (isPart2 && currentPath[2] == null) {
						todos.add(new String[] { nextNode, currentPath[1] + "|" + nextNode, nextNode });
					}
				} else { // big cave
					todos.add(new String[] { nextNode, currentPath[1] + "|" + nextNode, currentPath[2] });
				}
			}
		}
		return ret;
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
