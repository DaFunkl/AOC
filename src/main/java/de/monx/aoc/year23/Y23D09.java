package de.monx.aoc.year23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D09 extends Day {

	List<int[]> in = getInputList().stream()//
			.map(x -> Arrays.stream(x.split(" ")).mapToInt(Integer::valueOf).toArray())//
			.toList();

	int p2 = 0;

	@Override
	public Object part1() {
		return in.stream().map(this::extrapolatedNext).reduce((a, b) -> a + b).get();
	}

	@Override
	public Object part2() {
		return p2;
	}

	int extrapolatedNext(int[] vals) {
		if (!Arrays.stream(vals).anyMatch(x -> x != 0)) {
			return 0;
		}
		int[] cur = vals;
		List<int[]> l = new ArrayList<>();
		while (Arrays.stream(cur).anyMatch(x -> x != 0)) {
			l.add(cur);
			int[] next = new int[cur.length - 1];
			for (int i = 0; i < next.length; i++) {
				next[i] = cur[i + 1] - cur[i];
			}
			cur = next;
		}
		int ret = 0;
		int r2 = 0;
		int[] arr;
		for (int i = l.size() - 1; i > 0; i--) {
			arr = l.get(i);
			r2 = arr[0] - r2;
			ret = arr[arr.length - 1] + ret;
		}
		arr = l.get(0);
		p2 += arr[0] - r2;
		ret = arr[arr.length - 1] + ret;
		return ret;
	}

}
