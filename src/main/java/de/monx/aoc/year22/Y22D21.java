package de.monx.aoc.year22;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import lombok.Data;

public class Y22D21 extends Day {
	Map<String, Monkey> monks1 = init();
	Map<String, Monkey> monks2 = init();

	@Override
	public Object part1() {
		return fetchValue(monks1, "root").result;
	}

	Monkey fetchValue(Map<String, Monkey> monks, String key) {
		var monk = monks.get(key);
		if (!monk.waiting) {
			return monk;
		}
		monk.operate(fetchValue(monks, monk.name1).result, fetchValue(monks, monk.name2).result);
		return monk;
	}

	Monkey fetchValue2(Map<String, Monkey> monks, String key) {
		var monk = monks.get(key);
		if (!monk.waiting || key.equals("humn")) {
			return monk;
		}

		var monk1 = fetchValue2(monks, monk.name1);
		var monk2 = fetchValue2(monks, monk.name2);

		if (!monk1.waiting) {
			monk.w1 = false;
			monk.res1 = monk1.result;
		}
		if (!monk2.waiting) {
			monk.w2 = false;
			monk.res2 = monk2.result;
		}
		if (!monk.isWaiting()) {
			monk.operate(fetchValue(monks, monk.name1).result, fetchValue(monks, monk.name2).result);
		}
		return monk;
	}

	@Override
	public Object part2() {
//		Map<String, Monkey> monks2 = init();
		monks2.get("root").operator = "=";
		var monk = monks2.get("humn");
		monk.name1 = "X";
		monk.name2 = "X";
		monk.operator = "X";
		monk.waiting = true;
		monk = fetchValue2(monks2, "root");
		long y = 0;
		while (!monk.name.equals("humn")) {
			long num1 = y;
			long num2 = monk.w1 ? monk.res2 : monk.res1;
			if (monk.w2 && monk.operator.equals("-")) {
				monk.operator = "+";
				num1 = num2;
				num2 = y;
			}
			if (monk.w2 && monk.operator.equals("/")) {
				System.out.println("c2");
				monk.operator = "*";
			}

			y = switch (monk.operator) {
			case "+" -> num1 - num2;
			case "-" -> num1 + num2;
			case "*" -> num1 / num2;
			case "/" -> num1 * num2;
			case "=" -> num2;
			default -> Monkey.error(monk);
			};
			monk = monk.w1 ? monks2.get(monk.name1) : monks2.get(monk.name2);
		}
		return y;
	}

	Map<String, Monkey> init() {
		Map<String, Monkey> ret = new HashMap<>();
		for (var str : getInputList()) {
			var monk = new Monkey(str);
			ret.put(monk.name, monk);
		}
		return ret;
	}

	@Data
	class Monkey {
		String name;
		long result = 0;
		String name1;
		boolean w1 = true;
		long res1 = 0;
		String name2;
		boolean w2 = true;
		long res2 = 0;

		String operator;
		boolean waiting = true;

		public Monkey(String str) {
			var sar = str.split(": ");
			name = sar[0];
			sar = sar[1].split(" ");
			if (sar.length == 1) {
				result = Long.valueOf(sar[0]);
				waiting = false;
			} else {
				name1 = sar[0];
				operator = sar[1];
				name2 = sar[2];
			}
		}

		boolean isWaiting() {
			return w1 || w2;
		}

		void operate(long a, long b) {
			waiting = false;
			result = switch (operator) {
			case "+" -> a + b;
			case "-" -> a - b;
			case "*" -> a * b;
			case "/" -> a / b;
			case "=" -> a == b ? 1 : 0;
			default -> error(this);
			};
		}

		static long error(Monkey m) {
			System.err.println("op Error: " + Util.gsonPretty.toJson(m));
			return 0;
		}

	}
}
