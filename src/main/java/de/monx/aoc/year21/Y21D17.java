package de.monx.aoc.year21;

import de.monx.aoc.util.Day;

public class Y21D17 extends Day {
	static final int X_IDX = 0;
	static final int X_MIN_IDX = 0;
	static final int X_MAX_IDX = 1;
	static final int Y_IDX = 1;
	static final int Y_MIN_IDX = 0;
	static final int Y_MAX_IDX = 1;

	int[][] target = getTarget();
	int[] xRange = { 0, 0 };

	@Override
	public Object part1() {
		int maxY = 0;
		int minXStart = fetchMinX();
		for (int x = minXStart; x <= target[0][1]; x++) {
			for (int y = 1; y <= (-1 * target[1][1]); y++) {
				int cx = 0;
				int cy = 0;
				int cmy = 0;
				for (int step = 0; cx <= target[0][1] && cy >= target[1][1]; step++) {
					cx += Math.max(x - step, 0);
					cy += y - step;
					cmy = Math.max(cy, cmy);
					if (target[0][0] <= cx && cx <= target[0][1] && //
							target[1][0] >= cy && cy >= target[1][1] //
					) {
						maxY = Math.max(maxY, cmy);
					}
				}
			}
		}
		return maxY;
	}

	@Override
	public Object part2() {
		int count = 0;
		int minXStart = fetchMinX();
		for (int x = minXStart; x <= target[0][1]; x++) {
			for (int y = target[1][1]; y <= (-1 * target[1][1]); y++) {
				int cx = 0;
				int cy = 0;
				int cmy = 0;
				for (int step = 0; cx <= target[0][1] && cy >= target[1][1]; step++) {
					cx += Math.max(x - step, 0);
					cy += y - step;
					cmy = Math.max(cy, cmy);
					if (target[0][0] <= cx && cx <= target[0][1] && //
							target[1][0] >= cy && cy >= target[1][1] //
					) {
						count++;
						break;
					}
				}
			}
		}
		return count;
	}

	int fetchMinX() {
		int cx = 0;
		for (int i = 1; i <= target[0][1]; i++) {
			cx += i;
			if (cx >= target[0][0]) {
				return i;
			}
		}
		return -1;
	}

	int[][] getTarget() {
		// target area: x=20..30, y=-10..-5
		String str = getInputString();
		var sar = str.replace(",", "").replace("..", ",").split(" ");
		var xar = sar[2].split("=")[1].split(",");
		var yar = sar[3].split("=")[1].split(",");
		return new int[][] { //
				{ Integer.valueOf(xar[0]), Integer.valueOf(xar[1]) }, //
				{ Integer.valueOf(yar[1]), Integer.valueOf(yar[0]) }, //
		};
	}
}
