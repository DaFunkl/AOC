package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y16D15 extends Day {

	@Override
	public Object part1() {
		return solve(parse());
	}

	@Override
	public Object part2() {
		var data = parse();
		data.add(new int[] { 11, 0, 0 });
		return solve(data);
	}

	Object solve(List<int[]> data) {
		int[] goals = goals(data);
		int adder = 1;
		long ticks = 0;
		int idx = 0;
		while (idx < goals.length) {
			if (finished(goals, data)) {
				return ticks;
			}
			for (int i = 0; i < data.size(); i++) {
				data.get(i)[2] = (int) ((data.get(i)[2] + adder) % data.get(i)[0]);
			}
			ticks += adder;
			if (data.get(idx)[2] == goals[idx]) {
				adder *= data.get(idx)[0];
				idx++;
			}
		}
		return ticks;
	}

	boolean finished(int[] goal, List<int[]> data) {
		for (int i = 0; i < goal.length; i++) {
			if (goal[i] != data.get(i)[2]) {
				return false;
			}
		}
		return true;
	}

	int[] goals(List<int[]> data) {
		int[] ret = new int[data.size()];
		for (int i = 0; i < ret.length; i++) {
			var t = data.get(i);
			ret[i] = (t[0] + t[0] - (i + 1)) % t[0];
		}
		return ret;
	}

	List<int[]> parse() {
		List<int[]> ret = new ArrayList<>();
		for (var s : getInputList()) {
			var spl = s.replace(".", "").replace(",", "").replace("=", " ").split(" ");
			ret.add(new int[] { //
					Integer.valueOf(spl[3]), //
					Integer.valueOf(spl[7]), //
					Integer.valueOf(spl[12])//

			});
		}
		return ret;
	}
}
