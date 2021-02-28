package de.monx.aoc.year16;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.pairs.LongPair;

public class Y16D20 extends Day {

	@Override
	public Object part1() {
		var pairs = parse();
		long min = 0;
		for (var p : pairs) {
			if (min < p.first) {
				return min;
			}
			if (p.first <= min && min <= p.second) {
				min = p.second + 1;
			}
		}
		return null;
	}

	@Override
	public Object part2() {
		var pairs = parse();
		long min = 0;
		long ret = 0;
		for (var p : pairs) {
			if (min < p.first) {
				ret += p.first - min;
			}
			if (min < p.second) {
				min = p.second + 1;
			}
		}
		return ret;
	}

	List<LongPair> parse() {
		return getInputList().stream() //
				.map(x -> x.split("-")) //
				.map(x -> new LongPair(x[0], x[1])) //
				.sorted(new Comparator<LongPair>() {
					@Override
					public int compare(LongPair o1, LongPair o2) {
						return o1.first.compareTo(o2.first);
					}
				}).collect(Collectors.toList());
	}
}
