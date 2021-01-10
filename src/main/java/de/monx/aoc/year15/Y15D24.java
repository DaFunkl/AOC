package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y15D24 extends Day {
	List<Integer> data = getInputList().stream() //
			.map(Integer::valueOf) //
			.sorted(Comparator.reverseOrder()) //
			.collect(Collectors.toList());

	int maxWeight = data.stream().reduce(0, (a, e) -> a + e);
	int third = maxWeight / 3;

	@Override
	public Object part1() {
		return solve(maxWeight / 3);
	}

	@Override
	public Object part2() {
		return solve(maxWeight / 4);
	}

	long solve(int limit) {
		List<IntPair> fq = new ArrayList<>();
		fq.add(new IntPair(0, 1));
		long w = data.get(0);
		int f = 0;
		List<List<Integer>> quadrants = new ArrayList<>();
		int minSize = Integer.MAX_VALUE;

		while (f <= fetchLimit(limit)) {
			if (fq.isEmpty()) { // fetch a new starting point
				fq = new ArrayList<>();
				fq.add(new IntPair(++f, 1));
				w = data.get(f);
				continue;
			}
			if (w == limit) { // limit is matched, add to possible solutions
				if (fq.size() <= minSize) { // only add possible solutions, with equal or less items
					quadrants.add(fq.stream().map(x -> data.get(x.first)).collect(Collectors.toList()));
					minSize = fq.size();
				}
				w -= data.get(fq.get(fq.size() - 1).first);
				fq.remove(fq.size() - 1);
				if (!fq.isEmpty()) {
					fq.get(fq.size() - 1).second++;
				}
			} else if (fq.size() > minSize) { // if a possible solution has too many items, step foward
				w -= data.get(fq.get(fq.size() - 1).first);
				fq.remove(fq.size() - 1);
				if (!fq.isEmpty()) {
					fq.get(fq.size() - 1).second++;
				}
			} else if (w < limit) { // weight is less, add an item
				var prev = fq.get(fq.size() - 1);
				int next = prev.first + prev.second;
				if (next >= data.size()) { // if pointer goes out of limit, step foward
					w -= data.get(fq.get(fq.size() - 1).first);
					fq.remove(fq.size() - 1);
					if (!fq.isEmpty()) { // only add stepper, if stack not null
						fq.get(fq.size() - 1).second++;
					}
				} else { // add next item
					int nextValue = data.get(next);
					fq.add(new IntPair(next, 1));
					w += nextValue;
				}
			} else if (w > limit) { // weight is too high, step backwards and add stepper pointer
				w -= data.get(fq.get(fq.size() - 1).first);
				fq.remove(fq.size() - 1);
				if (!fq.isEmpty()) {
					fq.get(fq.size() - 1).second++;
				}
			}
		}

		// fetch smallest list with smallest quadratic entanglement
		final int min = minSize;
		return quadrants.stream().filter(x -> x.size() == min)
				.map(x -> new Pair<Long, List<Integer>>(x.stream().map(y -> (long) y).reduce(1L, (a, e) -> a * e), x))
				.map(x -> x.first).sorted(Comparator.naturalOrder()).findFirst().get();
	}

	int fetchLimit(int limit) {
		int accu = 0;
		for (int i = data.size() - 1; i >= 0; i--) {
			accu += data.get(i);
			if (accu >= third) {
				return i;
			}
		}
		return 0;
	}
}
