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
		int i = 0;
		while (!cur.equals("ZZZ")) {
			cur = opts.get(cur)[dir[steps++ % dir.length] == 'R' ? 1 : 0];
		}
		return steps;
	}

	@Override
	public Object part2() {
		String[] todos = opts.keySet().stream().filter(x -> x.charAt(2) == 'A').toArray(String[]::new);
		int[] seen = new int[todos.length];
		int steps = 0;
		int idx = 0;
		int reached = 0;
		BigInteger ret = new BigInteger("1");
		while (reached < todos.length) {
			steps++;
			for (int i = 0; i < todos.length; i++) {
				if (seen[i] != 0) {
					continue;
				}
				todos[i] = opts.get(todos[i])[dir[idx] == 'R' ? 1 : 0];
				if (todos[i].charAt(2) == 'Z') {
					if (seen[i] == 0) {
						BigInteger bi = new BigInteger("" + steps);
						ret = ret.multiply(bi).divide(ret.gcd(bi));
						seen[i] = steps;
						reached++;
					}
				}
			}
			idx = (idx + 1) % dir.length;
		}
		return ret;
	}

}