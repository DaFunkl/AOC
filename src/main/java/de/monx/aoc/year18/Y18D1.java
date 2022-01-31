package de.monx.aoc.year18;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y18D1 extends Day {
	List<Long> in = getInputList().stream().map(Long::valueOf).toList();

	@Override
	public Object part1() {
		return in.stream().reduce(Long::sum).get();
//		return null;
	}

	@Override
	public Object part2() {
		Set<Long> fqz = new HashSet<>();
		long c = 0;
		fqz.add(0l);
		for (int i = 0; i <= in.size(); i++) {
			i %= in.size();
			c += in.get(i);
			if (fqz.contains(c)) {
				return c;
			}
			fqz.add(c);
		}
		return null;
	}

}
