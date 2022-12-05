package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y22D05 extends Day {

	List<List<Character>> stacks = null;
	List<int[]> instructions = null;

	@Override
	public Object part1() {
		init();
		for (var instr : instructions) {
			for (int i = 0; i < instr[0]; i++) {
				stacks.get(instr[2]).add(0, stacks.get(instr[1]).get(0));
				stacks.get(instr[1]).remove(0);
			}
		}
		String ret = "";
		for (var st : stacks) {
			if (!st.isEmpty()) {
				ret += st.get(0);
			}
		}
		return ret.trim();
	}

	@Override
	public Object part2() {
		init();
		for (var instr : instructions) {
			for (int i = instr[0] - 1; i >= 0; i--) {
				stacks.get(instr[2]).add(0, stacks.get(instr[1]).get(i));
				stacks.get(instr[1]).remove(i);
			}
		}
		String ret = "";
		for (var st : stacks) {
			if (!st.isEmpty()) {
				ret += st.get(0);
			}
		}
		return ret.trim();
	}

	void init() {
		boolean instrPhase = false;
		instructions = new ArrayList<>();
		stacks = null;
		for (var str : getInputList()) {
			if (instrPhase) {
				String[] arr = str.split(" ");
				instructions.add(new int[] { //
						Integer.valueOf(arr[1]), //
						Integer.valueOf(arr[3]) - 1, //
						Integer.valueOf(arr[5]) - 1, //
				});
			} else {
				if (str.isBlank()) {
					instrPhase = true;
					continue;
				}
				if (!str.contains("[")) {
					continue;
				}
				if (stacks == null) {
					stacks = new ArrayList<>();
					for (int i = 0; i < (str.length() + 1) / 4; i++) {
						stacks.add(new ArrayList<>());
					}
				}
				char[] arr = str.toCharArray();
				for (int i = 1; i < arr.length; i += 4) {
					if (arr[i] != ' ') {
						stacks.get((i - 1) / 4).add(arr[i]);
					}
				}
			}
		}
	}
}
