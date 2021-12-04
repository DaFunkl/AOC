package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y21D04 extends Day {
	List<Integer> drawing = new ArrayList<>();
	List<int[][]> boards = new ArrayList<>();
	Map<Integer, List<int[]>> numRef = new HashMap<>();

	int sol1 = -1;
	int sol2 = -1;

	@Override
	public Object part1() {
		solve();
		return sol1;
	}

	@Override
	public Object part2() {
		return sol2;
	}

	void solve() {
		init();
		Set<Integer> drawn = new HashSet<>();
		Set<Integer> winners = new HashSet<>();
		int card = 0, score = 0;
		for (int d : drawing) { // @formatter:off
			card = d;
			drawn.add(d);
			if (drawn.size() < 5) continue;
			for (var winner : findWinner(drawn, d)) {
				if (winners.contains(winner)) continue;
				winners.add(winner);
				score = calcWinner(winner, drawn) * card;
				if (sol1 < 0) sol1 = score; 				// Part1, first Score ever seen
				if (!(winners.size() < boards.size())) {	// Part2, last Board winning
					sol2 = score; 
					return;
				}
			} // @formatter:on
		}
	}

	List<Integer> findWinner(Set<Integer> drawn, int card) {
		List<Integer> ret = new ArrayList<>();
		for (var ref : numRef.get(card)) {
			var board = boards.get(ref[0]);
			boolean v = true, h = true;
			for (int i = 0; i < 5; i++) { // @formatter:off
				if (v && !drawn.contains(board[ref[1]][i])) v = false;
				if (h && !drawn.contains(board[i][ref[2]])) h = false;
				if (!v && !h) break;
			} 
			if (v || h) ret.add(ref[0]);
		} // @formatter:on
		return ret;
	}

	int calcWinner(int winner, Set<Integer> drawn) {
		var board = boards.get(winner);
		int ret = 0;// @formatter:off 	// Stream performce worse --> Streaming over Arrays ain't good
		for (var arr : board) for (var x : arr) if (!drawn.contains(x)) ret += x;
		return ret;// @formatter:on
	}

	void init() {
		var in = getInputList();
		drawing = Arrays.stream(in.get(0).split(",")).map(Integer::valueOf).toList();
		for (int i = 2; i < in.size(); i += 6) {
			int[][] board = new int[5][5];
			for (int j = 0; j < 5; j++) {
				var sar = in.get(i + j).trim().replace("  ", " ").split(" ");
				for (int k = 0; k < 5; k++) {
					board[j][k] = Integer.valueOf(sar[k]);
					if (!numRef.containsKey(board[j][k])) {
						numRef.put(board[j][k], new ArrayList<>());
					}
					numRef.get(board[j][k]).add(new int[] { boards.size(), j, k });
				}
			}
			boards.add(board);
		}
	}
}
