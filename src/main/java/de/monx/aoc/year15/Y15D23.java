package de.monx.aoc.year15;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y15D23 extends Day {

	List<String> opCode = getInputList();

	@Override
	public Object part1() {
		return solve(0, 0);
	}

	@Override
	public Object part2() {
		return solve(1, 0);
	}

	int solve(int a, int b) {
		Map<String, Integer> regs = new HashMap<>();
		regs.put("a", a);
		regs.put("b", b);

		for (int ip = 0; ip < opCode.size(); ip++) {
			var opc = opCode.get(ip);
			var op = opc.replace(", ", " ").split(" ");
			switch (op[0]) { //@formatter:off
				case "hlf": regs.put(op[1], regs.get(op[1]) >> 1); break;
				case "tpl": regs.put(op[1], regs.get(op[1]) * 3); break;
				case "inc": regs.put(op[1], regs.get(op[1]) + 1); break;
				case "jmp": ip += Integer.valueOf(op[1]) - 1; break;
				case "jie": if (regs.get(op[1]) % 2 == 0) ip += Integer.valueOf(op[2]) - 1; break;
				case "jio": if (regs.get(op[1]) == 1) ip += Integer.valueOf(op[2]) - 1; break;
				default: System.err.println("Unkown opCode[" + op[0] + "] -> " + opc); break;
				}//@formatter:on
		}
		return regs.get("b");
	}
}
