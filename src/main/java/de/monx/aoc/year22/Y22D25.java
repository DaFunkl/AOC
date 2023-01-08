package de.monx.aoc.year22;

import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D25 extends Day {

	List<String> in = getInputList();
	long[] fp = new long[25];

	@Override
	public Object part1() {
		fp[0] = 1;
		for (int i = 1; i < fp.length; i++) {
			fp[i] = fp[i - 1] * 5;
		}
		long sum = 0;
		for (var str : in) {
			long num = 0;
			for (int i = 0; i < str.length(); i++) {
				num += (fp[i] * switch (str.charAt(str.length() - 1 - i)) {
				case '0' -> 00l;
				case '1' -> 01l;
				case '2' -> 02l;
				case '-' -> -1l;
				case '=' -> -2l;
				default -> throw new IllegalArgumentException("Unexpected value: " + str);
				});
			}
			sum += num;
		}
		var builder = new StringBuilder();
		char[] car = { '0', '1', '2', '=', '-' };
		while (sum != 0) {
			builder.append(car[(int) (sum % 5l)]);
			sum -= (sum + 2) % 5 - 2;
			sum /= 5;
		}
		return builder.reverse().toString();
	}

	@Override
	public Object part2() {
		return null;
	}

}
