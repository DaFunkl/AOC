package de.monx.aoc.year23;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y23D08 extends Day {
	List<String> in = getInputList();
	char[] dir = null;
	Map<String, String[]> opts = new HashMap<>();

	@Override
	public Object part1() {
		dir = in.get(0).toCharArray();
		for (int i = 2; i < in.size(); i++) {
			String[] sar = in.get(i).split(" = ");
			opts.put(sar[0], sar[1].replace("(", "").replace(")", "").split(", "));
		}
		String cur = "AAA";
		int steps = 0;
		while (!cur.equals("ZZZ")) {
			cur = opts.get(cur)[dir[steps++ % dir.length] == 'R' ? 1 : 0];
		}
		return steps;
	}

	@Override
	public Object part2() {
		String[] todos = opts.keySet().stream().filter(x -> x.endsWith("A")).toArray(String[]::new);
		int[] seen = new int[todos.length];
		int steps = 0;
		int idx = 0;
		int reached = 0;
		while (reached < todos.length) {
			steps++;
			for (int i = 0; i < todos.length; i++) {
				if (seen[i] != 0) {
					continue;
				}
				todos[i] = opts.get(todos[i])[dir[idx] == 'R' ? 1 : 0];
				if (todos[i].endsWith("Z")) {
					if (seen[i] == 0) {
						seen[i] = steps;
						reached++;
					}
				}
			}
			idx = (idx + 1) % dir.length;
		}
		BigInteger ret = new BigInteger("" + seen[0]);
		for (int i = 1; i < seen.length; i++) {
			BigInteger bi = new BigInteger("" + seen[i]);
			ret = ret.multiply(bi).divide(ret.gcd(bi));
		}
		return ret;
	}

}