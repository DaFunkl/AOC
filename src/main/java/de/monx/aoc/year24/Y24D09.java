package de.monx.aoc.year24;

import de.monx.aoc.util.Day;

public class Y24D09 extends Day {

	String inStr = getInputString();
	int[] in = inStr.chars().map(x -> x - '0').toArray();

	@Override
	public Object part1() {
		long sum = 0;
		int l = 0;
		int r = in.length + 1;
		int ri = 0;
		long cp = 0;
		while (l < r) {
			for (int i = 0; i < in[l]; i++) {
				sum += cp * l / 2;
				cp++;
			}
			l++;
			for (int i = 0; i < in[l] && l < r; i++) {
				if (ri == 0) {
					r -= 2;
					ri = in[r];
					if (r <= l) {
						break;
					}
				}
				sum += cp * r / 2;
				cp++;
				ri--;
			}
			l++;
		}
		for (int i = ri; i > 0; i--) {
			sum += cp * r / 2;
			cp++;
			ri--;
		}
		return sum;
	}

	@Override
	public Object part2() {
		long sum = 0;
		int r = in.length - 1;
		int[] ll = new int[in.length];
		int[] cps = new int[in.length];
		for (int i = 1; i < in.length; i++) {
			cps[i] = cps[i - 1] + in[i - 1];
		}
		while (r >= 0) {
			int l = 1;
			for (l = 1; l <= r; l += 2) {
				if ((in[l] - ll[l]) >= in[r]) {
					break;
				}
			}
			l = Math.min(r, l);
			for (int i = 0; i < in[r]; i++) {
				sum += ((long) cps[l] + ll[l]) * r / 2;
				ll[l]++;
			}
			r -= 2;
		}
		return sum;
	}
}
