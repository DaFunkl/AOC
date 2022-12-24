package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y22D23 extends Day {
	int[][] mm = { //
			{ Integer.MAX_VALUE, Integer.MIN_VALUE }, //
			{ Integer.MAX_VALUE, Integer.MIN_VALUE } //
	};

	List<String> in = getInputList();
	Set<IntPair> initialState = init();

	static final IntPair[] _NSWE = { //
			new IntPair(-1, 00), // N
			new IntPair(01, 00), // S
			new IntPair(00, -1), // W
			new IntPair(00, 01), // E
	};

//	if (!(nn[p[0]][p[1]] || nn[p[2]][p[3]] || nn[p[4]][p[5]])) { // North
//		pointer.addi(_NSWE[p[6]]);
	final static int[][] _PRIO = { //
			{ 0, 0, 0, 1, 0, 2, 0 }, // N
			{ 2, 0, 2, 1, 2, 2, 1 }, // S
			{ 0, 0, 1, 0, 2, 0, 2 }, // W
			{ 0, 2, 1, 2, 2, 2, 3 } // E
	};

	@Override
	public Object part1() {
		return solve(false)[0];
	}

	@Override
	public Object part2() {
		return solve(true)[1];
	}

	int[] solve(boolean p2) {
		Set<IntPair> state = new HashSet<>();
		state.addAll(initialState);
		int steps = 0;
		for (; steps < 10 || p2; steps++) {
			Set<IntPair> newState = new HashSet<>();
			Map<IntPair, List<IntPair>> phase = new HashMap<>();

			// Phase 1: determine next Positions
			for (var ip : state) {
				boolean[][] nn = new boolean[3][3];
				boolean canMove = false;
				IntPair pointer = ip.clone();
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i == 0 && j == 0) {
							continue;
						}
						var np = pointer.add(i, j);
						if (state.contains(np)) {
							nn[i + 1][j + 1] = true;
							canMove = true;
						}
					}
				}
				if (canMove) {
					canMove = false;
					for (int i = 0; i < 4; i++) {
						int[] p = _PRIO[(i + steps) % 4];
						if (!(nn[p[0]][p[1]] || nn[p[2]][p[3]] || nn[p[4]][p[5]])) { // North
							pointer.addi(_NSWE[p[6]]);
							phase.putIfAbsent(pointer, new ArrayList<>());
							phase.get(pointer).add(ip);
							canMove = true;
							break;
						}
					}
				}
				if (!canMove) {
					phase.putIfAbsent(ip, new ArrayList<>());
					phase.get(ip).add(ip);
				}
			}

			// Phase 2: write Positions, which are viable
			boolean moved = false;
			for (var k : phase.keySet()) {
				if (phase.get(k).size() > 1) {
					for (var o : phase.get(k)) {
						newState.add(o);
					}
				} else {
					if (!k.equals(phase.get(k).get(0))) {
						moved = true;
					}
					newState.add(k);
				}
			}
			if (!moved) {
				break;
			}
			state = newState;
		}
		mm = new int[][] { //
				{ Integer.MAX_VALUE, Integer.MIN_VALUE }, //
				{ Integer.MAX_VALUE, Integer.MIN_VALUE } //
		};
		for (var k : state) {
			mm[0][0] = Math.min(mm[0][0], k.first);
			mm[0][1] = Math.max(mm[0][1], k.first);
			mm[1][0] = Math.min(mm[1][0], k.second);
			mm[1][1] = Math.max(mm[1][1], k.second);
		}
		int w = Math.abs(mm[1][1] - mm[1][0]) + 1;
		int h = Math.abs(mm[0][1] - mm[0][0]) + 1;
		return new int[] { w * h - state.size(), steps + 1 };
	}

	Set<IntPair> init() {
		Set<IntPair> ret = new HashSet<>();
		for (int i = 0; i < in.size(); i++) {
			for (int j = 0; j < in.get(i).length(); j++) {
				if (in.get(i).charAt(j) == '#') {
					ret.add(new IntPair(i, j));
					mm[0][0] = Math.min(mm[0][0], i);
					mm[0][1] = Math.max(mm[0][1], i);
					mm[1][0] = Math.min(mm[1][0], j);
					mm[1][1] = Math.max(mm[1][1], j);
				}
			}
		}
		return ret;
	}
}
