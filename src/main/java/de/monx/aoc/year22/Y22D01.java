package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D01 extends Day {
	List<Integer> elfCal = new ArrayList<>();

	@Override
	public Object part1() {
		int maxSum = 0;
		int curSum = 0;
		for (var i : getInputList()) {
			if (i.isBlank()) {
				elfCal.add(curSum);
				maxSum = Math.max(maxSum, curSum);
				curSum = 0;
			} else {
				curSum += Integer.valueOf(i);
			}
		}
		elfCal.add(curSum);
		maxSum = Math.max(maxSum, curSum);
		curSum = 0;
		return maxSum;
	}

	@Override
	public Object part2() {
		Collections.sort(elfCal);
		int ret = 0;
		ret += elfCal.get(elfCal.size() - 1);
		ret += elfCal.get(elfCal.size() - 2);
		ret += elfCal.get(elfCal.size() - 3);
		return ret;
	}

}
