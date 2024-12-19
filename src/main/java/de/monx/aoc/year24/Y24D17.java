package de.monx.aoc.year24;

import java.util.Arrays;
import java.util.List;

import de.monx.aoc.util.Day;

public class Y24D17 extends Day {

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
		return run(prog, regs[0]);
	}

	@Override
	public Object part2() {
		return fun(prog, prog);
	}

	long fun(long[] prog, long[] target) {
		long aStart = target.length == 1 ? 0 : 8 * fun(prog, Arrays.stream(target).skip(1).toArray());
		String mtch = Arrays.toString(target).replace("[", "").replace("]", "").replace(" ", "").trim();
		String rstr = run(prog, aStart);
		while (!rstr.equals(mtch)) {
			aStart++;
			rstr = run(prog, aStart);
		}

		return aStart;
	}

	String run(long[] prog, long aStart) {
		out[0] = "";
		long[] regs = { 0, aStart, this.regs[1], this.regs[2] };
		while (regs[IP] < prog.length) {
			regs = operate(regs, prog[(int) regs[IP]], prog[(int) (regs[IP] + 1)], 0);
			regs[IP] += 2;
		}
		return out[0];
	}

	long[] operate(long[] regs, long op, long lo, int outIdx) {
//		System.out.println("op: " + op + ", lo: " + lo + " regs: " + Arrays.toString(regs));
		long cmb = lo < 4 ? lo : lo < 7 ? regs[(int) (lo - 3)] : -1; // & 7;
		switch (((int) op)) {
		case 0: // adv
			regs[A] >>= cmb;
			break;
		case 1: // bxl
			regs[B] = (regs[B] ^ lo) & 7;
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
			regs[B] = (regs[B] ^ regs[C]) & 7;
			break;
		case 5: // out
			if (!out[outIdx].isBlank()) {
				out[outIdx] += ",";
			}
			out[outIdx] += cmb & 7;
			break;
		case 6: // bdv
			regs[B] = regs[A] >> cmb;
			break;
		case 7: // cdv
			regs[C] = regs[A] >> cmb;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + op);
		}
		return regs;
	}

}
