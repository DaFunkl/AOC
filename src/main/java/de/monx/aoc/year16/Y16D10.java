package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y16D10 extends Day {

	List<int[]> data = getInputList().stream().map(x -> x.split(" ")) //
			.map(x -> x[0].equals("bot") //
					? new int[] { //
							Integer.valueOf(x[1]), // botId
							x[5].equals("bot") //
									? Integer.valueOf(x[6])// low to botId
									: Integer.valueOf(x[6]) + 1000, // low to outputId
							x[10].equals("bot") //
									? Integer.valueOf(x[11]) // high to botId
									: Integer.valueOf(x[11]) + 1000 // high to outputId
					} //
					: new int[] { //
							Integer.valueOf(x[1]), // value
							Integer.valueOf(x[5]) // botId
					} //
			).collect(Collectors.toList());

	Map<Integer, List<Integer>> bots = new HashMap<>();
	Map<Integer, List<Integer>> output = new HashMap<>();
	Map<Integer, IntPair> instructions = new HashMap<>();

	Pair solution = null;

	@Override
	public Object part1() {
		solution = solve();
		return solution.first;
	}

	@Override
	public Object part2() {
		return solution.second;
	}

	Pair solve() {
		init();
		int p1 = -1;
		while (bots.keySet().stream().anyMatch(x -> bots.get(x).size() >= 2)) {
			for (int id : bots.keySet()) {
				if (bots.get(id).size() >= 2) {
					var p = fetchPair(id);
					if (p.equals(new IntPair(17, 61)) && p1 < 0) {
						p1 = id;
					}
					var d = instructions.get(id);
					if (d.first < 1000) {
						bots.get(d.first).add(p.first);
					} else {
						output.get(d.first - 1000).add(p.first);
					}

					if (d.second < 1000) {
						bots.get(d.second).add(p.second);
					} else {
						output.get(d.second - 1000).add(p.second);
					}
				}
			}
		}
		long p2 = 1;
		for (var i : new int[] { 0, 1, 2 }) {
			p2 *= output.get(i).stream().reduce(1, (a, e) -> a * e);
		}
		return new Pair(p1, p2);
	}

	IntPair fetchPair(int id) {
		var l = bots.get(id);
		l.sort(Comparator.naturalOrder());
		var ret = new IntPair(l.get(0), l.get(l.size() - 1));
		l.remove(0);
		l.remove(l.size() - 1);
		return ret;
	}

	void init() {
		bots = new HashMap<>();
		instructions = new HashMap<>();
		for (var x : data) {
			if (x.length == 2) {
				if (!bots.containsKey(x[1])) {
					bots.put(x[1], new ArrayList<>());
				}
				bots.get(x[1]).add(x[0]);
			} else {
				for (int i : x) {
					if (i < 1000 && !bots.containsKey(i)) {
						bots.put(i, new ArrayList<>());
					} else if (i >= 1000 && !output.containsKey(i)) {
						output.put(i - 1000, new ArrayList<>());
					}
				}
				instructions.put(x[0], new IntPair(x[1], x[2]));
			}
		}
	}
}
