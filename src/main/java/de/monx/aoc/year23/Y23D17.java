package de.monx.aoc.year23;

import java.util.Comparator;
import java.util.PriorityQueue;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.anim.Animation;
import de.monx.aoc.util.anim.DP_23_17;

public class Y23D17 extends Day {

	int[][] map = getInputList().stream().map(x -> x.chars().map(y -> y - '0').toArray()).toList()
			.toArray(new int[0][]);

	int[][] _DIRS = { //
			{ -1, 0 }, // U
			{ 0, -1 }, // L
			{ 1, 0 }, // D
			{ 0, 1 }, // R
	};

	int[] _AD = { 1, 3 };

	boolean isAnim = false;
	Animation anim = null;
	long sleep = 1;

	void drawAnim(long sleep, int[][][] bw) {
		if (!isAnim) {
			return;
		}
		if (anim == null) {
			anim = new Animation(1030, 1030, new DP_23_17());
			((DP_23_17) anim.pane).update(sleep, map, bw);
			Util.readLine();
		}
		((DP_23_17) anim.pane).update(sleep, map, bw);
	}

	@Override
	public Object part1() {
		return solve(0, 4);
	}

	@Override
	public Object part2() {
		return solve(3, 11);
	}

	int solve(int min, int max) {
		int[][][] bw = new int[map.length][map[0].length][2];
		PriorityQueue<int[]> st = new PriorityQueue<>(new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[3] - o2[3];
			}
		});
		drawAnim(sleep, bw);
		st.add(new int[] { 0, 0, 0, 0 });
		st.add(new int[] { 0, 0, 1, 0 });
		int cc = 0;
		int ret = Integer.MAX_VALUE;
		int gy = map.length - 1;
		int gx = map[0].length - 1;
		while (!st.isEmpty()) {
			var cur = st.poll();
			if (bw[cur[0]][cur[1]][cur[2] % 2] != 0 && bw[cur[0]][cur[1]][cur[2] % 2] <= cur[3]) {
				continue;
			}
			bw[cur[0]][cur[1]][cur[2] % 2] = cur[3];
			if (cur[0] == gy && cur[1] == gx) {
				ret = Math.min(ret, cur[3]);
				continue;
			}
			if (cc++ >= 10) {
				cc = 0;
				drawAnim(2, bw);
			} else {
				drawAnim(0, bw);
			}
			for (int id : _AD) {
				int ny = cur[0];
				int nx = cur[1];
				int nd = (cur[2] + id) % 4;
				int w = cur[3];
				for (int i = 1; i < max; i++) {
					ny += _DIRS[nd][0];
					nx += _DIRS[nd][1];
					if (!(nx >= 0 && ny >= 0 && nx < map[0].length && ny < map.length)) {
						break;
					}
					w += map[ny][nx];
					if (i > min) {
						st.add(new int[] { ny, nx, nd, w });
					}
				}
			}
		}
		drawAnim(sleep * 10, bw);
		return ret;
	}

}
