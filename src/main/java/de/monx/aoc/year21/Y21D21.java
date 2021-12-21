package de.monx.aoc.year21;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y21D21 extends Day {

	// players[0] -> player 1 | players[1] -> player 2
	// players[x][0] -> location
	// players[x][1] -> score
	// players[x][2] -> rolls
	int[][] ps = new int[2][3];

	@Override
	public Object part1() {
		init();
		int cd = 1;
		boolean turn = true;
		while (ps[0][1] < 1000 && ps[1][1] < 1000) {
			int idx = turn ? 0 : 1;
			turn = !turn;
			int rolls = 0;
			if (cd < 998) {
				rolls = 3 * cd + 3;
				cd += 3;
			} else {
				for (int i = 0; i < 3; i++) {
					rolls += cd;
					cd++;
					if (cd == 1001) {
						cd = 1;
					}
				}
			}

			ps[idx][0] = (ps[idx][0] + rolls) % 10;
			ps[idx][1] += ps[idx][0] + 1;
			ps[idx][2] += 3;

		}
		return Math.min(ps[0][1], ps[1][1]) * (ps[0][2] + ps[1][2]);
	}

	public static final int[][] diracDice = { //
			{ 3, 1 }, // 1,1,1
			{ 4, 3 }, // 2,1,1
			{ 5, 6 }, // 3,1,1 | 1,2,2
			{ 6, 7 }, // 1,2,3 | 2,2,2
			{ 7, 6 }, // 3,2,2 | 1,3,3
			{ 8, 3 }, // 2,3,3
			{ 9, 1 }, // 3,3,3
	};

	@Override
	public Object part2() {
		init();
		long[] wins = { 0, 0 };
		Deque<GS> stack = new ArrayDeque<>();
		stack.push(new GS(ps, true, 1));
		long maxStackAmt = 0;
		while (!stack.isEmpty()) {
			var newStates = stack.pop().play();
			wins[0] += newStates.second[0];
			wins[1] += newStates.second[1];
			for (var ns : newStates.first) {
				stack.push(ns);
			}
			maxStackAmt = Math.max(maxStackAmt, stack.size());
		}
		return Math.max(wins[0], wins[1]);
	}

	static class GS {
		long universes = 1;
		int[][] ps = new int[2][2];
		boolean turn = true;

		public GS(int[][] nps, boolean turn, long universes) {
			ps = new int[][] { { nps[0][0], nps[0][1] }, { nps[1][0], nps[1][1] } };
			this.turn = turn;
			this.universes = universes;
		}

		Pair<List<GS>, long[]> play() {
			int idx = turn ? 0 : 1;
			long[] wins = { 0, 0 };
			List<GS> newGS = new ArrayList<>();
			for (var dd : diracDice) {
				GS ngs = new GS(ps, !turn, universes * dd[1]);
				ngs.ps[idx][0] = (ngs.ps[idx][0] + dd[0]) % 10;
				ngs.ps[idx][1] += ngs.ps[idx][0] + 1;
				if (ngs.ps[idx][1] >= 21) {
					wins[idx] += universes * dd[1];
				} else {
					ngs.turn = !turn;
					newGS.add(ngs);
				}
			}
			return new Pair<List<GS>, long[]>(newGS, wins);
		}
	}

	void init() {
		ps = new int[2][3];
		var in = getInputList();
		String str = in.get(0);
		ps[0][0] = Character.getNumericValue(str.charAt(str.length() - 1)) - 1;
		str = in.get(1);
		ps[1][0] = Character.getNumericValue(str.charAt(str.length() - 1)) - 1;
	}
}
