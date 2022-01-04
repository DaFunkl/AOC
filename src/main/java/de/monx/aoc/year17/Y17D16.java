package de.monx.aoc.year17;

import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;

public class Y17D16 extends Day {
	String[] in = getInputString().split(",");
	String p1 = "";

	@Override
	public Object part1() {
		String msg = "abcdefghijklmnop";
//		String msg = "abcde";
		for (var op : in) {
			msg = switch (op.charAt(0)) {
			case 's' -> sop(msg, op.substring(1));
			case 'x' -> xop(msg, op.substring(1));
			case 'p' -> pop(msg, op.substring(1));
			default -> null;
			};
		}
		p1 = msg;
		return msg;
	}

	@Override
	public Object part2() {
		String msg = p1;
		Map<String, Long> seen = new HashMap<>();
		seen.put(msg, 0l);
		boolean skipSeen = false;
		for (long i = 1; i < 1_000_000_000l; i++) {
			if (i % 1_000l == 0) {
				System.out.println(i + " / 1_000_000_000l");
			}
			for (var op : in) {
				msg = switch (op.charAt(0)) {
				case 's' -> sop(msg, op.substring(1));
				case 'x' -> xop(msg, op.substring(1));
				case 'p' -> pop(msg, op.substring(1));
				default -> null;
				};
			}
			if (!skipSeen) {
				if (seen.containsKey(msg)) {
					skipSeen = true;
					long p = seen.get(msg);
					long cl = i - p;
					while(i < 1_000_000_001l) {
						i += cl;
					}
					i -= cl;
				} else {
					seen.put(msg, i);
				}
			}
		}
		return msg;
	}

	String sop(String msg, String op) {
		int mover = Integer.valueOf(op);
		int offset = msg.length() - mover;
		String a = msg.substring(offset);
		String b = msg.substring(0, offset);
		return a + b;
	}

	String xop(String msg, String op) {
		var sar = op.split("/");
		int a = Integer.valueOf(sar[0]);
		int b = Integer.valueOf(sar[1]);
		var car = msg.toCharArray();
		char c = car[a];
		car[a] = car[b];
		car[b] = c;
		return new String(car);
	}

	String pop(String msg, String op) {
		var sar = op.split("/");
		return msg.replace(sar[0], "@").replace(sar[1], sar[0]).replace("@", sar[1]);
	}
}
