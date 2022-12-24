package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y22D24 extends Day {
	List<String> in = getInputList();
	IP3 start = new IP3(0, 1);
	IP3 end = new IP3(in.size() - 1, in.get(0).length() - 2);
	Pair<Map<IP3, List<IP3>>, Integer> p1 = null;
	int[][] mm = { //
			{ 1, in.size() - 2 }, //
			{ 1, in.get(0).length() - 2 }, //
	};

	static final IP3[] _MOVES = { //
			new IP3(-1, 00, 0), // up
			new IP3(01, 00, 0), // down
			new IP3(00, -1, 0), // left
			new IP3(00, 01, 0), // right
			new IP3(00, 00, 0), // wait
	};

	@Override
	public Object part1() {
		Map<IP3, List<IP3>> state = initBlizzards();
		p1 = solve(state, start, end);
		return p1.second;
	}

	@Override
	public Object part2() {
		var answer = p1;
		int ret = answer.second;
		answer = solve(answer.first, end, start);
		ret += answer.second;
		answer = solve(answer.first, start, end);
		ret += answer.second;
		return ret;
	}

	Pair<Map<IP3, List<IP3>>, Integer> solve(Map<IP3, List<IP3>> initialState, IP3 start, IP3 end) {
		Map<IP3, List<IP3>> state = new HashMap<>();
		for (var k : initialState.keySet()) {
			state.put(k, new ArrayList<>());
			state.get(k).addAll(initialState.get(k));
		}

		Set<IP3> positions = new HashSet<>();
		positions.add(start.clone());
		int minSteps = 1;
		while (minSteps > 0 && positions.size() > 0) {
			// tick blizzards
			Map<IP3, List<IP3>> newState = new HashMap<>();
			for (var k : state.keySet()) {
				for (var b : state.get(k)) {
					IP3 np = b.add(_MOVES[b.p3]);
					np.p1 = np.p1 > mm[0][1] ? mm[0][0] : np.p1 < mm[0][0] ? mm[0][1] : np.p1;
					np.p2 = np.p2 > mm[1][1] ? mm[1][0] : np.p2 < mm[1][0] ? mm[1][1] : np.p2;
					newState.computeIfAbsent(np, blah -> new ArrayList<>()).add(np);
				}
			}
			state = newState;

//			// Calculate all new possible Positions
			Set<IP3> newPos = new HashSet<>();
			for (var pos : positions) {
				for (var dir : _MOVES) {
					var np = pos.add(dir);
					// did it finish?
					if (np.equals(end)) {
						return new Pair<Map<IP3, List<IP3>>, Integer>(state, minSteps);
					}
					// skip out of bounce
					if (np.p1 < mm[0][0] || np.p1 > mm[0][1] || np.p2 < mm[1][0] || np.p2 > mm[1][1]) {
						continue;
					}
					if (!state.containsKey(np)) {
						newPos.add(np);
					}
					newPos.add(start);
				}
			}
			positions = newPos;
			minSteps++;
		}
		return new Pair<Map<IP3, List<IP3>>, Integer>(state, minSteps);
	}

	private Map<IP3, List<IP3>> initBlizzards() {
		Map<IP3, List<IP3>> ret = new HashMap<>();
		for (int i = 1; i < in.size() - 1; i++) {
			for (int j = 1; j < in.get(i).length() - 1; j++) {
				if (in.get(i).charAt(j) != '.') {
					IP3 blizzard = new IP3(i, j);
					blizzard.p3 = switch (in.get(i).charAt(j)) {
					case '^' -> 0; // up
					case 'v' -> 1; // down
					case '<' -> 2; // left
					case '>' -> 3; // right
					default -> -1;
					};
					ret.computeIfAbsent(blizzard, k -> new ArrayList<>()).add(blizzard);
				}
			}
		}
		return ret;
	}

	public static class IP3 {
		int p1 = 0;
		int p2 = 0;
		int p3 = 0;

		public IP3() {
		}

		public IP3(int p1, int p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		public IP3(int p1, int p2, int p3) {
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
		}

		public IP3 add(IP3 o) {
			return new IP3(p1 + o.p1, p2 + o.p2, p3 + o.p3);
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(new int[] { p1, p2 });
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof IP3)) {
				return false;
			}
			IP3 o = (IP3) obj;
			return p1 == o.p1 && p2 == o.p2;
		}

		@Override
		public IP3 clone() {
			return new IP3(p1, p2, p3);
		}
	}
}
