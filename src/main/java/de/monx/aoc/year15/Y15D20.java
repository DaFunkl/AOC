package de.monx.aoc.year15;

import java.util.HashSet;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y15D20 extends Day {
	int input = Integer.valueOf(getInputString());

	@Override
	public Object part1() {
		int divs = fetchPresents(input);
		for (int i = input + 1; i < input*2; i++) {
			int ip = fetchPresents(i);
			if (i % 100000 == 0) {
				System.out.println(i + " -> " + ip + " =/= " + divs);
			}
			if (divs == ip) {
				return i;
			}
		}
		return "Not Found";
	}

	@Override
	public Object part2() {

		return null;
	}

	int fetchPresents(int n) {
		int ret = 0;
		int sqrt = (int) Math.sqrt(n);
		for (int i = 1; i <= sqrt; i++) {
			if (n % i == 0) {
				ret += i;
				if(i != sqrt) {
					ret += n/i;
				}
			}
		}
		return ret;
	}
}
