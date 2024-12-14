package de.monx.aoc.year24;

import java.util.Arrays;
import java.util.List;

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
		int seconds = 100;
		int[][] grid = new int[tiles[0]][tiles[1]];
		for (int i = 0; i < 1_000_000_000; i++) {
			int[][] nGrid = new int[tiles[0]][tiles[1]];
			int[] np = { //
					(x[0][0] + (seconds * (tiles[0] + x[1][0]))) % tiles[0], //
					(x[0][1] + (seconds * (tiles[1] + x[1][1]))) % tiles[1] };

			
		}
		for (var x : in) {
			int[] np = { //
					(x[0][0] + (seconds * (tiles[0] + x[1][0]))) % tiles[0], //
					(x[0][1] + (seconds * (tiles[1] + x[1][1]))) % tiles[1] };
			int dy = np[0] < tiles[0] / 2 ? 0 : np[0] > tiles[0] / 2 ? 1 : 2;
			int dx = np[1] < tiles[1] / 2 ? 0 : np[1] > tiles[1] / 2 ? 1 : 2;
			count[dy][dx]++;
			grid[np[0]][np[1]]++;
		}
		return count[0][0] * count[0][1] * count[1][0] * count[1][1];
	}

}
