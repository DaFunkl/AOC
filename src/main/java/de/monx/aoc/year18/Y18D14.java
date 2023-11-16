package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y18D14 extends Day {

	int num = Integer.valueOf(getInputString());
	String p1 = null;
	int p2 = -1;

	@Override
	public Object part1() {
		solve();
		return p1;
	}

	@Override
	public Object part2() {
		return p2;
	}

	void solve() {
		int idx1 = 0;
		int idx2 = 1;
		List<Integer> nums = new ArrayList<>();
		nums.add(3);
		nums.add(7);
		String strNum = "" + num;
		while (p1 == null || p2 < 0) {
			int sum = nums.get(idx1) + nums.get(idx2);
			if (sum > 9) {
				nums.add(sum / 10);
			}
			nums.add(sum % 10);
			idx1 = (idx1 + nums.get(idx1) + 1) % nums.size();
			idx2 = (idx2 + nums.get(idx2) + 1) % nums.size();
			String str = "";
			for (int i = Math.max(0, nums.size() - 10); i < nums.size(); i++) {
				str += nums.get(i);
			}
			if (nums.size() == num + 10) {
				p1 = str;
			}
			if (p2 < 0 && str.contains(strNum)) {
				p2 = nums.size() - strNum.length();
				if (!str.endsWith(strNum)) {
					p2 -= str.split(strNum)[1].length();
				}
			}
		}
	}
}
