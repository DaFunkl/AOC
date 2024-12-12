package de.monx.aoc.year24;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.LongPair;

public class Y24D11 extends Day {

	Map<LongPair, Long> memo = new HashMap<>();
	String[] in = getInputString().split(" ");

	@Override
	public Object part1() {
		return solve(25);
	}

	@Override
	public Object part2() {
		return solve(75);
	}

	long solve(long startVal) {
		long ret = 0;
		for (var s : in) {
			ret += fetchAmt(new LongPair(Long.valueOf(s), startVal));
		}
		return ret;
	}

	long max = 0;
	long fetchAmt(LongPair lp) {
		if (memo.containsKey(lp)) {
			return memo.get(lp);
		}
		if (lp.second == 0l) {
			return 1l;
		} else {
			if (lp.first == 0l) {
				memo.put(lp, fetchAmt(lp.add(1, -1)));
				return memo.get(lp);
			} else if (("" + lp.first).length() % 2 == 0) {
				if(lp.first > max) {
					max = lp.first;
					System.out.println(max);
				}
				String s = "" + lp.first;
				long l = Long.valueOf(s.substring(0, s.length() / 2));
				long r = Long.valueOf(s.substring(s.length() / 2));
				var lnlp = lp.add(0, -1);
				lnlp.first = l;
				var rnlp = lp.add(0, -1);
				rnlp.first = r;
				memo.put(lp, fetchAmt(lnlp) + fetchAmt(rnlp));
				return memo.get(lp);
			} else {
				var nlp = lp.add(0, -1);
				nlp.first *= 2024;
				memo.put(lp, fetchAmt(nlp));
				return memo.get(lp);
			}
		}
	}
}
