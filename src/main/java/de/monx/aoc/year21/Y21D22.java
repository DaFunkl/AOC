
package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y21D22 extends Day {
	List<String> in = getInputList();
	List<Pair<Boolean, long[][]>> ops = new ArrayList<>();

	long p1, p2;

	@Override
	public Object part1() {
		solve2();
		return p1;
	}

	@Override
	public Object part2() {
		return p2;
	}

	void solve2() {
		init();
		p1 = 0;
		p2 = 0;
		List<Pair<Boolean, long[][]>> cops = new ArrayList<>();
		for (int i = 0; i < ops.size(); i++) {
			var op = ops.get(i);
			int mul = 1;
			int cap = cops.size();
			for (int j = 0; j < cap; j++) {
				var cop = cops.get(j);
				var inter = intersect(op.second, cop.second);
				if (inter == null) {
					continue;
				}
				var ncop = new Pair<Boolean, long[][]> //
				(!cop.first, inter);

				mul = ncop.first ? 1 : -1;
				p1 += mul * cubes(intersect(ncop.second, irange));
				p2 += mul * cubes(ncop.second);
				cops.add(ncop);
			}
			if (!op.first) {
				continue;
			}
			mul = op.first ? 1 : -1;
			p1 += mul * cubes(intersect(op.second, irange));
			p2 += mul * cubes(op.second);
			cops.add(op);
		}
	}

	long[][] irange = { //
			{ -50, 50 }, //
			{ -50, 50 }, //
			{ -50, 50 } //
	};

	long cubes(long[][] arr) {
		if (arr == null) {
			return 0l;
		}
		return (1 + Math.abs(arr[0][1] - arr[0][0])) //
				* (1 + Math.abs(arr[1][1] - arr[1][0])) //
				* (1 + Math.abs(arr[2][1] - arr[2][0]));
	}

	long[][] intersect(long[][] a, long[][] b) {
		long[][] ret = new long[3][2];
		for (int i = 0; i < 3; i++) {
			if (a[i][1] < b[i][0] || b[i][1] < a[i][0]) {
				return null;
			} else {
				ret[i][0] = Math.max(a[i][0], b[i][0]);
				ret[i][1] = Math.min(a[i][1], b[i][1]);
			}
		}
		return ret;
	}

	void init() {
		for (String s : in) {
			boolean on = s.startsWith("on");
			var xyz = s.split(" ")[1].replaceAll("[xyz=]", "").replace("..", ",").split(",");
			ops.add(new Pair<Boolean, long[][]>(on, new long[][] { //
					{ Long.valueOf(xyz[0]), Long.valueOf(xyz[1]) }, //
					{ Long.valueOf(xyz[2]), Long.valueOf(xyz[3]) }, //
					{ Long.valueOf(xyz[4]), Long.valueOf(xyz[5]) }, //
			}));
		}
	}
}