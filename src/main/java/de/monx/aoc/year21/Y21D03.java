package de.monx.aoc.year21;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y21D03 extends Day {
	List<String> in = new ArrayList<>();

	@Override
	public Object part1() {
		in = getInputList();

		int[] counter = new int[in.get(0).length()];
		for (var s : in) {
			var sar = s.toCharArray();
			for (int i = 0; i < sar.length; i++) {
				if (sar[i] == '1') {
					counter[i]++;
				}
			}
		}

		int half = in.size() / 2;
		String gammaStr = "";
		String epsilonStr = "";
		for (var x : counter) {
			if (x >= half) {
				gammaStr += "1";
				epsilonStr += "0";
			} else {
				gammaStr += "0";
				epsilonStr += "1";
			}
		}

		int g = Integer.parseInt(gammaStr, 2);
		int e = Integer.parseInt(epsilonStr, 2);
		return g * e;
	}

	@Override
	public Object part2() {
		String oxStr = "";
		String scStr = "";
		var spl = splitByAmt(in, 0);
		if (spl.first.size() < spl.second.size()) {
			oxStr = fetchP2Var(spl.second, true);
			scStr = fetchP2Var(spl.first, false);
		} else {
			oxStr = fetchP2Var(spl.first, true);
			scStr = fetchP2Var(spl.second, false);
		}
		int o = Integer.parseInt(oxStr, 2);
		int s = Integer.parseInt(scStr, 2);
		return o * s;
	}

	String fetchP2Var(List<String> in, boolean upper) {
		int i = 1;
		while (in.size() > 1) {
			var spl = splitByAmt(in, i++);
			if (upper == spl.first.size() < spl.second.size()) {
				in = spl.second;
			} else {
				in = spl.first;
			}
		}
		return in.get(0);
	}

	Pair<List<String>, List<String>> splitByAmt(List<String> in, int idx) {
		List<String> ones = new ArrayList<>();
		List<String> zeros = new ArrayList<>();
		for (var s : in) {
			if (s.charAt(idx) == '1') {
				ones.add(s);
			} else {
				zeros.add(s);
			}
		}
		return new Pair(ones, zeros);
	}

}
