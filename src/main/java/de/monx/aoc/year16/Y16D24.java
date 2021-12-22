package de.monx.aoc.year16;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y16D24 extends Day {
	int[][] grid = init();
	Map<Integer, IntPair> poi = new HashMap<>();
	Map<Integer, int[]> distance = new HashMap<>();

	@Override
	public Object part1() {
		return null;
	}

	@Override
	public Object part2() {

		return null;
	}

	void connect() {
		for (int i = 0; i < poi.size(); i++) {
			distance.putIfAbsent(i, new int[poi.size()]);
		}
		for (int i = 0; i < poi.size(); i++) {
			for (int j = i + 1; j < poi.size(); j++) {
				var minDist = fetchDistance(i, j);
				distance.get(i)[j] = minDist;
				distance.get(j)[i] = minDist;
			}
		}
	}

	int fetchDistance(int a, int b) {
		Map<int[], Integer> weights = new HashMap<>();
		Deque<int[]> stack = new ArrayDeque<>();
		stack.push(poi.get(a).arr());
		while (!stack.isEmpty()) {
			var p = stack.pop();
			for (var d : Util._DIRS4) {
				int[] nd = { p[0] + d[0], p[1] + d[1] };
				if (grid[nd[0]][nd[1]] < 0) {
					continue;
				}
			}
		}
		return 0;
	}

	int[][] init() {
		List<String> in = getInputList();
		int[][] ret = new int[in.size()][in.get(0).length()];
		for (int y = 0; y < ret.length; y++) {
			var car = in.get(y).toCharArray();
			for (int x = 0; x < ret[0].length; x++) {
				ret[y][x] = switch (car[x]) {
				case '.' -> -1;
				case '#' -> -2;
				default -> Character.getNumericValue(car[x]);
				};
				if (ret[y][x] >= 0) {
					poi.put(ret[y][x], new IntPair(y, x));
				}
			}
		}
		return ret;
	}
}
