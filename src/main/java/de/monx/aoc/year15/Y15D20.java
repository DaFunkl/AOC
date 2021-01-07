package de.monx.aoc.year15;

import de.monx.aoc.util.Day;

public class Y15D20 extends Day {
	int input = Integer.valueOf(getInputString());

	@Override
	public Object part1() {
		return solve(input, false);
	}

	@Override
	public Object part2() {
		return solve(input, true);
	}

	int solve(int input, boolean p2) { //@formatter:off
		int i = 0, ip = 0;
		while (ip < input) {
			i++; ip = fetchPresents(i, p2);
		}
		return i;
	}//@formatter:on

	int fetchPresents(int n, boolean p2) {
		if (!p2) {
			return fetchPresents1(n);
		} else {
			return fetchPresents2(n);
		}
	}

	int fetchPresents1(int n) {
		int sqrt = (int) Math.sqrt(n);
		int ret = 0;
		for (int i = 1; i <= sqrt; i++) {
			if (n % i == 0) {
				ret += i * 10;
				if (i != sqrt) {

					ret += (n / i) * 10;
				}
			}
		}
		return ret;
	}

	int fetchPresents2(int n) {
		int sqrt = (int) Math.sqrt(n);
		int ret = 0;
		for (int i = 1; i <= sqrt; i++) {
			if (n % i == 0) {
				if (n / i <= 50) {
					ret += i * 11;
				}
				if (i != sqrt) {
					int ni = n / i;
					if (n / ni <= 50) {
						ret += (n / i) * 11;
					}
				}
			}
		}
		return ret;
	}
}
