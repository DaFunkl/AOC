package de.monx.aoc.year17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y17D18 extends Day {
	List<String> in = getInputList();

	@Override
	public Object part1() {
		int ip = 0;
		Map<String, Long> regs = new HashMap<>();
		Map<String, Long> freq = new HashMap<>();
		while (ip >= 0 && ip < in.size()) {
			var sar = in.get(ip).split(" ");
			switch (sar[0]) {
			case "snd":
				freq.put(sar[1], value(sar[1], regs));
				break;
			case "set":
				regs.put(sar[1], value(sar[2], regs));
				break;
			case "add":
				regs.put(sar[1], value(sar[1], regs) + value(sar[2], regs));
				break;
			case "mul":
				regs.put(sar[1], value(sar[1], regs) * value(sar[2], regs));
				break;
			case "mod":
				regs.put(sar[1], value(sar[1], regs) % value(sar[2], regs));
				break;
			case "rcv":
				if (freq.getOrDefault(sar[1], -1l) > 0) {
					return freq.get(sar[1]);
				}
				break;
			case "jgz":
				if (value(sar[1], regs) > 0) {
					ip += value(sar[2], regs);
					ip--;
				}
				break;
			default:
				System.err.println("Unknown op:_" + Arrays.toString(sar));
				break;
			}
			ip++;
		}
		return null;
	}

	@Override
	public Object part2() {
		Program a = new Program();
		Program b = new Program();
		b.regs.put("p", 0l);
		b.regs.put("p", 1l);

		b.rcv = a.snd;
		a.rcv = b.snd;
		a.run(in);
		while (!(a.snd.isEmpty() && b.snd.isEmpty())) {
			if (!a.snd.isEmpty()) {
				b.run(in);
			} else if (!b.snd.isEmpty()) {
				a.run(in);
			} else {
				break;
			}
		}
		return a.count;
	}

	@Data
	static class Program {
		Map<String, Long> regs = new HashMap<>();
		int ip = 0;

		long count = 0;

		List<Long> snd = new ArrayList<>();
		List<Long> rcv = new ArrayList<>();

		void run(List<String> in) {
			while (ip >= 0 && ip < in.size()) {
				var sar = in.get(ip).split(" ");
				switch (sar[0]) {
				case "snd":
					snd.add(value(sar[1], regs));
					break;
				case "set":
					regs.put(sar[1], value(sar[2], regs));
					break;
				case "add":
					regs.put(sar[1], value(sar[1], regs) + value(sar[2], regs));
					break;
				case "mul":
					regs.put(sar[1], value(sar[1], regs) * value(sar[2], regs));
					break;
				case "mod":
					regs.put(sar[1], value(sar[1], regs) % value(sar[2], regs));
					break;
				case "rcv":
					if (rcv.isEmpty()) {
						return;
					} else {
						count++;
						regs.put(sar[1], rcv.get(0));
						rcv.remove(0);
					}
					break;
				case "jgz":
					if (value(sar[1], regs) > 0) {
						ip += value(sar[2], regs);
						ip--;
					}
					break;
				default:
					System.err.println("Unknown op:_" + Arrays.toString(sar));
					break;
				}
				ip++;
			}
		}

	}

	public static long value(String adr, Map<String, Long> regs) {
		try {
			return Long.valueOf(adr);
		} catch (Exception e) {
		}
		return regs.getOrDefault(adr, 0l);
	}

}
