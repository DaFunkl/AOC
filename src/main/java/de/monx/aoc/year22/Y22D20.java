package de.monx.aoc.year22;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.monx.aoc.util.Day;
import de.monx.aoc.util.Util;

public class Y22D20 extends Day {
	long[] in = getInputList().stream().mapToLong(Long::valueOf).toArray();

	@Override
	public Object part1() {
		Map<Integer, Integer> map = new HashMap<>();
		long[] ret = sim(in, in, map);
		int zIdx = map.get(-1);

		System.out.println(zIdx);
		long num1 = ret[(zIdx + 1000) % in.length];
		long num2 = ret[(zIdx + 2000) % in.length];
		long num3 = ret[(zIdx + 3000) % in.length];
		return num1 + num2 + num3;
	}

	@Override
	public Object part2() {
		Map<Integer, Integer> map = new HashMap<>();
		long[] instr = Arrays.stream(in).map(x -> x * 811589153).toArray();
		long[] arr = Arrays.copyOf(instr, instr.length);

		for (int i = 0; i < 10; i++) {
			System.out.println("Step: " + (i + 1));
			arr = sim(instr, arr, map);
		}
		int zIdx = map.get(-1);
		System.out.println(zIdx);
		long num1 = instr[(zIdx + 1000) % in.length];
		long num2 = instr[(zIdx + 2000) % in.length];
		long num3 = instr[(zIdx + 3000) % in.length];
		return num1 + num2 + num3;
	}

	long[] sim(long[] instr, long[] arr, Map<Integer, Integer> map) {
		int[][] moves = new int[in.length][2];
		long mul = instr.length - 1;

		for (int i = 0; i < instr.length; i++) {
			map.putIfAbsent(i, i);
			long start = map.get(i);
			for (int j = 0; j < i; j++) {
				int a = moves[j][0];
				int b = moves[j][1];
				if (a < b && a < start && start <= b) {
					start--;
				} else if (b < a && b <= start && start < a) {
					start++;
				}
			}
			long end = start + instr[i];

			if (instr[i] < 0 && end <= 0) {
				end = end + ((1 + (-end / mul)) * mul);
				if (end == 0) {
					end = mul;
				}
			}

			if (end >= instr.length) {
				end = end - ((end / mul) * mul);
				if (end <= 0) {
					end = mul;
				}
			}

			moves[i][0] = (int) start;
			moves[i][1] = (int) end;
		}

		long[] ret = new long[instr.length];
		for (int i = 0; i < instr.length; i++) {
			int pos = moves[i][1];
			for (int j = i + 1; j < instr.length; j++) {
				int a = moves[j][0];
				int b = moves[j][1];
				if (a < b && a < pos && pos <= b) {
					pos--;
				} else if (b < a && b <= pos && pos < a) {
					pos++;
				}
			}
			if (instr[i] == 0) {
				map.put(-1, pos);
			}
			map.put(i, pos);
			ret[pos] = instr[i];
		}
		return ret;
	}
}
