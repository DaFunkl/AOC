package de.monx.aoc.year24;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y24D01 extends Day {

	List<int[]> in = getInputList().stream()//
			.map(x -> x.replaceAll("\\s+", " ").split(" ")) //
			.map(x -> new int[] { //
					Integer.valueOf(x[0]), //
					Integer.valueOf(x[1]),//
			}) //
			.toList();

	@Override
	public Object part1() {
		int[] l = in.stream().mapToInt(x -> x[0]).toArray();
		int[] r = in.stream().mapToInt(x -> x[1]).toArray();
		Arrays.sort(l);
		Arrays.sort(r);
		int sum = 0;
		for (int i = 0; i < l.length; i++) {
			sum += Math.abs(l[i] - r[i]);
		}
		return sum;
	}

	@Override
	public Object part2() {
		Map<Integer, Integer> m = new HashMap<>();
		for (int[] i : in) {
			m.putIfAbsent(i[1], 0);
			m.put(i[1], m.get(i[1]) + 1);
		}
		return in.stream().mapToInt(x -> x[0] * m.getOrDefault(x[0], 0)).sum();
	}
}
