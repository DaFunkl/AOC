package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y15D6 extends Day {
	static final int TURN_ON = 0;
	static final int TURN_OFF = 1;
	static final int TOGGLE = 2;

	List<Pair<Integer, Pair<IntPair, IntPair>>> data = new ArrayList<>();

	@Override
	public Object part1() {
		parse();
		boolean[][] grid = new boolean[1000][1000];
		for (var k : data) {
			for (int i = k.second.first.first; i <= k.second.second.first; i++) {
				for (int j = k.second.first.second; j <= k.second.second.second; j++) {
					if (k.first == TURN_ON)
						grid[i][j] = true;
					else if (k.first == TURN_OFF)
						grid[i][j] = false;
					else if (k.first == TOGGLE)
						grid[i][j] = !grid[i][j];
				}
			}
		}
		return count(grid);
	}

	@Override
	public Object part2() {
		int[][] grid = new int[1000][1000];
		for (var k : data) {
			for (int i = k.second.first.first; i <= k.second.second.first; i++) {
				for (int j = k.second.first.second; j <= k.second.second.second; j++) {
					if (k.first == TURN_ON)
						grid[i][j]++;
					else if (k.first == TURN_OFF)
						grid[i][j] = Math.max(0, grid[i][j] - 1);
					else if (k.first == TOGGLE)
						grid[i][j] += 2;
				}
			}
		}
		return count(grid);
	}

	int count(boolean[][] grid) {
		int count = 0;
		for (var g : grid)
			for (var b : g)
				if (b)
					count++;
		return count;
	}

	int count(int[][] grid) {
		int count = 0;
		for (var g : grid)
			for (var b : g)
				count += b;
		return count;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	void parse() {
		getInputList().stream().forEach(l -> {
			int instr = -1;
			if (l.startsWith("turn on "))
				instr = TURN_ON;
			if (l.startsWith("turn off "))
				instr = TURN_OFF;
			if (l.startsWith("toggle "))
				instr = TOGGLE;

			var spl = l.split(" through ");
			var splP2 = spl[1].split(",");
			var p2 = new IntPair(Integer.valueOf(splP2[0]), Integer.valueOf(splP2[1]));
			spl = spl[spl.length - 2].split(" ");
			spl = spl[spl.length - 1].split(",");
			var p1 = new IntPair(Integer.valueOf(spl[0]), Integer.valueOf(spl[1]));
			data.add(new Pair(instr, new Pair(p1, p2)));
		});
	}
}
