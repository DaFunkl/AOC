package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import lombok.Data;

public class Y22D11 extends Day {
	List<Monkey> monkeys = new ArrayList<>();
	List<List<Long>> initialItems = new ArrayList<>();

	@Override
	public Object part1() {
		monkeys = Arrays.stream(getFileString().split("\r\n\r\n")).map(x -> new Monkey(x)).toList();
		return solve(20, 3);
	}

	@Override
	public Object part2() {
		return solve(10000, 1);
	}

	long solve(int rounds, long div) {
		long mod = monkeys.stream().map(x -> x.divBy).reduce(1l, (a, b) -> a * b);
		List<List<Long>> items = new ArrayList<>();
		for (var i : initialItems) {
			items.add(new ArrayList<>());
			for (var j : i) {
				items.get(items.size() - 1).add(j);
			}
		}
		long[] inspects = new long[monkeys.size()];
		for (int i = 0; i < rounds; i++) {
			for (int m = 0; m < monkeys.size(); m++) {
				var monkey = monkeys.get(m);
				var operated = monkey.operate(div, mod, items.get(monkey.idx));
				items.set(monkey.idx, new ArrayList<>());
				inspects[m] += operated.length;
				for (var item : operated) {
					items.get((int) item[1]).add(item[0]);
				}
			}
		}
		Arrays.sort(inspects);
		return inspects[inspects.length - 1] * inspects[inspects.length - 2];
	}

	Map<Long, Long> modCache = new HashMap<>();

	@Data
	class Monkey {
		static final int _MUL = 0;
		static final int _ADD = 1;
		static final long _OLD = -1;
		static int idxCount = 0;
		int idx = idxCount++;
		int op = -1;
		long isOld = 0;
		long adder = 0;
		long divBy;
		long[] throwTo = new long[2];

		long[][] operate(long div, long mod, List<Long> items) {
			long[][] ret = new long[items.size()][2];
			for (int i = 0; i < items.size(); i++) {
				long sec = items.get(i) * isOld + adder;
				if (op == _MUL) {
					ret[i][0] = ((items.get(i) * sec) / div) % mod;
				} else {
					ret[i][0] = ((items.get(i) + sec) / div) % mod;
				}

				ret[i][1] = (ret[i][0] % divBy) == 0l //
						? throwTo[0]
						: throwTo[1];
			}
			return ret;
		}

		public Monkey(String str) {
			initialItems.add(new ArrayList<>());
			for (var l : str.split("\r\n")) {
				if (l.charAt(2) == 'S') { // Starting Items
					for (var i : l.split(": ")[1].split(", ")) {
						initialItems.get(idx).add(Long.valueOf(i));
					}
				} else if (l.charAt(2) == 'O') { // Operation
					var ll = l.split(" = old ")[1].split(" ");
					if (ll[0].equals("+")) {
						op = _ADD;
					} else if (ll[0].equals("*")) {
						op = _MUL;
					} else {
						System.err.println("Unknown Operator: " + l);
					}
					if (ll[1].equals("old")) {
						isOld = 1;
					} else {
						adder = Long.valueOf(ll[1]);
					}
				} else if (l.charAt(2) == 'T') { // Test
					divBy = Long.valueOf(l.split("by ")[1]);
				} else if (l.charAt(8) == 'r') { // if true
					throwTo[0] = Long.valueOf(l.split("y ")[1]);
				} else if (l.charAt(8) == 'a') { // if false
					throwTo[1] = Long.valueOf(l.split("y ")[1]);
				}
			}
		}
	}

}
