package de.monx.aoc.year22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y22D11 extends Day {

	@Override
	public Object part1() {
		return solve(20, 3);
	}

	@Override
	public Object part2() {
		return solve(10000, 1);
	}

	long solve(int rounds, long div) {
		List<Monkey> monkeys = Arrays.stream(getFileString().split("\r\n\r\n")).map(x -> new Monkey(x)).toList();
		long mod = monkeys.stream().map(x -> x.divBy).reduce(1l, (a, b) -> a * b);
		long[] inspects = new long[monkeys.size()];
		for (int i = 0; i < rounds; i++) {
			for (int m = 0; m < monkeys.size(); m++) {
				var monkey = monkeys.get(m);
				var operated = monkey.operate(div, mod);
				inspects[m] += operated.length;
				for (var item : operated) {
					monkeys.get((int) item[1]).items.add(item[0]);
				}
			}
		}
		Arrays.sort(inspects);
		return inspects[inspects.length - 1] * inspects[inspects.length - 2];
	}

	@Data
	static class Monkey {
		static final int _MUL = 0;
		static final int _ADD = 1;
		static final long _OLD = -1;
		List<Long> items = new ArrayList<>();
		int op = -1;
		long second;
		long divBy;
		long[] throwTo = new long[2];

		long[][] operate(long div, long mod) {
			long[][] ret = new long[items.size()][2];
			for (int i = 0; i < items.size(); i++) {
				long sec = second;
				if (second == _OLD) {
					sec = items.get(i);
				}
				if (op == _MUL) {
					ret[i][0] = ((items.get(i) * sec) / div) % mod;
				} else {
					ret[i][0] = ((items.get(i) + sec) / div) % mod;
				}
				ret[i][1] = (ret[i][0] % divBy) == 0l //
						? throwTo[0]
						: throwTo[1];
			}
			items = new ArrayList<>();
			return ret;
		}

		public Monkey(String str) {
			for (var l : str.split("\r\n")) {
				if (l.charAt(2) == 'S') { // Starting Items
					for (var i : l.split(": ")[1].split(", ")) {
						items.add(Long.valueOf(i));
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
						second = _OLD;
					} else {
						second = Long.valueOf(ll[1]);
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
