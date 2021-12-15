package de.monx.aoc.year21;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y21D14 extends Day {

	List<String> in = getInputList();
	long[] is = null;
	long[] ic = null;
	int[][] sm = null;

	@Override
	public Object part1() {
		init();
		return solve(10);
	}

	@Override
	public Object part2() {
		return solve(40);
	}

	boolean init() {
		Map<Character, Integer> charMap = new HashMap<>();
		Map<String, Integer> nodeMap = new HashMap<>();
		Map<String, Character> nextMap = new HashMap<>();
		for (int i = 2; i < in.size(); i++) {
			String str = in.get(i);
			charMap.putIfAbsent(str.charAt(0), charMap.size());
			charMap.putIfAbsent(str.charAt(1), charMap.size());
			charMap.putIfAbsent(str.charAt(6), charMap.size());

			String node = str.substring(0, 2);
			nodeMap.put(node, nodeMap.size());
			nextMap.put(node, str.charAt(6));
		}

		ic = new long[charMap.size()];
		is = new long[nodeMap.size()];
		sm = new int[nextMap.size()][3];
		char[] car = getInputList().get(0).toCharArray();
		ic[charMap.get(car[0])]++;
		for (int i = 1; i < car.length; i++) {
			ic[charMap.get(car[i])]++;
			String node = "" + car[i - 1] + car[i];
			is[nodeMap.get(node)]++;
		}

		for (var node : nextMap.keySet()) {
			char nc = nextMap.get(node);
			sm[nodeMap.get(node)][0] = charMap.get(nc);
			sm[nodeMap.get(node)][1] = nodeMap.get("" + node.charAt(0) + nc);
			sm[nodeMap.get(node)][2] = nodeMap.get("" + nc + node.charAt(1));
		}
		return true;
	}

	long solve(int steps) {

		long[] is = Arrays.copyOf(this.is, this.is.length);
		long[] ic = Arrays.copyOf(this.ic, this.ic.length);

		for (int step = 0; step < steps; step++) {
			long[] nis = new long[is.length];
			for (int i = 0; i < is.length; i++) {
				if (is[i] <= 0) {
					continue;
				}
				ic[sm[i][0]] += is[i];
				nis[sm[i][1]] += is[i];
				nis[sm[i][2]] += is[i];
			}
			is = nis;
		}
		return solveCount(ic);
	}

	long solveCount(long[] counter) {
		long[] mm = { Long.MAX_VALUE, -1 };
		for (var k : counter) {
			mm[0] = Math.min(mm[0], k);
			mm[1] = Math.max(mm[1], k);
		}
		return mm[1] - mm[0];
	}
}
