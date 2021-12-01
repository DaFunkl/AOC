package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;

public class Y21D01 extends Day {
	List<Integer> in = new ArrayList<>();

	@Override
	public Object part1() {
		in = getInputList().stream().map(x -> Integer.valueOf(x)).collect(Collectors.toList());
		int count = 0;
		for (int i = 1; i < in.size(); i++) {
			if (in.get(i - 1) < in.get(i)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public Object part2() {
		int count = 0;
		int prevSum = in.get(0) + in.get(1) + in.get(2);
		for (int i = 3; i < in.size(); i++) {
			int newSum = prevSum - in.get(i - 3) + in.get(i);
			if (prevSum < newSum) {
				count++;
			}
			prevSum = newSum;
		}
		return count;
	}

}
