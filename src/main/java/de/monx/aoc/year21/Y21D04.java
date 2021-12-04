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

	@Override
	public Object part1() {
		init();
		Set<Integer> drawn = new HashSet<>();
		int winner = -1;
		int card = 0;
		for (int d : drawing) {
			card = d;
			drawn.add(d);
			if (drawn.size() > 4) {
				var winners = findWinner(drawn, d);
				if (winners.size() > 0) {
					winner = winners.get(0);
				}
			}
			if (winner >= 0) {
				break;
			}
		}
		return calcWinner(winner, drawn) * card;
	}

	List<Integer> findWinner(Set<Integer> drawn, int card) {
		List<Integer> ret = new ArrayList<>();
		for (var ref : numRef.get(card)) {
			var board = boards.get(ref[0]);
			boolean a = true;
			boolean b = true;
			for (int i = 0; i < 5; i++) {
				if (a && !drawn.contains(board[ref[1]][i])) {
					a = false;
				}
				if (b && !drawn.contains(board[i][ref[2]])) {
					b = false;
				}
				if (!a && !b) {
					break;
				}
			}
			if (a || b) {
				ret.add(ref[0]);
			}
		}
		return ret;
	}

	int calcWinner(int winner, Set<Integer> drawn) {
		int ret = 0;
		var board = boards.get(winner);
		for (var arr : board) {
			for (var x : arr) {
				if (!drawn.contains(x)) {
					ret += x;
				}
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		Set<Integer> drawn = new HashSet<>();
		Set<Integer> winners = new HashSet<>();
		int card = 0;
		int score = 0;
		for (int d : drawing) {
			card = d;
			drawn.add(d);
			if (drawn.size() > 4) {
				var drawWinners = findWinner(drawn, d);
				for (var winner : drawWinners) {
					if (winner >= 0) {
						if (winners.contains(winner)) {
							continue;
						}
						winners.add(winner);
						score = calcWinner(winner, drawn) * card;
						if (!(winners.size() < boards.size())) {
							break;
						}
					}
				}
			}
		}
		return score;
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
