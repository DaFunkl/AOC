package de.monx.aoc.year24;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D17 extends Day {
//	Register A: 729
//	Register B: 0
//	Register C: 0
//
//	Program: 0,1,5,4,3,0

	List<String> in = getInputList();
	long[] regs = { //
			Integer.valueOf(in.get(0).split(" ")[2]), //
			Integer.valueOf(in.get(1).split(" ")[2]), //
			Integer.valueOf(in.get(2).split(" ")[2]) //
	};
	long[] prog = Arrays.stream(in.get(4).split(" ")[1].split(",")).mapToLong(Long::valueOf).toArray();
	static final int IP = 0;
	static final int A = 1;
	static final int B = 2;
	static final int C = 3;
	String[] out = { "", "" };

	@Override
	public Object part1() {
		long[] regs = { 0, this.regs[0], this.regs[1], this.regs[2] };
		while (regs[IP] < prog.length) {
			regs = operate(regs, prog[(int) regs[IP]], prog[(int) (regs[IP] + 1)], 0);
			regs[IP] += 2;
		}
		return out[0];
	}

	@Override
	public Object part2() {
		long i = 873000000000l;
		out[0] = Arrays.toString(prog).replace("[", "").replace("]", "").replace(", ", ",").trim();
		int maxL = 0;
		long mi = 0;
		String mStr = "";
		for (; i < Long.MAX_VALUE; i++) {
			if (i == this.regs[1]) {
				continue;
			}
			if (i % 1_000_000_000 == 0) {
				System.out.println("i -> " + i + " | " + out[1] + "|| " + mi + " | " + maxL + " | " + mStr);
			}
			out[1] = "";
			long[] regs = { 0, i, this.regs[1], this.regs[2] };
			while (regs[IP] < prog.length && out[0].startsWith(out[1])) {
				regs = operate(regs, prog[(int) regs[IP]], prog[(int) (regs[IP] + 1)], 1);
				regs[IP] += 2;
			}
			if (out[1].length() >= maxL) {
				mi = i;
				maxL = out[1].length();
				mStr = out[1];
				System.out.println("i -> " + i + " | " + out[1] + "|| " + mi + " | " + maxL + " | " + mStr);
			}
			if (out[0].equals(out[1])) {
				System.out.println("============================");
				System.out.println(out[0]);
				System.out.println(out[1]);
				return i;
			}
		}
		System.out.println("----------------------");
		System.out.println(out[0]);
		System.out.println(out[1]);
		return i;
	}

	long[] operate(long[] regs, long op, long lo, int outIdx) {
//		System.out.println("op: " + op + ", lo: " + lo + " regs: " + Arrays.toString(regs));
		long cmb = lo < 4 ? lo : lo < 7 ? regs[(int) (lo - 3)] : -1; // & 7;
		switch (((int) op)) {
		case 0: // adv
			regs[A] /= (cmb < 1 ? 1 : 2 << (cmb - 1));
			break;
		case 1: // bxl
			regs[B] ^= lo;
			break;
		case 2: // bst
			regs[B] = cmb % 8;
			break;
		case 3: // jnz
			if (0 != regs[A]) {
				regs[IP] = lo - 2;
			}
			break;
		case 4: // bxc
			regs[B] ^= regs[C];
			break;
		case 5: // out
			if (!out[outIdx].isBlank()) {
				out[outIdx] += ",";
			}
			out[outIdx] += cmb & 7;
			break;
		case 6: // bdv
			regs[B] = regs[A] / (cmb < 1 ? 1 : 2 << (cmb - 1));
			break;
		case 7: // cdv
			regs[C] = regs[A] / (cmb < 1 ? 1 : 2 << (cmb - 1));
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + op);
		}
		return regs;
	}

	void test() {
		int[] regs = { 0, 0, 0, 0 };
		String ret = "";
		while (regs[A] != 0) {
			regs[B] = regs[A] % 8;
			regs[B] ^= 1;
			regs[C] = regs[A] / (regs[B] < 1 ? 1 : 2 << (regs[B] - 1));
			regs[B] ^= 5;
			regs[B] ^= regs[C];
			regs[A] /= 8;
			regs[B] = regs[A] % 8;
			ret += regs[B] & 7;
		}
		System.out.println(ret);
	}
}
