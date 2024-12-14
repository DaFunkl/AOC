package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D13 extends Day {

	List<long[][]> in = init();

	@Override
	public Object part1() {
		return in.stream().mapToLong(x -> cost(x, 0)).sum();
	}

	@Override
	public Object part2() {
		System.out.println(Long.MAX_VALUE);
		return in.stream().mapToLong(x -> cost(x, 10000000000000l)).sum();
	}

	long cost(long[][] m, long adder) {
		m[2][0] += adder;
		m[2][1] += adder;
		long ma = Math.min(m[2][0] / m[0][0], m[2][1] / m[0][1]);
		long[] best = { Integer.MAX_VALUE - 1, -1, -1 };
		for (long i = ma; i >= 0; i--) {
			long[] dxy = { m[2][0] - i * m[0][0], m[2][1] - i * m[0][1] };
			if (dxy[0] % m[1][0] != 0 || dxy[1] % m[1][1] != 0 || (dxy[0] / m[1][0] != dxy[1] / m[1][1])) {
				continue;
			}
			long j = dxy[0] / m[1][0];
			long score = i * 3 + j;
			if (score < best[0]) {
				best[0] = score;
				best[1] = i;
				best[2] = j;
			}
		}
		if (best[1] == -1) {
			return 0;
		}
		return best[0];
	}

	List<long[][]> init() {
		List<long[][]> ret = new ArrayList<>();
		long[][] cur = new long[3][2];
		int ct = 0;
		for (var str : getInputList()) {
			if (ct == 3) {
				ret.add(cur);
				cur = new long[3][2];
				ct = 0;
				continue;
			}
			var sar = str.replace("+", " ").replace(", ", " ").replace("=", " ").split(" ");
			if (ct < 2) {
				cur[ct] = new long[] { Long.valueOf(sar[3]), Long.valueOf(sar[5]) };
			} else {
				cur[ct] = new long[] { Long.valueOf(sar[2]), Long.valueOf(sar[4]) };
			}
			ct++;
		}
		ret.add(cur);
		return ret;
	}
}
