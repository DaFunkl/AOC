package de.monx.aoc.year17;

import java.util.ArrayList;
import java.util.List;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.common.LinkList;

public class Y17D10 extends Day {

	static final int _LEN = 255;
//	static final int _LEN = 4;

	@Override
	public Object part1() {
		List<Integer> in = new ArrayList<>();
		for (var s : getInputString().split(",")) {
			in.add(Integer.valueOf(s.trim()));
		}
		var arr = solve(in, new int[][] { init(_LEN), { 0, 0 } });
		return arr[0][0] * arr[0][1];
	}

	@Override
	public Object part2() {
		List<Integer> in = new ArrayList<>();
		for (var c : getInputString().toCharArray()) {
			in.add((int) c);
		}
		in.add(17);
		in.add(31);
		in.add(73);
		in.add(47);
		in.add(23);

		int[][] state = new int[][] { init(_LEN), { 0, 0 } };
		for (int i = 0; i < 64; i++) {
			state = solve(in, state);
		}
		String ret = "";
		for (int i = 0; i < 16; i++) {
			int x = state[0][i * 16];
			for (int j = 1; j < 16; j++) {
				x ^= state[0][i * 16 + j];
			}
			String hx = Integer.toHexString(x);
			while (hx.length() < 2) {
				hx = "0" + hx;
			}
			ret += hx;
		}
		return ret;
	}

	static final int _CP = 0;
	static final int _SKIP = 1;

	int[][] solve(List<Integer> in, int[][] state) {

		for (int len : in) {
			int[] t = new int[len];
			for (int i = 0; i < len; i++) {
				t[len - i - 1] = state[0][(i + state[1][_CP]) % state[0].length];
			}
			for (int i = 0; i < len; i++) {
				state[0][(i + state[1][_CP]) % state[0].length] = t[i];
			}
			state[1][_CP] = (state[1][_CP] + len + state[1][_SKIP]) % state[0].length;
			state[1][_SKIP]++;
		}
		return state;
	}

	int[] init(int amt) {
		int[] ret = new int[amt + 1];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = i;
		}
		return ret;
	}

	LinkList<Integer> initLL(int amt) {
		LinkList<Integer> root = new LinkList<Integer>(0);
		LinkList<Integer> cur = root;
		for (int i = 1; i <= amt; i++) {
			cur = cur.add(i);
		}
		cur.append(root);
		return root;
	}

}
