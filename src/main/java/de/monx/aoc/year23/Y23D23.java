package de.monx.aoc.year23;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y23D23 extends Day {

	List<char[]> in = getInputList().stream().map(String::toCharArray).toList();
	IntPair start = new IntPair(0, 1);
	IntPair goal = new IntPair(in.size() - 1, in.get(0).length - 2);
	IntPair[] _DIRS = { //
			new IntPair(-1, 0), //
			new IntPair(0, -1), //
			new IntPair(1, 0), //
			new IntPair(0, 1), //
	};
	Map<IntPair, Map<IntPair, Integer>> nodes = new HashMap<>();

	@Override
	public Object part1() {
		ArrayDeque<Pair<IntPair, Set<IntPair>>> stack = new ArrayDeque<>();
		int max = 0;
		stack.push(new Pair<>(start, new HashSet<>()));
		while (!stack.isEmpty()) {

			if (stack.size() % 1000 == 0) {
				System.out.println("ss: " + stack.size());
			}
			var cur = stack.pop();
			if (goal.equals(cur.first)) {
				max = Math.max(max, cur.second.size());
				continue;
			}
			for (var d : _DIRS) {
				var nd = d.add(cur.first);
				if (nd.first < 0) {
					continue;
				}
				char c = in.get(nd.first)[nd.second];
				if (c == '#' || cur.second.contains(nd)) {
					continue;
				}
				var ip = switch (c) {
				case '^' -> _DIRS[0];
				case '<' -> _DIRS[1];
				case 'v' -> _DIRS[2];
				case '>' -> _DIRS[3];
				default -> d;
				};
				if (d.equals(ip)) {
					var hs = new HashSet<>(cur.second);
					hs.add(nd);
					stack.push(new Pair<>(nd, hs));
				}
			}
		}
		return max;
	}

	IntPair isValid(IntPair ip, IntPair d) {
		return switch (in.get(ip.first)[ip.second]) {
		case '^' -> _DIRS[0];
		case '<' -> _DIRS[1];
		case 'v' -> _DIRS[2];
		case '>' -> _DIRS[3];
		default -> d;

		};
	}

	@Override
	public Object part2() {
		findNodes();
		int max = 0;
		ArrayDeque<Pair<IntPair, Pair<Integer, Set<IntPair>>>> stack = new ArrayDeque<>();
		stack.push(new Pair<>(start, new Pair<>(0, Set.of(start))));
		while (!stack.isEmpty()) {
			var cur = stack.pop();
			if (cur.first.equals(goal)) {
				max = Math.max(max, cur.second.first);
				continue;
			}
			for (var o : nodes.get(cur.first).keySet()) {
				if (!cur.second.second.contains(o)) {
					Set<IntPair> s = new HashSet<>(cur.second.second);
					s.add(o);
					stack.push(new Pair<>(o, new Pair<>(cur.second.first + nodes.get(cur.first).get(o), s)));
				}
			}
		}
		return max;
	}

	void findNodes() {
		ArrayDeque<IntPair[]> st = new ArrayDeque<>();
		Set<IntPair> done = new HashSet<>();
		st.push(new IntPair[] { start.add(_DIRS[2]), start, start, new IntPair(1, 0) });
		while (!st.isEmpty()) {
			if (st.size() % 1000 == 0) {
				System.out.println("ss: " + st.size());
			}
			var cur = st.pop();
			var opts = Arrays.stream(_DIRS).map(cur[0]::add)
					.filter(x -> !x.equals(cur[1]) && in.get(x.first)[x.second] != '#').toArray(IntPair[]::new);
			while (opts.length == 1) {
				cur[1] = cur[0];
				cur[0] = opts[0];
				cur[3].first++;
				if (cur[0].equals(goal)) {
					nodes.putIfAbsent(cur[0], new HashMap<>());
					nodes.putIfAbsent(cur[2], new HashMap<>());
					nodes.get(cur[0]).put(cur[2], cur[3].first);
					nodes.get(cur[2]).put(cur[0], cur[3].first);
					break;
				}
				opts = Arrays.stream(_DIRS).map(cur[0]::add)
						.filter(x -> !x.equals(cur[1]) && in.get(x.first)[x.second] != '#').toArray(IntPair[]::new);
			}
			if (opts.length > 1) {
				nodes.putIfAbsent(cur[0], new HashMap<>());
				nodes.putIfAbsent(cur[2], new HashMap<>());
				nodes.get(cur[0]).put(cur[2], cur[3].first);
				nodes.get(cur[2]).put(cur[0], cur[3].first);
				if (!done.contains(cur[0])) {
					for (var o : opts) {
						st.push(new IntPair[] { o, cur[0], cur[0], new IntPair(1, 0) });
					}
				}
				done.add(cur[0]);
			}
		}
	}
}
