package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y21D13 extends Day {
	List<String> input = getInputList();
	List<int[]> points = new ArrayList<>();
	List<int[]> folds = new ArrayList<>();
	int maxFX = Integer.MAX_VALUE, maxFY = Integer.MAX_VALUE;
	int maxX = 0, maxY = 0;
//	boolean inted = init();

	@Override
	public Object part1() {
		init();
		return solve(true);
	}

	@Override
	public Object part2() {
		return solve(false);
	}

	Object solve(boolean p1) {
		boolean[][] grid = p1 ? new boolean[maxY + 1][maxX + 1] : new boolean[maxFY][maxFX];
		int ret = 0;
		for (var p : points) {
			int[] fp = applyFold(p, p1 ? 1 : folds.size());
			if (!grid[fp[0]][fp[1]]) {
				ret++;
			}
			grid[fp[0]][fp[1]] = true;
		}
		if (p1) {
			return ret;
		}

		StringBuilder sb = new StringBuilder("\n");
		for (var g : grid) {
			for (var b : g) {
				sb.append(b ? "#" : ".");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	int[] applyFold(int[] point, int foldAmt) {
		int counter = 0;
		for (var f : folds) {
			if (counter++ >= foldAmt) {
				break;
			}
			if (f[1] == 0) {
				if (f[0] < point[0]) {
					point[0] = f[0] - (point[0] - f[0]);
				}
			} else {
				if (f[1] < point[1]) {
					point[1] = f[1] - (point[1] - f[1]);
				}
			}
		}
		return point;
	}

	boolean init() {
		boolean dots = true;
		for (var in : input) {
			if (in.isBlank()) {
				dots = false;
			} else if (dots) {
				var sar = in.split(",");
				int[] point = new int[] { Integer.valueOf(sar[1]), Integer.valueOf(sar[0]) };
				points.add(point);
				if (point[1] > maxX) {
					maxX = point[1];
				}
				if (point[0] > maxY) {
					maxY = point[0];
				}
			} else {
				var sar = in.replace("=", " ").split(" ");
				int[] f = new int[2];
				if (sar[2].equals("x")) {
					f[1] = Integer.valueOf(sar[3]);
					if (f[1] < maxFX) {
						maxFX = f[1];
					}
				} else {
					f[0] = Integer.valueOf(sar[3]);
					if (f[0] < maxFX) {
						maxFY = f[0];
					}
				}
				folds.add(f);
			}
		}
		return true;
	}
}