package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y15D17 extends Day {
	List<Integer> data = getInputList().stream() //
			.map(x -> Integer.valueOf(x)) //
			.sorted(Comparator.reverseOrder()) //
			.collect(Collectors.toList());
	static final int MAX_NOG = 150;

	IntPair solution = new IntPair(0,0);

	@Override
	public Object part1() {
		solution = solve();
		return solution.first;
	}

	@Override
	public Object part2() {
		return solution.second;
	}

	private IntPair solve() {
		int count = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i <= fetchLimit(); i++) {
			List<int[]> stack = new ArrayList<>();
			stack.add(new int[] { i, data.get(i), 1 });
			while (!stack.isEmpty()) {
				int lastIdx = stack.size() - 1;
				int[] peek = stack.get(lastIdx);
				if (peek[1] == MAX_NOG) { // combination found
					count++;
					if (stack.size() < min) {
						min = stack.size();
					}
					stack.remove(lastIdx);
					stack.get(lastIdx - 1)[2]++;
				} else if (peek[1] < MAX_NOG) { // less
					int next = peek[0] + peek[2];
					if (next <= data.size() - 1) {
						stack.add(fetchNext(peek));
					} else {
						stack.remove(lastIdx);
						if (stack.isEmpty()) {
							break;
						}
						stack.get(lastIdx - 1)[2]++;
					}
				} else { // more
					stack.remove(lastIdx);
					if (stack.isEmpty()) {
						break;
					}
					stack.get(lastIdx - 1)[2]++;
				}
			}
		}
		return new IntPair(count, min);
	}

	int[] fetchNext(int[] x) {
		int next = x[0] + x[2];
		return new int[] { next, x[1] + data.get(next), 1 };
	}

	int fetchLimit() {
		int t = 0;
		for (int i = data.size() - 1; i >= 0; i--) {
			t += data.get(i);
			if (t >= MAX_NOG) {
				return i;
			}
		}
		return 0;
	}

}