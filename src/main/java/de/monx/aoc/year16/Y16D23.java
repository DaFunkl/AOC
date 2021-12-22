package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import de.monx.aoc.util.common.Pair;
import lombok.Data;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y16D23 extends Day {

	@Override
	public Object part1() {
		var asm = new Assembly(getInputList());
		asm.register.put("a", 7l);
		return asm.execute();
	}

	@Override
	public Object part2() {
		return p2();
	}

	long p2() {
		List<String> in = getInputList();

		int idx = 0;
		long[] ls = new long[2];
		for (int i = in.size() - 1; i > 0; i--) {
			Integer a = Util.isInteger(in.get(i).split(" ")[1]);
			if (a != null && a > 10) {
				ls[idx++] = a;
			}
			if (idx > 1) {
				break;
			}
		}
		return 479001600 + (ls[0] * ls[1]);
	}

	@Data
	class Assembly {
		Map<String, Long> register = new HashMap<>();
		List<Instruction> stack = new ArrayList<>();

		public Assembly(List<String> in) {
			in.forEach(x -> stack.add(new Instruction(x)));
		}

		long execute() {
			int ip = 0;
			while (ip >= 0 && ip < stack.size()) {
				var instr = stack.get(ip);
				switch (instr.cmd) {
				case "tgl":
					int amt = (int) value(instr.arg1);
					if (ip + amt >= 0 && ip + amt < stack.size()) {
						stack.get(ip + amt).tgl();
					}
					break;
				case "cpy":
					register.put(instr.arg2.first, value(instr.arg1));
					break;
				case "inc":
					register.put(instr.arg1.first, register.get(instr.arg1.first) + 1);
					break;
				case "dec":
					register.put(instr.arg1.first, register.get(instr.arg1.first) - 1);
					break;
				case "jnz":
					if (value(instr.arg1) != 0) {
						ip += value(instr.arg2) - 1;
					}
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + instr.cmd);
				}
				ip++;
			}
			return register.get("a");
		}

		long value(Pair<String, Long> arg) {
			if (arg.first != null) {
				if (!register.containsKey(arg.first)) {
					register.put(arg.first, 0l);
				}
				return register.get(arg.first);
			} else {
				return arg.second;
			}
		}
	}

	@Data
	class Instruction {
		String cmd;
		Pair<String, Long> arg1;
		Pair<String, Long> arg2;

		public Instruction(String line) {
			var spl = line.split(" ");
			cmd = spl[0];
			arg1 = parse(spl[1]);
			if (spl.length == 3) {
				arg2 = parse(spl[2]);
			}
		}

		public void tgl() {
			cmd = switch (cmd) {
			case "inc" -> "dec";
			case "dec" -> "inc";
			case "tgl" -> "inc";
			case "cpy" -> "jnz";
			case "jnz" -> "cpy";

			default -> errorTgl(this);
			};
		}

		String errorTgl(Instruction s) {
			System.err.println("Error TGL: " + s);
			return "ERR";
		}

		Pair<String, Long> parse(String s) {
			try {
				long a1 = Long.valueOf(s);
				return new Pair(null, a1);
			} catch (Exception e) {
			}
			return new Pair(s, null);
		}
	}
}
