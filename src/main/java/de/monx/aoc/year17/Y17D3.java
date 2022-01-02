package de.monx.aoc.year17;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.IntPair;

public class Y17D3 extends Day {
	int in = Integer.valueOf(getInputString());

	static final IntPair[] _DIRS = { //
			new IntPair(1, 0), // right
			new IntPair(0, 1), // up
			new IntPair(-1, 0), // left
			new IntPair(0, -1) // down
	};

	int ret1 = 0;
	int ret2 = 0;

	@Override
	public Object part1() {
		solve();
		return ret1;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	public void solve() {
		Map<Integer, IntPair> grid = new HashMap<>();
		Map<IntPair, Integer> vals = new HashMap<>();
		IntPair ip = new IntPair(0, 0);
		int c = 1;
		grid.put(c, ip);
		vals.put(ip.clone(), c);
		int[] mm = { 0, 0, 0, 0 };
		int dirIdx = 0;
		while (c < in) {
			c++;
			ip.addi(_DIRS[dirIdx]);

			int val = 0;
			var vip = ip.clone();

			for (int ii = -1; ii < 2; ii++) {
				for (int jj = -1; jj < 2; jj++) {
					if (ii == 0 && jj == 0) {
						continue;
					}
					var nvip = vip.add(ii, jj);
					val += vals.getOrDefault(nvip, 0);
				}
			}
			if (val >= in && ret2 == 0) {
				ret2 = val;
			}
			vals.put(vip, val);
			if (dirIdx < 2) {
				if (mm[dirIdx] < (dirIdx == 0 ? ip.first : ip.second)) {
					mm[dirIdx]++;
					dirIdx = (dirIdx + 1) % 4;
				}
			} else {
				if (mm[dirIdx] > (dirIdx == 2 ? ip.first : ip.second)) {
					mm[dirIdx]--;
					dirIdx = (dirIdx + 1) % 4;
				}
			}
		}
		ret1 = Math.abs(ip.first) + Math.abs(ip.second);
	}

}
