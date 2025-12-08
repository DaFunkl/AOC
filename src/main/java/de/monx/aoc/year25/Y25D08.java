package de.monx.aoc.year25;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
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
	List<Set<Integer>> cons = new ArrayList<>();
	List<long[]> odis = new ArrayList<>();

	@Override
	public Object part1() {
		for (int i = 0; i < in.size(); i++) {
			var a = in.get(i);
			for (int j = i + 1; j < in.size(); j++) {
				var b = in.get(j);
				long[] sols = { a[0] - b[0], a[1] - b[1], a[2] - b[2] };
				sols[0] *= sols[0];
				sols[1] *= sols[1];
				sols[2] *= sols[2];
				odis.add(new long[] { i, j, sols[0] + sols[1] + sols[2] });
			}
		}

		odis.sort(new Comparator<long[]>() {
			@Override
			public int compare(long[] o1, long[] o2) {
				return Long.compare(o1[2], o2[2]);
			}
		});

		for (int consCount = 0; consCount < 1000; consCount++) {
			doIt();
		}
		return cons.stream().mapToInt(Set::size).boxed().sorted(Collections.reverseOrder()) //
				.limit(3).reduce(1, (a, b) -> a * b);
	}

	@Override
	public Object part2() {
		int[] sol = null;
		while (cons.get(0).size() < in.size()) {
			sol = doIt();
		}
		return in.get(sol[0])[0] * in.get(sol[1])[0];
	}

	int[] doIt() {
		var ods = odis.get(0);
		int i = (int) ods[0];
		int j = (int) ods[1];
		int coni = -1;
		int conj = -1;
		for (int ci = 0; ci < cons.size(); ci++) {
			var cs = cons.get(ci);
			if (cs.contains(i)) {
				coni = ci;
			}
			if (cs.contains(j)) {
				conj = ci;
			}
			if (coni != -1 && conj != -1) {
				break;
			}
		}
		if (coni == conj && coni != -1) {
			odis.remove(0);
			return null;
		}
		if (coni == -1 && conj != -1) {
			coni = conj;
			conj = -1;
		} else if (coni == -1 && coni == -1) {
			cons.add(new HashSet<>());
			coni = cons.size() - 1;
		}
		cons.get(coni).add(i);
		cons.get(coni).add(j);
		if (conj != -1) {
			cons.get(coni).addAll(cons.get(conj));
			cons.remove(conj);
		}
		odis.remove(0);
		return new int[] { i, j };
	}
}
