package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D02 extends Day {
	List<String> in = new ArrayList<>();

	@Override
	public Object part1() {
		in = getInputList();
		long horizontalPosition = 0;
		long depth = 0;

		for (var s : in) {
			var sar = s.split(" ");
			long x = Long.valueOf(sar[1]);
			switch (sar[0]) {
			case "forward":
				horizontalPosition += x;
				break;
			case "down":
				depth += x;
				break;
			case "up":
				depth = Math.max(0, depth - x);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + sar[0]);
			}
		}
		return depth * horizontalPosition;
	}

	@Override
	public Object part2() {
		long horizontalPosition = 0;
		long aim = 0;
		long depth = 0;

		for (var s : in) {
			var sar = s.split(" ");
			long x = Long.valueOf(sar[1]);
			switch (sar[0]) {
			case "forward":
				horizontalPosition += x;
				depth = Math.max(0, depth + (x * aim));
				break;
			case "down":
				aim += x;
				break;
			case "up":
				aim -= x;
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + sar[0]);
			}
		}
		return depth * horizontalPosition;
	}

}
