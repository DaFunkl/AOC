package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y21D06 extends Day {
	List<Integer> in = Arrays.stream(getInputString().split(",")).map(Integer::valueOf).toList();

	@Override
	public Object part1() {
		return solve2(80);
	}

	@Override
	public Object part2() {
		return solve2(256);
	}

	// after getting hint about array approach
	long solve2(int days) {
		long[] arr = new long[9];
		for (int i : in) {
			arr[i]++;
		}
		for (int d = 0; d < days; d++) {
			arr[(7 + d) % arr.length] += arr[d % arr.length];
		}
		long ret = 0;
		for (long l : arr) {
			ret += l;
		}
		return ret;
	}

	// own approach:
	Map<Integer, Long> vals = new HashMap<>();

	long solve(int days) {
		days++;
		vals = new HashMap<>();
		List<Integer> todos = new ArrayList<>();
		long ret = 0;
		for (int i : in) {
			todos.add(1 - (6 - i));
			ret++;
		}
		for (int i : todos) {
			ret += fetchValues(i, days);
		}
		return ret;
	}

	long fetchValues(int x, int days) {
		if (x > days) {
			return 0;
		}
		if (vals.containsKey(x)) {
			return vals.get(x);
		}
		long ret = (days - x) / 7;
		for (int i = x + 7; i < days; i += 7) {
			ret += fetchValues(i + 2, days);
		}
		vals.put(x, ret);
		return ret;
	}
}
