package de.monx.aoc.year24;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y24D18 extends Day {

	List<IntPair> in = getInputList().stream().map(x -> x.split(",")).map(x -> new IntPair(x[1], x[0])).toList();
//	IntPair end = new IntPair(6, 6);
	IntPair end = new IntPair(70, 70);
	IntPair[] _Dirs = { //
			new IntPair(-1, 0), // u
			new IntPair(0, 1), // r
			new IntPair(1, 0), // d
			new IntPair(0, -1), // l
	};

//	int fallen = 12;
	int fallen = 1024;

	@Override
	public Object part1() {
		Map<IntPair, Integer> bests = new HashMap<>();
		Set<IntPair> blocks = new HashSet<>();

		for (int i = 0; i < fallen; i++) {
			blocks.add(in.get(i));
		}

		ArrayDeque<Pair<IntPair, Integer>> stack = new ArrayDeque<>();
		stack.push(new Pair<>(new IntPair(0, 0), 0));
		bests.put(stack.peek().first, 1);
		while (!stack.isEmpty()) {
			var curState = stack.pollLast();
			var cur = curState.first;
			var steps = curState.second;
			if (bests.getOrDefault(cur, Integer.MAX_VALUE) <= steps) {
				continue;
			}
			bests.put(cur, steps);
			for (var d : _Dirs) {
				var nd = d.add(cur);
				if (nd.first < 0 || nd.second < 0 || nd.first > end.first || nd.second > end.second
						|| blocks.contains(nd)) {
					continue;
				}
				stack.push(new Pair<>(nd, steps + 1));
			}
		}
		return bests.get(end);
	}

	@Override
	public Object part2() {
		fallen = 3000;
		for (; fallen < in.size(); fallen++) {
			var res = part1();
//			System.out.println(fallen + " - " + res);
			if (res == null) {
				fallen--;
				break;
			}
		}
		var ip = in.get(fallen);
		return ip.second + "," + ip.first;
	}
}
