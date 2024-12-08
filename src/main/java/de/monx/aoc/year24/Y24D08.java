package de.monx.aoc.year24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y24D08 extends Day {

	List<String> in = getInputList();
	Map<Character, List<int[]>> coords = new HashMap<>();
	int r1 = 0;
	int r2 = 0;

	@Override
	public Object part1() {
		solve();
		return r1;
	}

	@Override
	public Object part2() {
		return r2;
	}

	void solve() {
		int[][] seen = new int[in.size()][in.get(0).length()];
		for (int i = 0; i < in.size(); i++) { // init, find coords
			for (int j = 0; j < in.get(i).length(); j++) {
				char c = in.get(i).charAt(j);
				if (c != '.') {
					coords.putIfAbsent(c, new ArrayList<>());
					coords.get(c).add(new int[] { i, j });
				}
			}
		}
		for (var cors : coords.values()) {
			for (int i = 0; i < cors.size(); i++) {
				var ci = cors.get(i);
				for (int j = i + 1; j < cors.size(); j++) {
					var cj = cors.get(j);
					int[] del = { cj[0] - ci[0], cj[1] - ci[1] };
					boolean[] d12 = { false, false };
					for (int m = 0; !d12[0] || !d12[1]; m++) {
						int[][] p = { //
								{ ci[0] - m * del[0], ci[1] - m * del[1] }, //
								{ cj[0] + m * del[0], cj[1] + m * del[1] } //
						};
						for (int k = 0; k < 2; k++) {
							if (p[k][0] < 0 || p[k][1] < 0 || p[k][0] >= in.size() || p[k][1] >= in.get(0).length()) {
								d12[k] = true;
								continue;
							}
							if (seen[p[k][0]][p[k][1]] == 0) {
								r2++;
								seen[p[k][0]][p[k][1]] = 1;
							}
							if (m == 1 && seen[p[k][0]][p[k][1]] < 2) {
								r1++;
								seen[p[k][0]][p[k][1]] = 2;
							}
						}
					}
				}
			}
		}
	}
}
