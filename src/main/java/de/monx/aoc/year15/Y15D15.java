package de.monx.aoc.year15;

import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;

public class Y15D15 extends Day {

	List<int[]> data = getInputList().parallelStream() //
			.map(x -> x.replace(",", "").split(" ")) //
			.map(x -> new int[] { //
					Integer.valueOf(x[2]), // capacity
					Integer.valueOf(x[4]), // durability
					Integer.valueOf(x[6]), // flavor
					Integer.valueOf(x[8]), // texture
					Integer.valueOf(x[10]), // calories
			}).collect(Collectors.toList());

	static final int MAX_SPOONS = 100;

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	public int solve(boolean p2) {
		int maxScore = Integer.MIN_VALUE;
		for (int i = 0; i < MAX_SPOONS; i++) {
			for (int j = 0; (i + j) <= MAX_SPOONS; j++) {
				for (int k = 0; (i + j + k) <= MAX_SPOONS; k++) {
					int h = MAX_SPOONS - i - j - k;
					int score = fetchScore(new int[] { i, j, k, h }, p2);
					if (score > maxScore) {
						maxScore = score;
					}
				}
			}
		}
		return maxScore;
	}

	int fetchScore(int[] m, boolean p2) {
		int[] arr = new int[5];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < data.size(); j++) {
				arr[i] += data.get(j)[i] * m[j];
			}
		}
		if (p2 && arr[4] != 500) {
			return 0;
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] = Math.max(arr[i], 0);
		}
		return arr[0] * arr[1] * arr[2] * arr[3];
	}

}
