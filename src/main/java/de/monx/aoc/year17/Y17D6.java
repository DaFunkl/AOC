package de.monx.aoc.year17;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y17D6 extends Day {
	int[] in = init();
	int ret1, ret2;

	@Override
	public Object part1() {
		solve();
		return ret1;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	void solve() {
		Map<String, Integer> seen = new HashMap<>();
		ret1 = 0;
		int[] state = Arrays.copyOf(in, in.length);
		String sk = Arrays.toString(state);
		while (!seen.containsKey(sk)) {
			seen.put(sk, ret1);
			ret1++;
			int max = 0;
			int idx = 0;
			for (int i = 0; i < state.length; i++) {
				if (max < state[i]) {
					max = state[i];
					idx = i;
				}
			}
			state[idx] = 0;
			for (int i = 1; i <= max; i++) {
				state[(i + idx) % state.length]++;
			}
			sk = Arrays.toString(state);
		}
		ret2 = ret1 - seen.get(sk);
	}

	int[] init() {
		var in = getInputString();
		var sar = in.split("\t");
		int[] ret = new int[sar.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = Integer.valueOf(sar[i]);
		}
		return ret;
	}
}
