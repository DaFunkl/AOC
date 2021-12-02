package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D02 extends Day {
	List<String> in = new ArrayList<>();
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
		in = getInputList();
		long horizontalPosition = 0;
		long depth1 = 0;
		long depth2 = 0;
		long aim = 0;

		for (var s : in) {
			var sar = s.split(" ");
			long x = Long.valueOf(sar[1]);
			switch (sar[0]) {
			case "forward":
				horizontalPosition += x;
				depth2 = Math.max(0, depth2 + (x * aim));
				break;
			case "down":
				depth1 += x;
				aim += x;
				break;
			case "up":
				depth1 = Math.max(0, depth1 - x);
				aim -= x;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + sar[0]);
			}
		}
		sol1 = depth1 * horizontalPosition;
		sol2 = depth2 * horizontalPosition;
	}
}
