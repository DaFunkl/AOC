package de.monx.aoc.year23;

import java.math.BigDecimal;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D24 extends Day {

	List<BigDecimal[]> in = getInputList().stream().map(x -> x.replace(" @", ",").replace(", ", ",").split(","))
			.map(x -> new BigDecimal[] { new BigDecimal(x[0]), new BigDecimal(x[1]), new BigDecimal(x[2]),
					new BigDecimal(x[3]), new BigDecimal(x[4]), new BigDecimal(x[5]) })
			.toList();

	@Override
	public Object part1() {
		int ret = 0;
		for (int i = 0; i < in.size(); i++) {
			for (int j = i + 1; j < in.size(); j++) {
				ret += intersect(in.get(i), in.get(j)) ? 1 : 0;
			}
		}
		return ret;
	}

	@Override
	public Object part2() {
		return null;
	}

	boolean intersect(BigDecimal[] bd1, BigDecimal[] bd2) {
		
		return false;
	}
}
