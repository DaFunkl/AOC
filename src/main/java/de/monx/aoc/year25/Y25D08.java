package de.monx.aoc.year25;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y25D08 extends Day {

	List<long[]> in = getInputList().stream()//
			.map(x -> x.split(",")) //
			.map(x -> new long[] { //
					Long.valueOf(x[0]), //
					Long.valueOf(x[1]), //
					Long.valueOf(x[2]) //
			}) //
			.toList();
	List<Set<Integer>> connections = new ArrayList<>();
	PriorityQueue<long[]> queue = new PriorityQueue<>(new Comparator<long[]>() {
		@Override
		public int compare(long[] o1, long[] o2) {
			return Long.compare(o1[2], o2[2]);
		}
	});

	@Override
	public Object part1() {
		for (int i = 0; i < in.size(); i++) {
			var a = in.get(i);
			for (int j = i + 1; j < in.size(); j++) {
				var b = in.get(j);
				queue.add(new long[] { i, j, //
						((a[0] - b[0]) * (a[0] - b[0])) // x
								+ ((a[1] - b[1]) * (a[1] - b[1])) // y
								+ ((a[2] - b[2]) * (a[2] - b[2])) });// z
			}
		}
		for (int consCount = 0; consCount < 1000; consCount++) {
			doIt();
		}
		return connections.stream().mapToInt(Set::size).boxed().sorted(Collections.reverseOrder()) //
				.limit(3).reduce(1, (a, b) -> a * b);
	}

	@Override
	public Object part2() {
		int[] sol = null;
		while (connections.get(0).size() < in.size()) {
			sol = doIt();
		}
		return in.get(sol[0])[0] * in.get(sol[1])[0];
	}

	int[] doIt() {
//		var ods = orderedDistances.get(0);
		var ods = queue.poll();
		int[] pt = { (int) ods[0], (int) ods[1], -1, -1 };
		for (int ci = 0; ci < connections.size(); ci++) {
			var cs = connections.get(ci); // @formatter:off
			if (cs.contains(pt[0])) pt[2] = ci;
			if (cs.contains(pt[1])) pt[3] = ci;
			if (pt[2] != -1 && pt[3] != -1) break;
		}// @formatter:on 
		if (pt[2] == pt[3] && pt[2] != -1) {
			return null;
		}
		if (pt[2] == -1 && pt[3] != -1) {
			pt[2] = pt[3];
			pt[3] = -1;
		} else if (pt[2] == -1 && pt[2] == -1) {
			connections.add(new HashSet<>());
			pt[2] = connections.size() - 1;
		}
		connections.get(pt[2]).add(pt[0]);
		connections.get(pt[2]).add(pt[1]);
		if (pt[3] != -1) {
			connections.get(pt[2]).addAll(connections.get(pt[3]));
			connections.remove(pt[3]);
		}
		return new int[] { pt[0], pt[1] };
	}
}
