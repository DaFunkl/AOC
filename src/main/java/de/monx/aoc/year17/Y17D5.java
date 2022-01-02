package de.monx.aoc.year17;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y17D5 extends Day {
	List<Integer> in = getInputList().stream().map(Integer::valueOf).toList();

	@Override
	public Object part1() {
		var in = new ArrayList<>(this.in);
		int ip = 0;
		int stepper = 0;
		while (0 <= ip && ip < in.size()) {
			in.set(ip, in.get(ip) + 1);
			ip += in.get(ip) - 1;
			stepper++;
		}
		return stepper;
	}

	@Override
	public Object part2() {
		var in = new ArrayList<>(this.in);
		int ip = 0;
		int stepper = 0;
		while (0 <= ip && ip < in.size()) {
			int offset = in.get(ip);
			int adder = offset > 2 ? -1 : 1;
			in.set(ip, offset + adder);
			ip += in.get(ip) - adder;
			stepper++;
		}
		return stepper;
	}
}