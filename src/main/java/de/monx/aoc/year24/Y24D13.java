package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D13 extends Day {
	List<long[][]> in = init();

	@Override
	public Object part1() {
		long ret = 0;
		for (int i = 0; i < in.size(); i++) {
			long cost = cost(in.get(i), 123l);
			ret += cost;
		}
		return ret;
	}

	long adder = 10000000000000l;

	@Override
	public Object part2() {
		long ret = 0;
		for (int i = 0; i < in.size(); i++) {
			in.get(i)[2][0] += adder;
			in.get(i)[2][1] += adder;
			long cost = cost(in.get(i), adder);
			ret += cost;
		}
		return ret;
	}

	long cost(long[][] m, long limit) {
		long au = (m[1][0] * m[2][1] - m[1][1] * m[2][0]);
		long ad = (m[1][0] * m[0][1] - m[1][1] * m[0][0]);
		long a = au / ad;
		long bu = (m[0][0] * m[2][1] - m[0][1] * m[2][0]);
		long bd = (m[0][0] * m[1][1] - m[0][1] * m[1][0]);
		long b = bu / bd;

		if (bu % bd == 0 && au % ad == 0 && 0 <= b && b <= limit && 0 <= a && a <= limit) {
			return 3 * a + b;
		}
		return 0l;
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
