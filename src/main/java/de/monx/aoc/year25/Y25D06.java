package de.monx.aoc.year25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y25D06 extends Day {

//	123 328  51 64 
//	 45 64  387 23 
//	  6 98  215 314
//	*   +   *   +  
	List<String> in = getInputList();
	String[] ops = new String[] {};

	@Override
	public Object part1() {
		in = getInputList();
		List<long[]> nums = new ArrayList<>();
		for (int i = 0; i < in.size(); i++) {
			in.set(i, in.get(i).replaceAll("\\s+", " ").trim());
			if (i == in.size() - 1) {
				ops = in.get(i).split(" ");
			} else {
				nums.add(Arrays.stream(in.get(i).split(" ")).mapToLong(Long::parseLong).toArray());
			}
		}
		long ret = 0;
		for (int i = 0; i < ops.length; i++) {
			long sol = ops[i].equals("*") ? 1 : 0;
			for (int j = 0; j < in.size() - 1; j++) {
				if (ops[i].equals("*")) {
					sol *= nums.get(j)[i];
				} else {
					sol += nums.get(j)[i];
				}
			}
			ret += sol;
		}
		return ret;
	}

	@Override
	public Object part2() {
		in = getInputList();
		char op = ops[0].charAt(0);
		int pi = 0;
		String le = in.get(in.size() - 1);
		long ret = 0;
		for (int i = 1; i < le.length(); i++) {
			if (le.charAt(i) != ' ' || i == le.length() - 1) {
				if (i == le.length() - 1) {
					i += 2;
				}
				long sol = op == '*' ? 1 : 0;
				for (int x = pi; x < i - 1; x++) {
					long nr = 0;
					for (int y = 0; y < in.size() - 1; y++) {
						char c = in.get(y).charAt(x);
						if (c == ' ') {
							continue;
						}
						nr *= 10;
						nr += Character.getNumericValue(c);
					}
					if (op == '*') {
						sol *= nr;
					} else {
						sol += nr;
					}
				}
				ret += sol;
				op = le.charAt(Math.min(i, le.length() - 1));
				pi = i;
			}
		}
		return ret;
	}
}
