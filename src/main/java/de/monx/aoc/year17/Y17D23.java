package de.monx.aoc.year17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;
import lombok.Data;

public class Y17D23 extends Day {
	List<String> in = getInputList();

	@Override
	public Object part1() {
		Program a = new Program();
		a.run(in);
		return a.mults;
	}

	@Override
	public Object part2() {
		int b = Integer.valueOf("10" + in.get(0).split(" ")[2] + "00");
		int c = b + 17000;
		int h = 0;
		initPrimes(c);
		Set<Integer> nonPrim = new HashSet<>();
		for (int i = b; i <= c; i += 17) {
			if (!isPrime(i)) {
				h++;
				nonPrim.add(i);
			}
		}
		return h;
	}

	boolean isPrime(int nr) {
		for (var p : primes) {
			if (!(p <= Math.sqrt(nr))) {
				break;
			}
			if (nr % p == 0) {
				return false;
			}
		}
		return true;
	}

	List<Integer> primes = new ArrayList<>();

	void initPrimes(int nr) {
		if (primes.isEmpty()) {
			primes.add(2);
			primes.add(3);
		}
		for (int i = 5; i <= Math.sqrt(nr) + 1; i += 2) {
			boolean prime = true;
			for (var prims : primes) {
				if (i % prims == 0) {
					prime = false;
					break;
				}
			}
			if (prime) {
				primes.add(i);
			}
		}
	}

	@Data
	static class Program {
		Map<String, Long> regs = new HashMap<>();
		int ip = 0;
		int mults = 0;

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
				case "sub":
					regs.put(sar[1], value(sar[1], regs) - value(sar[2], regs));
					break;
				case "mul":
					mults++;
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
				case "jnz":
					if (value(sar[1], regs) != 0) {
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
