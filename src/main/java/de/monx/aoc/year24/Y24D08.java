package de.monx.aoc.year24;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D08 extends Day {

	List<String> in = getInputList();
	int[][] seen = new int[in.size()][in.get(0).length()];
	int[][][] cords = new int[160][20][2];
	int[] dots = new int[100];
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

		for (int i = 0; i < in.size(); i++) { // init, find coords
			for (int j = 0; j < in.get(i).length(); j++) {
				char ch = in.get(i).charAt(j);
				int c = (int) ch;
				if (ch != '.') {
					if (cords[c][0][0] == 0) {
						dots[0]++;
						dots[dots[0]] = c;
					}
					cords[c][0][0]++;
					cords[c][cords[c][0][0]][0] = i;
					cords[c][cords[c][0][0]][1] = j;
				}
			}
		}
		for (int i = 1; i <= dots[0]; i++) {
			for (int j = 1; j <= cords[dots[i]][0][0]; j++) {
				var c1 = cords[dots[i]][j];
				for (int n = j + 1; n <= cords[dots[i]][0][0]; n++) {
					var c2 = cords[dots[i]][n];
					int[] del = { c2[0] - c1[0], c2[1] - c1[1] };
					boolean[] d12 = { false, false };
					for (int m = 0; !d12[0] || !d12[1]; m++) {
						int[][] p = { //
								{ c1[0] - m * del[0], c1[1] - m * del[1] }, //
								{ c2[0] + m * del[0], c2[1] + m * del[1] } //
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
