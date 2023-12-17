package de.monx.aoc.year23;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import de.monx.aoc.util.Day;

public class Y23D17 extends Day {

	int[][] map = getInputList().stream().map(x -> x.chars().map(y -> y - '0').toArray()).toList()
			.toArray(new int[0][]);

	int[][] _DIRS = { //
			{ -1, 0 }, // U
			{ 0, -1 }, // L
			{ 1, 0 }, // D
			{ 0, 1 }, // R
	};

	int[] _AD = { 1, 3 };

	@Override
	public Object part1() {
		return solve(0, 4);
	}

	@Override
	public Object part2() {
		return solve(3, 11);
	}

	int solve(int min, int max) {
		Map<Integer, Integer> bw = new HashMap<>();
		PriorityQueue<int[]> st = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[4] - o2[4];
			}
		});
		st.add(new int[] { 0, 0, 0, 0, 0 });
		st.add(new int[] { 0, 0, 1, 0, 0 });
		int ret = Integer.MAX_VALUE;
		int gy = map.length - 1;
		int gx = map[0].length - 1;
		while (!st.isEmpty()) {

			var cur = st.poll();

			var k = cur[0] * 10000 + cur[1] * 10 + (cur[2] % 2);
			if (bw.containsKey(k) && bw.get(k) <= cur[4]) {
				continue;
			}
			bw.put(k, cur[4]);
			if (cur[0] == gy && cur[1] == gx) {
				ret = Math.min(ret, cur[4]);
				continue;
			}

			for (int id : _AD) {
				int ny = cur[0];
				int nx = cur[1];
				int nd = (cur[2] + id) % 4;
				int w = cur[4];
				for (int i = 1; i < max; i++) {
					ny += _DIRS[nd][0];
					nx += _DIRS[nd][1];
					if (!(nx >= 0 && ny >= 0 && nx < map[0].length && ny < map.length)) {
						break;
					}
					w += map[ny][nx];
					if (i > min) {
						st.add(new int[] { ny, nx, nd, i, w });
					}
				}
			}
		}
		return ret;
	}

}
