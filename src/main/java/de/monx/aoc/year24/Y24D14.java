package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y24D14 extends Day {

	List<int[][]> in = getInputList().stream().map(x -> x.replace(",", " ").replace("=", " ").split(" "))
			.map(x -> new int[][] { //
					{ Integer.valueOf(x[1]), Integer.valueOf(x[2]) }, //
					{ Integer.valueOf(x[4]), Integer.valueOf(x[5]) } //
			}).toList();

	int[] tiles = //
			{ 101, 103 };
//			{ 11, 7 };

	@Override
	public Object part1() {
		int seconds = 100;
		int[][] count = new int[3][3];
		for (var x : in) {
			int[] np = { //
					(x[0][0] + (seconds * (tiles[0] + x[1][0]))) % tiles[0], //
					(x[0][1] + (seconds * (tiles[1] + x[1][1]))) % tiles[1] };
			int dy = np[0] < tiles[0] / 2 ? 0 : np[0] > tiles[0] / 2 ? 1 : 2;
			int dx = np[1] < tiles[1] / 2 ? 0 : np[1] > tiles[1] / 2 ? 1 : 2;
			count[dy][dx]++;
		}
		return count[0][0] * count[0][1] * count[1][0] * count[1][1];
	}

	@Override
	public Object part2() {
		int[][] grid = new int[tiles[0]][tiles[1]];
		boolean print = false;
		int step = 1;
		int inRowAmt = 10;
		for (; step < 1_000_000_000; step++) {
			int[][] ng = new int[tiles[0]][tiles[1]];
			Map<Integer, List<Integer>> rows = new HashMap<>();
			Map<Integer, List<Integer>> cols = new HashMap<>();
			for (int i = 0; i < in.size(); i++) {
				var x = in.get(i);
				int[] np = { //
						(x[0][0] + (step * (tiles[0] + x[1][0]))) % tiles[0], //
						(x[0][1] + (step * (tiles[1] + x[1][1]))) % tiles[1] };
				ng[np[0]][np[1]]++;
				rows.putIfAbsent(np[0], new ArrayList<>());
				rows.get(np[0]).add(np[1]);
				cols.putIfAbsent(np[1], new ArrayList<>());
				cols.get(np[1]).add(np[0]);
			}
			int max = 0;
			grid = ng;
			for (var c : cols.values()) {
				Collections.sort(c);
				int amt = 0;
				for (int i = 1; i < c.size() && max < inRowAmt; i++) {
					int del = c.get(i) - c.get(i - 1);
					if (del == 1) {
						amt++;
						max = Math.max(amt, max);
					} else if (del > 1) {
						amt = 0;
					}
				}
				if (max >= inRowAmt) {
					break;
				}
			}
			if (max >= inRowAmt) {
				break;
			}
		}
		if(print) {
			System.out.println("===== GRID =====");
			for (var x : grid) {
				System.out.println(Arrays.toString(x).replace("0", ".").replace(",", ""));
			}
			System.out.println("===== GRID =====");
		}
		return step;
	}

}
