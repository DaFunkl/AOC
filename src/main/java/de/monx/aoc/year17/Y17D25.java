package de.monx.aoc.year17;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y17D25 extends Day {
	List<String> in = getInputList();
	int steps = 0;
	int[][][] op = init();

	@Override
	public Object part1() {
		Map<Integer, Integer> tape = new HashMap<>();
		int ip = 0;
		int is = 0;

		for (int s = 0; s < steps; s++) {
			int val = tape.getOrDefault(ip, 0);
			tape.put(ip, op[is][val][0]);
			ip += op[is][val][1];
			is = op[is][val][2];
		}
		System.out.println(in.size() / 9);
		return tape.values().stream().filter(x -> x == 1).count();
	}

	@Override
	public Object part2() {
		return "Wub Wub >=]";
	}

	int[][][] init() {
		int[][][] ret = new int[in.size() / 9][2][3];
		steps = Integer.valueOf(in.get(1).split(" ")[5]);
		for (int i = 0; i < ret.length; i++) {
			int s = i * 10 + 5;
			ret[i][0][0] = Character.getNumericValue(in.get(s).charAt(22));
			ret[i][0][1] = in.get(s + 1).endsWith("right.") ? 1 : -1;
			ret[i][0][2] = in.get(s + 2).charAt(26) - 'A';

			s += 4;
			ret[i][1][0] = Character.getNumericValue(in.get(s).charAt(22));
			ret[i][1][1] = in.get(s + 1).endsWith("right.") ? 1 : -1;
			ret[i][1][2] = in.get(s + 2).charAt(26) - 'A';
		}

		return ret;
	}

}
