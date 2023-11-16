package de.monx.aoc.year18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.monx.aoc.util.Day;

public class Y18D16 extends Day {

	List<String> in = getInputList();
	Map<Integer, List<int[][]>> bma = new HashMap<>();
	List<int[]> prog = new ArrayList<>();

	static final int ADDR = 1;
	static final int ADDI = 2;
	static final int MULR = 3;
	static final int MULI = 4;
	static final int BANR = 5;
	static final int BANI = 6;
	static final int BORR = 7;
	static final int BORI = 8;
	static final int SETR = 9;
	static final int SETI = 10;
	static final int GTIR = 11;
	static final int GTRI = 12;
	static final int GTRR = 13;
	static final int EQIR = 14;
	static final int EQRI = 15;
	static final int EQRR = 16;

	Map<Integer, Set<Integer>> om = new HashMap<>();

	@Override
	public Object part1() {
		init();
		int amt = 0;
		for (int i = 0; i < 16; i++) {
			for (var t : bma.get(i)) {
				int works = 0;
				for (int j = 0; j < 16; j++) {
					int[] pre = t[0];
					int[] aft = t[2];
					int[] opr = Arrays.copyOf(t[1], 4);
					opr[0] = j;
					var ret = operate(pre, opr);
					if (ret[0] == aft[0] && ret[1] == aft[1] && ret[2] == aft[2] && ret[3] == aft[3]) {
						works++;
					}
				}
				if (works >= 3) {
					amt++;
				}
			}
		}
		return amt;
	}

	@Override
	public Object part2() {
		int[] sol = fetchMapping();
		int[] regs = new int[4];
		for (var line : prog) {
			line[0] = sol[line[0]];
			regs = operate(regs, line);
		}
		return regs[0];
	}

	int[] fetchMapping() {
		int[] sol = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
		Map<Integer, Set<Integer>> pos = new HashMap<>();
		Set<Integer> nums = new HashSet<>();
		for (int i = 0; i < 16; i++) {
			nums.add(i);
			pos.put(i, new HashSet<>());
			for (int j = 0; j < 16; j++) {
				pos.get(i).add(j);
			}
		}
		while (!nums.isEmpty()) {
			for (int i = 0; i < 16; i++) {
				if (sol[i] != -1) {
					continue;
				}
				for (var t : bma.get(i)) {
					for (var j : pos.get(i)) {
						int[] pre = t[0];
						int[] aft = t[2];
						int[] opr = Arrays.copyOf(t[1], 4);
						opr[0] = j;
						var ret = operate(pre, opr);
						if (!(ret[0] == aft[0] && ret[1] == aft[1] && ret[2] == aft[2] && ret[3] == aft[3])) {
							pos.get(i).remove(j);
							break;
						}
						if (pos.get(i).size() <= 1) {
							int s = -1;
							for (var x : pos.get(i)) {
								s = x;
								break;
							}
							nums.remove(s);
							sol[i] = s;
							for (int k = 0; k < 16; k++) {
								if (k == i) {
									continue;
								}
								pos.get(k).remove(s);
							}
							break;
						}
					}
					if (sol[i] != -1) {
						break;
					}

				}
			}
		}
		return sol;
	}

	int[] operate(int[] regs, int[] op) {
		int[] ret = Arrays.copyOf(regs, 4);
		switch (op[0]) {
		case 0: // addr
			ret[op[3]] = ret[op[1]] + ret[op[2]];
			break;
		case 1: // addi
			ret[op[3]] = ret[op[1]] + op[2];
			break;
		case 2: // mulr
			ret[op[3]] = ret[op[1]] * ret[op[2]];
			break;
		case 3: // muli
			ret[op[3]] = ret[op[1]] * op[2];
			break;
		case 4: // banr
			ret[op[3]] = ret[op[1]] & ret[op[2]];
			break;
		case 5: // bani
			ret[op[3]] = ret[op[1]] & op[2];
			break;
		case 6: // borr
			ret[op[3]] = ret[op[1]] | ret[op[2]];
			break;
		case 7: // bori
			ret[op[3]] = ret[op[1]] | op[2];
			break;
		case 8: // setr
			ret[op[3]] = ret[op[1]];
			break;
		case 9: // seti
			ret[op[3]] = op[1];
			break;
		case 10: // gtir
			ret[op[3]] = op[1] > ret[op[2]] ? 1 : 0;
			break;
		case 11: // gtri
			ret[op[3]] = ret[op[1]] > op[2] ? 1 : 0;
			break;
		case 12: // gtrr
			ret[op[3]] = ret[op[1]] > ret[op[2]] ? 1 : 0;
			break;
		case 13: // eqir
			ret[op[3]] = op[1] == ret[op[2]] ? 1 : 0;
			break;
		case 14: // eqri
			ret[op[3]] = ret[op[1]] == op[2] ? 1 : 0;
			break;
		case 15: // eqrr
			ret[op[3]] = ret[op[1]] == ret[op[2]] ? 1 : 0;
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + op[0]); // eqrr
		}
		return ret;
	}

	void init() {
		int state = 0;
		int[][] mat = new int[3][4];
		for (var str : in) {
			if (str.startsWith("B")) {
				state = 1;
				var spl = str.split(": ")[1].replace("[", "").replace("]", "").replace(",", "").split(" ");
				mat[0][0] = Integer.valueOf(spl[0]);
				mat[0][1] = Integer.valueOf(spl[1]);
				mat[0][2] = Integer.valueOf(spl[2]);
				mat[0][3] = Integer.valueOf(spl[3]);
			} else if (state == 1) {
				state = 2;
				var spl = str.split(" ");
				mat[1][0] = Integer.valueOf(spl[0]);
				mat[1][1] = Integer.valueOf(spl[1]);
				mat[1][2] = Integer.valueOf(spl[2]);
				mat[1][3] = Integer.valueOf(spl[3]);
			} else if (state == 2) {
				state = 0;
				var spl = str.split(":  ")[1].replace("[", "").replace("]", "").replace(",", "").split(" ");
				mat[2][0] = Integer.valueOf(spl[0]);
				mat[2][1] = Integer.valueOf(spl[1]);
				mat[2][2] = Integer.valueOf(spl[2]);
				mat[2][3] = Integer.valueOf(spl[3]);
				bma.putIfAbsent(mat[1][0], new ArrayList<>());
				bma.get(mat[1][0]).add(mat);
				mat = new int[3][4];
			} else if (str.isEmpty()) {
				state--;
			} else if (state < -1) {
				var spl = str.split(" ");
				prog.add(new int[] { //
						Integer.valueOf(spl[0]), //
						Integer.valueOf(spl[1]), //
						Integer.valueOf(spl[2]), //
						Integer.valueOf(spl[3]), //
				});
			}
		}
	}
}
