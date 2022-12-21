package de.monx.aoc.year22;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;
import lombok.Data;

public class Y22D21 extends Day {

	@Override
	public Object part1() {
		Map<String, Monkey> monks = init();
		while (monks.get("root").waiting) {
			for (var k : monks.keySet()) {
				var monk = monks.get(k);
				if (!monk.waiting) {
					continue;
				}
				var monk1 = monks.get(monk.name1);
				if (monk1.waiting) {
					continue;
				}
				var monk2 = monks.get(monk.name2);
				if (monk2.waiting) {
					continue;
				}
				monk.operate(monk1.result, monk2.result);
			}
		}

		return monks.get("root").result;
	}

	@Override
	public Object part2() {
		Map<String, Monkey> monks = init();
		monks.get("root").operator = "=";
		var monk = monks.get("humn");
		monk.name1 = "X";
		monk.name2 = "X";
		monk.operator = "X";
		monk.waiting = true;
		boolean changed = true;
		while (changed) {
			changed = false;
			for (var k : monks.keySet()) {
				monk = monks.get(k);
				if (!monk.waiting || monk.name.equals("humn")) {
					continue;
				}
				var monk1 = monks.get(monk.name1);
				var monk2 = monks.get(monk.name2);

				if (!monk1.waiting) {
					monk.w1 = false;
					monk.res1 = monk1.result;
				}
				if (!monk2.waiting) {
					monk.w2 = false;
					monk.res2 = monk2.result;
				}
				if (!monk.isWaiting()) {
					monk.operate(monk1.result, monk2.result);
					changed = true;
				}
			}
		}
		monk = monks.get("root");
		long y = 0;
		while (!monk.name.equals("humn")) {
			long num1 = y;
			long num2 = monk.w1 ? monk.res2 : monk.res1;
//			 x + 5 = 10 | - 5
//			 5 + x = 10 | - 5
//			 x - 10 = 2 | + 10 => 2 + 10 = 12
//			 10 - x = 2 | + x - 2 => 10 - 2 <--- this case
			if (monk.w2 && monk.operator.equals("-")) {
				monk.operator = "+";
				num1 = num2;
				num2 = y;
			}

//			 x * 5 = 10 | / 5
//			 5 * x = 10 | / 5
//			 x / 10 = 2 | * 10 => 10 * 2
//			 10 / x = 2 | * x / 2 => 10 / 2 <-- this case
			if (monk.w2 && monk.operator.equals("/")) {
				System.out.println("c2");
				monk.operator = "*";
			}

			y = switch (monk.operator) {
			case "+" -> num1 - num2;
			case "-" -> num1 + num2;
			case "*" -> div(num1, num2);// y / num;
			case "/" -> num1 * num2;
			case "=" -> num2;
			default -> Monkey.error(monk);
			};
			monk = monk.w1 ? monks.get(monk.name1) : monks.get(monk.name2);
		}
		return y;
	}

	long div(long a, long b) {
		long r = a / b;
		if (r * b != a) {
			System.err.println(a + " / " + b + " = " + r + " -> floored");
		}
		return r;
	}

	String formular(Map<String, Monkey> map, String key) {
		StringBuilder sb = new StringBuilder();
		if (!map.containsKey(key)) {
			return key;
		}
		var monk = map.get(key);
		if (monk.waiting) {
			sb.append("(");
			sb.append(formular(map, monk.name1));
			sb.append(monk.operator);
			sb.append(formular(map, monk.name2));
			sb.append(")");
		} else {
			sb.append(monk.result);
		}
		return sb.toString();
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
