package de.monx.aoc.year22;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y22D16 extends Day {
	List<String> in = getInputList();
	int[][] matrix = init();
	static final int _EPOS = 4;
	static final int _ETIM = 2;
	static int _IPOS = 2;
	static final int _ITIM = 1;
	static final int _PRES = 0;

	String hash(int[] arr, int start) {
		StringBuilder sb = new StringBuilder();
		sb.append(arr[start]);
		for (int i = start + 1; i < arr.length; i++) {
			sb.append("|").append(arr[i]);
		}
		return sb.toString();
	}

	@Override
	public Object part1() {
		Map<String, int[]> weights = new HashMap<>();
		ArrayDeque<int[]> stack = new ArrayDeque<>();
		final int ofs = 3; // offset
		int[] iState = new int[matrix.length + ofs];
		iState[_ITIM] = 30;
		iState[ofs] = 1;
		stack.add(iState);
		int max = 0;
		while (!stack.isEmpty()) {
			var state = stack.pop(); // 160
			if (state[_ITIM] <= 0) {
				continue;
			}
			var key = hash(state, 2);
			if (weights.containsKey(key)) {
				var weight = weights.get(key);
				if (weight[_ITIM] >= state[_ITIM] && weight[_PRES] >= state[_PRES]) {
					continue;
				}
			}
			weights.put(key, state);
			int cp = state[_IPOS];
			if (state[cp + ofs] != 1) {
				int[] nState = Arrays.copyOf(state, state.length);
				nState[cp + ofs] = 1;
				nState[_ITIM]--;
				nState[_PRES] += nState[_ITIM] * matrix[cp][cp];
				max = Math.max(max, nState[_PRES]);
				stack.add(nState);
			}
			for (int i = 1; i < matrix.length; i++) {
				if (i == cp || matrix[cp][i] == 0 || (state[_ITIM] - matrix[cp][i] <= 0)) {
					continue;
				}
				int[] nState = Arrays.copyOf(state, state.length);
				nState[_ITIM] -= matrix[cp][i];
				nState[_IPOS] = i;
				stack.add(nState);
			}
		}
		return max;
	}

	@Override
	public Object part2() {
		_IPOS = 3;
		Map<String, int[]> weights = new HashMap<>();
		ArrayDeque<int[]> stack = new ArrayDeque<>();
		final int ofs = 5; // offset
		int[] iState = new int[matrix.length + ofs];
		iState[_ITIM] = 26;
		iState[_ETIM] = 26;
		iState[ofs] = 1;
		stack.add(iState);
		int max = 0;
		while (!stack.isEmpty()) {
			var state = stack.pop(); // 160
			if (state[_ITIM] <= 0) {
				continue;
			}
			var key = hash(state, 3);
			if (weights.containsKey(key)) {
				var weight = weights.get(key);
				if (weight[_ITIM] >= state[_ITIM] && weight[_PRES] >= state[_PRES]) {
					continue;
				}
			}
			weights.put(key, state);
			int cp = state[_IPOS];
			if (state[cp + ofs] != 1) {
				int[] nState = Arrays.copyOf(state, state.length);
				nState[cp + ofs] = 1;
				nState[_ITIM]--;
				nState[_PRES] += nState[_ITIM] * matrix[cp][cp];
				max = Math.max(max, nState[_PRES]);
				if (nState[_ITIM] < nState[_ETIM]) {
					int t1 = nState[_ITIM];
					nState[_ITIM] = nState[_ETIM];
					nState[_ETIM] = t1;
					t1 = nState[_IPOS];
					nState[_IPOS] = nState[_EPOS];
					nState[_EPOS] = t1;
				}
				stack.add(nState);
			}
			for (int i = 1; i < matrix.length; i++) {
				if (i == cp || matrix[cp][i] == 0 || (state[_ITIM] - matrix[cp][i] <= 0)) {
					continue;
				}
				int[] nState = Arrays.copyOf(state, state.length);
				nState[_ITIM] -= matrix[cp][i];
				nState[_IPOS] = i;
				if (nState[_ITIM] < nState[_ETIM]) {
					int t1 = nState[_ITIM];
					nState[_ITIM] = nState[_ETIM];
					nState[_ETIM] = t1;
					t1 = nState[_IPOS];
					nState[_IPOS] = nState[_EPOS];
					nState[_EPOS] = t1;
				}
				stack.add(nState);
			}
		}
		return max;
	}

//	Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
//	Valve BB has flow rate=13; tunnels lead to valves CC, AA
	int[][] init() {
		Map<String, Integer> idx = new HashMap<>();
		Map<Integer, Integer> vGates = new HashMap<>();
		List<Integer> zGates = new ArrayList<>();
		int[][] matrix = new int[in.size()][in.size()];

		idx.put("AA", 0);
		vGates.put(0, 0);

		for (var line : in) {
			var sar = line.split(" ");
			String key = sar[1];
			idx.putIfAbsent(key, idx.size());
			int id = idx.get(key);
			int flow = Integer.valueOf(sar[4].split("=")[1].replace(";", ""));
			matrix[id][id] = flow;
			if (flow == 0) {
				zGates.add(id);
			} else {
				vGates.put(id, vGates.size());
			}
			for (int i = 9; i < sar.length; i++) {
				var k2 = sar[i].replace(",", "");
				idx.putIfAbsent(k2, idx.size());
				int id2 = idx.get(k2);
				matrix[id][id2] = 1;
			}
		}
		boolean changed = true;
		int dis = 1;
		while (changed) {
			changed = false;
			for (var zv : zGates) {
				List<Integer> comp = new ArrayList<>();
				for (int i = 0; i < matrix.length; i++) {
					if (i == zv || matrix[zv][i] < dis) {
						continue;
					}
					comp.add(i);
				}
				for (int x = 0; x < comp.size(); x++) {
					int i = comp.get(x);
					int w1 = matrix[zv][i];
					for (int y = x + 1; y < comp.size(); y++) {
						int j = comp.get(y);
						int ww = matrix[zv][j] + w1;

						if (matrix[i][j] == 0 || ww < matrix[i][j]) {
							changed = true;
							matrix[i][j] = ww;
							matrix[j][i] = ww;
						}
					}
				}
			}
			dis++;
		}
		int vgs = vGates.size();
		int[][] ret = new int[vgs][vgs];
		for (int i = 0; i < matrix.length; i++) {
			if (!vGates.containsKey(i)) {
				continue;
			}
			int ii = vGates.get(i);
			for (int j = 0; j < matrix.length; j++) {
				if (!vGates.containsKey(j)) {
					continue;
				}
				int jj = vGates.get(j);
				ret[ii][jj] = matrix[i][j];
			}
		}
		return ret;
	}
}
