package de.monx.aoc.year17;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y17D11 extends Day {
	String[] in = getInputString().split(",");
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
		IntPair cp = new IntPair(0, 0);
		int maxSecond = 0;
		for (var s : in) {
			cp.addi(dir(s));
			maxSecond = Math.max(maxSecond, Math.abs(cp.second));
		}
		ret2 = maxSecond;
		ret1 = cp.second;
	}

	IntPair dir(String str) {
		return switch (str) {
		case "n" -> new IntPair(-2, 0);
		case "s" -> new IntPair(2, 0);
		case "nw" -> new IntPair(1, -1);
		case "sw" -> new IntPair(-1, -1);
		case "ne" -> new IntPair(1, 1);
		case "se" -> new IntPair(-1, 1);
		default -> null;
		};
	}
}
