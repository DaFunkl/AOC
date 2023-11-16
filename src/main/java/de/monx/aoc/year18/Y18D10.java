package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y18D10 extends Day {

	List<int[]> in = getIn();
	int cc = 0;

	@Override
	public Object part1() {
//		int[] mm = { Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, };
		int state = 0;
		while (state < 2) {
			cc++;
			int[] mm = { Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, };
			for (var x : in) {
				x[0] += x[2];
				x[1] += x[3];
				mm[0] = Math.min(x[0], mm[0]); // x Min Val
				mm[1] = Math.max(x[0], mm[1]); // x Max Val
				mm[2] = Math.min(x[1], mm[2]); // y Min Val
				mm[3] = Math.max(x[1], mm[3]); // y Max Val
			}
			int dy = Math.abs(mm[3] - mm[2]);
			int dx = Math.abs(mm[1] - mm[0]);
			if (state == 0 && dy < 10) {
				state++;
			} else if (state == 1 && dy > 9) {
				state++;
			}
			if (state == 1) {
				boolean[][] grid = new boolean[dy + 1][dx + 1];
				for (var xx : in) {
					grid[xx[1] - mm[2]][xx[0] - mm[0]] = true;
				}
				for (int i = 0; i < grid.length; i++) {
					String str = "";
					for (int j = 0; j < grid[i].length; j++) {
						str += grid[i][j] ? '#' : ' ';
					}
					System.out.println(str);
				}
				System.out.println(cc);
				System.out.println();
			}
		}
		return null;
	}

	@Override
	public Object part2() {
		return cc - 1;
	}

	List<int[]> getIn() {
		List<int[]> ret = new ArrayList<>();
		for (var s : getInputList()) {
			int[] t = new int[4];
			var sar = s.split("<");
			var sar1 = sar[1].split(",");
			t[0] = Integer.valueOf(sar1[0].trim());
			t[1] = Integer.valueOf(sar1[1].split(">")[0].trim());
			sar1 = sar[2].split(",");
			t[2] = Integer.valueOf(sar1[0].trim());
			t[3] = Integer.valueOf(sar1[1].split(">")[0].trim());
			ret.add(t);
		}
		return ret;
	}
}
