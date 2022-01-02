package de.monx.aoc.year17;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y17D8 extends Day {
	int ret1, ret2;

	@Override
	public Object part1() {
		solve();
		return ret1;
	}

	@Override
	public Object part2() {
		return ret2;
	}

	void solve() {
		Map<String, Integer> regs = new HashMap<>();
		ret2 = Integer.MIN_VALUE;
		for (var s : getInputList()) {
			new Instr(s).exec(regs);
			if (!regs.isEmpty()) {
				ret2 = Integer.max(regs.values().stream().max(Integer::compare).get(), ret2);
			}
		}
		ret1 = regs.values().stream().max(Integer::compare).get();
	}

	static class Instr {
		String reg1, reg2, op;
		int cmp, amt;

		public Instr(String str) {
			var sar = str.split(" ");
			reg1 = sar[0];
			amt = Integer.valueOf(sar[2]);
			if (sar[1].equals("dec")) {
				amt *= -1;
			}
			reg2 = sar[4];
			op = sar[5];
			cmp = Integer.valueOf(sar[6]);
		}

		void exec(Map<String, Integer> regs) {
			int rv = val(regs, reg1);
			int rcp = val(regs, reg2);
			// ml inc -357 if uz == 565
			if (switch (op) {
			case "<" -> rcp < cmp;
			case ">" -> rcp > cmp;
			case "<=" -> rcp <= cmp;
			case ">=" -> rcp >= cmp;
			case "==" -> rcp == cmp;
			case "!=" -> rcp != cmp;
			default -> err(op);
			}) {
				regs.put(reg1, rv + amt);
			}
		}

		boolean err(String op) {
			System.err.println("Unkown OP: " + op);
			return false;
		}

		int val(Map<String, Integer> regs, String reg) {
			return regs.getOrDefault(reg, 0);
		}
	}

}
