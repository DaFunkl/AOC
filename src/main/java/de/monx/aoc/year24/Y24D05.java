package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y24D05 extends Day {

	List<Integer> empty = new ArrayList<>();
	List<String> in = getInputList();
	Map<Integer, List<Integer>> pa = new HashMap<>();
	List<int[]> ups = new ArrayList<>();

	int[] rets = { 0, 0 };

	@Override
	public Object part1() {
		init();
		for (var up : ups) {
			var fo = fixOrder(up);
			rets[fo[1]] += fo[0];
		}
		return rets[0];
	}

	@Override
	public Object part2() {
		return rets[1];
	}

	int[] fixOrder(int[] arr) {
		int r1 = 0;
		for (int i = 0; i < arr.length; i++) {
			for (var x : pa.getOrDefault(arr[i], empty)) {
				for (int j = 0; j < i; j++) {
					if (arr[j] == x) {
						r1 = 1;
						arr[j] = arr[i];
						arr[i] = x;
						i = j - 1;
						break;
					}
				}
			}
		}
		return new int[] { arr[arr.length / 2], r1 };
	}

	void init() {
		for (var str : in) {
			if (str.contains(",")) {
				ups.add(Arrays.stream(str.split(",")).mapToInt(Integer::valueOf).toArray());
			} else if (str.contains("|")) {
				int[] arr = Arrays.stream(str.split("\\|")).mapToInt(Integer::valueOf).toArray();
				pa.putIfAbsent(arr[0], new ArrayList<>());
				pa.get(arr[0]).add(arr[1]);
			}
		}
	}
}
