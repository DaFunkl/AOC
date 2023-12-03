package de.monx.aoc.year23;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D03 extends Day {

	List<String> in = getInputList();

	@Override
	public Object part1() {
		int sum = 0;
		int start = -1;
		String str = "";
		for (int i = 0; i < in.size(); i++) {
			str = in.get(i);
			start = -1;
			for (int j = 0; j < str.length(); j++) {
				int num = isNum(str.charAt(j));
				if (num < 0) {
					if (start == -1) {
						continue;
					}
					if (isPart1(start, j, i)) {
						sum += Integer.valueOf(str.substring(start, j));
					}
					start = -1;
				} else {
					if (start == -1) {
						start = j;
					}
				}
			}
			if (start != -1 && isPart1(start, str.length(), i)) {
				sum += Integer.valueOf(str.substring(start));
			}
		}
		return sum;
	}

	boolean isPart1(int s, int e, int r) {
		for (int i = Math.max(0, r - 1); i < r + 2 && i < in.size(); i++) {
			char[] arr = in.get(i).toCharArray();
			for (int j = Math.max(0, s - 1); j < e + 1 && j < arr.length; j++) {
				if (i == r && j >= s && j < e) {
					continue;
				}
				if (arr[j] != '.') {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Object part2() {
		int sum = 0;
		for (int i = 0; i < in.size(); i++) {
			String s = in.get(i);
			for (int j = 0; j < s.length(); j++) {
				if (s.charAt(j) == '*') {
					sum += isPart2(i, j);
				}
			}
		}
		return sum;
	}

	int isPart2(int y, int x) {
		int[] nums = { 0, 0 };
		int idx = 0;
		for (int i = Math.max(y - 1, 0); i < y + 2 && i < in.size(); i++) {
			String s = in.get(i);
			for (int j = Math.max(x - 1, 0); j < x + 2 && j < s.length(); j++) {
				if (isNum(s.charAt(j)) >= 0) {
					int n = j, m = j;
					while (n > 0 && isNum(s.charAt(n)) >= 0) {
						n--;
					}
					if (!(isNum(s.charAt(n)) >= 0)) {
						n++;
					}
					while (m < s.length() - 1 && isNum(s.charAt(m)) >= 0) {
						m++;
					}
					if (!(isNum(s.charAt(m)) >= 0)) {
						m--;
					}
					j = m;
					nums[idx] = Integer.valueOf(s.substring(n, m + 1));
					idx++;
					if (idx == 2) {
						return nums[0] * nums[1];
					}
				}
			}
		}
		return 0;
	}

	int isNum(char c) {
		int num = Character.getNumericValue(c);
		if (num > 9) {
			return -1;
		}
		return num;
	}
}
