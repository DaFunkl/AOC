package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;
import lombok.Data;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y21D23 extends Day {
	List<String> in = getInputList();
	static final int[] iEnergy = { 1, 10, 100, 1000 };
	int[][] grid;
	State iState = null;

	@Override
	public Object part1() {
		init();
		print(iState);
		return solve(grid.length - 3);
//		return null;
	}

	@Override
	public Object part2() {
//		init();
//		var nis = iState.copy();
//		for (var k : nis.pos.keySet()) {
//			if (k.second == 3) {
//				iState.pos.put(k.add(0, 2), iState.pos.remove(k));
//			}
//			iState.pos.put(new IntPair(3, 3), 3);
//			iState.pos.put(new IntPair(3, 4), 3);
//			iState.pos.put(new IntPair(5, 3), 2);
//			iState.pos.put(new IntPair(5, 4), 1);
//			iState.pos.put(new IntPair(7, 3), 1);
//			iState.pos.put(new IntPair(7, 4), 0);
//			iState.pos.put(new IntPair(9, 3), 0);
//			iState.pos.put(new IntPair(9, 4), 2);
//		}
//		return solve(4);
		return null;
	}

	Object solve(int depth) {
		Map<State, Long> map = new HashMap<>();

		PriorityQueue<Pair<State, Long>> stack = new PriorityQueue<>((a, b) -> {
//			return Long.compare(a.second - a.first.reducer(), b.second - b.first.reducer());
//			return Long.compare(b.second, a.second);
			return Long.compare(a.second, b.second);
		});
		stack.add(new Pair(iState.copy(), 0l));

		long minEnergy = Integer.MAX_VALUE;
		System.out.println(minEnergy);
		while (!stack.isEmpty()) {
			var stp = stack.poll();
			var state = stp.first;
			var energy = stp.second;
			if (minEnergy <= energy || (map.containsKey(state) && map.get(state) <= energy)) {
				continue;
			}
//			print(state);
//			Util.readLine();
			map.put(state, energy);
			if (state.isFinished()) {
				minEnergy = Math.min(minEnergy, energy);
				System.out.println("minE : " + minEnergy + ", StackSize: " + stack.size());
				print(state);
				continue;
			}
			for (var nstp : fetchStates(state, energy, depth)) {
				var nst = nstp.first;
				var ne = nstp.second;
				if (minEnergy <= ne || (map.containsKey(nst) && map.get(nst) <= ne)) {
					continue;
				}
				stack.add(nstp);
			}
		}
		return minEnergy;
	}

	List<Pair<State, Long>> fetchStates(State st, long energy, int depth) {
		List<Pair<State, Long>> ret = new ArrayList<>();
		var stps = st.pos;

		for (var position : stps.keySet()) {
			var pos = position.clone();
			int idx = stps.get(pos);
			if (pos.second > 1) { // move Up + Left or Right once
				var np = pos.clone();
				boolean free = true;
				int steps = 0;
				while (np.second != 1) { // move down
					np.addi(0, -1);
					steps++;
					if (stps.containsKey(np)) {
						free = false;
						break;
					}
				}
				if (!free) {
					continue;
				}
				var lp = np.add(-1, 0); // try left
				if (!stps.containsKey(lp)) {
					var lState = st.move(pos, lp, energy, steps + 1);
					ret.add(lState);
					walkHallWay(lState.first.copy(), lState.second, depth, ret, stps, lp, idx, false, false);
				}
				var rp = np.add(1, 0); // try right
				if (!stps.containsKey(rp)) {
					var rState = st.move(pos, rp, energy, steps + 1);
					ret.add(rState);
					walkHallWay(rState.first.copy(), rState.second, depth, ret, stps, rp, idx, true, false);
				}
			} else {
				walkHallWay(st, energy, depth, ret, stps, pos, idx, true, true);
				walkHallWay(st, energy, depth, ret, stps, pos, idx, false, true);
			}
		}
		return ret;
	}

	private void walkHallWay(State st, long energy, int depth, List<Pair<State, Long>> ret, Map<IntPair, Integer> stps,
			IntPair pos, int idx, boolean right, boolean down) {
		var dp = pos.clone();
		int dSteps = 0;
		int dir = right ? 1 : -1;
		while (right ? dp.first < 11 : dp.first > 1) {
			dp.addi(dir, 0);
			dSteps++;
			if (stps.containsKey(dp)) {
				break;
			}
			if (!down && (dp.first % 2 == 0 || dp.first == 11 || dp.first == 1)) { // halt
				ret.add(st.move(pos, dp, energy, dSteps));
			} else if (down && (3 + (idx * 2)) == dp.first) { // go down if is right room
				var dpdp = dp.clone();
				boolean isAllowed = true;
				for (int i = 0; i < depth; i++) {
					dpdp.addi(0, 1);
					if (idx != stps.getOrDefault(dpdp, idx)) {
						isAllowed = false;
						break;
					}
				}
				if (isAllowed) {
					for (int i = depth; i > 0; i--) {
						if (!stps.containsKey(dp.add(0, i))) {
							ret.add(st.move(pos, dp.add(0, i), energy, dSteps + i));
							break;
						}
					}
				}
			}

		}
	}

	@Data
	class State {
		Map<IntPair, Integer> pos = new HashMap<>();

		public State(Map<IntPair, Integer> pos) {
			this.pos = pos;
		}

		State copy() {
			return new State(new HashMap<>(pos));
		}

		boolean isFinished() {
			for (var xy : pos.keySet()) {
				if (xy.second == 1 || ((pos.get(xy) * 2 + 3) != xy.first)) {
					return false;
				}
			}
			return true;
		}

		long reducer() {
			long ret = 0;
			for (var xy : pos.keySet()) {
				if (!(xy.second == 1 || ((pos.get(xy) * 2 + 3) != xy.first))) {
					ret -= iEnergy[pos.get(xy)];
				}
			}
			return ret;
		}

		Pair<State, Long> move(IntPair prev, IntPair now, long energy, int steps) {
			State ret = copy();
			int idx = ret.pos.remove(prev);
			ret.pos.put(now.clone(), idx);
			return new Pair(ret, energy + (steps * iEnergy[idx]));
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof State)) {
				return false;
			}
			return o.hashCode() == hashCode();
		}

		@Override
		public int hashCode() {
			var hc = pos.entrySet().stream().map(x -> x.getKey() + "|" + x.getValue()).toArray();
			Arrays.sort(hc);
			return Arrays.hashCode(hc);
		}
	}

	void print(State st) {
		for (int y = 0; y < grid.length; y++) {
			StringBuilder sb = new StringBuilder();
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] != 1) {
					sb.append("#");
				} else {
					IntPair ip = new IntPair(x, y);
					if (st.pos.containsKey(ip)) {
						sb.append((char) ('A' + st.pos.get(ip)));
					} else {
						sb.append(".");
					}
				}
			}
			System.out.println(sb.toString());
		}
		System.out.println();
	}

	void init() {
		grid = new int[in.size()][in.get(0).length()];
		iState = new State(new HashMap<>());
		for (int i = 0; i < in.size(); i++) {
			char[] arr = in.get(i).toCharArray();
			for (int j = 0; j < arr.length; j++) {
				if (arr[j] == '.') {
					grid[i][j] = 1;
				} else if (arr[j] != '#' && arr[j] != ' ') {
					grid[i][j] = 1;
					iState.pos.put(new IntPair(j, i), arr[j] - 'A');
				}
			}
		}
	}

}
