package de.monx.aoc.year25;

import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

public class Y25D01 extends Day {

	List<Pair<Character, Integer>> in = getInputList().stream()//
			.map(x -> new Pair<>( //
					x.charAt(0), //
					Integer.valueOf(x.substring(1)) //
			)).toList();
	int r1 = 0;

	@Override
	public Object part1() {
		int current = 50;
		for (var p : in) {
			int mul = p.first == 'R' ? 1 : -1;
			current = (100 + current + (mul * p.second)) % 100;
			r1 += current == 0 ? 1 : 0;
		}
		return r1;
	}

	@Override
	public Object part2() {
		int cur = 50;
		int ret = 0;
		for (var p : in) {
			int mul = p.first == 'L' ? -1 : 1;
			int adder = mul * p.second;
			ret += Math.abs(adder / 100);
			adder %= 100;
			if (cur == 0 && adder < 0) {
				cur += 100;
			}
			cur += adder;
			if (cur <= 0 || cur >= 100) {
				ret++;
				cur = (cur + 100) % 100;
			}
			System.out.println(ret + "\t" + cur + "\t" + p);
		}
		return ret;
	}
}
