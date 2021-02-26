package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y16D13 extends Day {

	int input = Integer.valueOf(getInputString());

	Map<IntPair, Boolean> grid = new HashMap<>();

	@Override
	public Object part1() {
		List<Pair<IntPair, Integer>> stack = new ArrayList<>();
		Set<IntPair> set = new HashSet<>();
		stack.add(new Pair(new IntPair(1, 1), 0));
		set.add(new IntPair(1, 1));
		boolean found = false;
		final var goal = new IntPair(31, 39);
		while (!stack.isEmpty() && !found) {
			var ipp = stack.get(0);
			var ip = ipp.first;
			int steps = ipp.second + 1;
			stack.remove(0);
			IntPair[] next = new IntPair[] { //
					ip.add(+0, +1), //
					ip.add(+1, +0), //
					ip.add(+0, -1), //
					ip.add(-1, +0) //
			};
			for (var n : next) {
				if (n.first < 0 || n.second < 0 || set.contains(n)) {
					continue;
				}
				if (n.equals(goal)) {
					return steps;
				}
				if (isFree(n)) {
					stack.add(new Pair(n, steps));
					set.add(n);
				}

			}
		}
		return "Error";
	}

	@Override
	public Object part2() {
		List<Pair<IntPair, Integer>> stack = new ArrayList<>();
		Set<IntPair> set = new HashSet<>();
		stack.add(new Pair(new IntPair(1, 1), 0));
		set.add(new IntPair(1, 1));
		boolean found = false;
		int count = 0;
		while (!stack.isEmpty() && !found) {
			var ipp = stack.get(0);
			var ip = ipp.first;
			int steps = ipp.second;
			if (steps > 50) {
				return count;
			} else {
				count++;
			}
			steps++;
			stack.remove(0);
			IntPair[] next = new IntPair[] { //
					ip.add(+0, +1), //
					ip.add(+1, +0), //
					ip.add(+0, -1), //
					ip.add(-1, +0) //
			};
			for (var n : next) {
				if (n.first < 0 || n.second < 0 || set.contains(n)) {
					continue;
				}
				if (isFree(n)) {
					stack.add(new Pair(n, steps));
					set.add(n);
				}

			}
		}
		return "Error";
	}

	boolean isFree(IntPair ip) {
		if (grid.containsKey(ip)) {
			return grid.get(ip);
		} else {
			boolean type = type(ip);
			grid.put(ip, type);
			return type;
		}
	}

	boolean type(IntPair ip) {
		int x = ip.first;
		int y = ip.second;
		int r = x * x + 3 * x + 2 * x * y + y + y * y;
		r += input;
		return Integer.bitCount(r) % 2 == 0;
	}
}
