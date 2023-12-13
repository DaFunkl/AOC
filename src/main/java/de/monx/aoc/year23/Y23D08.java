package de.monx.aoc.year23;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y23D08 extends Day {
	List<String> in = getInputList();
	char[] dir = null;
	int[][] opts = null;
	List<Integer> starts = new ArrayList<>();
	boolean[] goals;

	@Override
	public Object part1() {
		dir = in.get(0).toCharArray();
		int aaa = -1;
		int zzz = -1;
		opts = new int[Arrays.hashCode("ZZZ".toCharArray()) + 1][2];
		goals = new boolean[Arrays.hashCode("ZZZ".toCharArray()) + 1];
		for (int i = 2; i < in.size(); i++) {
			String[] sar = in.get(i).split(" = ");
			int n1 = Arrays.hashCode(sar[0].toCharArray());
			if (sar[0].equals("AAA")) {
				aaa = n1;
			}
			if (sar[0].charAt(2) == 'A') {
				starts.add(n1);
			}
			if (sar[0].equals("ZZZ")) {
				zzz = n1;
			}
			if (sar[0].charAt(2) == 'Z') {
				goals[n1] = true;
			}

			sar = sar[1].replace("(", "").replace(")", "").split(", ");
			int n2 = Arrays.hashCode(sar[0].toCharArray());
			int n3 = Arrays.hashCode(sar[1].toCharArray());
			opts[n1] = new int[] { n2, n3 };
		}
		int cur = aaa;
		int steps = 0;
		while (cur != zzz) {
			cur = opts[cur][dir[steps++ % dir.length] == 'R' ? 1 : 0];
		}
		return steps;
	}

	@Override
	public Object part2() {
		int[] todos = starts.stream().mapToInt(i -> i).toArray();
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
				todos[i] = opts[todos[i]][dir[idx] == 'R' ? 1 : 0];
				if (goals[todos[i]]) {
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