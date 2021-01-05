package de.monx.aoc.year15;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y15D18 extends Day {

	List<List<Integer>> data = getInputList().stream()
			.map(x -> x.chars().mapToObj(i -> (char) i).map(y -> y == '#' ? 1 : 0).collect(Collectors.toList()))
			.collect(Collectors.toList());
	static final int RUNS = 100;

	@Override
	public Object part1() {
		return solve(false);
	}

	@Override
	public Object part2() {
		return solve(true);
	}

	int solve(boolean p2) {
		var grid = Util.shallowCopy(data); //@formatter:off
		for (int i = 0; i < RUNS; i++) grid = doGOA(grid, p2); //@formatter:on
		return grid.parallelStream().mapToInt(x -> x.parallelStream().mapToInt(Integer::valueOf).sum()).sum();
	}

	List<List<Integer>> doGOA(List<List<Integer>> grid, boolean p2) {
		var newGrid = new ArrayList<List<Integer>>(grid.size());
		for (int i = 0; i < grid.size(); i++) {
			var row = new ArrayList<Integer>(grid.get(i).size());
			for (int j = 0; j < grid.get(i).size(); j++) {
				row.add(applyRule(grid, i, j, p2));
			}
			newGrid.add(row);
		}
		return newGrid;
	}

	int countAdj(List<List<Integer>> grid, int i, int j) { //@formatter:off
		int count = 0;
		for (int di = -1; di <= 1; di++) { int ii = i + di;
			if (ii < 0 || ii >= grid.size()) continue;
			for (int dj = -1; dj <= 1; dj++) { int jj = j + dj;
				if (jj < 0 || jj >= grid.get(ii).size() || (di == 0 && dj == 0)) continue;
				if (grid.get(ii).get(jj) == 1) count++;
			}
		} //@formatter:on
		return count;
	}

	int applyRule(List<List<Integer>> grid, int i, int j, boolean p2) {
		int adj = countAdj(grid, i, j);
		int oldCell = grid.get(i).get(j);
		int x = grid.size() - 1;
		int y = grid.get(i).size() - 1;
		//@formatter:off
		if(adj == 3) return 1;
		if(p2 && (  (i == 0 && j == 0)||
					(i == 0 && j == y)||
					(i == x && j == 0)||
					(i == x && j == y))
				) return 1;
		if(oldCell == 1 && adj == 2) return 1;
		//@formatter:on
		return 0;
	}
}
