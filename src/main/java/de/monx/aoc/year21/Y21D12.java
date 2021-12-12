package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

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
		Stack<String[]> todos = new Stack<>();
		todos.push(new String[] { "start", "", null });
		int ret = 0;
		while (!todos.isEmpty()) {
			var currentPath = todos.pop();
			for (var nextNode : graph.get(currentPath[0])) { // end
				if (nextNode.equals("end")) {
					ret++;
				} else if (nextNode.equals("start")) {
					continue;
				} else if (Util.isLoweCase(nextNode)) { // small cave
					if (!currentPath[1].contains(nextNode)) {
						todos.push(new String[] { nextNode, currentPath[1] + nextNode, currentPath[2] });
					} else if (isPart2 && currentPath[2] == null) {
						todos.push(new String[] { nextNode, currentPath[1] + nextNode, nextNode });
					}
				} else { // big cave
					todos.push(new String[] { nextNode, currentPath[1] + nextNode, currentPath[2] });
				}
			}
		}
		return ret;
	}

	Map<String, List<String>> init() {
		Map<String, List<String>> graph = new HashMap<>();
		for (var s : getInputList()) {
			var spl = s.split("-");
			graph.computeIfAbsent(spl[0], k -> new ArrayList<>()).add(spl[1]);
			graph.computeIfAbsent(spl[1], k -> new ArrayList<>()).add(spl[0]);
		}
		return graph;
	}
}
