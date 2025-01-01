package de.monx.aoc.year24;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y24D22 extends Day {
	long[] in = getInputList().stream().mapToLong(Long::valueOf).toArray();
	Map<Long, Pair<Set<Integer>, Long>> changes = new HashMap<>();
	long p2 = 0;

	@Override
	public Object part1() {
		long ret = 0;
		for (int j = 0; j < in.length; j++) {
			long x = in[j];
			long n = nextSecret(x);
			long num = 0;
			for (int i = 0; i < 2000 - 1; i++) {
				n = nextSecret(x);
				long del = (x % 10) - (n % 10) + 10;
				num *= 100;
				num = num + del;
				if (i >= 3) {
					changes.putIfAbsent(num, new Pair<>(new HashSet<>(), 0l));
					if (!changes.get(num).first.contains(j)) {
						changes.get(num).first.add(j);
						changes.get(num).second += (n % 10);
						if (p2 < changes.get(num).second) {
							p2 = changes.get(num).second;
						}
					}
					num %= 1_000_000;
				}
				x = n;
			}
			ret += x;
		}
		return ret;
	}

	@Override
	public Object part2() {
		return p2;
	}

	long nextSecret(long num) {
		long ret = num;

		ret ^= (ret << 6);
		ret %= 16777216;

		ret ^= (ret >> 5);
		ret %= 16777216;

		ret ^= (ret << 11);
		ret %= 16777216;
		return ret;
	}
}
