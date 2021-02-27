package de.monx.aoc.year16;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;

public class Y16D3 extends Day {

	@Override
	public Object part1() {
		var input = parseData();
		int ret = 0;
		for (int[] i : input) {
			Arrays.sort(i);
			if (i[0] + i[1] > i[2]) {
				ret++;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		var input = parseData();
		int ret = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < input.size(); j += 3) {
				var nums = sorted(input.get(j)[i], input.get(j + 1)[i], input.get(j + 2)[i]);
				if (nums[0] + nums[1] > nums[2]) {
					ret++;
				}
			}
		}
		return ret;
	}

	int[] sorted(int... nums) {
		Arrays.sort(nums);
		return nums;
	}

	List<int[]> parseData() {
		return getInputList().stream().map(x -> x.replaceAll(" +", " ").trim().split(" ")) //
				.map(x -> new int[] { //
						Integer.valueOf(x[0]), //
						Integer.valueOf(x[1]), //
						Integer.valueOf(x[2]), //
				}).collect(Collectors.toList());
	}
}
