package de.monx.aoc.year18;

import java.util.Arrays;

import de.monx.aoc.util.Day;

public class Y18D11 extends Day {

	int[][] grid = new int[300][300];
	int in = Integer.valueOf(getInputString());
	int[] p1 = null;

	@Override
	public Object part1() {
		init();
		p1 = maxSum(3);
		return Arrays.toString(p1);
	}

	@Override
	public Object part2() {
		int[] prev = { Integer.MIN_VALUE, 0, 0, 0 };
		int[] ret = p1;
		for (int i = 1; i <= 300; i++) {
			int[] cur = maxSum(i);
			if (cur[0] > ret[0]) {
				ret = cur;
			} else if (prev[0] > (cur[0] << 1)) {
				break;
			}
			prev = cur;
		}
		return Arrays.toString(ret);
	}

	int[] maxSum(int rad) {
		int ret[] = { Integer.MIN_VALUE, 1, 1, rad };
		for (int i = 0; i < grid.length - rad; i++) {
			for (int j = 0; j < grid[i].length - rad; j++) {
				int sum = 0;
				for (int ii = 0; ii < rad; ii++) {
					for (int jj = 0; jj < rad; jj++) {
						sum += grid[i + ii][j + jj];
					}
				}
				if (sum > ret[0]) {
					ret[0] = sum;
					ret[1] = i + 1;
					ret[2] = j + 1;
				}
			}
		}
		return ret;
	}

	int fuelPower(int xx, int yy) {
		int rackID = xx + 10;
		int powerLevel = rackID * yy;
		powerLevel += in;
		powerLevel *= rackID;
		return ((powerLevel % 1000) / 100) - 5;
	}

	void init() {
		for (int i = 1; i <= 300; i++) {
			for (int j = 1; j <= 300; j++) {
				grid[i - 1][j - 1] = fuelPower(i, j);
			}
		}
	}
}
