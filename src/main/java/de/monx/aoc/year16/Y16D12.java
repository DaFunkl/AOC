package de.monx.aoc.year16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.Pair;
import lombok.Data;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Y16D12 extends Day {

	@Override
	public Object part1() {
		return new Assembly(getInputList()).execute();
	}

	@Override
	public Object part2() {
		var asm = new Assembly(getInputList());
		asm.register.put("c", 1);
		return asm.execute();
	}

	@Data
	class Assembly {
		Map<String, Integer> register = new HashMap<>();
		List<Instruction> stack = new ArrayList<>();

		public Assembly(List<String> in) {
			in.forEach(x -> stack.add(new Instruction(x)));
		}

		int execute() {
			int ip = 0;
			while (ip >= 0 && ip < stack.size()) {
				var instr = stack.get(ip);
				switch (instr.cmd) {
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

		int value(Pair<String, Integer> arg) {
			if (arg.first != null) {
				if (!register.containsKey(arg.first)) {
					register.put(arg.first, 0);
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
		Pair<String, Integer> arg1;
		Pair<String, Integer> arg2;

		public Instruction(String line) {
			var spl = line.split(" ");
			cmd = spl[0];
			arg1 = parse(spl[1]);
			if (spl.length == 3) {
				arg2 = parse(spl[2]);
			}
		}

		Pair<String, Integer> parse(String s) {
			try {
				int a1 = Integer.valueOf(s);
				return new Pair(null, a1);
			} catch (Exception e) {
			}
			return new Pair(s, null);
		}
	}
}
