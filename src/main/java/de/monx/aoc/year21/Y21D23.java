package de.monx.aoc.year21;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y21D23 extends Day {
	List<String> in = getInputList();
	int[] iEnergy = { 1, 10, 100, 1000 };
	int[] aHallWay = { 3, 5, 7, 9 };
	int[][] hallWayPos = { //
			{ 1, 1 }, //
			{ 2, 1 }, //
			{ 4, 1 }, //
			{ 6, 1 }, //
			{ 8, 1 }, //
			{ 10, 1 }, //
			{ 11, 1 },//
	};
	int[][] grid;
	State iState = null;
	State goal = null;

	@Override
	public Object part1() {
		return solveP1();
	}

	@Override
	public Object part2() {
		return null;
	}

	Object solveP1() {
		init();
		Map<State, Long> map = new HashMap<>();
		Deque<Pair<State, Long>> stack = new ArrayDeque<>();
		stack.push(new Pair(iState.copy(), 0l));
		long minEnergy = Long.MAX_VALUE;
		while (!stack.isEmpty()) {
			var stp = stack.pop();
			var state = stp.first;
			var energy = stp.second;
			if (minEnergy <= energy || (map.containsKey(state) && map.get(state) <= energy)) {
				continue;
			}
			map.put(state, energy);
			if (state.isFinished()) {
				minEnergy = Math.min(minEnergy, energy);
				continue;
			}
			for (var nstp : fetchStates(state, energy)) {
				var nst = nstp.first;
				var ne = nstp.second;
				if (minEnergy <= ne || (map.containsKey(nst) && map.get(nst) <= ne)) {
					continue;
				}
				stack.push(nstp);
			}
		}
		return minEnergy;
	}

	List<Pair<State, Long>> fetchStates(State st, long energy) {
		List<Pair<State, Long>> ret = new ArrayList<>();

		for (int c = 0; c < st.pos.length; c++) {
			for (int cc = 0; cc < st.pos[c].length; cc++) {
				int[] pos = st.pos[c][cc];
				if (pos[1] > 1) { // try move up
					
				} else { // try move left / right / down

				}
			}
		}

		return ret;
	}

	static class State {
		int[][][] pos = null;

		public State(int[][][] pos) {
			this.pos = pos;
		}

		State copy() {
			int[][][] np = new int[pos.length][pos[0].length][pos[0][0].length];
			for (int i = 0; i < np.length; i++) {
				for (int j = 0; j < np[0].length; j++) {
					for (int k = 0; k < np[0][0].length; k++) {
						np[i][j][k] = pos[i][j][k];
					}
				}
			}
			return new State(np);
		}

		boolean isFinished() {
			for (int c = 0; c < pos.length; c++) {
				for (int cc = 0; cc < pos[c].length; cc++) {
					int[] cp = pos[c][cc];
					if (cp[0] != (3 + (c * 2)) || !(cp[1] == 2 || cp[1] == 3)) {
						return false;
					}
				}
			}
			return true;
		}
	}

	void init() {
		grid = new int[in.get(0).length()][in.size()];
		int[] cc = new int[4];
		iState = new State(new int[4][2][2]);
		for (int i = 0; i < in.size(); i++) {
			char[] arr = in.get(i).toCharArray();
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == '.') {
					grid[j][i] = 1;
				} else if (arr[j] != '#' && arr[j] != ' ') {
					int idx = 'A' - arr[j];
					iState.pos[idx][cc[idx]++] = new int[] { j, i };
				}
			}
		}
	}
}
