package de.monx.aoc.year25;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y25D09 extends Day {

	List<long[]> in = getInputList().stream().map(x -> x.split(",")) //
			.map(x -> new long[] { Long.valueOf(x[0]), Long.valueOf(x[1]) }) //
			.sorted(new Comparator<long[]>() {
				@Override
				public int compare(long[] o1, long[] o2) {
					return Long.compare((o1[0] + o1[1]), (o2[0] + o2[1]));
				}
			}).toList();

	@Override
	public Object part1() {
		long max = 0;
		for (int i = 0; i < in.size(); i++) {
			var pi = in.get(i);
			for (int j = in.size() - 1; j > i; j--) {
				var pj = in.get(j);
				long y = 1 + Math.abs((pi[0] - pj[0]));
				long x = 1 + Math.abs((pi[1] - pj[1]));
				long area = x * y;
				max = Math.max(max, area);
			}
		}
		return max;
	}

	Set<String> dots = new HashSet<>();

	@Override
	public Object part2() {
		Map<Integer, Set<Integer>> pol = new HashMap<>();
		for (int i = 0; i < in.size(); i++) {
			var pi = in.get(i);
			dots.add(pi[0] + "," + pi[1]);
			for (int j = in.size() - 1; j > i; j--) {
				var pj = in.get(j);
				if (pi[0] != pj[0] && pi[1] != pj[1]) {
					continue;
				}
				pol.computeIfAbsent(i, HashSet::new);
				pol.get(i).add(j);
			}
		}
		long max = 0;
		for (int i = 0; i < in.size(); i++) {
			var pi = in.get(i);
			for (int j = i + 1; j < in.size(); j++) {
				var pj = in.get(j);
				if (!isAreaInPol(pi, pj, pol)) {
					continue;
				}
				long y = 1 + Math.abs((pi[0] - pj[0]));
				long x = 1 + Math.abs((pi[1] - pj[1]));
				long area = x * y;
				max = Math.max(max, area);
			}
		}
		return max;
	}

	boolean isAreaInPol(long[] pi, long[] pj, Map<Integer, Set<Integer>> pol) {
		long[][] corners = { //
				new long[] { Math.min(pi[0], pj[0]), Math.min(pi[1], pj[1]) }, //
				new long[] { Math.min(pi[0], pj[0]), Math.max(pi[1], pj[1]) }, //
				new long[] { Math.max(pi[0], pj[0]), Math.min(pi[1], pj[1]) }, //
				new long[] { Math.max(pi[0], pj[0]), Math.max(pi[1], pj[1]) } //
		};
		for (var c : corners) {
			if (!isDotInPoly(c, pol)) {
				return false;
			}
		}
		for (int i = 0; i < 4; i++) {
			var c1 = corners[i];
			var c2 = corners[(i + 1) % 4];
			if (doesCut(c1, c2, pol)) {
				return false;
			}
		}
		return true;
	}

	Map<String, Boolean> polySegs = new HashMap<>();

	boolean doesCut(long[] c1, long[] c2, Map<Integer, Set<Integer>> pol) {
		long[] pi = { 0, 0 };
		long[] pj = { 0, 0 };
		String cs = c1[0] + "," + c1[1] + "," + c2[0] + "," + c2[1];
		if (polySegs.containsKey(cs)) {
			return polySegs.get(cs);
		}
		int id = c2[0] == c2[0] ? 1 : 0;
		int is = (id + 1) % 2;
		for (var i : pol.keySet()) {
			pi = in.get(i);
			if (pi[id] <= Math.min(c1[id], c2[id]) || pi[id] >= Math.max(c1[id], c2[id])) {
				continue;
			}
			for (var j : pol.get(i)) {
				pj = in.get(j);
				if (pj[id] != pi[id]) {
					continue;
				}
				if (Math.min(pi[is], pj[is]) < c1[is] && c1[is] < Math.max(pi[is], pj[is])) {
					polySegs.put(cs, true);
					return true;
				}
			}
		}
		polySegs.put(cs, false);
		return false;
	}

	Map<long[], Boolean> polyDots = new HashMap<>();

	boolean isDotInPoly(long[] c, Map<Integer, Set<Integer>> pol) {
		String cs = c[0] + "," + c[1];
		if (dots.contains(cs)) {
			return true;
		}
		if (polyDots.containsKey(c)) {
			return polyDots.get(c);
		}
		boolean ret = false;
		for (var i : pol.keySet()) {
			var pi = in.get(i);
			for (var j : pol.get(i)) {
				var pj = in.get(j);
				boolean intersect = ((pi[0] > c[0]) != (pj[0] > c[0]))
						&& (c[1] < (pj[1] - pi[1]) * (c[0] - pi[0]) / (pj[0] - pi[0]) + pi[1]);
				if (intersect) {
					ret = !ret;
				}
			}
		}
		polyDots.put(c, ret);
		return ret;
	}
}
