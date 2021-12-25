package de.monx.aoc.year21;

import java.util.List;
import java.util.Stack;

import de.monx.aoc.util.Day;

public class Y21D24 extends Day {
	List<String> in = getInputList();

	@Override
	public Object part1() {
		return solve(true);
	}

	@Override
	public Object part2() {
		return solve(false);
	}

	Object solve(boolean p1) {
		int[] ret = new int[14];
		Stack<int[]> stack = new Stack<>();

		for (int i = 0; i < 14; i++) {
			int xAdder = Integer.valueOf(in.get(18 * i + 5).split(" ")[2]);
			int yAdder = Integer.valueOf(in.get(18 * i + 15).split(" ")[2]);
			if (xAdder > 0) {
				stack.push(new int[] { yAdder, i });
			} else {
				var state = stack.pop();
				yAdder = state[0];
				int fIdx = state[1];
				int add = 0;
				if (p1) {
					add = 9;
					while (add + yAdder + xAdder > 9) {
						add--;
					}
				} else {
					add++;
					while (add + yAdder + xAdder < 1) {
						add++;
					}
				}
				ret[fIdx] = add;
				ret[i] = add + yAdder + xAdder;
			}
		}
		String retStr = "";
		for (var r : ret) {
			retStr += r;
		}
		return retStr;
	}

}
