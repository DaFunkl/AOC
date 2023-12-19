package de.monx.aoc.year23;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.Data;

public class Y23D19 extends Day {
	List<String> in = getInputList();
	Map<String, List<WF>> worlkflows = new HashMap<>();
	List<int[]> parts = new ArrayList<>();
	int mc = 4000;

	@Override
	public Object part1() {
		init();
		int[] ret = new int[4];

		for (var p : parts) {
			String cs = "in";
			while (!(cs.equals("R") || cs.equals("A"))) {
				var wfs = worlkflows.get(cs);
				for (var wf : wfs) {
					var ns = wf.nextState(p);
					if (ns != null) {
						cs = ns;
						break;
					}
				}
			}
			if (cs.equals("A")) {
				for (int i = 0; i < 4; i++) {
					ret[i] += p[i];
				}
			}
		}

		return ret[0] + ret[1] + ret[2] + ret[3];
	}

	@Override
	public Object part2() {

		List<int[][]> ranges = new ArrayList<>();
		ArrayDeque<Pair<String, int[][]>> stack = new ArrayDeque<>();
		stack.push(new Pair<>("in", new int[][] { { 1, 1, 1, 1 }, { mc, mc, mc, mc } }));
		while (!stack.isEmpty()) {
			var cur = stack.pop();
			if (cur.first.equals("R")) {
				continue;
			}
			if (cur.first.equals("A")) {
				ranges.add(cur.second);
				continue;
			}
			var wfs = worlkflows.get(cur.first);
			int[][] cr = { Arrays.copyOf(cur.second[0], 4), Arrays.copyOf(cur.second[1], 4) };
			for (var wf : wfs) {
				var ns = wf.adjustRanges(cr);
				if (ns != null) {
					stack.push(ns);
				}
				cr = wf.adjustRangesAfter(cr);
				if (cr == null) {
					break;
				}
			}
		}

		BigInteger ret = new BigInteger("0");
		for (var r : ranges) {
			BigInteger adder = new BigInteger("1");
			for (int i = 0; i < 4; i++) {
				adder = adder.multiply(new BigInteger("" + (1 + r[1][i] - r[0][i])));
			}
			ret = ret.add(adder);
		}

		return ret;
	}

	void init() {
		boolean wf = true;
		for (var str : in) {
			if (str.isBlank()) {
				wf = false;
				continue;
			}
			if (wf) {
				var spl = str.split("\\{"); //
				var k = spl[0];
				spl = spl[1].substring(0, spl[1].length() - 1).split(",");
				List<WF> wfs = new ArrayList<>();
				for (var p : spl) {
					wfs.add(new WF(p));
				}
				worlkflows.put(k, wfs);
			} else {
				var spl = str.replace("{x=", "") //
						.replace("m=", "") //
						.replace("a=", "") //
						.replace("s=", "") //
						.replace("}", "").split(",");
				int[] ia = new int[4];
				for (int i = 0; i < 4; i++) {
					ia[i] = Integer.valueOf(spl[i]);
				}
				parts.add(ia);
			}
		}
	}

	@Data
	class WF {
		String next;
		int cmp = -1;
		int val = -1;
		boolean smaller = false;

		public WF(String s) {
			if (!s.contains(":")) {
				next = s;
				return;
			}
			var spl = s.split(":");
			next = spl[1];
			var splitter = ">";
			if (spl[0].contains("<")) {
				smaller = true;
				splitter = "<";
			}
			spl = spl[0].split(splitter);
			val = Integer.valueOf(spl[1]);
			cmp = switch (spl[0]) {
			case "x" -> 0;
			case "m" -> 1;
			case "a" -> 2;
			case "s" -> 3;
			default -> throw new IllegalArgumentException("Unexpected value: " + spl[0]);
			};
		}

		String nextState(int[] nums) {
			if (cmp == -1) {
				return next;
			}
			if (smaller && nums[cmp] < val) {
				return next;
			}
			if (!smaller && nums[cmp] > val) {
				return next;
			}
			return null;
		}

		Pair<String, int[][]> adjustRanges(int[][] nums) {
			int[][] ret = { Arrays.copyOf(nums[0], 4), Arrays.copyOf(nums[1], 4) };
			if (cmp == -1) {
				return new Pair<>(next, ret);
			}
			if (smaller && nums[0][cmp] < val) {
				if (nums[1][cmp] > val) {
					ret[1][cmp] = val - 1;
				}
				return new Pair<>(next, ret);
			}

			if (!smaller && nums[1][cmp] > val) {
				if (nums[0][cmp] <= val) {
					ret[0][cmp] = val + 1;
				}
				return new Pair<>(next, ret);
			}
			return null;
		}

		int[][] adjustRangesAfter(int[][] nums) {
			int[][] ret = { Arrays.copyOf(nums[0], 4), Arrays.copyOf(nums[1], 4) };
			if (cmp == -1) {
				return ret;
			}
			if (smaller && nums[0][cmp] < val) {
				if (nums[1][cmp] < val) {
					return null;
				}
				ret[0][cmp] = val;
				return ret;
			}

			if (!smaller && nums[1][cmp] > val) {
				if (nums[0][cmp] > val) {
					return null;
				}
				ret[1][cmp] = val;
				return ret;
			}
			return null;
		}
	}
}
