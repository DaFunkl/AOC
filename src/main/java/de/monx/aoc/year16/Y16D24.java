package de.monx.aoc.year16;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y16D24 extends Day {
	Map<Integer, IntPair> poi = new HashMap<>();
	Map<Integer, Map<Integer, Integer>> nodes = new HashMap<>();
	Map<Integer, Integer> distToZero = new HashMap<>();
	int[][] grid = init();

	int ret1, ret2;

	@Override
	public Object part1() {
		solve();
		return ret1;
	}

	public void solve() {
		initNodes();
		Map<String, Integer> weights = new HashMap<>();
		Deque<Pair<int[], Integer>> stack = new ArrayDeque<>();
		ret1 = Integer.MAX_VALUE;
		ret2 = Integer.MAX_VALUE;
		int[] iState = new int[poi.size() + 1];
		iState[0] = 1;
		stack.push(new Pair<int[], Integer>(iState, 0));
		while (!stack.isEmpty()) {
			var cState = stack.pop();
			String ck = pathToStr(cState.first);
			if (weights.getOrDefault(ck, Integer.MAX_VALUE) <= cState.second) {
				continue;
			}
			weights.put(ck, cState.second);
			int[] path = cState.first;
			int prev = path[path.length - 1];
			if (isFinished(cState.first)) {
				weights.put(ck, cState.second);
				ret1 = Math.min(ret1, cState.second);
				ret2 = Math.min(ret2, cState.second + distToZero.get(prev));
				continue;
			}
			for (int i : nodes.get(prev).keySet()) {
				if (path[i] < 0) {
					continue;
				}
				int[] np = Arrays.copyOf(path, path.length);
				np[np.length - 1] = i;
				np[i] += 1;
				stack.push(new Pair<int[], Integer>(np, cState.second + nodes.get(prev).get(i)));
			}
		}
		System.out.println("p2: " + ret2);
	}

	@Override
	public Object part2() {
		return ret2;
	}

	boolean isFinished(int[] path) {
		for (int i = 0; i < path.length - 1; i++) {
			if (path[i] == 0) {
				return false;
			}
		}
		return true;
	}

	String pathToStr(int[] path) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < path.length - 1; i++) {
			sb.append(path[i] == 0 ? 0 : 1);
		}
		sb.append("|");
		sb.append(path[path.length - 1]);
		return sb.toString();
	}

	void initNodes() {

		for (int idx : poi.keySet()) {
			Map<IntPair, Integer> weights = new HashMap<>();
			Deque<Pair<IntPair, IntPair>> stack = new ArrayDeque<>();
			stack.push(new Pair<IntPair, IntPair>(poi.get(idx).clone(), new IntPair(0, 0)));
			while (!stack.isEmpty()) {
				var cState = stack.pop();
				if (weights.getOrDefault(cState.first, Integer.MAX_VALUE) <= cState.second.first) {
					continue;
				}
				weights.put(cState.first.clone(), cState.second.first);
				int gvc = gridVal(cState.first);
				if (gvc >= 0 && gvc != idx) {
					if (cState.second.second == 0) {
						int a = idx;
						int b = gridVal(cState.first);
						int l = cState.second.first;
						nodes.computeIfAbsent(a, k -> new HashMap<>()).put(b, l);
						nodes.computeIfAbsent(b, k -> new HashMap<>()).put(a, l);
						if (idx != 0) {
							continue;
						} else {
							cState.second.addi(0, 1);
						}
					}
					if (idx == 0) {
						distToZero.put(gvc, cState.second.first);
					}
				}
				for (var dir : Util._DIRS4) {
					var nDir = dir.add(cState.first);
					int gv = gridVal(nDir);
					if (gv >= -1 && gv != idx
							&& weights.getOrDefault(nDir, Integer.MAX_VALUE) > (cState.second.first + 1)) {
						stack.push(new Pair<IntPair, IntPair>(nDir, cState.second.add(1, 0)));
					}
				}
			}
		}
	}

	int gridVal(IntPair ip) {
		if (ip.first < 0 || ip.second < 0 || ip.first >= grid.length || ip.second >= grid[0].length) {
			return -2;
		}
		return grid[ip.first][ip.second];
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
