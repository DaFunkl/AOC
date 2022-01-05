package de.monx.aoc.year17;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class Y17D21 extends Day {
	List<String> in = getInputList();
	Map<Grid, Grid> rules = new HashMap<>();
	String initState = ".#./..#/###";
	int ret1 = 0, ret2 = 0;

	@Override
	public Object part1() {
		init();
		solve();
		return ret1;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	void solve() {
		Grid current = new Grid(initState);
		for (int steps = 0; steps < 18; steps++) {
			var grids = current.split();
			for (int i = 0; i < grids.length; i++) {
				for (int j = 0; j < grids[i].length; j++) {
					grids[i][j] = rules.get(grids[i][j]);
				}
			}
			current = Grid.merge(grids);
			if (steps == 4) {
				ret1 = current.lit();
			}
		}
		ret2 = current.lit();
	}

	@Data
	@EqualsAndHashCode
	static class Grid {
		char[][] grid;

		public Grid() {
		}

		public Grid(char[][] c) {
			grid = c;
		}

		public Grid(String str) {
			var sar = str.split("/");
			grid = new char[sar.length][sar[0].length()];
			for (int i = 0; i < grid.length; i++) {
				grid[i] = sar[i].toCharArray();
			}
		}

		Grid[][] split() {
			int ss = 3;
			if (grid.length % 2 == 0) {
				ss = 2;
			}
			int spl = grid.length / ss;
			Grid[][] ret = new Grid[spl][spl];
			for (int i = 0; i < spl; i++) {
				for (int j = 0; j < spl; j++) {
					char[][] nc = new char[ss][ss];
					for (int k = 0; k < ss; k++) {
						for (int m = 0; m < ss; m++) {
							nc[k][m] = grid[i * ss + k][j * ss + m];
						}
					}
					ret[i][j] = new Grid(nc);
				}
			}
			return ret;
		}

		static Grid merge(Grid[][] grids) {
			int amt = grids.length;
			int gs = grids[0][0].grid.length;
			int ns = gs * amt;
			char[][] nc = new char[ns][ns];
			for (int i = 0; i < grids.length; i++) {
				for (int j = 0; j < grids[i].length; j++) {
					var g = grids[i][j].grid;
					for (int k = 0; k < g.length; k++) {
						for (int m = 0; m < g[k].length; m++) {
							nc[i * gs + k][j * gs + m] = g[k][m];
						}
					}
				}
			}
			return new Grid(nc);
		}

		int lit() {
			int ret = 0;
			for (var car : grid) {
				for (var c : car) {
					if (c == '#') {
						ret++;
					}
				}
			}
			return ret;
		}

		void print() {
			for (var g : grid) {
				System.out.println(new String(g));
			}
		}
	}

	void init() {
		for (var s : in) {
			var sar = s.split(" => ");
			var kg = new Grid(sar[0]);
			for (var g : rotsAndFlips(kg.grid)) {
				rules.put(g, new Grid(sar[1]));
			}
		}
	}

	Set<Grid> rotsAndFlips(char[][] grid) {
		Set<Grid> ret = new HashSet<>();
		char[][] ng = new char[grid[0].length][grid.length];
		for (var r = 0; r < 4; r++) {
			ng = new char[grid[0].length][grid.length];
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					ng[i][j] = grid[grid[0].length - 1 - j][i];
				}
			}
			ret.add(new Grid(grid));
			grid = ng;
		}

		ng = new char[grid.length][grid[0].length];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				ng[i][j] = grid[i][grid[i].length - 1 - j];
			}
		}
		grid = ng;

		for (var r = 0; r < 4; r++) {
			ng = new char[grid[0].length][grid.length];
			for (int i = 0; i < grid.length; i++) {
				for (int j = 0; j < grid[0].length; j++) {
					ng[i][j] = grid[grid[0].length - 1 - j][i];
				}
			}
			ret.add(new Grid(grid));
			grid = ng;
		}
		return ret;
	}

}
