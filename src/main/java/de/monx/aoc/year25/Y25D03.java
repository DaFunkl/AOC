package de.monx.aoc.year25;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y25D03 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		return solve(2);
	}

	@Override
	public Object part2() {
		return solve(12);
	}

	long solve(int size) {
		long ret = 0;
		for (var l : in) {
			int[][] dic = new int[10][l.length() + 2];
			for (int i = 0; i < 10; i++) {
				dic[i][0] = 1;
			}
			for (int i = 0; i < l.length(); i++) {
				int val = Character.getNumericValue(l.charAt(i));
				dic[val][dic[val][0]] = i;
				dic[val][0]++;
			}
			long r = getHighest(dic, -1, l.length(), 0l, size);
			ret += r;
		}
		return ret;
	}

	long getHighest(int[][] dic, int cp, int sl, long cn, int ll) {
		if (ll == 0) {
			return cn;
		}
		if ((cp + ll) >= sl) {
			return 0l;
		}
		for (int i = 9; i >= 0; i--) {
			for (int j = 1; j < dic[i][0]; j++) {
				if (dic[i][j] > cp) {
					long ret = getHighest(dic, dic[i][j], sl, (cn * 10) + i, ll - 1);
					if (ret > 0l) {
						return ret;
					}
				}
			}
		}
		return 0l;
	}
}
