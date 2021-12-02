package de.monx.aoc.year21;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D02 extends Day {
	long sol1 = 0;
	long sol2 = 0;

	@Override
	public Object part1() {
		solve();
		return sol1;
	}

	@Override
	public Object part2() {
		return sol2;
	}

	void solve() {
		List<String> in = getInputList();
		long horizontalPosition = 0;
		long depth = 0;
		long aim = 0;

		for (var s : in) {
			var sar = s.split(" ");
			long x = Long.valueOf(sar[1]);
			switch (sar[0]) { //@formatter:off
			case "forward":
				horizontalPosition += x;
				depth = depth + (x * aim);
				break;
				
			case "down":	aim += x; break;
			case "up":		aim -= x; break;
			default: throw new IllegalArgumentException("Unexpected value: " + sar[0]);
			}
		} //@formatter:on
		sol1 = aim * horizontalPosition;
		sol2 = depth * horizontalPosition;
	}
}
