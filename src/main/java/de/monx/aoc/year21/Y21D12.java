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
		// Stack to solve all paths,
		// values = String[]
		// idx 0: current Position
		// idx 1: path
		// idx 2: smallCave (Part2)
		Stack<String[]> todos = new Stack<>();
		// initial state = start, idx 1 can be left empty, doesn't matter, id 2 has to
		// be null, since no small cave was visited twice
		todos.push(new String[] { "start", "", null });
		int ret = 0;
		while (!todos.isEmpty()) {
			var currentPath = todos.pop(); // dfs - approach

			// if a new node is pushed to stack, add nextNode to path (idx 1)
			for (var nextNode : graph.get(currentPath[0])) { // end
				// end was visited, increment return value, don't push to stack
				if (nextNode.equals("end")) {
					ret++;
				} else if (nextNode.equals("start")) {
					// start Node --> ignore
					continue;
				} else if (Util.isLowerCase(nextNode)) { // small cave
					// small cave, only push small cave, if hasn't been seen before,
					// or part2 and it's allowed to visit a small cave twice (idx 2 == null)
					if (!currentPath[1].contains(nextNode)) {
						// idx 2 = previous idx 2, since the path (idx 1) doesn't contain next Node
						todos.push(new String[] { nextNode, currentPath[1] + nextNode, currentPath[2] });
					} else if (isPart2 && currentPath[2] == null) {
						// idx 2 has to be adjusted, next Node is contained in path (idx 1)
						todos.push(new String[] { nextNode, currentPath[1] + nextNode, nextNode });
					}
				} else { // big cave
					// big Cave, always push big caves, the are no cycles
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
