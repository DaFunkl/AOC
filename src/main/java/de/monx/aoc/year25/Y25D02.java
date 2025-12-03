package de.monx.aoc.year25;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y25D02 extends Day {

	List<String[]> in = Arrays.stream(getInputString().split(",")) //
			.map(x -> x.split("-")) //
			.toList();
	long r1 = 0;

	@Override
	public Object part1() {
		for (var k : in) {
			long n1 = Long.valueOf(k[0]);
			long n2 = Long.valueOf(k[1]);
			String cstr = k[0].substring(0, k[0].length() / 2);
			if (cstr.isBlank()) {
				cstr = "0";
			}
			long cur = Long.valueOf(cstr);
			long curcur = Long.valueOf(cur + "" + cur);
			while (curcur <= n2) {
				if (curcur >= n1) {
					r1 += curcur;
				}
				cur++;
				curcur = Long.valueOf(cur + "" + cur);
			}
		}
		return r1;
	}

	@Override
	public Object part2() {
		long ret = 0;
		for (var k : in) {
			long n1 = Long.valueOf(k[0]);
			long n2 = Long.valueOf(k[1]);
			Set<Long> seen = new HashSet<>();
			long cl = 1l;
			String cs = "1";
			while (cs.length() <= (n2 + "").length() / 2) {
				String cs2 = cs;
				long cl2 = cl;
				while ((cs2.length() + cs.length()) <= (n2 + "").length()) {
					cs2 = cs2 + cs;
					cl2 = Long.valueOf(cs2);
					if (cl2 > n2 || seen.contains(cl2)) {
						break;
					}
					if (cl2 >= n1) {
						ret += cl2;
						seen.add(cl2);
					}
				}
				cl++;
				cs = cl + "";
			}
		}
		return ret;
	}

}
